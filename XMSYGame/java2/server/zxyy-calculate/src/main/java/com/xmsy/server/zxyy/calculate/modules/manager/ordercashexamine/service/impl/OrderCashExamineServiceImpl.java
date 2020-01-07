package com.xmsy.server.zxyy.calculate.modules.manager.ordercashexamine.service.impl;

import java.math.BigDecimal;
import java.util.Date;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xmsy.common.define.page.PageParam;
import com.xmsy.server.zxyy.calculate.common.exception.RRException;
import com.xmsy.server.zxyy.calculate.common.utils.Constant;
import com.xmsy.server.zxyy.calculate.common.utils.Constant.TransactionDetailType;
import com.xmsy.server.zxyy.calculate.common.utils.DateUtils;
import com.xmsy.server.zxyy.calculate.modules.manager.gamerecord.dao.GameRecordDao;
import com.xmsy.server.zxyy.calculate.modules.manager.ordercashexamine.dao.OrderCashExamineDao;
import com.xmsy.server.zxyy.calculate.modules.manager.ordercashexamine.entity.OrderCashExamineEntity;
import com.xmsy.server.zxyy.calculate.modules.manager.ordercashexamine.service.OrderCashExamineService;
import com.xmsy.server.zxyy.calculate.modules.manager.orderrecharge.entity.OrderRechargeEntity;
import com.xmsy.server.zxyy.calculate.modules.manager.user.dao.UserDao;
import com.xmsy.server.zxyy.calculate.modules.manager.user.entity.UserEntity;
import com.xmsy.server.zxyy.calculate.modules.manager.userhierarchy.dao.UserHierarchyDao;
import com.xmsy.server.zxyy.calculate.modules.manager.userhierarchy.entity.UserHierarchyEntity;
import com.xmsy.server.zxyy.calculate.utils.InviteCode;

import lombok.extern.slf4j.Slf4j;

/**
 * .取款稽查逻辑
 * 
 * @author Administrator
 *
 */
@Slf4j
@Service("orderCashExamineService")
public class OrderCashExamineServiceImpl extends ServiceImpl<OrderCashExamineDao, OrderCashExamineEntity>
		implements OrderCashExamineService {

	@Resource
	private UserHierarchyDao userHierarchyDao;
	@Resource
	private GameRecordDao gameRecordDao;
	@Resource
	private UserDao userDao;

	/**
	 * 涉及步骤: 1.结算上次稽查记录 3.生成新稽查记录 返回本次充值新增的需要打码量
	 */
	@Override
	public BigDecimal saveCashExamine(OrderRechargeEntity orderRechargeEntity, UserEntity user) throws Exception {
		// 本次充值需要满足的打码量
		BigDecimal userNeedBetResult = BigDecimal.ZERO;
		Long hierarchyId = user.getHierarchyId();
		UserHierarchyEntity hierarchyEntity = userHierarchyDao.selectById(hierarchyId);
		if (null == hierarchyEntity) {
			log.error("[saveCashExamine] 用户等级为空 orderRechargeEntity {}", orderRechargeEntity);
			throw new RuntimeException();
		}
		// 结算上次稽查记录
		OrderCashExamineEntity preOrderCashExamineEntity = updateCashExamineForRecharge(user, hierarchyEntity, true);
		OrderCashExamineEntity orderCashExamineEntity = new OrderCashExamineEntity();
		// 充值金额
		orderCashExamineEntity.setRechargeAmount(orderRechargeEntity.getAmount().longValue());
		orderCashExamineEntity.setUserId(orderRechargeEntity.getUserId());
		orderCashExamineEntity.setUserAccount(orderRechargeEntity.getUserAccount());
		orderCashExamineEntity.setOrderNo(orderRechargeEntity.getOrderNo());
		orderCashExamineEntity.setOrderTime(orderRechargeEntity.getRechargeTime());
		// 优惠金额
		BigDecimal discountAmount = orderRechargeEntity.getDiscountAmount();
		orderCashExamineEntity.setHierarchyDiscountAmount(discountAmount);
		// 人工充值没有优惠
		if (TransactionDetailType.ARTIFICIALRECHARGE.getValue() == orderRechargeEntity.getRechargeChannel()) {
			discountAmount = BigDecimal.ZERO;
		}
		// 层级优惠打码倍数
		BigDecimal hierarchyDiscountMultiple = hierarchyEntity.getDiscountMultiple();
		orderCashExamineEntity.setHierarchyDiscountMultiple(hierarchyDiscountMultiple);
		// 层级优惠打码
		BigDecimal hierarchyDiscountBet = discountAmount.multiply(hierarchyEntity.getDiscountMultiple());
		orderCashExamineEntity.setHierarchyDiscountBet(hierarchyDiscountBet);
		// 层级常态打码倍数
		BigDecimal hierarchyNormalMultiple = hierarchyEntity.getRechargeMultiple();
		orderCashExamineEntity.setHierarchyNormalMultiple(hierarchyNormalMultiple);
		// 层级常态打码
		BigDecimal hierarchyNormalBet = new BigDecimal(orderRechargeEntity.getAmount())
				.multiply(hierarchyNormalMultiple);
		orderCashExamineEntity.setHierarchyNormalBet(hierarchyNormalBet);
		// 层级放宽打码比例
		BigDecimal hierarchyRelaxationRate = hierarchyEntity.getRelaxationRate();
		orderCashExamineEntity.setHierarchyRelaxationRate(hierarchyEntity.getRelaxationRate());
		// 层级放宽打码： （层级优惠打码+层级常态打码)*层级放宽打码比例
		BigDecimal hierarchyRelaxationBet = (hierarchyDiscountBet.add(hierarchyNormalBet))
				.multiply(hierarchyRelaxationRate);
		orderCashExamineEntity.setHierarchyRelaxationBet(hierarchyRelaxationBet);
		// 层级行政费率
		orderCashExamineEntity.setHierarchyAdministrativeRate(hierarchyEntity.getAdministrativeRate());
		// 总需要打码： （层级优惠打码+层级常态打码-层级放宽打码)
		BigDecimal userNeedBet = hierarchyDiscountBet.add(hierarchyNormalBet).subtract(hierarchyRelaxationBet);
		userNeedBet = userNeedBet.compareTo(BigDecimal.ZERO) < 0 ? BigDecimal.ZERO : userNeedBet;
		userNeedBetResult = userNeedBet;
		// 金币加余额
		BigDecimal validMoney = new BigDecimal(user.getCoin()).divide(new BigDecimal(Constant.COIN_RATE))
				.setScale(2, BigDecimal.ROUND_DOWN).add(user.getMoney()).add(discountAmount)
				.add(new BigDecimal(orderRechargeEntity.getAmount()));
		// 上条稽查记录
		if (null != preOrderCashExamineEntity) {
			// 重新充值的稽查记录把上次缺少的打码算上，充值的时候只计算充值部分的打码，所以上次稽查剩余的用户余额没算入打码，意味着上次用户剩余的打码可以直接取的（前提是满足这次充值的打码才可以一起取）
			userNeedBet = userNeedBet.add(preOrderCashExamineEntity.getUserNeedBet());
			if (validMoney.compareTo(preOrderCashExamineEntity.getUserValidBet()) > 0) {
				orderCashExamineEntity.setUserValidBet(preOrderCashExamineEntity.getUserValidBet());
			} else {
				orderCashExamineEntity.setUserValidBet(validMoney);
			}
		}
		orderCashExamineEntity.setUserNeedBet(userNeedBet);
		// 用户行政费计算
		orderCashExamineEntity.setHierarchyAdministrativeAmount(
				orderCashExamineEntity.getHierarchyAdministrativeRate().multiply(validMoney));
		// 用户可兑换余额：余额+充值金额+充值优惠金额+用户金币等价的余额
		orderCashExamineEntity.setUserMoney(validMoney);
		baseMapper.insert(orderCashExamineEntity);
		return userNeedBetResult;
	}

	@Override
	public Page<OrderCashExamineEntity> findOrderCashExamines(PageParam pageParam,
			OrderCashExamineEntity orderCashExamineEntity, String startTime, String endTime) {
		Page<OrderCashExamineEntity> page = new Page<>(pageParam.getPage(), pageParam.getLimit());// 当前页，总条数 构造 page 对象
		return page.setRecords(baseMapper.findOrderCashExamines(orderCashExamineEntity, startTime, endTime));
	}

	@Override
	public OrderCashExamineEntity findRecentOrderCashExamine(Long userId) {
		return baseMapper.findRecentOrderCashExamine(userId);
	}

	/**
	 * . 获取用户有效下注
	 * 
	 * @param userId
	 * @param orderTime
	 * @return
	 */
	private long getUserValidBet(Long userId, Date orderTime) {
		// 查询用户的有效下注
		String startTime = DateUtils.formatTime(orderTime);
		String endTime = DateUtils.formatTime(new Date());
		Long userValidBet = gameRecordDao.getUserValidBet(userId, startTime, endTime);
		return userValidBet == null ? 0 : userValidBet;
	};

	/**
	 * .充值结算上个订单
	 */
	@Transactional
	public OrderCashExamineEntity updateCashExamineForRecharge(UserEntity user, UserHierarchyEntity hierarchyEntity,
			boolean isClose) {
		OrderCashExamineEntity orderCashExamineEntity = null;
		int i = 0;
		while (i < 10) {
			// 上次的稽查记录
			orderCashExamineEntity = baseMapper.findRecentOrderCashExamine(user.getId());
			if (orderCashExamineEntity == null) {
				return null;
			}
			// 用户有效下注
			Long userValidBet = getUserValidBet(user.getId(), orderCashExamineEntity.getUpdateTime());
			if (null == userValidBet || null == hierarchyEntity || null == user) {
				log.error("[updateCashExamineForRecharge] userValidBet {} hierarchyEntity {} user {}", userValidBet,
						hierarchyEntity, user);
				throw new RRException("updateCashExamineForRecharge param null exceprion");
			}
			BigDecimal userValidBetDecimal = new BigDecimal(userValidBet == null ? 0 : userValidBet)
					.divide(new BigDecimal(Constant.COIN_RATE)).add(orderCashExamineEntity.getUserValidBet()); // 用户有效打码
			orderCashExamineEntity.setUserValidBet(userValidBetDecimal);
			BigDecimal money = new BigDecimal(user.getCoin()).divide(new BigDecimal(Constant.COIN_RATE))
					.setScale(2, BigDecimal.ROUND_DOWN).add(user.getMoney());
			orderCashExamineEntity.setUserMoney(money);
			orderCashExamineEntity.setUpdateTime(new Date());
			orderCashExamineEntity.setStatus(isClose);
			if (updateById(orderCashExamineEntity)) {
				OrderCashExamineEntity orderCashExamine = new OrderCashExamineEntity();
				orderCashExamine.setUserValidBet(userValidBetDecimal);
				BigDecimal userNeedBet = orderCashExamineEntity.getUserNeedBet(); // 用户免行政费需要的打码
				orderCashExamine.setUserNeedBet(userNeedBet);
				// 不满足打码情况
				if (userValidBetDecimal.compareTo(userNeedBet) < 0) {
					// 用户缺少的打码
					BigDecimal userLackBet = userNeedBet.subtract(userValidBetDecimal);
					if (userValidBetDecimal.compareTo(userNeedBet.multiply(hierarchyEntity.getBetRate())) < 0) {
						// 用户打码数量不满足用户下注阈值的时候，缺少的打码数量为应该完成打码的数量
						userNeedBet = userLackBet;
					} else {
						// 用户打码数量满足用户下注阈值的时候，缺少的打码数量为:用户余额和未满足的打码中取小的
						userNeedBet = userLackBet.compareTo(money) < 0 ? userLackBet : money;
					}
					orderCashExamine.setUserNeedBet(userNeedBet);
				}
				return orderCashExamine;

			}
			i++;
		}
		log.error("[updateCashExamineForRecharge]  失败  orderCashExamineEntity {}, user {} hierarchyEntity {}",
				orderCashExamineEntity, user, hierarchyEntity);
		throw new RRException("结算上次稽查记录失败");
	}

	/**
	 * . 绑定用户信息赠送金币,生成稽查记录
	 */
	@Override
	public boolean bindUserinfoGiftCashExamine(UserEntity user, Long coin) throws Exception {
		UserHierarchyEntity hierarchyEntity = userHierarchyDao.selectById(user.getHierarchyId());
		if (null == hierarchyEntity) {
			log.error("[bindUserinfoGiftCashExamine] 用户等级为空 user {}", user);
			throw new RuntimeException();
		}
		// 赠送的金转金额
		BigDecimal amount = new BigDecimal(coin).divide(new BigDecimal(Constant.COIN_RATE));
		// 结算上次稽查记录
		OrderCashExamineEntity preOrderCashExamineEntity = updateCashExamineForRecharge(user, hierarchyEntity, false);
		OrderCashExamineEntity orderCashExamineEntity = new OrderCashExamineEntity();
		// 充值金额
		orderCashExamineEntity.setRechargeAmount(amount.longValue());
		orderCashExamineEntity.setUserId(user.getId());
		orderCashExamineEntity.setUserAccount(user.getAccount());
		orderCashExamineEntity.setOrderNo(System.nanoTime() + InviteCode.create());
		orderCashExamineEntity.setOrderTime(new Date());
		// 优惠金额
		BigDecimal discountAmount = BigDecimal.ZERO;
		orderCashExamineEntity.setHierarchyDiscountAmount(discountAmount);
		// 层级优惠打码倍数
		BigDecimal hierarchyDiscountMultiple = hierarchyEntity.getDiscountMultiple();
		orderCashExamineEntity.setHierarchyDiscountMultiple(hierarchyDiscountMultiple);
		// 层级优惠打码
		BigDecimal hierarchyDiscountBet = discountAmount.multiply(hierarchyEntity.getDiscountMultiple());
		orderCashExamineEntity.setHierarchyDiscountBet(hierarchyDiscountBet);
		// 层级常态打码倍数
		BigDecimal hierarchyNormalMultiple = hierarchyEntity.getRechargeMultiple();
		orderCashExamineEntity.setHierarchyNormalMultiple(hierarchyNormalMultiple);
		// 层级常态打码
		BigDecimal hierarchyNormalBet = amount.multiply(hierarchyEntity.getDiscountMultiple());
		orderCashExamineEntity.setHierarchyNormalBet(hierarchyNormalBet);
		// 层级放宽打码比例
		BigDecimal hierarchyRelaxationRate = hierarchyEntity.getRelaxationRate();
		orderCashExamineEntity.setHierarchyRelaxationRate(hierarchyEntity.getRelaxationRate());
		// 层级放宽打码： （层级优惠打码+层级常态打码)*层级放宽打码比例
		BigDecimal hierarchyRelaxationBet = (hierarchyDiscountBet.add(hierarchyNormalBet))
				.multiply(hierarchyRelaxationRate);
		orderCashExamineEntity.setHierarchyRelaxationBet(hierarchyRelaxationBet);
		// 层级行政费率
		orderCashExamineEntity.setHierarchyAdministrativeRate(hierarchyEntity.getAdministrativeRate());
		// 总需要打码： （层级优惠打码+层级常态打码+上次缺少的打码量-上次剩余打码-层级放宽打码)
		BigDecimal userNeedBet = hierarchyDiscountBet.add(hierarchyNormalBet).subtract(hierarchyRelaxationBet);
		userNeedBet = userNeedBet.compareTo(BigDecimal.ZERO) < 0 ? BigDecimal.ZERO : userNeedBet;
		// 金币加余额
		BigDecimal validMoney = amount.add(user.getMoney()).add(discountAmount);
		// 上条稽查记录
		if (null != preOrderCashExamineEntity) {
			return baseMapper.updateOrderCashExamineUserNeedBet(preOrderCashExamineEntity.getId(), userNeedBet);
		} else {
			Long userValidBet = getUserValidBet(user.getId(), user.getCreateTime());
			BigDecimal userValidBetDecimal = new BigDecimal(userValidBet == null ? 0 : userValidBet)
					.divide(new BigDecimal(Constant.COIN_RATE)); // 用户有效打码
			orderCashExamineEntity.setUserValidBet(userValidBetDecimal);
		}
		orderCashExamineEntity.setUserNeedBet(userNeedBet);
		// 用户行政费计算
		orderCashExamineEntity.setHierarchyAdministrativeAmount(
				orderCashExamineEntity.getHierarchyAdministrativeRate().multiply(validMoney));
		// 用户可兑换余额：余额+充值金额+充值优惠金额+用户金币等价的余额
		orderCashExamineEntity.setUserMoney(validMoney);
		return baseMapper.insert(orderCashExamineEntity) > 0;
	}

	// 用户取款查看稽查记录，更新有效打码返回最新稽查情况
	@Override
	public OrderCashExamineEntity updateCashExamineForQuery(UserEntity user) {
		OrderCashExamineEntity orderCashExamineEntity = null;
		UserHierarchyEntity hierarchyEntity = userHierarchyDao.selectById(user.getHierarchyId());
		if (null == hierarchyEntity) {
			log.error("[updateCashExamineForQuery] 用户等级为空 user {}", user);
			throw new RuntimeException();
		}
		int i = 0;
		while (i < 10) {
			// 上次的稽查记录
			orderCashExamineEntity = baseMapper.findRecentOrderCashExamine(user.getId());
			if (orderCashExamineEntity == null) {
				return null;
			}
			// 用户有效下注
			Long userValidBet = getUserValidBet(user.getId(), orderCashExamineEntity.getUpdateTime());
			if (null == userValidBet || null == hierarchyEntity || null == user) {
				log.error("[updateCashExamineForQuery] userValidBet {} hierarchyEntity {} user {}", userValidBet,
						hierarchyEntity, user);
				throw new RRException("updateCashExamineForQuery param null exceprion");
			}
			BigDecimal userValidBetDecimal = new BigDecimal(userValidBet == null ? 0 : userValidBet)
					.divide(new BigDecimal(Constant.COIN_RATE)).add(orderCashExamineEntity.getUserValidBet()); // 用户有效打码
			orderCashExamineEntity.setUserValidBet(userValidBetDecimal);
			BigDecimal money = new BigDecimal(user.getCoin()).divide(new BigDecimal(Constant.COIN_RATE))
					.setScale(2, BigDecimal.ROUND_DOWN).add(user.getMoney());
			orderCashExamineEntity.setUserMoney(money);
			orderCashExamineEntity.setUpdateTime(new Date());
			BigDecimal userNeedBet = orderCashExamineEntity.getUserNeedBet(); // 用户免行政费需要的打码
			if (userValidBetDecimal.compareTo(userNeedBet) < 0) {
				orderCashExamineEntity.setHierarchyAdministrativeAmount(
						orderCashExamineEntity.getHierarchyAdministrativeRate().multiply(money));
			} else {
				orderCashExamineEntity.setHierarchyAdministrativeAmount(BigDecimal.ZERO);
			}
			if (updateById(orderCashExamineEntity)) {
				return orderCashExamineEntity;
			}
			i++;
		}
		log.error("[updateCashExamineForQuery]  失败  orderCashExamineEntity {}, user {} hierarchyEntity {}",
				orderCashExamineEntity, user, hierarchyEntity);
		throw new RRException("结算上次稽查记录失败");
	}

	@Override
	public OrderCashExamineEntity updateCashExamineForTake(UserEntity user, BigDecimal takeMoney) {
		OrderCashExamineEntity orderCashExamineEntity = null;
		UserHierarchyEntity hierarchyEntity = userHierarchyDao.selectById(user.getHierarchyId());
		if (null == hierarchyEntity) {
			log.error("[updateCashExamineForQuery] 用户等级为空 user {}", user);
			throw new RuntimeException();
		}
		int i = 0;
		while (i < 10) {
			// 上次的稽查记录
			orderCashExamineEntity = baseMapper.findRecentOrderCashExamine(user.getId());
			if (orderCashExamineEntity == null) {
				return null;
			}
			// 用户有效下注
			Long userValidBet = getUserValidBet(user.getId(), orderCashExamineEntity.getUpdateTime());
			if (null == userValidBet || null == hierarchyEntity || null == user) {
				log.error("[updateCashExamineForTake] userValidBet {} hierarchyEntity {} user {}", userValidBet,
						hierarchyEntity, user);
				throw new RRException("updateCashExamineForTake param null exceprion");
			}
			BigDecimal userValidBetDecimal = new BigDecimal(userValidBet == null ? 0 : userValidBet)
					.divide(new BigDecimal(Constant.COIN_RATE)).add(orderCashExamineEntity.getUserValidBet()); // 用户有效打码
			orderCashExamineEntity.setUserValidBet(userValidBetDecimal);
			BigDecimal money = new BigDecimal(user.getCoin()).divide(new BigDecimal(Constant.COIN_RATE))
					.setScale(2, BigDecimal.ROUND_DOWN).add(user.getMoney());
			orderCashExamineEntity.setUserMoney(money);
			orderCashExamineEntity.setUpdateTime(new Date());
			BigDecimal userNeedBet = orderCashExamineEntity.getUserNeedBet(); // 用户免行政费需要的打码
			if (userValidBetDecimal.compareTo(userNeedBet) < 0) {
				orderCashExamineEntity.setUserNeedBet(BigDecimal.ZERO);
				orderCashExamineEntity.setUserValidBet(BigDecimal.ZERO);
				orderCashExamineEntity.setDeductionNeedBet(userNeedBet);
				orderCashExamineEntity.setDeductionValidBet(userValidBetDecimal);
				orderCashExamineEntity.setHierarchyAdministrativeAmount(BigDecimal.ZERO);
				orderCashExamineEntity.setDeductionAdministrative(
						orderCashExamineEntity.getHierarchyAdministrativeRate().multiply(money));
			} else {
				orderCashExamineEntity.setUserNeedBet(BigDecimal.ZERO);
				orderCashExamineEntity.setDeductionNeedBet(userNeedBet);
				orderCashExamineEntity.setUserValidBet(BigDecimal.ZERO);
				orderCashExamineEntity.setDeductionValidBet(userValidBetDecimal);
				orderCashExamineEntity.setHierarchyAdministrativeAmount(BigDecimal.ZERO);
				orderCashExamineEntity.setDeductionAdministrative(BigDecimal.ZERO);
			}
			if (updateById(orderCashExamineEntity)) {
				return orderCashExamineEntity;
			}
			i++;
		}
		log.error("[updateCashExamineForTake]  失败  orderCashExamineEntity {}, user {} hierarchyEntity {}",
				orderCashExamineEntity, user, hierarchyEntity);
		throw new RRException("结算上次稽查记录失败");
	}
}

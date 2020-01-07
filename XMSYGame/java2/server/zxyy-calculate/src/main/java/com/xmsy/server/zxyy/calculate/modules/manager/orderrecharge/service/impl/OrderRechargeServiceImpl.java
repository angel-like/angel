package com.xmsy.server.zxyy.calculate.modules.manager.orderrecharge.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xmsy.server.zxyy.calculate.common.exception.RRException;
import com.xmsy.server.zxyy.calculate.common.utils.Constant;
import com.xmsy.server.zxyy.calculate.common.utils.Constant.OrderStatus;
import com.xmsy.server.zxyy.calculate.common.utils.DateUtils;
import com.xmsy.server.zxyy.calculate.common.utils.MathUtil;
import com.xmsy.server.zxyy.calculate.common.utils.OrderNoUtil;
import com.xmsy.server.zxyy.calculate.modules.manager.ordercashexamine.dao.OrderCashExamineDao;
import com.xmsy.server.zxyy.calculate.modules.manager.ordercashexamine.entity.OrderCashExamineEntity;
import com.xmsy.server.zxyy.calculate.modules.manager.ordercashexamine.service.OrderCashExamineService;
import com.xmsy.server.zxyy.calculate.modules.manager.orderpreferential.entity.OrderPreferentialEntity;
import com.xmsy.server.zxyy.calculate.modules.manager.orderpreferential.service.OrderPreferentialService;
import com.xmsy.server.zxyy.calculate.modules.manager.orderrecharge.dao.OrderRechargeDao;
import com.xmsy.server.zxyy.calculate.modules.manager.orderrecharge.entity.OrderRechargeEntity;
import com.xmsy.server.zxyy.calculate.modules.manager.orderrecharge.service.OrderRechargeService;
import com.xmsy.server.zxyy.calculate.modules.manager.user.entity.UserEntity;
import com.xmsy.server.zxyy.calculate.modules.manager.user.service.UserService;
import com.xmsy.server.zxyy.calculate.modules.manager.usertransactionrecord.entity.UserTransactionRecordEntity;
import com.xmsy.server.zxyy.calculate.modules.manager.usertransactionrecord.service.UserTransactionRecordService;
import com.xmsy.server.zxyy.calculate.modules.manager.uservip.entity.UserVipEntity;
import com.xmsy.server.zxyy.calculate.modules.manager.uservip.service.UserVipService;

import lombok.extern.slf4j.Slf4j;

/**
 * .充值订单处理逻辑
 *
 * @author Administrator
 *
 */
@Slf4j
@Transactional
@Service("orderRechargeService")
public class OrderRechargeServiceImpl extends ServiceImpl<OrderRechargeDao, OrderRechargeEntity>
		implements OrderRechargeService {
	@Autowired
	private UserService userService;

	@Autowired
	private OrderCashExamineService orderCashExamineService;

	@Autowired
	private UserTransactionRecordService userTransactionRecordService;

	@Autowired
	private OrderPreferentialService orderPreferentialService;
	@Autowired
	private OrderCashExamineDao orderCashExamineDao;
	@Autowired
	private UserVipService userVipService;

	@Override
	public Integer updateOrderRecharge(String merchantOrderNo, String orderNo, BigDecimal userNeedBet,
			String rechargeTime) {
		log.info("[updateOrderRecharge] merchantOrderNo {},orderNo {},userNeedBet {},rechargeTime {}", merchantOrderNo,
				orderNo, userNeedBet, rechargeTime);
		return baseMapper.updateOrderRecharge(merchantOrderNo, orderNo, userNeedBet, rechargeTime);
	}

	@Override
	@Transactional
	public void saveAdministratorRecharge(OrderRechargeEntity orderRecharge, int transactionType, UserEntity user)
			throws Exception {
		// 保存订单
		orderRecharge.setOrderNo(OrderNoUtil.getOrderNo());
		BigDecimal userNeedBet = orderCashExamineService.saveCashExamine(orderRecharge, user);
		orderRecharge.setUserNeedBet(userNeedBet);
		this.baseMapper.insert(orderRecharge);
		saveOrderRechargeTransactionRecord(orderRecharge, transactionType, user);
	}

	@Override
	public boolean orderRechargeConfirm(OrderRechargeEntity orderRecharge) throws Exception {
		UserEntity user = userService.selectById(orderRecharge.getUserId());
		if (null == user) {
			log.error("[zxyy-calculate]->[orderRechargeConfirm] null == user");
			throw new RuntimeException();
		}
		// 充值优惠计算.
		if (rechargePreferential(orderRecharge, user)) {
			// 如果是首冲,修改首冲状态
			user.setFirstRecharge(true);
			userService.updateUserFirstRecharge(user.getId());
		}
		// 创建存款稽查记录
		BigDecimal userNeedBet = orderCashExamineService.saveCashExamine(orderRecharge, user);
		orderRecharge.setUserNeedBet(userNeedBet);
		BigDecimal discountAmount = orderRecharge.getDiscountAmount();
		if(discountAmount==null){
			discountAmount=BigDecimal.ZERO;
		}
		Integer result = this.baseMapper.updateOrderRechargeByOrderNo(orderRecharge.getMerchantOrderNo(),
				orderRecharge.getOrderNo(), userNeedBet, DateUtils.formatTime(new Date()),discountAmount,orderRecharge.getFristrecharge());
		if (result == null || result == 0) {
			log.info("[orderRechargeConfirm]->updateOrderRecharge 没有该订单，或已经被确认  orderRecharge {}", orderRecharge);
			return false;
		}
		// 添加用户交易记录
		saveOrderRechargeTransactionRecord(orderRecharge, Constant.TransactionType.RECHARGE.getValue(), user);
		return true;
	}

	// 订单充值修改用户账号金额，生成交易记录
	private void saveOrderRechargeTransactionRecord(OrderRechargeEntity orderRecharge, int transactionType,
			UserEntity user) {
		UserTransactionRecordEntity transactionRecord = new UserTransactionRecordEntity();
		transactionRecord.setType(transactionType);
		transactionRecord.setOrderNo(orderRecharge.getOrderNo());
		transactionRecord.setDetailType(orderRecharge.getRechargeChannel().intValue());
		transactionRecord.setFristrecharge(orderRecharge.getFristrecharge());
		transactionRecord.setUserId(orderRecharge.getUserId());
		transactionRecord.setUserAccount(orderRecharge.getUserAccount());
		// 充值金额=充值金额+优惠金额
		transactionRecord.setAmount(MathUtil.getBigDecimal(orderRecharge.getAmount()));
		// 更新用户余额(本来需要更新用户金额，再额度转换，两个动作直接合成一个额度转换直接把冲的金额转成冲金币，但是交易记录两个两条)
		// 充值之后的账户余额=账户余额+充值金额
		transactionRecord.setMoney(user.getMoney());
		transactionRecord.setCommission(user.getCommission());
		transactionRecord.setRemake(orderRecharge.getRemark());
		transactionRecord.setCoin(user.getCoin()+transactionRecord.getAmount().multiply(new BigDecimal(Constant.COIN_RATE)).longValue());
		// 保存交易记录
		userTransactionRecordService.insert(transactionRecord);
		//额度转换交易记录--废弃 adu 2019-11-21
		UserEntity userUpdateParam = new UserEntity();
		userUpdateParam.setId(user.getId());
		userUpdateParam.setCoin(transactionRecord.getAmount().multiply(new BigDecimal(Constant.COIN_RATE)).longValue());
		userService.updateUserWalletByUserId(userUpdateParam);
		//start  保存充值返利账目明细
		//获取优惠金额
		BigDecimal discountAmount = orderRecharge.getDiscountAmount() == null ? BigDecimal.ZERO : orderRecharge.getDiscountAmount();
		//判断优惠金额大于零
		UserTransactionRecordEntity transactionRecord1 = new UserTransactionRecordEntity();
		if (discountAmount.compareTo(BigDecimal.ZERO) > 0) {
			//保存返利记录
			Long coin = transactionRecord.getCoin();
			transactionRecord1 = transactionRecord;
			transactionRecord1.setId(null);
			transactionRecord1.setType(3);
			transactionRecord1.setAmount(discountAmount);
			transactionRecord1.setDetailType(50);
			transactionRecord1.setCoin(coin+transactionRecord1.getAmount().multiply(new BigDecimal(Constant.COIN_RATE)).longValue());
			userTransactionRecordService.insert(transactionRecord1);
			//额度转换交易记录--废弃 adu 2019-11-21
			UserEntity userUpdateParam1 = new UserEntity();
			userUpdateParam1.setId(user.getId());
			userUpdateParam1.setCoin(transactionRecord1.getAmount().multiply(new BigDecimal(Constant.COIN_RATE)).longValue());
			userService.updateUserWalletByUserId(userUpdateParam1);
		}
		//end
		// 充值+额度转换两个动作一起做（只修改一次数据库）
//		userService.rechargeAndChanger(user, transactionRecord.getAmount(),
//				transactionRecord.getAmount().multiply(new BigDecimal(Constant.COIN_RATE)).longValue());
	}

	// 优惠计算
	private boolean rechargePreferential(OrderRechargeEntity rechargeOrder, UserEntity user) {
		// 获取层级优惠
		List<OrderPreferentialEntity> preferentials = orderPreferentialService
				.getPreferentialsByHierarchyId(user.getHierarchyId());
		// 首冲优惠对象
		OrderPreferentialEntity firstRecharge = null;
		// 满送优惠对象
		OrderPreferentialEntity presentRecharge = null;
		for (OrderPreferentialEntity preferential : preferentials) {
			if (preferential.getFirstRecharge()) {
				firstRecharge = preferential;
			} else {
				presentRecharge = preferential;
			}
		}
		if (log.isInfoEnabled()) {
			log.info("[优惠对象] firstRecharge:{} presentRecharge：{}", firstRecharge, presentRecharge);
		}
		BigDecimal preferentialDiscountAmount = BigDecimal.ZERO;
		BigDecimal rechargeAmount = new BigDecimal(rechargeOrder.getAmount());
		if (user.getFirstRecharge()) {
			// 非首冲（不是首冲判断是否符合满送）
			if (rechargeAmount.longValue() > presentRecharge.getRechargeAmount().longValue()) {
				preferentialDiscountAmount = presentRecharge.getGiftProportion()
						.multiply(new BigDecimal(rechargeOrder.getAmount()));
				rechargeOrder.setDiscountId(presentRecharge.getId());
			}
		} else {
			// 首冲
			preferentialDiscountAmount = firstRecharge.getGiftProportion().multiply(rechargeAmount);
			rechargeOrder.setDiscountId(firstRecharge.getId());
			rechargeOrder.setFristrecharge(true);
		}
		// VIP用户优惠
		BigDecimal vipDiscountAmount = BigDecimal.ZERO;
		UserVipEntity userVip = userVipService.selectById(user.getVipId());
		if (null != userVip && null != userVip.getRechargeRate()) {
			vipDiscountAmount = userVip.getRechargeRate().multiply(rechargeAmount);
		}
		BigDecimal totalDiscountAmount = preferentialDiscountAmount.add(vipDiscountAmount);
		if (log.isInfoEnabled()) {
			log.info("[VIP优惠] userVip:{} totalDiscountAmount:{}", firstRecharge, totalDiscountAmount);
		}
		rechargeOrder.setDiscountAmount(totalDiscountAmount);
		return !user.getFirstRecharge();
	}

	@Override
	@Transactional
	public void revokeRecharge(Long rechargeId) {
		OrderRechargeEntity orderRecharge = this.baseMapper.selectById(rechargeId);
		if (orderRecharge == null || orderRecharge.getId() == null
				|| orderRecharge.getStatus() == OrderStatus.REVOKE.getValue()) {
			log.error("[zxyy-calculate]->[orderRechargeConfirm] null == user");
			throw new RuntimeException();
		}
		try {
			orderCancelUpdateCashExamine(orderRecharge.getOrderNo(), orderRecharge.getUserNeedBet());
			userService.revokeMoney(orderRecharge);
			orderRecharge.setStatus(OrderStatus.REVOKE.getValue());
			this.updateById(orderRecharge);
		} catch (Exception e) {
			log.error("[充值订单撤销保存失败] revokeMoney {}", orderRecharge, e);
		}
	}

	/**
	 * 取消人工充值订单，稽查订单修改重试
	 *
	 * @param orderRechargeOrderNo
	 */
	private void orderCancelUpdateCashExamine(String orderRechargeOrderNo, BigDecimal userNeedBet) {
		int i = 0;
		while (i < 10) {
			OrderCashExamineEntity orderCashExamine = new OrderCashExamineEntity();
			orderCashExamine.setOrderNo(orderRechargeOrderNo);
			orderCashExamine = orderCashExamineDao.selectOne(orderCashExamine);
			if (orderCashExamine == null || orderCashExamine.getId() == null) {
				log.error("[zxyy-calculate]->[orderCancelUpdateCashExamine] null == orderCashExamine");
				throw new RuntimeException();
			}
			// 是否结算（0：未审核，1：已审核）
			OrderCashExamineEntity newOrderCashExamine = new OrderCashExamineEntity();
			if (orderCashExamine.getStatus()) {// 1：已审核
				// 新的取款稽查需要扣除已经结算的 总需要打码数
				newOrderCashExamine = orderCashExamineDao.findRecentOrderCashExamine(orderCashExamine.getUserId());
				if (newOrderCashExamine == null || newOrderCashExamine.getId() == null) {
					log.error("[zxyy-calculate]->[orderCancelUpdateCashExamine] null == orderCashExamine");
					throw new RuntimeException();
				}
			} else {// 0：未审核
					// 需要把上一条取款稽查记录修改为未审核
				newOrderCashExamine = orderCashExamine;
			}
			newOrderCashExamine.setUserNeedBet(newOrderCashExamine.getUserNeedBet().subtract(userNeedBet));
			if (newOrderCashExamine.getUserNeedBet().compareTo(BigDecimal.ZERO) < 0) {
				newOrderCashExamine.setUserNeedBet(BigDecimal.ZERO);
			}
			if (orderCashExamineDao.updateById(newOrderCashExamine) > 0) {
				return;
			} else {
				i++;
			}
		}
		log.error("[revokeRecharge] ->orderCancelUpdateCashExamine 失败  orderRechargeOrderNo {}", orderRechargeOrderNo);
		throw new RRException("取消人工充值订单,稽查记录更新失败 ");

	}

	@Override
	public Long getRechargeAmount(Long userId, String startTime, String endTime) {
		return this.baseMapper.getRechargeAmount(userId,startTime, endTime);
	}

}

package com.xmsy.server.zxyy.webhome.modules.manager.orderadministratorrecharge.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.shiro.util.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xmsy.server.zxyy.webhome.common.exception.RRException;
import com.xmsy.server.zxyy.webhome.common.utils.Constant;
import com.xmsy.server.zxyy.webhome.common.utils.Constant.OrderStatus;
import com.xmsy.server.zxyy.webhome.common.utils.Constant.RechargeType;
import com.xmsy.server.zxyy.webhome.common.utils.Constant.TransactionDetailType;
import com.xmsy.server.zxyy.webhome.common.utils.Constant.TransactionType;
import com.xmsy.server.zxyy.webhome.common.utils.ErrorCode;
import com.xmsy.server.zxyy.webhome.common.utils.MathUtil;
import com.xmsy.server.zxyy.webhome.common.utils.OrderNoUtil;
import com.xmsy.server.zxyy.webhome.constant.SysConstant;
import com.xmsy.server.zxyy.webhome.modules.app.event.PushService;
import com.xmsy.server.zxyy.webhome.modules.app.event.userinfo.UserInfoMessage;
import com.xmsy.server.zxyy.webhome.modules.manager.messagemanagement.dao.MessageManagementDao;
import com.xmsy.server.zxyy.webhome.modules.manager.messagemanagement.entity.MessageManagementEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.messageuser.dao.MessageUserDao;
import com.xmsy.server.zxyy.webhome.modules.manager.messageuser.entity.MessageUserEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.orderadministratorrecharge.dao.OrderAdministratorRechargeDao;
import com.xmsy.server.zxyy.webhome.modules.manager.orderadministratorrecharge.entity.OrderAdministratorRechargeEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.orderadministratorrecharge.param.AdminRechargeParam;
import com.xmsy.server.zxyy.webhome.modules.manager.orderadministratorrecharge.service.OrderAdministratorRechargeService;
import com.xmsy.server.zxyy.webhome.modules.manager.ordercashexamine.entity.OrderCashExamineEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.ordercashexamine.service.OrderCashExamineService;
import com.xmsy.server.zxyy.webhome.modules.manager.orderrecharge.entity.OrderRechargeEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.orderrecharge.service.OrderRechargeService;
import com.xmsy.server.zxyy.webhome.modules.manager.ordertakemoney.entity.OrderTakeMoneyEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.user.dao.UserDao;
import com.xmsy.server.zxyy.webhome.modules.manager.user.entity.UserEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.usertransactionrecord.entity.UserTransactionRecordEntity;
import com.xmsy.server.zxyy.webhome.utils.UserStatusVerificationUtil;

import lombok.extern.slf4j.Slf4j;

/**
 * .人工充值逻辑
 * 
 * @author Administrator
 *
 */
@Slf4j
@Transactional
@Service("orderAdministratorRechargeService")
public class OrderAdministratorRechargeServiceImpl
		extends ServiceImpl<OrderAdministratorRechargeDao, OrderAdministratorRechargeEntity>
		implements OrderAdministratorRechargeService {

	@Autowired
	private UserDao userDao;

	@Autowired
	private PushService pushService;

	@Autowired
	private OrderRechargeService orderRechargeService;

	@Autowired
	private MessageManagementDao messageManagementDao;

	@Autowired
	private MessageUserDao messageUserDao;
	
	@Autowired
	private OrderCashExamineService orderCashExamineService;

	@Override
	@Transactional
	public void createAdminRecharge(AdminRechargeParam adminRecharge, String ip, Long sysUserId, String sysUserName) {
		log.info("[人工充值创建订单] createAdminRecharge {}", adminRecharge);
		// 管理员充值订单
		BigDecimal amount = MathUtil.getBigDecimal(adminRecharge.getAmount());
		OrderAdministratorRechargeEntity adminOrder = new OrderAdministratorRechargeEntity();
		adminOrder.setSysUserId(sysUserId);
		adminOrder.setSysUserAccount(sysUserName);
		adminOrder.setOperationType(adminRecharge.getOperationType());
		adminOrder.setDesignated(adminRecharge.isDesignated());
		adminOrder.setOrderNo(OrderNoUtil.getOrderNo());
		adminOrder.setAmount(adminRecharge.getAmount());
		adminOrder.setDiscountAmount(MathUtil.getBigDecimal(adminRecharge.getDiscountAmount()));
		amount=amount.add(adminOrder.getDiscountAmount());
		adminOrder.setRemake(adminRecharge.getRemake());
		// 指定多个账号充值
		if (adminRecharge.getTargetObject() == Constant.AdministratorRechargeType.USER.getValue()) {
			adminOrder.setAccount(adminRecharge.getAccount());
		} else if (adminRecharge.getTargetObject() == Constant.AdministratorRechargeType.HIERARCHY.getValue()) {
			adminOrder.setHierarchyId(adminRecharge.getHierarchyId());
		}
		this.baseMapper.insert(adminOrder);
		// 查询需要充值的会员
		List<UserEntity> userList = new ArrayList<>();
		if (adminRecharge.getTargetObject() == Constant.AdministratorRechargeType.USER.getValue()) {
			userList = userDao.findUserListByAccount(adminRecharge.getAccount().split(","));
		} else if (adminRecharge.getTargetObject() == Constant.AdministratorRechargeType.HIERARCHY.getValue()) {
			userList = userDao.findUserListByHierarchy(adminRecharge.getHierarchyId().split(","));
		}
		if (CollectionUtils.isEmpty(userList)) {
			throw new RRException(ErrorCode.MessagemanagementErrorCode.NOCHOICE_ACCOUNT_ERRO.getErrMsg(),
					ErrorCode.MessagemanagementErrorCode.NOCHOICE_ACCOUNT_ERRO.getCode());
		}
		if (adminRecharge.getTargetObject() == Constant.AdministratorRechargeType.USER.getValue()) {
			String[] accountList = adminRecharge.getAccount().split(",");
			String accountStr = "";// 不存在的账号
			if (accountList.length > userList.size()) {
				for (String account : accountList) {
					for (UserEntity user : userList) {
						if (user.getAccount().equals(account)) {
							break;
						}
						accountStr += "," + account;
					}
				}
				throw new RRException(
						accountStr.replaceFirst(",", "") + ErrorCode.UserErrorCode.USER_IS_NULL.getErrMsg(),
						ErrorCode.UserErrorCode.USER_IS_NULL.getCode());
			}
		}
		// 充值订单总表
		OrderRechargeEntity orderRecharge = new OrderRechargeEntity();
		for (UserEntity user : userList) {
			// 充值信息
			BigDecimal userCoinToMoney = new BigDecimal(user.getCoin()).divide(new BigDecimal(Constant.COIN_RATE))
					.setScale(2, BigDecimal.ROUND_DOWN);
			orderRecharge.setDepositDate(new Date());
			orderRecharge.setAdminOrderNo(adminOrder.getOrderNo());
			orderRecharge.setAmount(adminOrder.getAmount());
			orderRecharge.setRemark(adminOrder.getRemake());
			orderRecharge.setDiscountAmount(adminOrder.getDiscountAmount());
			orderRecharge.setDiscountId(adminRecharge.getDiscountId());
			
			// 用户信息
			orderRecharge.setUserAccount(user.getAccount());
			orderRecharge.setUserId(user.getId());
			orderRecharge.setPreMoney(user.getMoney().add(userCoinToMoney));// 存款时候的余额+金币
			orderRecharge.setFinalMoney(user.getMoney().add(amount).add(userCoinToMoney));// 存款后的余额+金币
			orderRecharge.setHierarchyId(user.getHierarchyId());
			// //其他
			orderRecharge.setRechargeType(RechargeType.ADMIN.getValue());
			orderRecharge.setRechargeChannel(Long.valueOf(TransactionDetailType.ARTIFICIALRECHARGE.getValue()));
			orderRecharge.setRechargeTime(new Date());
			orderRecharge.setFristrecharge(false);
			orderRecharge.setIp(ip);
			orderRecharge.setSysUserAccount(sysUserName);
			orderRecharge.setSysUserId(sysUserId);
			orderRecharge.setStatus(OrderStatus.COMPLETE.getValue());
			// 保存充值订单
			try {
				orderRechargeService.saveAdministratorRecharge(orderRecharge, TransactionType.RECHARGE.getValue(),
						user);
				UserEntity pushMessage = new UserEntity();
				pushMessage.setCoin(amount.multiply(new BigDecimal(Constant.COIN_RATE)).longValue());
				pushMessage.setId(user.getId());
				UserInfoMessage message = new UserInfoMessage(pushMessage, null);
				log.info("[人工充值-推送消息] message {}", message);
				pushService.pushExchange(message);
			} catch (Exception e) {
				log.error("[人工充值订单保存失败] orderRecharge {}", orderRecharge, e);
			}
		}

		if (adminRecharge.getSendMessage()) {// 需要发送站内信
			MessageManagementEntity messageManagement = new MessageManagementEntity();
			messageManagement.setContentType(1);
			messageManagement.setTitle(adminRecharge.getMessageTitle());
			messageManagement.setContent(adminRecharge.getMessageContent());
			messageManagement.setEffectTime(new Date());
			messageManagement.setEnable(true);
			messageManagement.setReadonly(true);
			messageManagement.setTargetObject(adminRecharge.getTargetObject());
			if (adminRecharge.getTargetObject() == 1) {
				messageManagement.setUserAccount(adminRecharge.getAccount());
			} else if (adminRecharge.getTargetObject() == 2) {
				messageManagement.setHierarchyIds(adminRecharge.getHierarchyId());
			}
			// 保存站内信
			messageManagementDao.insertEntityReturnId(messageManagement);
			if (adminRecharge.getTargetObject() == 1) {
				// 指定用户需要保存站内信和会员的关系
				List<MessageUserEntity> messageUserList = new ArrayList<>();
				for (UserEntity user : userList) {
					MessageUserEntity messageUser = new MessageUserEntity();
					messageUser.setMessageId(messageManagement.getId());
					messageUser.setUserAccount(user.getAccount());
					messageUser.setUserId(user.getId());
					messageUser.setStatus(false);
					messageUser.setDeleteMessage(false);
					messageUser.setReceive(true);
					messageUserList.add(messageUser);
				}
				messageUserDao.insertBatch(messageUserList);
			}
		}

	}

	@Override
	public void createAdminTakeMoney(AdminRechargeParam adminRecharge, String ip, Long sysUserId, String sysUserName) {
		log.info("[人工取款创建订单] createAdminRecharge {}", adminRecharge);
		// 管理员充值订单
		BigDecimal amount = MathUtil.getBigDecimal(adminRecharge.getAmount());
		OrderAdministratorRechargeEntity adminOrder = new OrderAdministratorRechargeEntity();
		adminOrder.setSysUserId(sysUserId);
		adminOrder.setSysUserAccount(sysUserName);
		adminOrder.setOperationType(adminRecharge.getOperationType());
		adminOrder.setDesignated(adminRecharge.isDesignated());
		adminOrder.setOrderNo(OrderNoUtil.getOrderNo());
		adminOrder.setAmount(adminRecharge.getAmount());
		adminOrder.setCoin(adminRecharge.getTakeCoin());
		adminOrder.setRemake(adminRecharge.getRemake());
		// 指定多个账号充值
		if (adminRecharge.getTargetObject() == Constant.AdministratorRechargeType.USER.getValue()) {
			adminOrder.setAccount(adminRecharge.getAccount());
		} else if (adminRecharge.getTargetObject() == Constant.AdministratorRechargeType.HIERARCHY.getValue()) {
			adminOrder.setHierarchyId(adminRecharge.getHierarchyId());
		}
		this.baseMapper.insert(adminOrder);
		// 查询需要充值的会员
		List<UserEntity> userList = new ArrayList<>();
		if (adminRecharge.getTargetObject() == Constant.AdministratorRechargeType.USER.getValue()) {
			userList = userDao.findUserListByAccount(adminRecharge.getAccount().split(","));
		} else if (adminRecharge.getTargetObject() == Constant.AdministratorRechargeType.HIERARCHY.getValue()) {
			userList = userDao.findUserListByHierarchy(adminRecharge.getHierarchyId().split(","));
		}
		if (CollectionUtils.isEmpty(userList)) {
			throw new RRException(ErrorCode.MessagemanagementErrorCode.NOCHOICE_ACCOUNT_ERRO.getErrMsg(),
					ErrorCode.MessagemanagementErrorCode.NOCHOICE_ACCOUNT_ERRO.getCode());
		}
		if (adminRecharge.getTargetObject() == Constant.AdministratorRechargeType.USER.getValue()) {
			String[] accountList = adminRecharge.getAccount().split(",");
			String accountStr = "";// 不存在的账号
			if (accountList.length > userList.size()) {
				for (String account : accountList) {
					for (UserEntity user : userList) {
						if (user.getAccount().equals(account)) {
							break;
						}
						accountStr += "," + account;
					}
				}
				throw new RRException(
						accountStr.replaceFirst(",", "") + ErrorCode.UserErrorCode.USER_IS_NULL.getErrMsg(),
						ErrorCode.UserErrorCode.USER_IS_NULL.getCode());
			}
		}
		for(UserEntity user : userList) {
			if (user.getUserType().equals(SysConstant.TRIAL)) {
				throw new RRException(ErrorCode.UserErrorCode.USER_TRIAL_ERRO.getErrMsg(),
						ErrorCode.UserErrorCode.USER_TRIAL_ERRO.getCode());
			}
			// 校验用户是否被冻结
			UserStatusVerificationUtil.userFrozenValidate(user.getFrozenEnable());
			// 是否余额不足
			BigDecimal money =MathUtil.getBigDecimal(user.getMoney());
			BigDecimal totalMoney =MathUtil.getBigDecimal(user.getCoin()).divide(new BigDecimal(Constant.COIN_RATE),2,BigDecimal.ROUND_HALF_UP)
					.add(money);
			if (totalMoney.compareTo(amount) < 0) {
				throw new RRException(ErrorCode.OrderErrorCode.MONEY_NOT_ENOUGH.getErrMsg(),
						ErrorCode.OrderErrorCode.MONEY_NOT_ENOUGH.getCode());
			}
			
			 
			
			OrderTakeMoneyEntity orderTakeMoney = new OrderTakeMoneyEntity();
			orderTakeMoney.setUserId(user.getId());
			orderTakeMoney.setUserAccount(user.getAccount());
			orderTakeMoney.setAccount(user.getAccount());
			orderTakeMoney.setTakeAmount(amount.longValue());
			orderTakeMoney.setPoundage(BigDecimal.ZERO);
			orderTakeMoney.setObtainAmount(amount);
			orderTakeMoney.setStatus(Constant.OrderStatus.COMPLETE.getValue());
			orderTakeMoney.setType(Constant.TakeMoneyType.TAKE_MONEY.getValue());
			orderTakeMoney.setOrderNo(OrderNoUtil.getOrderNo());
			orderTakeMoney.setAccountType(Constant.TakeMoneyChannel.ADMIN.getValue());
			orderTakeMoney.setSysUserAccount(sysUserName);
			orderTakeMoney.setSysUserId(sysUserId);
			orderTakeMoney.setRemark(adminRecharge.getRemake());
			orderTakeMoney.setAdminOrderNo(adminOrder.getOrderNo());
			orderTakeMoney.insert();
			
			UserEntity userUpdateParam = new UserEntity();
			userUpdateParam.setId(user.getId());
			if (money.compareTo(amount) >= 0) {
				userUpdateParam.setMoney(amount.multiply(new BigDecimal(-1)));
				userUpdateParam.setCoin(0l);
			}else {
				userUpdateParam.setMoney(money.multiply(new BigDecimal(-1)));
				userUpdateParam.setCoin(amount.subtract(money).multiply(
						new BigDecimal(Constant.COIN_RATE)).longValue()*-1);
			}
			
			Integer updateNum = this.userDao.updateUserWalletByUserId(userUpdateParam);
			if (updateNum <= 0) {
				throw new RRException(ErrorCode.UserErrorCode.USER_COIN_NOT_ENOUGH.getErrMsg(),
						ErrorCode.UserErrorCode.USER_COIN_NOT_ENOUGH.getCode());
			}
			//扣除相应的打码量
			OrderCashExamineEntity OrderCashExamine = orderCashExamineService.
					findRecentOrderCashExamine(user.getId());
			if(OrderCashExamine !=null && OrderCashExamine.getId()>0){
				BigDecimal userNeedBet = orderCashExamineService.countUserNeedBet(amount, user.getHierarchyId());
				if(OrderCashExamine.getUserNeedBet().compareTo(userNeedBet)>0) {
					OrderCashExamine.setUserNeedBet(OrderCashExamine.getUserNeedBet().subtract(userNeedBet));
				}else {
					OrderCashExamine.setUserNeedBet(BigDecimal.ZERO);
				}
				orderCashExamineService.updateById(OrderCashExamine);
			}
			// 保存交易记录
			UserTransactionRecordEntity transactionRecord = new UserTransactionRecordEntity();
			transactionRecord.setType(Constant.TransactionType.TAKE.getValue());
			transactionRecord.setUserId(orderTakeMoney.getUserId());
			transactionRecord.setUserAccount(orderTakeMoney.getUserAccount());
			transactionRecord.setOrderNo(orderTakeMoney.getOrderNo());
			transactionRecord.setMoney(user.getMoney().add(userUpdateParam.getMoney()));
			transactionRecord.setAmount(amount);
			transactionRecord.setCoin(user.getCoin()+userUpdateParam.getCoin());
			transactionRecord.setDetailType(Constant.TransactionDetailType.ADMINTAKE.getValue());
			transactionRecord.setCommission(user.getCommission());
			transactionRecord.setRemake(orderTakeMoney.getRemark());
			transactionRecord.insert();
			UserEntity pushMessage = new UserEntity();
			pushMessage.setMoney(userUpdateParam.getMoney());
			pushMessage.setCoin(userUpdateParam.getCoin());
			pushMessage.setId(user.getId());
			UserInfoMessage message = new UserInfoMessage(pushMessage, null);
			log.info("[人工取款-推送消息] message {}", message);
			pushService.pushExchange(message);
			
		}
		
		
	}

}

package com.xmsy.server.zxyy.manager.modules.manager.ordertakemoney.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.druid.util.StringUtils;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xmsy.common.define.page.PageParam;
import com.xmsy.server.zxyy.manager.common.exception.RRException;
import com.xmsy.server.zxyy.manager.common.utils.Constant;
import com.xmsy.server.zxyy.manager.common.utils.Constant.TransactionDetailType;
import com.xmsy.server.zxyy.manager.common.utils.ErrorCode;
import com.xmsy.server.zxyy.manager.modules.app.take.result.OrderTakeMoneyResult;
import com.xmsy.server.zxyy.manager.modules.manager.ordercashexamine.entity.OrderCashExamineEntity;
import com.xmsy.server.zxyy.manager.modules.manager.ordercashexamine.service.OrderCashExamineService;
import com.xmsy.server.zxyy.manager.modules.manager.ordertakemoney.dao.OrderTakeMoneyDao;
import com.xmsy.server.zxyy.manager.modules.manager.ordertakemoney.entity.OrderTakeMoneyEntity;
import com.xmsy.server.zxyy.manager.modules.manager.ordertakemoney.entity.OrderTakeMoneyLockingEntity;
import com.xmsy.server.zxyy.manager.modules.manager.ordertakemoney.entity.OrderTakeMoneyStatisticsThree;
import com.xmsy.server.zxyy.manager.modules.manager.ordertakemoney.entity.OrderTakeMoneyStatisticsTwo;
import com.xmsy.server.zxyy.manager.modules.manager.ordertakemoney.service.OrderTakeMoneyService;
import com.xmsy.server.zxyy.manager.modules.manager.statistics.entity.TakeMoneyTableReport;
import com.xmsy.server.zxyy.manager.modules.manager.user.entity.UserEntity;
import com.xmsy.server.zxyy.manager.modules.manager.user.param.UserParamFour;
import com.xmsy.server.zxyy.manager.modules.manager.user.service.UserService;
import com.xmsy.server.zxyy.manager.modules.manager.userinfo.entity.UserInfoEntity;
import com.xmsy.server.zxyy.manager.modules.manager.userinfo.service.UserInfoService;
import com.xmsy.server.zxyy.manager.modules.manager.userpassword.service.UserPasswordService;
import com.xmsy.server.zxyy.manager.modules.manager.usertransactionrecord.entity.UserTransactionRecordEntity;
import com.xmsy.server.zxyy.manager.modules.manager.usertransactionrecord.service.UserTransactionRecordService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * .取款逻辑
 *
 * @author Administrator
 *
 */
@Slf4j
@Transactional
@Service("orderTakeMoneyService")
public class OrderTakeMoneyServiceImpl extends ServiceImpl<OrderTakeMoneyDao, OrderTakeMoneyEntity>
		implements OrderTakeMoneyService {

	@Resource
	private UserPasswordService userPasswordService;
	@Resource
	private UserService userService;
	@Autowired
	private UserInfoService userInfoService;
	@Autowired
	private OrderCashExamineService orderCashExamineService;
	@Autowired
	private UserTransactionRecordService userTransactionRecordService;

	/**
	 * 保存现金取款订单
	 *
	 * @throws Exception
	 */
	@Override
	public void saveTakeMoneyOrder(OrderTakeMoneyEntity orderTakeMoneyEntity, UserEntity userEntity) throws Exception {
		if (!userPasswordService.validateBankPassword(orderTakeMoneyEntity.getPassword(), userEntity.getId())) {
			throw new RRException(ErrorCode.PasswordErrorCode.PASSWORD_INVALID_ERRO.getErrMsg(),
					ErrorCode.PasswordErrorCode.PASSWORD_INVALID_ERRO.getCode());
		}
		UserInfoEntity userinfo = userInfoService
				.selectOne(new EntityWrapper<>(new UserInfoEntity().setUserId(userEntity.getId())));
		if (userinfo == null) {
			throw new RRException(ErrorCode.OrderErrorCode.USER_BANK_IS_NULL.getErrMsg(),
					ErrorCode.OrderErrorCode.USER_BANK_IS_NULL.getCode());
		}
		// 判断用户的收款渠道是否正确
		if (Constant.TakeMoneyChannel.ALIPAY.getValue() != orderTakeMoneyEntity.getAccountType()
				&& Constant.TakeMoneyChannel.BANK.getValue() != orderTakeMoneyEntity.getAccountType()) {
			throw new RRException(ErrorCode.OrderErrorCode.RECEIPT_CHANNEL_ERR.getErrMsg(),
					ErrorCode.OrderErrorCode.RECEIPT_CHANNEL_ERR.getCode());
		}
		// 判断用户银行信息是否完善
		if (Constant.TakeMoneyChannel.BANK.getValue() == orderTakeMoneyEntity.getAccountType()
				&& StringUtils.isEmpty(userinfo.getBankCard())) {
			throw new RRException(ErrorCode.OrderErrorCode.USER_BANK_IS_NULL.getErrMsg(),
					ErrorCode.OrderErrorCode.USER_BANK_IS_NULL.getCode());
		}
		// 判断用户支付宝信息是否完善
		if (Constant.TakeMoneyChannel.ALIPAY.getValue() == orderTakeMoneyEntity.getAccountType()
				&& StringUtils.isEmpty(userinfo.getAlipayAccount())) {
			throw new RRException(ErrorCode.OrderErrorCode.USER_ALIPAY_IS_NULL.getErrMsg(),
					ErrorCode.OrderErrorCode.USER_ALIPAY_IS_NULL.getCode());
		}
		// 查询是否有未结算的取款订单
		OrderTakeMoneyEntity record = new OrderTakeMoneyEntity();
		record.setUserId(userEntity.getId());
		record.setStatus(Constant.OrderStatus.UNCONFIRMED.getValue());
		record.setType(Constant.TakeMoneyType.TAKE_MONEY.getValue());
		List<OrderTakeMoneyEntity> recordList = baseMapper.selectList(new EntityWrapper<OrderTakeMoneyEntity>(record));
		if (!CollectionUtils.isEmpty(recordList)) {
			throw new RRException(ErrorCode.OrderErrorCode.TAKE_RECORD_ISNOTNULL.getErrMsg(),
					ErrorCode.OrderErrorCode.TAKE_RECORD_ISNOTNULL.getCode());
		}
		// 行政费
		BigDecimal administrativeAmount = BigDecimal.ZERO;
		// 修改稽查记录
		OrderCashExamineEntity preCashExamine = orderCashExamineService.updateCashExamineForTake(userEntity,
				new BigDecimal(orderTakeMoneyEntity.getTakeAmount()));
		if (null != preCashExamine) {
			administrativeAmount = preCashExamine.getDeductionAdministrative();
		}
		// 取款金额
		BigDecimal takeAmount = new BigDecimal(orderTakeMoneyEntity.getTakeAmount());
		// 余额不足: 账户余额<取款金额+手续费
		if (userEntity.getMoney().compareTo(administrativeAmount.add(takeAmount)) < 0) {
			throw new RRException(ErrorCode.OrderErrorCode.MONEY_NOT_ENOUGH.getErrMsg(),
					ErrorCode.OrderErrorCode.MONEY_NOT_ENOUGH.getCode());
		}
		orderTakeMoneyEntity.setOrderExamineId(preCashExamine.getId());
		orderTakeMoneyEntity.setPoundage(administrativeAmount);
		orderTakeMoneyEntity.setObtainAmount(takeAmount);
		orderTakeMoneyEntity.setUserAccount(userEntity.getAccount());
		orderTakeMoneyEntity.setUserId(userEntity.getId());
		// 银行取款
		if (Constant.TakeMoneyChannel.BANK.getValue() == orderTakeMoneyEntity.getAccountType()) {
			orderTakeMoneyEntity.setBankName(userinfo.getBankName());
			orderTakeMoneyEntity.setIncomeNo(userinfo.getBankCard());
			orderTakeMoneyEntity.setAccountName(userEntity.getUserName());
			orderTakeMoneyEntity.setAccountType(Constant.TakeMoneyChannel.BANK.getValue());
		}
		if (Constant.TakeMoneyChannel.ALIPAY.getValue() == orderTakeMoneyEntity.getAccountType()) {
			orderTakeMoneyEntity.setIncomeNo(userinfo.getAlipayAccount());
			orderTakeMoneyEntity.setAccountType(Constant.TakeMoneyChannel.ALIPAY.getValue());
		}
		saveOrderTakeMoneyTransactionRecord(orderTakeMoneyEntity, Constant.TransactionDetailType.ACCOUNTTAKE);
		baseMapper.insert(orderTakeMoneyEntity);
	}

	@Override
	public void saveTakeCommissionOrder(OrderTakeMoneyEntity orderTakeMoneyEntity, UserEntity userEntity) {
		if (!userPasswordService.validateBankPassword(orderTakeMoneyEntity.getPassword(), userEntity.getId())) {
			throw new RRException(ErrorCode.PasswordErrorCode.PASSWORD_INVALID_ERRO.getErrMsg(),
					ErrorCode.PasswordErrorCode.PASSWORD_INVALID_ERRO.getCode());
		}
		UserInfoEntity userinfo = userInfoService
				.selectOne(new EntityWrapper<>(new UserInfoEntity().setUserId(userEntity.getId())));
		if (userinfo == null) {
			throw new RRException(ErrorCode.OrderErrorCode.USER_BANK_IS_NULL.getErrMsg(),
					ErrorCode.OrderErrorCode.USER_BANK_IS_NULL.getCode());
		}
		// 判断用户的收款渠道是否正确
		if (Constant.TakeMoneyChannel.ALIPAY.getValue() != orderTakeMoneyEntity.getAccountType()
				&& Constant.TakeMoneyChannel.BANK.getValue() != orderTakeMoneyEntity.getAccountType()) {
			throw new RRException(ErrorCode.OrderErrorCode.RECEIPT_CHANNEL_ERR.getErrMsg(),
					ErrorCode.OrderErrorCode.RECEIPT_CHANNEL_ERR.getCode());
		}
		// 判断用户银行信息是否完善
		if (Constant.TakeMoneyChannel.BANK.getValue() == orderTakeMoneyEntity.getAccountType()
				&& StringUtils.isEmpty(userinfo.getBankCard())) {
			throw new RRException(ErrorCode.OrderErrorCode.USER_BANK_IS_NULL.getErrMsg(),
					ErrorCode.OrderErrorCode.USER_BANK_IS_NULL.getCode());
		}
		// 判断用户支付宝信息是否完善
		if (Constant.TakeMoneyChannel.ALIPAY.getValue() == orderTakeMoneyEntity.getAccountType()
				&& StringUtils.isEmpty(userinfo.getAlipayAccount())) {
			throw new RRException(ErrorCode.OrderErrorCode.USER_ALIPAY_IS_NULL.getErrMsg(),
					ErrorCode.OrderErrorCode.USER_ALIPAY_IS_NULL.getCode());
		}
		// 查询是否有未结算的取款订单
		OrderTakeMoneyEntity record = new OrderTakeMoneyEntity();
		record.setUserId(userEntity.getId());
		record.setUserAccount(userEntity.getAccount());
		record.setStatus(Constant.OrderStatus.UNCONFIRMED.getValue());
		record.setType(Constant.TakeMoneyType.COMMISSION.getValue());
		List<OrderTakeMoneyEntity> recordList = baseMapper.selectList(new EntityWrapper<OrderTakeMoneyEntity>(record));
		if (!CollectionUtils.isEmpty(recordList)) {
			throw new RRException(ErrorCode.OrderErrorCode.TAKE_RECORD_ISNOTNULL.getErrMsg(),
					ErrorCode.OrderErrorCode.TAKE_RECORD_ISNOTNULL.getCode());
		}
		orderTakeMoneyEntity.setUserAccount(userEntity.getAccount());
		// 取款金额大于余额不让提款
		if (new BigDecimal(orderTakeMoneyEntity.getTakeAmount()).compareTo(userEntity.getCommission()) > 0) {
			throw new RRException(ErrorCode.OrderErrorCode.MONEY_NOT_ENOUGH.getErrMsg(),
					ErrorCode.OrderErrorCode.MONEY_NOT_ENOUGH.getCode());
		}
		// 银行取款
		if (Constant.TakeMoneyChannel.BANK.getValue() == orderTakeMoneyEntity.getAccountType()) {
			orderTakeMoneyEntity.setBankName(userinfo.getBankName());
			orderTakeMoneyEntity.setIncomeNo(userinfo.getBankCard());
			orderTakeMoneyEntity.setAccountName(userinfo.getBankAccountName());
			orderTakeMoneyEntity.setAccountType(Constant.TakeMoneyChannel.BANK.getValue());
		}
		if (Constant.TakeMoneyChannel.ALIPAY.getValue() == orderTakeMoneyEntity.getAccountType()) {
			orderTakeMoneyEntity.setIncomeNo(userinfo.getAlipayAccount());
			orderTakeMoneyEntity.setAccountType(Constant.TakeMoneyChannel.ALIPAY.getValue());
		}
		orderTakeMoneyEntity.setUserId(userEntity.getId());
		saveOrderTakeMoneyTransactionRecord(orderTakeMoneyEntity, Constant.TransactionDetailType.COMMISSIONTAKE);
		baseMapper.insert(orderTakeMoneyEntity);
	}

	/**
	 * 用户取款订单确认
	 */
	@Override
	public void orderTakeMoneyConfirm(OrderTakeMoneyEntity takeMoneyRecordEntity) throws Exception {
		if (takeMoneyRecordEntity.getType() == Constant.TakeMoneyType.COMMISSION.getValue()) {
			takeMoneyRecordEntity.updateById();
			return;
		}
		// orderConfirmUpdateCashExamine(takeMoneyRecordEntity.getOrderExamineId(),
		// takeMoneyRecordEntity.getUserId());
		takeMoneyRecordEntity.updateById();
	}

	// 保存取款订单交易
	private void saveOrderTakeMoneyTransactionRecord(OrderTakeMoneyEntity orderTakeMoneyEntity,
			TransactionDetailType transactionDetailType) {
		BigDecimal takeAmount = new BigDecimal(orderTakeMoneyEntity.getTakeAmount());
		orderTakeMoneyEntity.setPoundage(
				orderTakeMoneyEntity.getPoundage() == null ? BigDecimal.ZERO : orderTakeMoneyEntity.getPoundage());
		UserTransactionRecordEntity transactionRecord = new UserTransactionRecordEntity();
		transactionRecord.setType(Constant.TransactionType.TAKE.getValue());
		transactionRecord.setOrderNo(orderTakeMoneyEntity.getOrderNo());
		transactionRecord.setDetailType(transactionDetailType.getValue());
		transactionRecord.setUserId(orderTakeMoneyEntity.getUserId());
		transactionRecord.setUserAccount(orderTakeMoneyEntity.getUserAccount());
		transactionRecord.setRemake(orderTakeMoneyEntity.getRemark());
		transactionRecord.setAmount(takeAmount.add(orderTakeMoneyEntity.getPoundage()));
		// 更新用户余额
		UserEntity user = userService.selectById(orderTakeMoneyEntity.getUserId());
		if (user == null) {
			throw new RRException(ErrorCode.UserErrorCode.USER_IS_NULL.getErrMsg(),
					ErrorCode.UserErrorCode.USER_IS_NULL.getCode());
		}
		transactionRecord.setMoney(user.getMoney());
		transactionRecord.setCoin(user.getCoin());
		transactionRecord.setCommission(user.getCommission());
		UserEntity userUpdateParam = new UserEntity();
		userUpdateParam.setId(user.getId());
		if (transactionDetailType == Constant.TransactionDetailType.COMMISSIONTAKE) {
			// 佣金扣除取款金额
			userUpdateParam.setCommission(BigDecimal.ZERO.subtract(takeAmount));
			transactionRecord.setCommission(user.getCommission().subtract(takeAmount));
		} else if (transactionDetailType == Constant.TransactionDetailType.ACCOUNTTAKE) {
			// 余额扣除取款金额和手续费
			userUpdateParam.setMoney(BigDecimal.ZERO.subtract(takeAmount).subtract(orderTakeMoneyEntity.getPoundage()));
			transactionRecord
					.setMoney(user.getMoney().subtract(takeAmount).subtract(orderTakeMoneyEntity.getPoundage()));
		} else if (transactionDetailType == Constant.TransactionDetailType.FEE_REFUND) {
			Long takeCoin = orderTakeMoneyEntity.getPoundage().multiply(new BigDecimal(Constant.COIN_RATE)).longValue();
			transactionRecord.setCoin(user.getCoin()+takeCoin);
			transactionRecord.setAmount(orderTakeMoneyEntity.getPoundage());
			userUpdateParam.setCoin(takeCoin);
//			transactionRecord.setMoney(user.getMoney().add(orderTakeMoneyEntity.getPoundage()));
//			transactionRecord.setAmount(orderTakeMoneyEntity.getPoundage());
//			userUpdateParam.setMoney(orderTakeMoneyEntity.getPoundage());
		}
		userService.updateUserWalletByUserId(userUpdateParam);
		// 保存交易记录
		userTransactionRecordService.insert(transactionRecord);
	}

	// 保存取消取款订单交易
	private void saveOrderTakeMoneyCancelTransactionRecord(OrderTakeMoneyEntity orderTakeMoneyEntity) {
		BigDecimal takeAmount = new BigDecimal(orderTakeMoneyEntity.getTakeAmount());
		UserTransactionRecordEntity transactionRecord = new UserTransactionRecordEntity();
		transactionRecord.setType(Constant.TransactionType.TAKE_RETURN.getValue());
		transactionRecord.setOrderNo(orderTakeMoneyEntity.getOrderNo());
		transactionRecord.setUserId(orderTakeMoneyEntity.getUserId());
		transactionRecord.setUserAccount(orderTakeMoneyEntity.getUserAccount());
		transactionRecord.setRemake(orderTakeMoneyEntity.getRemark());
		transactionRecord.setAmount(takeAmount);
		// 更新用户余额
		UserEntity user = userService.selectById(orderTakeMoneyEntity.getUserId());
		if (user == null) {
			throw new RRException(ErrorCode.UserErrorCode.USER_IS_NULL.getErrMsg(),
					ErrorCode.UserErrorCode.USER_IS_NULL.getCode());
		}
		transactionRecord.setMoney(user.getMoney());
		transactionRecord.setCoin(user.getCoin());
		transactionRecord.setCommission(user.getCommission());
		UserEntity userUpdateParam = new UserEntity();
		userUpdateParam.setId(user.getId());
		if (orderTakeMoneyEntity.getType() == Constant.TakeMoneyType.TAKE_MONEY.getValue()) {
			transactionRecord.setDetailType(Constant.TransactionDetailType.TAKE_MONEY_CANCEL.getValue());
			// 余额退款
			Long takeCoin = takeAmount.add(orderTakeMoneyEntity.getPoundage())
					.multiply(new BigDecimal(Constant.COIN_RATE)).longValue();
			userUpdateParam.setCoin(takeCoin);
			transactionRecord.setCoin(user.getCoin()+takeCoin);
		} else {
			transactionRecord.setDetailType(Constant.TransactionDetailType.TAKE_COMMISSION_CANCEL.getValue());
			// 佣金退款
			userUpdateParam.setCommission(takeAmount);
			transactionRecord.setCommission(user.getCommission().add(takeAmount));
		}
		userService.updateUserWalletByUserId(userUpdateParam);
		// 保存交易记录
		userTransactionRecordService.insert(transactionRecord);
	}

//	private void saveOrderTakeMoneyCancelTransactionRecord(OrderTakeMoneyEntity orderTakeMoneyEntity) {
//		BigDecimal takeAmount = new BigDecimal(orderTakeMoneyEntity.getTakeAmount());
//		UserTransactionRecordEntity transactionRecord = new UserTransactionRecordEntity();
//		transactionRecord.setType(Constant.TransactionType.TAKE_RETURN.getValue());
//		transactionRecord.setOrderNo(orderTakeMoneyEntity.getOrderNo());
//		transactionRecord.setUserId(orderTakeMoneyEntity.getUserId());
//		transactionRecord.setUserAccount(orderTakeMoneyEntity.getUserAccount());
//		transactionRecord.setRemake(orderTakeMoneyEntity.getRemark());
//		transactionRecord.setAmount(takeAmount);
//		// 更新用户余额
//		UserEntity user = userService.selectById(orderTakeMoneyEntity.getUserId());
//		if (user == null) {
//			throw new RRException(ErrorCode.UserErrorCode.USER_IS_NULL.getErrMsg(),
//					ErrorCode.UserErrorCode.USER_IS_NULL.getCode());
//		}
//		transactionRecord.setMoney(user.getMoney());
//		transactionRecord.setCoin(user.getCoin());
//		transactionRecord.setCommission(user.getCommission());
//		UserEntity userUpdateParam = new UserEntity();
//		userUpdateParam.setId(user.getId());
//		if (orderTakeMoneyEntity.getType() == Constant.TakeMoneyType.TAKE_MONEY.getValue()) {
//			transactionRecord.setDetailType(Constant.TransactionDetailType.TAKE_MONEY_CANCEL.getValue());
//			// 余额退款
//			userUpdateParam.setMoney(takeAmount.add(orderTakeMoneyEntity.getPoundage()));
//			transactionRecord.setMoney(user.getMoney().add(takeAmount).add(orderTakeMoneyEntity.getPoundage()));
//		} else {
//			transactionRecord.setDetailType(Constant.TransactionDetailType.TAKE_COMMISSION_CANCEL.getValue());
//			// 佣金退款
//			userUpdateParam.setCommission(takeAmount);
//			transactionRecord.setCommission(user.getCommission().add(takeAmount));
//		}
//		userService.updateUserWalletByUserId(userUpdateParam);
//		// 保存交易记录
//		userTransactionRecordService.insert(transactionRecord);
//	}

	// 取消取款下注
	@Override
	public void orderTakeMoneyBetCancel(OrderTakeMoneyEntity orderTakeMoneyEntity) throws Exception {
		// 设置取消取款下注
		orderTakeMoneyEntity.setBetCancel(Boolean.TRUE);
		orderCancelBetUpdateCashExamine(orderTakeMoneyEntity.getOrderExamineId());
		// 取消打码免除手续费
		saveOrderTakeMoneyTransactionRecord(orderTakeMoneyEntity, Constant.TransactionDetailType.FEE_REFUND);
		baseMapper.updateById(orderTakeMoneyEntity);
	}

	// 取消取现订单
	@Override
	public void orderTakeMoneyCancel(OrderTakeMoneyEntity orderTakeMoneyEntity) throws Exception {

		if (orderTakeMoneyEntity.getType() == Constant.TakeMoneyType.TAKE_MONEY.getValue()) {
			orderCancelUpdateCashExamine(orderTakeMoneyEntity.getOrderExamineId());
		}
		// 获取关联的稽查订单
		orderTakeMoneyEntity.updateById();
		saveOrderTakeMoneyCancelTransactionRecord(orderTakeMoneyEntity);
	}

	@Override
	public Page<OrderTakeMoneyResult> getTakeMoneyRecord(PageParam pageParam, Long userId, String startTime,
			String endTime) {
		Page<OrderTakeMoneyResult> page = new Page<>(pageParam.getPage(), pageParam.getLimit());// 当前页，总条数 构造 page 对象
		return page.setRecords(this.baseMapper.getTakeMoneyRecord(page, userId, startTime, endTime));
	}

	// 取款订单确认，稽查订单修改重试
	// private void orderConfirmUpdateCashExamine(Long orderExamineId, Long userId)
	// {
	// int i = 0;
	// while (i < 10) {
	// // 手续费清0
	// OrderCashExamineEntity entity = new OrderCashExamineEntity();
	// entity.setId(orderExamineId);
	// OrderCashExamineEntity cashExamineEntity = orderCashExamineService
	// .selectOne(new EntityWrapper<OrderCashExamineEntity>(entity));
	// if (null == cashExamineEntity) {
	// throw new RRException(ErrorCode.OrderErrorCode.EXAMINE_IS_NULL.getErrMsg(),
	// ErrorCode.OrderErrorCode.EXAMINE_IS_NULL.getCode());
	// }
	// UserEntity user = userService.selectById(userId);
	// if (user == null) {
	// throw new RRException(ErrorCode.UserErrorCode.USER_IS_NULL.getErrMsg(),
	// ErrorCode.UserErrorCode.USER_IS_NULL.getCode());
	// }
	// // 当次取款被扣的费用（正常情况下就是行政费，预留以后行政费加其他费用叠加）
	// if (!orderCashExamineService.updateById(cashExamineEntity)) {
	// i++;
	// } else {
	// return;
	// }
	// }
	// log.error("[orderTakeMoneyConfirm] ->orderConfirmUpdateCashExamine 失败
	// orderExamineId {}", orderExamineId);
	// throw new RRException("取款订单确认,稽查记录更新失败 ");
	// }

	// 取消取款订单，稽查订单修改重试
	private void orderCancelUpdateCashExamine(Long orderExamineId) {
		int i = 0;
		while (i < 10) {
			// 获取关联的稽查订单
			OrderCashExamineEntity entity = new OrderCashExamineEntity();
			entity.setId(orderExamineId);
			OrderCashExamineEntity cashExamineEntity = orderCashExamineService
					.selectOne(new EntityWrapper<OrderCashExamineEntity>(entity));
			if (null == cashExamineEntity) {
				throw new RRException(ErrorCode.OrderErrorCode.EXAMINE_IS_NULL.getErrMsg(),
						ErrorCode.OrderErrorCode.EXAMINE_IS_NULL.getCode());
			}
			// 把原先扣除的行政费清0
			cashExamineEntity.setDeductionAdministrative(BigDecimal.ZERO);
			if (setValidBetAndNeedBet(cashExamineEntity)) {
				return;
			} else {
				i++;
			}
		}
		log.error("[orderTakeMoneyCancel] ->orderCancelUpdateCashExamine 失败  orderExamineId {}", orderExamineId);
		throw new RRException("取消取款订单,稽查记录更新失败 ");
	}

	// 取消取款订单打码，稽查订单修改重试
	private void orderCancelBetUpdateCashExamine(Long orderExamineId) {
		int i = 0;
		while (i < 10) {
			// 手续费清0
			OrderCashExamineEntity entity = new OrderCashExamineEntity();
			entity.setId(orderExamineId);
			OrderCashExamineEntity cashExamineEntity = orderCashExamineService
					.selectOne(new EntityWrapper<OrderCashExamineEntity>(entity));
			if (null == cashExamineEntity) {
				throw new RRException(ErrorCode.OrderErrorCode.EXAMINE_IS_NULL.getErrMsg(),
						ErrorCode.OrderErrorCode.EXAMINE_IS_NULL.getCode());
			}
			cashExamineEntity.setUserNeedBet(BigDecimal.ZERO);
			cashExamineEntity.setUserValidBet(BigDecimal.ZERO);
			cashExamineEntity.setDeductionNeedBet(BigDecimal.ZERO);
			cashExamineEntity.setDeductionValidBet(BigDecimal.ZERO);
			cashExamineEntity.setHierarchyAdministrativeAmount(BigDecimal.ZERO);
			if (!orderCashExamineService.updateById(cashExamineEntity)) {
				i++;
			} else {
				return;
			}
		}
		log.error("[orderTakeMoneyBetCancel] ->updateCashExamine 失败  orderExamineId {}", orderExamineId);
		throw new RRException("取消取款订单打码,稽查记录更新失败 ");
	}

	// 取消订单的话把原先的有效打码和用户需要的打码都加回去
	private boolean setValidBetAndNeedBet(OrderCashExamineEntity orderCashExamineEntity) {
		OrderCashExamineEntity currentOrderCashExamineEntity = orderCashExamineService
				.findRecentOrderCashExamine(orderCashExamineEntity.getUserId());
		BigDecimal userNeedBet = currentOrderCashExamineEntity.getUserNeedBet()
				.add(orderCashExamineEntity.getDeductionNeedBet());
		userNeedBet = userNeedBet.compareTo(BigDecimal.ZERO) < 0 ? BigDecimal.ZERO : userNeedBet;
		BigDecimal userValidBet = currentOrderCashExamineEntity.getUserValidBet()
				.add(orderCashExamineEntity.getDeductionValidBet());
		userValidBet = userValidBet.compareTo(BigDecimal.ZERO) < 0 ? BigDecimal.ZERO : userValidBet;
		currentOrderCashExamineEntity.setUserValidBet(userValidBet);
		currentOrderCashExamineEntity.setUserNeedBet(userNeedBet);
		currentOrderCashExamineEntity.setDeductionAdministrative(orderCashExamineEntity.getDeductionAdministrative());
		return orderCashExamineService.updateById(currentOrderCashExamineEntity);
	}

	/**
	 * 取款表格报表
	 */
	@Override
	public List<TakeMoneyTableReport> getTakeMoneyTableReport(String startTime, String endTime) {
		return baseMapper.getTakeMoneyTableReport(startTime, endTime);
	}

	@Override
	public boolean updateAll(OrderTakeMoneyEntity orderTakeMoneyEntity) {
		// TODO Auto-generated method stub
		return baseMapper.updateAll(orderTakeMoneyEntity);
	}

	@Override
	public int batchLocking(OrderTakeMoneyLockingEntity orderTakeMoneyEntity) {
		if (orderTakeMoneyEntity.getNum() <= 0) {
			throw new RRException("条数需大于0");
		}
		// 修改成功的订单集合
		List<OrderTakeMoneyEntity> unconfirmedList = new ArrayList<OrderTakeMoneyEntity>();
		while (true) {
			// 如果修改成功的订单数等于要求的数量，则跳出循环
			if (unconfirmedList.size() == orderTakeMoneyEntity.getNum()) {
				break;
			}
			// 获取所有状态为未确定的余额取款订单
			List<OrderTakeMoneyEntity> list = selectList(new EntityWrapper<OrderTakeMoneyEntity>(
					new OrderTakeMoneyEntity().setStatus(Constant.OrderStatus.UNCONFIRMED.getValue())
							.setType(Constant.TakeMoneyType.TAKE_MONEY.getValue())));
			// 如果没有未确认过订单，跳出循环
			if (CollectionUtil.isEmpty(list)) {
				if (unconfirmedList.size() == 0) {
					throw new RRException("无未确定订单");
				}
				break;
			} else {
				// 随机获取到订单，并进行修改
				Random random = new Random();
				int s = random.nextInt(list.size()) % (list.size() - 0 + 1) + 0;
				OrderTakeMoneyEntity updateRecharge = list.get(s);
				updateRecharge.setStatus(Constant.OrderStatus.LOCKING.getValue());
				updateRecharge.setSysUserAccount(orderTakeMoneyEntity.getSysUserAccount());
				updateRecharge.setSysUserId(orderTakeMoneyEntity.getSysUserId());
				boolean result = updateById(updateRecharge);
				if (result) {
					// 如果修改成功
					unconfirmedList.add(updateRecharge);
				}
			}

		}
		return unconfirmedList.size();
	}

	@Override
	public OrderTakeMoneyStatisticsTwo orderTakeTakeNum(Long userId) {
		return baseMapper.orderTakeTakeNum(userId);
	}

	@Override
	public OrderTakeMoneyStatisticsThree getLastTakeAmountByUserId(Long userId) {
		return baseMapper.getLastTakeAmountByUserId(userId);
	}

	@Override
	public Map<String, Object> sumTakeMoneyAmount(String startDate, String endDate, Integer rechargeType) {
		Map<String, Object> amount = baseMapper.sumTakeMoneyAmount(startDate, endDate, rechargeType);
		Map<String, Object> online = baseMapper.sumTakeOnlineMoneyAmount(startDate, endDate, rechargeType);
		Map<String, Object> admi = baseMapper.sumTakeAdmiMoneyAmount(startDate, endDate, rechargeType);
		amount.putAll(online);
		amount.putAll(admi);
		return amount;
	}

	@Override
	public List<Map<String,Object>> selectListOrderTakeMoney(OrderTakeMoneyEntity orderTakeMoney) {
		return baseMapper.selectListOrderTakeMoney(orderTakeMoney);
	}

	@Override
	public List<OrderTakeMoneyEntity> withdrawalRecord(UserParamFour user) {
		return baseMapper.withdrawalRecord(user);
	}

	@Override
	public int takeMoneyNum(OrderTakeMoneyEntity orderTakeMoney) {
		return baseMapper.takeMoneyNum(orderTakeMoney);
	}
	@Override
	public int conutStatus(OrderTakeMoneyEntity orderTakeMoney) { return baseMapper.conutStatus(orderTakeMoney); }

	@Override
	public Long takeMoneytotal(OrderTakeMoneyEntity orderTakeMoney) {
		Long num = baseMapper.takeMoneytotal(orderTakeMoney);
		return num == null ? 0L : num;
	}

	@Override
	public Long sumStatus(OrderTakeMoneyEntity orderTakeMoney) {
		Long num = baseMapper.sumStatus(orderTakeMoney);
		return num == null ? 0L : num;
	}

	@Override
	public BigDecimal poundageTotal(OrderTakeMoneyEntity orderTakeMoney){
		BigDecimal num = baseMapper.poundageTotal(orderTakeMoney);
		return num == null ? BigDecimal.valueOf(0) : num;
	}
	/**
	 * 根据会员id统计当前会员 总的取款金额+行政费(包含未审核 跟已完成)
	 */
	@Override
	public BigDecimal totalOrderTakeAmountTake(Long userId,String startTime,String endTime) {
		// TODO Auto-generated method stub
		return baseMapper.totalOrderTakeAmountTake(userId,startTime,endTime);
	}

}

package com.xmsy.server.zxyy.calculate.modules.manager.user.service.impl;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xmsy.server.zxyy.calculate.common.utils.Constant;
import com.xmsy.server.zxyy.calculate.modules.manager.orderrecharge.entity.OrderRechargeEntity;
import com.xmsy.server.zxyy.calculate.modules.manager.user.dao.UserDao;
import com.xmsy.server.zxyy.calculate.modules.manager.user.entity.UserEntity;
import com.xmsy.server.zxyy.calculate.modules.manager.user.param.UpdateCoinParam;
import com.xmsy.server.zxyy.calculate.modules.manager.user.service.UserService;
import com.xmsy.server.zxyy.calculate.modules.manager.userrecommendation.dao.UserRecommendationDao;
import com.xmsy.server.zxyy.calculate.modules.manager.userrecommendation.entity.UserRecommendationEntity;
import com.xmsy.server.zxyy.calculate.modules.manager.userrecommendationrecord.dao.UserRecommendationRecordDao;
import com.xmsy.server.zxyy.calculate.modules.manager.userrecommendationrecord.entity.UserRecommendationRecordEntity;
import com.xmsy.server.zxyy.calculate.modules.manager.usertransactionrecord.entity.UserTransactionRecordEntity;
import com.xmsy.server.zxyy.calculate.modules.manager.usertransactionrecord.service.UserTransactionRecordService;
import com.xmsy.server.zxyy.calculate.utils.InviteCode;
import com.xmsy.server.zxyy.calculate.common.utils.OrderNoUtil;

import lombok.extern.slf4j.Slf4j;

import com.xmsy.server.zxyy.calculate.common.exception.RRException;
import com.xmsy.server.zxyy.calculate.common.utils.ErrorCode;

@Slf4j
@Transactional
@Service("userService")
public class UserServiceImpl extends ServiceImpl<UserDao, UserEntity> implements UserService {
	
	@Autowired
	private UserTransactionRecordService userTransactionRecordService;
	@Autowired
	private UserRecommendationDao userRecommendationDao;
	@Autowired
	private UserRecommendationRecordDao userRecommendationRecordDao;

	@Override
	public void rechargeAndChanger(UserEntity user, BigDecimal money, Long coin) {
		UserEntity userUpdateParam = new UserEntity();
		userUpdateParam.setId(user.getId());
		userUpdateParam.setCoin(coin);
		baseMapper.updateUserWalletByUserId(userUpdateParam);
		// 保存交易记录
		UserTransactionRecordEntity transactionRecord = new UserTransactionRecordEntity();
		transactionRecord.setType(Constant.TransactionType.CHANGER.getValue());
		transactionRecord.setUserId(user.getId());
		transactionRecord.setUserAccount(user.getAccount());
		transactionRecord.setOrderNo(System.nanoTime() + InviteCode.create());
		transactionRecord.setMoney(user.getMoney());
		transactionRecord.setAmount(money);
		transactionRecord.setCoin(user.getCoin() + coin);
		transactionRecord.setDetailType(Constant.TransactionDetailType.QUOTACONVERSION.getValue());
		transactionRecord.setCommission(user.getCommission());
		userTransactionRecordService.insert(transactionRecord);
		
	}

	@Override
	public void updateUserFirstRecharge(Long id) {
		baseMapper.updateUserFirstRecharge(id);
	}

	@Override
	public void revokeMoney(OrderRechargeEntity orderRecharge) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateAgentCommission(Long userId,UserEntity agentUser, BigDecimal commission) {
		//1.代理上加上佣金值
		UserEntity userUpdateParam = new UserEntity();
		userUpdateParam.setId(agentUser.getId());
		userUpdateParam.setCommission(commission);
		baseMapper.updateUserWalletByUserId(userUpdateParam);
		//2.user_recommendation往总的佣金值加上当前佣金
		UserRecommendationEntity userRecommendation = new UserRecommendationEntity();
		userRecommendation.setUserId(agentUser.getId());
		userRecommendation.setAmount(commission);
		userRecommendationDao.updateSuperiorsAmount(userRecommendation);
		//3.user_recommendation_record往总的佣金值加上当前佣金（代理商下线贡献总值）
		UserRecommendationRecordEntity userRecommendationRecord = new UserRecommendationRecordEntity();
		userRecommendationRecord.setUserId(userId);
		userRecommendationRecord.setPromotingProfit(commission);
		userRecommendationRecordDao.updateRecommendationRecordAmount(userRecommendationRecord);
		//4.增加交易记录
		UserTransactionRecordEntity transactionRecord = new UserTransactionRecordEntity();
		transactionRecord.setType(Constant.TransactionType.REBATE.getValue());
		transactionRecord.setUserId(agentUser.getId());
		transactionRecord.setUserAccount(agentUser.getAccount());
		transactionRecord.setOrderNo(System.nanoTime() + InviteCode.create());
		transactionRecord.setMoney(agentUser.getMoney());
		transactionRecord.setAmount(commission);
		transactionRecord.setCoin(agentUser.getCoin());
		transactionRecord.setCommission(agentUser.getCommission().add(commission));
		transactionRecord.setDetailType(Constant.TransactionDetailType.AGENTSRECHARGECOMMISSION.getValue());
		userTransactionRecordService.insert(transactionRecord);
	}	
	@Override
	@Transactional
	public void updateUserCoin(UpdateCoinParam updateCoinParam) {
		UserEntity user = this.baseMapper.selectById(updateCoinParam.getUserId());
		if (user == null || user.getId() <= 0) {
			throw new RRException(ErrorCode.UserErrorCode.USER_IS_NULL.getErrMsg(),
					ErrorCode.UserErrorCode.USER_IS_NULL.getCode());
		}
		if (user.getCoin() + updateCoinParam.getCoin() < 0) {
			throw new RRException(ErrorCode.UserErrorCode.USER_COIN_NOT_ENOUGH.getErrMsg(),
					ErrorCode.UserErrorCode.USER_COIN_NOT_ENOUGH.getCode());
		}
		UserEntity updateUser = new UserEntity();
		updateUser.setCoin(updateCoinParam.getCoin());
		updateUser.setId(updateCoinParam.getUserId());
//		this.baseMapper.updateUserWalletByUserId(updateUser);
		this.updateUserWalletByUserId(updateUser);
	}
	@Override
	public void updateUserCoin(UserEntity user, Long coin, int type, int detailType) {
		UserEntity userUpdateParam = new UserEntity();
		userUpdateParam.setId(user.getId());
		userUpdateParam.setCoin(coin);
		this.updateUserWalletByUserId(userUpdateParam);
//		if(baseMapper.updateUserWalletByUserId(userUpdateParam)<=0) {
//			if(coin>0) {
//				throw new RRException(ErrorCode.UserErrorCode.USER_ACCOUNT_INVALID.getErrMsg(),
//						ErrorCode.UserErrorCode.USER_ACCOUNT_INVALID.getCode());
//			}else {
//				throw new RRException(ErrorCode.UserErrorCode.USER_COIN_NOT_ENOUGH.getErrMsg(),
//						ErrorCode.UserErrorCode.USER_COIN_NOT_ENOUGH.getCode());
//				
//			}
//		}
		// 保存交易记录
		UserTransactionRecordEntity transactionRecord = new UserTransactionRecordEntity();
		transactionRecord.setType(type);
		transactionRecord.setUserId(user.getId());
		transactionRecord.setUserAccount(user.getAccount());
		transactionRecord.setOrderNo(OrderNoUtil.getOrderNo());
		transactionRecord.setMoney(user.getMoney());
		transactionRecord.setAmount(
				new BigDecimal(Math.abs(coin)).divide(new BigDecimal(Constant.COIN_RATE), 2, BigDecimal.ROUND_HALF_UP));
		transactionRecord.setCoin(user.getCoin() + coin);
		transactionRecord.setDetailType(detailType);
		transactionRecord.setCommission(user.getCommission());
		userTransactionRecordService.insert(transactionRecord);
	}
	/**
	 * 根据用户Id修改用户金币，余额，佣金
	 */
	@Override
	public void updateUserWalletByUserId(UserEntity user) {
		//log.info("[updateUserWalletByUserId] user {} ", user);
		int i=0;
		try {
			i=baseMapper.updateUserWalletByUserId(user);
		} catch (Exception e) {
			log.error("updateUserWalletByUserId error",e);
		}
		if(i>0) {
			return;
		}
		throw new RRException(ErrorCode.UserErrorCode.MONEY_NOT_ENOUGH.getErrMsg(),
				ErrorCode.UserErrorCode.MONEY_NOT_ENOUGH.getCode());
	}
}

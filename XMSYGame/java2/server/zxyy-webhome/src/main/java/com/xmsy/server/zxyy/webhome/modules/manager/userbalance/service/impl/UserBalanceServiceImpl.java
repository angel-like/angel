package com.xmsy.server.zxyy.webhome.modules.manager.userbalance.service.impl;

import java.util.Date;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xmsy.server.zxyy.webhome.common.exception.RRException;
import com.xmsy.server.zxyy.webhome.common.utils.ErrorCode;
import com.xmsy.server.zxyy.webhome.modules.app.balance.param.SwitchOutParamNew;
import com.xmsy.server.zxyy.webhome.modules.app.balance.param.SwitchParam;
import com.xmsy.server.zxyy.webhome.modules.app.balance.param.SwitchParamNew;
import com.xmsy.server.zxyy.webhome.modules.manager.user.entity.UserEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.userbalance.dao.UserBalanceDao;
import com.xmsy.server.zxyy.webhome.modules.manager.userbalance.entity.UserBalanceEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.userbalance.service.UserBalanceService;


@Service("userBalanceService")
public class UserBalanceServiceImpl extends ServiceImpl<UserBalanceDao, UserBalanceEntity> implements UserBalanceService {

	@Override
	public boolean updateUserBalance(UserEntity user, SwitchParam param) {
		//2.修改(不存在就增加)用户余额宝金额表里的未收益金额
		UserBalanceEntity userBalanceEntity=new UserBalanceEntity();
		userBalanceEntity.setUserId(user.getId());//用户id
		Wrapper<UserBalanceEntity> entityWrapper = new EntityWrapper<UserBalanceEntity>(userBalanceEntity);
		userBalanceEntity = this.selectOne(entityWrapper);//通过用户id查询UserBalanceEntity
		Long unprofitMoney=0L;
		if(userBalanceEntity==null) {//第一次
			userBalanceEntity=new UserBalanceEntity();
			unprofitMoney=param.getNumber();
			userBalanceEntity.setUserId(user.getId());//用户id
			userBalanceEntity.setUnprofitMoney(unprofitMoney);//转入的金额
			userBalanceEntity.setUserAccount(user.getAccount());//用户账户
			userBalanceEntity.setCountDay(new Date());//计算时间
		}else {
			unprofitMoney=userBalanceEntity.getUnprofitMoney()+param.getNumber();
			userBalanceEntity.setUnprofitMoney(unprofitMoney);//转入的金额	
		}				
		return this.insertOrUpdate(userBalanceEntity);
	}

	@Override
	public UserBalanceEntity getUserBalance(UserEntity user) {
		UserBalanceEntity userBalanceEntity=new UserBalanceEntity();
		userBalanceEntity.setUserId(user.getId());//通过用户id查询userBalanceEntity
		Wrapper<UserBalanceEntity> entityWrapper = new EntityWrapper<UserBalanceEntity>(userBalanceEntity);
		userBalanceEntity = this.selectOne(entityWrapper);//通过用户id查询UserBalanceEntity
		if(userBalanceEntity==null) {
			throw new RRException(ErrorCode.UserErrorCode.USER_BALANCE_NULL_ERROR.getErrMsg(),
					ErrorCode.UserErrorCode.USER_BALANCE_NULL_ERROR.getCode());
		}	
		return userBalanceEntity;
	}

	//=============================================新版本功能==================================
	@Override
	public UserBalanceEntity getUserBalanceNew(UserEntity user,SwitchOutParamNew param) {
		UserBalanceEntity userBalanceEntity=new UserBalanceEntity();
		userBalanceEntity.setUserId(user.getId());//用户id
		userBalanceEntity.setUserBalanceProductId(param.getUserBalanceProductId());//理财产品id
		Wrapper<UserBalanceEntity> entityWrapper = new EntityWrapper<UserBalanceEntity>(userBalanceEntity);
		//通过  用户id + 理财产品id  查询UserBalanceEntity
		userBalanceEntity = this.selectOne(entityWrapper);
		if(userBalanceEntity==null) {
			throw new RRException(ErrorCode.UserErrorCode.USER_BALANCE_NULL_ERROR.getErrMsg(),
					ErrorCode.UserErrorCode.USER_BALANCE_NULL_ERROR.getCode());
		}	
		return userBalanceEntity;
	}
	@Override
	public boolean updateUserBalanceNew(UserEntity user, SwitchParamNew param) {
		//2.修改(不存在就增加)用户余额宝金额表里的未收益金额
		UserBalanceEntity userBalanceEntity=new UserBalanceEntity();
		userBalanceEntity.setUserId(user.getId());//用户id
		userBalanceEntity.setUserBalanceProductId(param.getUserBalanceProductId());//====理财产品id
		Wrapper<UserBalanceEntity> entityWrapper = new EntityWrapper<UserBalanceEntity>(userBalanceEntity);
		userBalanceEntity = this.selectOne(entityWrapper);//通过用户id+理财产品id   查询UserBalanceEntity
		Long unprofitMoney=0L;
		if(userBalanceEntity==null) {//第一次
			userBalanceEntity=new UserBalanceEntity();
			unprofitMoney=param.getNumber();
			userBalanceEntity.setUserId(user.getId());//用户id
			userBalanceEntity.setUserBalanceProductId(param.getUserBalanceProductId());//====理财产品id
			userBalanceEntity.setUnprofitMoney(unprofitMoney);//转入的金额
			userBalanceEntity.setUserAccount(user.getAccount());//用户账户
			userBalanceEntity.setCountDay(new Date());//计算时间
		}else {//否则就修改 未计算收益的金额  就行
			unprofitMoney=userBalanceEntity.getUnprofitMoney()+param.getNumber();
			userBalanceEntity.setUnprofitMoney(unprofitMoney);//转入的金额	
		}				
		return this.insertOrUpdate(userBalanceEntity);
	}
}

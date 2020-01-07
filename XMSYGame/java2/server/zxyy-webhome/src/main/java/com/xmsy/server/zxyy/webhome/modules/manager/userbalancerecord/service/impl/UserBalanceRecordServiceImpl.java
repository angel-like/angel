package com.xmsy.server.zxyy.webhome.modules.manager.userbalancerecord.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
import com.xmsy.server.zxyy.webhome.modules.manager.userbalancerecord.dao.UserBalanceRecordDao;
import com.xmsy.server.zxyy.webhome.modules.manager.userbalancerecord.entity.UserBalanceRecordEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.userbalancerecord.service.UserBalanceRecordService;


@Service("userBalanceRecordService")
public class UserBalanceRecordServiceImpl extends ServiceImpl<UserBalanceRecordDao, UserBalanceRecordEntity> implements UserBalanceRecordService {
	@Autowired
	private UserBalanceRecordDao UserBalanceRecordDao;
	/**
	 * 根据会员用户转入的金额数量
	 * 		插入用户账户金额存取记录数据
	 */
	@Override
	public boolean insertIntoUserBalanceRecord(UserEntity user, SwitchParam param) {
		UserBalanceRecordEntity userBalanceRecordEntity=new UserBalanceRecordEntity();
		userBalanceRecordEntity.setUserId(user.getId());//用户id
		userBalanceRecordEntity.setUserAccount(user.getAccount());
		userBalanceRecordEntity.setMoney(param.getNumber());//交易金额
		userBalanceRecordEntity.setEffect(false);//未生效  是否生效
		userBalanceRecordEntity.setType(0);// '类型 0:存入1:取出'
		boolean flag= this.insert(userBalanceRecordEntity);
		if(!flag) {
			throw new RRException(ErrorCode.UserErrorCode.USER_BALANCE_RECORD_ERROR.getErrMsg(),
					ErrorCode.UserErrorCode.USER_BALANCE_RECORD_ERROR.getCode());
		}
		return flag;
	}

	@Override
	public List<UserBalanceRecordEntity> getListUserBalanceRecord(Long userId) {
		UserBalanceRecordEntity userBalanceRecordEntity=new UserBalanceRecordEntity();
		userBalanceRecordEntity.setUserId(userId);
		userBalanceRecordEntity.setType(0);//类型 0:存入1:取出
		userBalanceRecordEntity.setEffect(false);//是否生效
		List<UserBalanceRecordEntity> listRecord=UserBalanceRecordDao.getListUserBalanceRecord(userBalanceRecordEntity);
		return listRecord;
	}

	@Override
	public List<UserBalanceRecordEntity> getAllListUserBalanceRecord(Long userId) {
		UserBalanceRecordEntity userBalanceRecordEntity=new UserBalanceRecordEntity();
		userBalanceRecordEntity.setUserId(userId);
		Wrapper<UserBalanceRecordEntity> wrapper = new EntityWrapper<UserBalanceRecordEntity>(userBalanceRecordEntity);
		wrapper.orderBy("id", false);
		List<UserBalanceRecordEntity> listRecord=this.selectList(wrapper);
		return listRecord;
	}
	//==================================新版本余额宝========================================
	/**
	 * 根据会员用户转入的金额数量
	 * 		插入用户账户金额存取记录数据	--新版本余额宝
	 */
	@Override
	public boolean insertIntoUserBalanceRecordNew(UserEntity user, SwitchParamNew param) {
		UserBalanceRecordEntity userBalanceRecordEntity=new UserBalanceRecordEntity();
		userBalanceRecordEntity.setUserId(user.getId());//用户id
		userBalanceRecordEntity.setUserBalanceProductId(param.getUserBalanceProductId());//===理财产品id
		userBalanceRecordEntity.setUserAccount(user.getAccount());
		userBalanceRecordEntity.setMoney(param.getNumber());//交易金额
		userBalanceRecordEntity.setEffect(false);//未生效  是否生效
		userBalanceRecordEntity.setType(0);// '类型 0:存入1:取出'
		boolean flag= this.insert(userBalanceRecordEntity);
		if(!flag) {
			throw new RRException(ErrorCode.UserErrorCode.USER_BALANCE_RECORD_ERROR.getErrMsg(),
					ErrorCode.UserErrorCode.USER_BALANCE_RECORD_ERROR.getCode());
		}
		return flag;
	}
	/**
	 * 通过用户id+理财产品id  查询 用户账户金额存取记录表		
	 * 		条件是  1. 类型：存入				--新版本余额宝
	 * 			 2.是否生效：未生效的  
	 * 			 3.money>takeMoney
	 * @param userId
	 */
	@Override
	public List<UserBalanceRecordEntity> getListUserBalanceRecordNew(Long userId,SwitchOutParamNew param) {
		UserBalanceRecordEntity userBalanceRecordEntity=new UserBalanceRecordEntity();
		userBalanceRecordEntity.setUserId(userId);//用户id
		userBalanceRecordEntity.setUserBalanceProductId(param.getUserBalanceProductId());//理财产品id
		userBalanceRecordEntity.setType(0);//类型 0:存入1:取出
		userBalanceRecordEntity.setEffect(false);//是否生效
		List<UserBalanceRecordEntity> listRecord=UserBalanceRecordDao.getListUserBalanceRecord(userBalanceRecordEntity);
		return listRecord;
	}
}

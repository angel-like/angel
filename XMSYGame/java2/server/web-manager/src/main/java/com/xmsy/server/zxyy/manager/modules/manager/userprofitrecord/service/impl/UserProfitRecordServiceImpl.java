package com.xmsy.server.zxyy.manager.modules.manager.userprofitrecord.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xmsy.common.define.page.PageParam;
import com.xmsy.server.zxyy.manager.common.exception.RRException;
import com.xmsy.server.zxyy.manager.common.utils.ErrorCode;
import com.xmsy.server.zxyy.manager.modules.manager.user.entity.UserEntity;
import com.xmsy.server.zxyy.manager.modules.manager.userprofitrecord.dao.UserProfitRecordDao;
import com.xmsy.server.zxyy.manager.modules.manager.userprofitrecord.entity.UserProfitRecordEntity;
import com.xmsy.server.zxyy.manager.modules.manager.userprofitrecord.service.UserProfitRecordService;


@Service("userProfitRecordService")
public class UserProfitRecordServiceImpl extends ServiceImpl<UserProfitRecordDao, UserProfitRecordEntity> implements UserProfitRecordService {
	@Override
	public Page<UserProfitRecordEntity> getList(UserProfitRecordEntity userProfitRecordEntity, PageParam pageParam){

		Page<UserProfitRecordEntity> page = new Page<>(pageParam.getPage(), pageParam.getLimit());// 当前页，总条数 构造 page 对象
		return page.setRecords(this.baseMapper.getList(page, userProfitRecordEntity));
	};
	@Override
	public List<UserProfitRecordEntity> getUserProfitRecordByUserId(UserEntity user) {
		UserProfitRecordEntity userProfitRecord=new UserProfitRecordEntity();
		userProfitRecord.setUserId(user.getId());
		Wrapper<UserProfitRecordEntity> entityWrapper = new EntityWrapper<UserProfitRecordEntity>(userProfitRecord);
		List<UserProfitRecordEntity> list=this.selectList(entityWrapper);
		if(list==null) {
			throw new RRException(ErrorCode.UserErrorCode.USER_IP_ISNULL_ERRO.getErrMsg(),
					ErrorCode.UserErrorCode.USER_IP_ISNULL_ERRO.getCode());
		}
		return list;
	}

	@Override
	public UserProfitRecordEntity getUserProfitRecordByUserIdAndDate(UserEntity user, Date day) {
		UserProfitRecordEntity userProfitRecord=new UserProfitRecordEntity();
		userProfitRecord.setUserId(user.getId());
		userProfitRecord.setIncomeDate(day);
		Wrapper<UserProfitRecordEntity> entityWrapper = new EntityWrapper<UserProfitRecordEntity>(userProfitRecord);
		userProfitRecord=this.selectOne(entityWrapper);
		return userProfitRecord;
	}


}

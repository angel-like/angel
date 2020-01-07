package com.xmsy.server.zxyy.manager.modules.manager.userinfo.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xmsy.server.zxyy.manager.common.utils.PageUtils;
import com.xmsy.server.zxyy.manager.common.utils.Query;
import com.xmsy.server.zxyy.manager.modules.manager.user.dao.UserDao;
import com.xmsy.server.zxyy.manager.modules.manager.user.entity.UserEntity;
import com.xmsy.server.zxyy.manager.modules.manager.userinfo.dao.UserInfoDao;
import com.xmsy.server.zxyy.manager.modules.manager.userinfo.entity.UserInfoEntity;
import com.xmsy.server.zxyy.manager.modules.manager.userinfo.entity.result.UserPushInfo;
import com.xmsy.server.zxyy.manager.modules.manager.userinfo.service.UserInfoService;

@Transactional
@Service("userInfoService")
public class UserInfoServiceImpl extends ServiceImpl<UserInfoDao, UserInfoEntity> implements UserInfoService {

	@Autowired
	private UserDao userdao;
	
	@Override
	public PageUtils queryPage(Map<String, Object> params) {
		Page<UserInfoEntity> page = this.selectPage(new Query<UserInfoEntity>(params).getPage(),
				new EntityWrapper<UserInfoEntity>());

		return new PageUtils(page);
	}

	@Override
	public Integer updateUserBaseInfo(UserInfoEntity entity) {
		if (!StringUtils.isBlank(entity.getUserName())) {
			UserEntity user = new UserEntity();
			user.setId(entity.getUserId());
			user.setUserName(entity.getUserName());
			user.updateById();
		}
		if(StringUtils.isBlank(entity.getRemark()) || entity.getRemark()=="") {
			userdao.setReamrkNull(entity.getUserId());
		}else {
			UserEntity users = new UserEntity();
			users.setId(entity.getUserId());
			users.setRemark(entity.getRemark());
			users.updateById();
		}
		if(StringUtils.isBlank(entity.getUserPhone()) || entity.getUserPhone()=="") {
			userdao.setPhoneNull(entity.getUserId());
		}else {
			UserEntity users = new UserEntity();
			users.setId(entity.getUserId());
			users.setPhone(entity.getUserPhone());
			users.updateById();
		}
		

		return this.baseMapper.updateUserBaseInfo(entity);
	}

	@Override
	public Integer deleteUserInfoByUserId(Long userId) {
		return this.baseMapper.deleteUserInfoByUserId(userId);
	}

	@Override
	public Integer updateByUserId(UserInfoEntity entity) {
		return this.baseMapper.updateByUserId(entity);
	}

	@Override
	public List<UserPushInfo> findUserInfoListByAccount(List<String> accountList) {
		return this.baseMapper.findUserInfoListByAccount(accountList);
	}

	@Override
	public List<String> findUserInfoListByhierarchyId(List<String> hierarchyIds) {
		return this.baseMapper.findUserInfoListByhierarchyId(hierarchyIds);
	}

}

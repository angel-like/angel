package com.xmsy.server.zxyy.webhome.modules.manager.usertrialconfig.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xmsy.server.zxyy.webhome.common.utils.MathUtil;
import com.xmsy.server.zxyy.webhome.common.utils.OrderNoUtil;
import com.xmsy.server.zxyy.webhome.common.utils.PageUtils;
import com.xmsy.server.zxyy.webhome.common.utils.Query;
import com.xmsy.server.zxyy.webhome.constant.SysConstant;
import com.xmsy.server.zxyy.webhome.modules.manager.user.dao.UserDao;
import com.xmsy.server.zxyy.webhome.modules.manager.user.entity.UserEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.userhierarchy.entity.UserHierarchyEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.userhierarchy.service.impl.UserHierarchyServiceImpl;
import com.xmsy.server.zxyy.webhome.modules.manager.usertrialconfig.dao.UserTrialConfigDao;
import com.xmsy.server.zxyy.webhome.modules.manager.usertrialconfig.entity.UserTrialConfigEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.usertrialconfig.service.UserTrialConfigService;

@Transactional
@Service("userTrialConfigService")
public class UserTrialConfigServiceImpl extends ServiceImpl<UserTrialConfigDao, UserTrialConfigEntity> implements UserTrialConfigService {
	@Autowired
	private UserDao userDao;
	@Autowired
	private UserHierarchyServiceImpl userHierarchyService;
	
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<UserTrialConfigEntity> page = this.selectPage(
                new Query<UserTrialConfigEntity>(params).getPage(),
                new EntityWrapper<UserTrialConfigEntity>()
        );

        return new PageUtils(page);
    }

	@Override
	public void save(UserTrialConfigEntity entity) {
		// 获取默认层级ID
		UserHierarchyEntity hierarchy = userHierarchyService.getDefaultHierarchy();
		Long hierarchyId = hierarchy == null ? 0l : hierarchy.getId();// 获取默认层级ID
		//试玩账号的数量是否改变
		boolean unchanged=true;
		if(entity.getId() != null && entity.getId()>0) {
			UserTrialConfigEntity newEntit=this.baseMapper.selectById(entity.getId());
			unchanged=entity.getTrialNumber().equals(newEntit.getTrialNumber());
			this.baseMapper.updateById(entity);
		}else {
			this.baseMapper.insert(entity);
		}
		if(!unchanged) {//试玩账号的数量发生改变
			List<UserEntity> trialUserList = userDao.getTrialUserList();
			if(trialUserList!=null && trialUserList.size()>0) {
				int unDelNum=0;//未删除人数
				int delNum=0;//删除人数
				List<UserEntity> unDelUserList=new ArrayList<>();//未删除列表
				List<UserEntity> delUserList=new ArrayList<>();//删除列表
				for(UserEntity trialUser : trialUserList) {
					if(trialUser.getDeleteStatus()==0) {
						unDelUserList.add(trialUser);
						unDelNum++;
					}else {
						delUserList.add(trialUser);
						delNum++;
					}
				}
				int differenceNum=entity.getTrialNumber().intValue()-unDelNum;
				int changeNum=Math.abs(differenceNum);//绝对值
				if(differenceNum>0) {//需要新增试玩账号
					int newNum=0;//需要重新创建的个数
					if(delNum>0) {
						int updateNum=Math.min(changeNum, delNum);//从删除列表更新为可用个数
						for(int i=0;i<updateNum;i++) {
							userDao.updateUserDeleteStatus(delUserList.get(i).getId());
						}
						if(delNum<changeNum) {//已删除的不足于需要新增个数
							newNum=changeNum-delNum;
						}
					}else {
						newNum=changeNum;
					}
					if(newNum>0) {
						Set<String> accountList = getAccount(newNum);
						for(String account: accountList) {
							UserEntity trialUser=new UserEntity();
							trialUser.setAccount(account);
							trialUser.setPortrait(MathUtil.getRandomPortrait(MathUtil.getRandomSex()));// 用户头像
							trialUser.setSex(MathUtil.getRandomSex());
							trialUser.setUserType(SysConstant.TRIAL.toUpperCase());
							trialUser.setAgentEnable(false);
							trialUser.setHierarchyId(hierarchyId);
							userDao.insert(trialUser);
						}
					}
				}else if(differenceNum<0){//需要删除试玩账号
					for(int i=0;i<changeNum;i++) {
						userDao.deleteById(unDelUserList.get(i).getId());
					}
				}
			}else {
				Set<String> accountList = getAccount(entity.getTrialNumber().intValue());
				for(String account: accountList) {
					UserEntity trialUser=new UserEntity();
					trialUser.setAccount(account);
					trialUser.setUserType(SysConstant.TRIAL.toUpperCase());
					trialUser.setPortrait(MathUtil.getRandomPortrait(MathUtil.getRandomSex()));// 用户头像
					trialUser.setSex(MathUtil.getRandomSex());
					trialUser.setAgentEnable(false);
					trialUser.setHierarchyId(0L);
					trialUser.setHierarchyId(hierarchyId);
					userDao.insert(trialUser);
				}
			}
			
			
		}
		
	}
	
	private Set<String> getAccount(int len){
		Set<String> accountSet=new HashSet<>();
		while (accountSet.size()<len) {
			String account="shiw"+OrderNoUtil.getRandomAlphanumeric(6);
			if(userDao.countUserByAccount(account)==0) {
				accountSet.add(account);
			}
		}
		return accountSet;
	}

}

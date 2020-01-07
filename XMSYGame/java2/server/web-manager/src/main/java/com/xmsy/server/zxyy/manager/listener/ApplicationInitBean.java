package com.xmsy.server.zxyy.manager.listener;

import java.util.List;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.xmsy.server.zxyy.manager.cache.LocalContentCache;
import com.xmsy.server.zxyy.manager.modules.manager.userhierarchy.entity.UserHierarchyEntity;
import com.xmsy.server.zxyy.manager.modules.manager.userhierarchy.service.UserHierarchyService;
import com.xmsy.server.zxyy.manager.modules.manager.uservip.entity.UserVipEntity;
import com.xmsy.server.zxyy.manager.modules.manager.uservip.service.UserVipService;

@Component
public class ApplicationInitBean implements InitializingBean {
	@Autowired
	private LocalContentCache localContentCache;
	@Autowired
	private UserVipService userVipService;
	@Autowired
	private UserHierarchyService userHierarchyService;
	@Override
	public void afterPropertiesSet() throws Exception {
		List<UserVipEntity> vipList = userVipService.selectList(new EntityWrapper<UserVipEntity>(new UserVipEntity()));
		if(vipList!=null && !vipList.isEmpty()) {
			for(UserVipEntity vip:vipList) {
				localContentCache.putVipName(vip.getId(), vip.getName());
			}
		}
		
		List<UserHierarchyEntity> hierarchyList = userHierarchyService.selectList(new EntityWrapper<UserHierarchyEntity>(new UserHierarchyEntity()));
		if(hierarchyList!=null && !hierarchyList.isEmpty()) {
			for(UserHierarchyEntity hierarchy:hierarchyList) {
				localContentCache.putHierarchypName(hierarchy.getId(), hierarchy.getName());
			}
		}
		
	}

}

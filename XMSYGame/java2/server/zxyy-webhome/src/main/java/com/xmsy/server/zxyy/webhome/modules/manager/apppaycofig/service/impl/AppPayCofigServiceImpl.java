package com.xmsy.server.zxyy.webhome.modules.manager.apppaycofig.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xmsy.server.zxyy.webhome.common.exception.RRException;
import com.xmsy.server.zxyy.webhome.common.utils.ErrorCode;
import com.xmsy.server.zxyy.webhome.modules.app.configuration.param.ConfigUrationParam;
import com.xmsy.server.zxyy.webhome.modules.manager.apppaycofig.dao.AppPayCofigDao;
import com.xmsy.server.zxyy.webhome.modules.manager.apppaycofig.entity.AppPayCofigEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.apppaycofig.service.AppPayCofigService;
import com.xmsy.server.zxyy.webhome.modules.manager.user.entity.UserEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.user.service.UserService;
import com.xmsy.server.zxyy.webhome.modules.manager.userhierarchy.entity.UserHierarchyEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.userhierarchy.service.UserHierarchyService;

import lombok.extern.slf4j.Slf4j;

@Slf4j	
@Transactional
@Service("appPayCofigService")
public class AppPayCofigServiceImpl extends ServiceImpl<AppPayCofigDao, AppPayCofigEntity> implements AppPayCofigService {
	@Autowired
	private UserService userService;
	@Autowired
	private UserHierarchyService userHierarchyService;
	
	
	@Override
	public List<ConfigUrationParam> selectPayConfigList(Long userId) {
		
		List<ConfigUrationParam> list=new ArrayList<ConfigUrationParam>();
		log.info("[selectPayConfigList] userId {}", userId);
		UserEntity entity=userService.selectById(userId);
		log.info("[selectPayConfigList] entity {}", entity);
		if(entity==null) {
			throw new RRException(ErrorCode.UserErrorCode.USER_IS_NULL.getErrMsg(),
					ErrorCode.UserErrorCode.USER_IS_NULL.getCode());
		}
		UserHierarchyEntity userHierarchy=userHierarchyService.selectById(entity.getHierarchyId());
		log.info("[selectPayConfigList] userHierarchy {}", userHierarchy);
		if(userHierarchy.getVipEnable()) {
			list=baseMapper.selectPayConfigList();
			log.info("[selectPayConfigList] list {}", list);
		}
		return list;
	}


}

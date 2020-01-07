package com.xmsy.server.zxyy.webhome.modules.manager.imcontact.service;

import java.util.List;

import com.baomidou.mybatisplus.service.IService;
import com.xmsy.server.zxyy.webhome.modules.manager.imcontact.entity.ImContactEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.imcontact.entity.ImContactManagerEntity;


/**
 * 33联系方式
 *
 * @author axiong
 * @email xxxxx
 * @date 2019-07-01 14:20:14
 */
public interface ImContactService extends IService<ImContactEntity> {
	
	List<ImContactManagerEntity> getImContact();

}


package com.xmsy.server.zxyy.webhome.modules.manager.imcontact.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xmsy.server.zxyy.webhome.modules.manager.imcontact.dao.ImContactDao;
import com.xmsy.server.zxyy.webhome.modules.manager.imcontact.entity.ImContactEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.imcontact.entity.ImContactManagerEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.imcontact.service.ImContactService;


@Service("imContactService")
public class ImContactServiceImpl extends ServiceImpl<ImContactDao, ImContactEntity> implements ImContactService {

	@Override
	public List<ImContactManagerEntity> getImContact() {
		return baseMapper.selectListByIm();
	}



}

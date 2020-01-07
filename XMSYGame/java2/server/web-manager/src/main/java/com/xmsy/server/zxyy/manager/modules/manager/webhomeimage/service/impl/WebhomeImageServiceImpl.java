package com.xmsy.server.zxyy.manager.modules.manager.webhomeimage.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xmsy.server.zxyy.manager.modules.manager.webhomeimage.dao.WebhomeImageDao;
import com.xmsy.server.zxyy.manager.modules.manager.webhomeimage.entity.WebHomeImageManagementEntity;
import com.xmsy.server.zxyy.manager.modules.manager.webhomeimage.entity.WebhomeImageEntity;
import com.xmsy.server.zxyy.manager.modules.manager.webhomeimage.service.WebhomeImageService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Transactional
@Service("imageManagementService")
public class WebhomeImageServiceImpl extends ServiceImpl<WebhomeImageDao, WebhomeImageEntity> implements WebhomeImageService {


    //修改图片状态
	@Override
	public void enable(Long id, Boolean enable) {
		WebhomeImageEntity imageManagement=new WebhomeImageEntity();
    	imageManagement.setId(id);
    	imageManagement.setEnable(enable);
    	log.info("[enable] imageManagement {}", imageManagement);
    	baseMapper.updateById(imageManagement);
		
	}

	@Override
	public List<WebHomeImageManagementEntity> selectListByWeb() {
		List<WebHomeImageManagementEntity> list=baseMapper.selectListByWeb();
		return list;
	}

}

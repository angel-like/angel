package com.xmsy.server.zxyy.webhome.modules.manager.webhomeimage.service;

import java.util.List;

import com.baomidou.mybatisplus.service.IService;
import com.xmsy.server.zxyy.webhome.modules.manager.webhomeimage.entity.WebHomeImageManagementEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.webhomeimage.entity.WebhomeImageEntity;

/**
 * 官网图片管理
 *
 * @author aleng
 * @email xxxxx
 * @date 2018-12-31 16:23:23
 */
public interface WebhomeImageService extends IService<WebhomeImageEntity> {

    //修改图片状态
	void enable(Long id, Boolean enable);
	//官网获取图片列表
	List<WebHomeImageManagementEntity> selectListByWeb();
}


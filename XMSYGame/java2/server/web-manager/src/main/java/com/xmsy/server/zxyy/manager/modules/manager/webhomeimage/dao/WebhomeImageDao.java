package com.xmsy.server.zxyy.manager.modules.manager.webhomeimage.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.xmsy.server.zxyy.manager.modules.manager.webhomeimage.entity.WebHomeImageManagementEntity;
import com.xmsy.server.zxyy.manager.modules.manager.webhomeimage.entity.WebhomeImageEntity;

/**
 * 官网图片管理
 * 
 * @author aleng
 * @email xxxxx
 * @date 2018-12-31 16:23:23
 */
@Mapper
public interface WebhomeImageDao extends BaseMapper<WebhomeImageEntity> {
	//获取图片路径
	List<WebHomeImageManagementEntity> selectListByWeb();
	
}

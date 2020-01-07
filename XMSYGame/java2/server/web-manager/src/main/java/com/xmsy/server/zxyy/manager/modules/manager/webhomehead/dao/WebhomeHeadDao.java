package com.xmsy.server.zxyy.manager.modules.manager.webhomehead.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.xmsy.server.zxyy.manager.modules.manager.webhomehead.entity.WebhomeHeadEntity;


/**
 * 官网头部管理
 * 
 * @author aleng
 * @email xxxxx
 * @date 2019-01-24 20:28:36
 */
@Mapper
public interface WebhomeHeadDao extends BaseMapper<WebhomeHeadEntity> {

	List<WebhomeHeadEntity> selectListByWeb();

	List<WebhomeHeadEntity> selectListBytype(@Param("type") String type);
	
}

package com.xmsy.server.zxyy.manager.modules.manager.webhomecontact.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.xmsy.server.zxyy.manager.modules.manager.webhomecontact.entity.WebContactManagementEntity;
import com.xmsy.server.zxyy.manager.modules.manager.webhomecontact.entity.WebhomeContactEntity;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

/**
 * 联系管理
 * 
 * @author aleng
 * @email xxxxx
 * @date 2019-01-01 16:52:13
 */
@Mapper
public interface WebhomeContactDao extends BaseMapper<WebhomeContactEntity> {

	List<WebContactManagementEntity> selectListByWeb();
	
}

package com.xmsy.server.zxyy.manager.modules.manager.webhomefriendship.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.xmsy.server.zxyy.manager.modules.manager.webhomefriendship.entity.WebhomeFriendshipEntity;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

/**
 * 友情链接管理
 * 
 * @author aleng
 * @email xxxxx
 * @date 2019-01-07 11:02:39
 */
@Mapper
public interface WebhomeFriendshipDao extends BaseMapper<WebhomeFriendshipEntity> {

	List<WebhomeFriendshipEntity> selectFriendshipList();
	
}

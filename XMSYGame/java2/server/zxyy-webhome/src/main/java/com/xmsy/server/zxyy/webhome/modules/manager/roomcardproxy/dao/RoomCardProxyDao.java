package com.xmsy.server.zxyy.webhome.modules.manager.roomcardproxy.dao;

import com.xmsy.server.zxyy.webhome.modules.manager.roomcardproxy.entity.RoomCardProxyEntity;
import com.baomidou.mybatisplus.mapper.BaseMapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

/**
 * 房卡代理说明
 * 
 * @author axiong
 * @email xxxxx
 * @date 2019-07-02 10:58:28
 */
@Mapper
public interface RoomCardProxyDao extends BaseMapper<RoomCardProxyEntity> {
	List<Map<String,Object>> selectRoomCardProxy();
}

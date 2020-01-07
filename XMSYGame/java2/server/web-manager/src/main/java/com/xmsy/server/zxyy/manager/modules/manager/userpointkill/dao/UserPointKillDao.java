package com.xmsy.server.zxyy.manager.modules.manager.userpointkill.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.xmsy.server.zxyy.manager.modules.manager.userpointkill.entity.UserPointKillEntity;
import com.xmsy.server.zxyy.manager.modules.manager.userpointkill.param.UserPointKillManagerParam;

/**
 * 点杀名单
 * 
 * @author aye
 * @email xxxxx
 * @date 2019-11-22 11:27:03
 */
@Mapper
public interface UserPointKillDao extends BaseMapper<UserPointKillEntity> {
	/**
	 * 点杀监管分页查询--游戏记录为主
	 */
	List<Map<String, Object>> findUserPointKillManagerPage(@Param("userPointKillManagerParam")UserPointKillManagerParam userPointKillManagerParam,
			Page<Map<String, Object>> page);
}

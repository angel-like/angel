package com.xmsy.server.zxyy.manager.modules.manager.uservip.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.xmsy.server.zxyy.manager.modules.manager.uservip.entity.UserVipEntity;

/**
 * 用户vip等级表
 * 
 * @author xiaoling
 * @email xxxxx
 * @date 2019-06-10 15:18:13
 */
@Mapper
public interface UserVipDao extends BaseMapper<UserVipEntity> {
	List<Map<String, Object>> findUserVipForSelect();
}

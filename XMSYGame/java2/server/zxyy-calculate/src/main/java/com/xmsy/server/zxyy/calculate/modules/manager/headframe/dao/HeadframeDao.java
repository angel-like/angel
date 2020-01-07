package com.xmsy.server.zxyy.calculate.modules.manager.headframe.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.xmsy.server.zxyy.calculate.modules.manager.headframe.entity.HeadframeEntity;

/**
 * 头像框表
 * 
 * @author axiong
 * @email xxxxx
 * @date 2019-06-19 10:17:16
 */
@Mapper
public interface HeadframeDao extends BaseMapper<HeadframeEntity> {
	List<Long> getHeadframeList(@Param("sort") Integer sort);
}

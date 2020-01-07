package com.xmsy.server.zxyy.webhome.modules.manager.imshuffling.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.xmsy.server.zxyy.webhome.modules.manager.imshuffling.entity.ImShufflingEntity;
import com.xmsy.server.zxyy.webhome.modules.webim.homepage.result.ImShufflingResult;

/**
 * 33轮播图管理
 * 
 * @author aleng
 * @email xxxxx
 * @date 2019-06-21 11:09:52
 */
@Mapper
public interface ImShufflingDao extends BaseMapper<ImShufflingEntity> {

	/**
	 * 获取删除状态为0 启用的轮播图 通过orderNo排序
	 * 
	 * @author xiaoliu
	 *
	 * @return
	 */
	List<ImShufflingResult> selectShufflingList();

}

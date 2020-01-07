package com.xmsy.server.zxyy.webhome.modules.manager.imservicemanager.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.xmsy.server.zxyy.webhome.modules.manager.imservicemanager.entity.ImServiceManagerEntity;
import com.xmsy.server.zxyy.webhome.modules.webim.homepage.result.ImServiceManagerResult;

/**
 * 33娱乐服务器管理
 * 
 * @author aleng
 * @email xxxxx
 * @date 2019-06-20 16:35:33
 */
@Mapper
public interface ImServiceManagerDao extends BaseMapper<ImServiceManagerEntity> {
	/**
	 * 获取已启用服务器名称列表
	 * 
	 * @return List<ImServiceManagerResult>
	 */
	List<ImServiceManagerResult> selectServiceList(Pagination page);

}

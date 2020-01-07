package com.xmsy.server.zxyy.manager.modules.manager.sysmessagemodelprop.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.xmsy.server.zxyy.manager.modules.manager.sysmessagemodelprop.entity.SysMessageModelPropEntity;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 站内信
 * 
 * @author xiaoling
 * @email xxxxx
 * @date 2019-05-22 16:15:53
 */
@Mapper
public interface SysMessageModelPropDao extends BaseMapper<SysMessageModelPropEntity> {
	
	/**
	 * 根据模板邮件id查询列表信息
	 * @return
	 */
	List<SysMessageModelPropEntity> queryByMessageId(Pagination page, @Param("prop") SysMessageModelPropEntity prop);
	
}

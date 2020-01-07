package com.xmsy.server.zxyy.manager.modules.manager.webhomemenutemplate.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.xmsy.server.zxyy.manager.modules.manager.webhomemenutemplate.entity.WebhomeMenuTemplateEntity;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 菜单模板表
 * 
 * @author aleng
 * @email xxxxx
 * @date 2019-01-02 19:32:58
 */
@Mapper
public interface WebhomeMenuTemplateDao extends BaseMapper<WebhomeMenuTemplateEntity> {

	List<WebhomeMenuTemplateEntity> selectListForWeb(@Param("menuId")Long menuId);
	
}

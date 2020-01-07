package com.xmsy.server.zxyy.manager.modules.manager.webhomeenclosure.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.xmsy.server.zxyy.manager.modules.manager.webhomeenclosure.entity.EnclosureResult;
import com.xmsy.server.zxyy.manager.modules.manager.webhomeenclosure.entity.WebhomeEnclosureEntity;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 文件附件表
 * 
 * @author aleng
 * @email xxxxx
 * @date 2018-12-31 15:05:47
 */
@Mapper
public interface WebhomeEnclosureDao extends BaseMapper<WebhomeEnclosureEntity> {

	void insertGetId(WebhomeEnclosureEntity entity);
      List<EnclosureResult> getGroupInfo();
	
}

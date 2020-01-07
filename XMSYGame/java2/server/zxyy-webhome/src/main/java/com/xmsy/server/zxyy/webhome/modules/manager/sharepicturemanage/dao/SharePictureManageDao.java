package com.xmsy.server.zxyy.webhome.modules.manager.sharepicturemanage.dao;

import com.xmsy.server.zxyy.webhome.modules.manager.sharepicturemanage.entity.SharePictureManageEntity;
import com.baomidou.mybatisplus.mapper.BaseMapper;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

/**
 * 分享图片管理
 * 
 * @author axiong
 * @email xxxxx
 * @date 2019-07-08 15:13:55
 */
@Mapper
public interface SharePictureManageDao extends BaseMapper<SharePictureManageEntity> {
	/**
	 * 查询最近插入的一条分享图片管理记录
	 * 		条件为启用的
	 * @return
	 */
	Map<String,Object> findOneSharePictureManage();
}

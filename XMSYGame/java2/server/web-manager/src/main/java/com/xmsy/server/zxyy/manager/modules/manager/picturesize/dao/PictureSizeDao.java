package com.xmsy.server.zxyy.manager.modules.manager.picturesize.dao;

import com.xmsy.server.zxyy.manager.modules.manager.picturesize.entity.PictureSizeEntity;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 图片尺寸配置
 * 
 * @author xiaoling
 * @email xxxxx
 * @date 2019-09-25 11:03:55
 */
@Mapper
public interface PictureSizeDao extends BaseMapper<PictureSizeEntity> {
	
	/**
	 * 通过Integer typeId,Integer menuId查询图片尺寸
	 * @param typeId
	 * @param menuId
	 * @return
	 */
	String selectPictureSize(@Param("typeId") Integer typeId,@Param("menuId") Integer menuId);
	
}

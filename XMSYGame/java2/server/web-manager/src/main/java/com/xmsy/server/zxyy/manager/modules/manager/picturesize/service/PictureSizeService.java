package com.xmsy.server.zxyy.manager.modules.manager.picturesize.service;

import com.baomidou.mybatisplus.service.IService;
import com.xmsy.server.zxyy.manager.modules.manager.picturesize.entity.PictureSizeEntity;


/**
 * 图片尺寸配置
 *
 * @author xiaoling
 * @email xxxxx
 * @date 2019-09-25 11:03:55
 */
public interface PictureSizeService extends IService<PictureSizeEntity> {
	
	/**
	 * 通过Integer typeId,Integer menuId查询图片尺寸
	 * @param typeId
	 * @param menuId
	 * @return
	 */
	String selectPictureSize(Integer typeId,Integer menuId);

}


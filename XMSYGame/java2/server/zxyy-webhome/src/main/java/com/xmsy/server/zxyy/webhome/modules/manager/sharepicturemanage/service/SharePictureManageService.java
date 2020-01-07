package com.xmsy.server.zxyy.webhome.modules.manager.sharepicturemanage.service;

import java.util.Map;

import com.baomidou.mybatisplus.service.IService;
import com.xmsy.server.zxyy.webhome.modules.manager.sharepicturemanage.entity.SharePictureManageEntity;


/**
 * 分享图片管理
 *
 * @author axiong
 * @email xxxxx
 * @date 2019-07-08 15:13:55
 */
public interface SharePictureManageService extends IService<SharePictureManageEntity> {
	/**
	 * 查询最近插入的一条分享图片管理记录
	 * 		条件为启用的
	 * @return
	 */
	Map<String,Object> findOneSharePictureManage();

}


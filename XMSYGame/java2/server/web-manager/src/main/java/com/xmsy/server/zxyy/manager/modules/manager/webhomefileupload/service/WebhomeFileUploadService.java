package com.xmsy.server.zxyy.manager.modules.manager.webhomefileupload.service;

import com.baomidou.mybatisplus.service.IService;
import com.xmsy.server.zxyy.manager.modules.manager.webhomefilepackage.entity.WebhomeFilePackageEntity;
import com.xmsy.server.zxyy.manager.modules.manager.webhomefileupload.entity.WebhomeFileUploadEntity;


/**
 * 文件上传
 *
 * @author axiong
 * @email xxxxx
 * @date 2019-08-16 11:52:13
 */
public interface WebhomeFileUploadService extends IService<WebhomeFileUploadEntity> {
	/**
	 * 调用第三方打包接口
	 * @param webhomeFileUpload
	 * @return
	 */
	public String doPackage(WebhomeFileUploadEntity webhomeFileUpload,WebhomeFilePackageEntity webhomeFilePackage);
	/**
	 * 调用第三方打包接口修改更新地址
	 * @param webhomeFileUpload
	 * @return
	 */
	public String updateUrl(WebhomeFilePackageEntity webhomeFilePackage);
}


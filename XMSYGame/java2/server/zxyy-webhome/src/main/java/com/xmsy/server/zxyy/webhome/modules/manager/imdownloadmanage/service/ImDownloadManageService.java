package com.xmsy.server.zxyy.webhome.modules.manager.imdownloadmanage.service;

import com.baomidou.mybatisplus.service.IService;
import com.xmsy.server.zxyy.webhome.modules.manager.imdownloadmanage.entity.ImDownloadManageEntity;
import com.xmsy.server.zxyy.webhome.modules.webim.homepage.result.ImDownloadManageResult;
import com.xmsy.server.zxyy.webhome.modules.webim.homepage.result.ImDownloadResult;

import java.util.List;

/**
 * 33推荐下载管理
 *
 * @author xiaoliu
 * @email xxxxx
 * @date 2019-06-21 19:44:44
 */
public interface ImDownloadManageService extends IService<ImDownloadManageEntity> {

	/**
	 * 通过类型获取下载列表
	 * 
	 * @author xiaoliu
	 *
	 * @param type
	 * @return
	 */
	List<ImDownloadManageResult> selectListForType(String type);

	/**
	 * 获取下载列表
	 *
	 * @author aleng
	 *
	 * @return
	 */
	List<ImDownloadResult> selectImDownloadList(String type);

}

package com.xmsy.server.zxyy.webhome.modules.manager.pushdispatchdetail.service;

import java.util.List;

import com.baomidou.mybatisplus.service.IService;
import com.xmsy.server.zxyy.webhome.common.utils.R;
import com.xmsy.server.zxyy.webhome.modules.manager.pushdispatchdetail.entity.PushDispatchDetailEntity;

/**
 * 
 *
 * @author aleng
 * @email xxxxx
 * @date 2019-05-22 19:49:20
 */
public interface PushDispatchDetailService extends IService<PushDispatchDetailEntity> {

	/**
	 * 根据层级id获取层级名称
	 * 
	 * @param userHierarchyIds
	 * @return
	 */
	public List<String> getUserHierarchyNames(List<String> userHierarchyIds);

	// 推送逻辑
	public R push(PushDispatchDetailEntity pushDispatchDetailEntity);

}

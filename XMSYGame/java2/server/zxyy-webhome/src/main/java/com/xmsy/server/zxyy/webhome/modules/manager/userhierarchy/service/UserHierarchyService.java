package com.xmsy.server.zxyy.webhome.modules.manager.userhierarchy.service;

import java.util.List;

import com.baomidou.mybatisplus.service.IService;
import com.xmsy.server.zxyy.webhome.modules.app.hierarchy.param.HierarchyRateResult;
import com.xmsy.server.zxyy.webhome.modules.manager.userhierarchy.entity.UserHierarchyEntity;

/**
 * 用户层级表
 *
 * @author aleng
 * @email xxxxx
 * @date 2019-01-02 11:57:53
 */
public interface UserHierarchyService extends IService<UserHierarchyEntity> {

	// 获取默认层级
	UserHierarchyEntity getDefaultHierarchy();

	/**
	 * 设置默认
	 * 
	 * @param id
	 */
	void setDefault(Long id);

	/**
	 * 新增用户层级并新增相应优惠
	 * 
	 * @param id
	 */
	void insertHierarchyAndPreferential(UserHierarchyEntity userHierarchy);

	/**
	 * 获取层级胜率列表
	 * 
	 * @return
	 */
	List<HierarchyRateResult> getHierarchyRateList();
}

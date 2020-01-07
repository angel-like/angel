package com.xmsy.server.zxyy.webhome.modules.manager.immaterial.service;

import java.util.List;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.xmsy.common.define.page.PageParam;
import com.xmsy.server.zxyy.webhome.modules.manager.immaterial.entity.ImMaterialEntity;

/**
 * 33娱乐
 *
 * @author aleng
 * @email xxxxx
 * @date 2019-06-17 15:27:58
 */
public interface ImMaterialService extends IService<ImMaterialEntity> {

	/**
	 * 获取素材列表分页
	 * @param imMaterial
	 * @param pageParam
	 * @return
	 */
	Page<ImMaterialEntity> getImMaterials(ImMaterialEntity imMaterial, PageParam pageParam);
	
	/**
	 * 获取素材列表
	 * @param imMaterial
	 * @return
	 */
	List<ImMaterialEntity> getImMaterials(ImMaterialEntity imMaterial);

}

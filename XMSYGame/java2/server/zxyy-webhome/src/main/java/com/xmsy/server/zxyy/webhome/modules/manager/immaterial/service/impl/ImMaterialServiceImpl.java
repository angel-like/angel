package com.xmsy.server.zxyy.webhome.modules.manager.immaterial.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xmsy.common.define.page.PageParam;
import com.xmsy.server.zxyy.webhome.modules.manager.immaterial.dao.ImMaterialDao;
import com.xmsy.server.zxyy.webhome.modules.manager.immaterial.entity.ImMaterialEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.immaterial.service.ImMaterialService;

@Service("imMaterialService")
public class ImMaterialServiceImpl extends ServiceImpl<ImMaterialDao, ImMaterialEntity> implements ImMaterialService {

	@Override
	public Page<ImMaterialEntity> getImMaterials(ImMaterialEntity imMaterial, PageParam pageParam) {
		Page<ImMaterialEntity> page = new Page<>(pageParam.getPage(), pageParam.getLimit());// 当前页，总条数 构造 // page 对象
		/*page.setOrderByField("id");
		page.setAsc(false);*/
		return page.setRecords(this.baseMapper.getImMaterials(page, imMaterial));
	}

	@Override
	public List<ImMaterialEntity> getImMaterials(ImMaterialEntity imMaterial) {
		PageParam pageParam=new PageParam();
		Page<ImMaterialEntity> page = new Page<>(pageParam.getPage(), pageParam.getLimit());// 当前页，总条数 构造 // page 对象
		/*page.setOrderByField("id");
		page.setAsc(false);*/
		return this.baseMapper.getImMaterials(page, imMaterial);
	}

}

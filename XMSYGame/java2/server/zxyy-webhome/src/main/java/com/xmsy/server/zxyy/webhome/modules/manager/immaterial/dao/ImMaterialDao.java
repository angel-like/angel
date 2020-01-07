package com.xmsy.server.zxyy.webhome.modules.manager.immaterial.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.xmsy.server.zxyy.webhome.modules.manager.immaterial.entity.ImMaterialEntity;

/**
 * 33娱乐
 * 
 * @author aleng
 * @email xxxxx
 * @date 2019-06-17 15:27:58
 */
@Mapper
public interface ImMaterialDao extends BaseMapper<ImMaterialEntity> {

	List<ImMaterialEntity> getImMaterials(Pagination page, @Param("imMaterial") ImMaterialEntity imMaterial);

}

package com.xmsy.server.zxyy.webhome.modules.manager.imcontact.dao;

import com.xmsy.server.zxyy.webhome.modules.manager.imcontact.entity.ImContactEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.imcontact.entity.ImContactManagerEntity;
import com.baomidou.mybatisplus.mapper.BaseMapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

/**
 * 33联系方式
 * 
 * @author axiong
 * @email xxxxx
 * @date 2019-07-01 14:20:14
 */
@Mapper
public interface ImContactDao extends BaseMapper<ImContactEntity> {
	/**
	 * 查询需要的33联系方式的信息
	 * 		管理webhome_enclosure表查询路径
	 * @return
	 */
	List<ImContactManagerEntity> selectListByIm();
}

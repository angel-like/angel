package com.xmsy.server.zxyy.webhome.modules.manager.apppromotions.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.xmsy.server.zxyy.webhome.modules.manager.apppromotions.entity.AppPromotionsEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.apppromotions.entity.AppPromotionsListEntity;

/**
 * 官网优惠活动
 * 
 * @author xiaoliu
 * @email xxxxx
 * @date 2019-01-28 16:22:11
 */
@Mapper
public interface AppPromotionsDao extends BaseMapper<AppPromotionsEntity> {

	List<AppPromotionsListEntity> selectListForWeb(Pagination page,@Param("typeId")Long typeId);
	
}

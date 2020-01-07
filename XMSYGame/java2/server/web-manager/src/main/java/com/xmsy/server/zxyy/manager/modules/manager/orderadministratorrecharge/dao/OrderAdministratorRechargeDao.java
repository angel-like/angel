package com.xmsy.server.zxyy.manager.modules.manager.orderadministratorrecharge.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.xmsy.server.zxyy.manager.modules.manager.orderadministratorrecharge.entity.OrderAdministratorRechargeEntity;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 人工充值
 *
 * @author lpp
 * @email xxxxx
 * @date 2019-01-17 16:48:50
 */
@Mapper
public interface OrderAdministratorRechargeDao extends BaseMapper<OrderAdministratorRechargeEntity> {
	Map<String, Object> getCount(@Param("entity")OrderAdministratorRechargeEntity entity);
	/**
	 * 为了获取csv下载数据而进行的查询
	 * @param orderAdministratorRechargeEntity
	 * @return
	 */
	List<Map<String,Object>> selectListRecharge(@Param("orderAdministratorRecharge") OrderAdministratorRechargeEntity orderAdministratorRechargeEntity);

}

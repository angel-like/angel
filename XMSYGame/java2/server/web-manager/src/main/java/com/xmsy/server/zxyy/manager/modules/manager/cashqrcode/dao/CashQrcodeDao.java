package com.xmsy.server.zxyy.manager.modules.manager.cashqrcode.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.xmsy.server.zxyy.manager.modules.app.recharge.result.AppQrcodeRechargeResult;
import com.xmsy.server.zxyy.manager.modules.manager.cashqrcode.entity.CashQrcodeEntity;

/**
 * 收款二维码
 * 
 * @author aleng
 * @email xxxxx
 * @date 2019-04-22 11:02:28
 */
@Mapper
public interface CashQrcodeDao extends BaseMapper<CashQrcodeEntity> {

	/**
	 * 根据用户层级和收款类型获取收款码
	 * 
	 * @param hierarchyId
	 * @param type
	 * @return
	 */
	List<CashQrcodeEntity> findCashQrcodeByHierarchyId(@Param("hierarchyId") Long hierarchyId,
			@Param("type") Long type);

	List<AppQrcodeRechargeResult> findCashQrcode(@Param("hierarchyId") Long hierarchyId, @Param("type") Long type);

}

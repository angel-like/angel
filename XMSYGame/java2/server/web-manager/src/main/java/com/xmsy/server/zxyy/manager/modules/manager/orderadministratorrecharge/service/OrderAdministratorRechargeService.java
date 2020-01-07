package com.xmsy.server.zxyy.manager.modules.manager.orderadministratorrecharge.service;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.service.IService;
import com.xmsy.common.bean.message.RechargeRebateMessage;
import com.xmsy.server.zxyy.manager.modules.manager.orderadministratorrecharge.entity.OrderAdministratorRechargeEntity;
import com.xmsy.server.zxyy.manager.modules.manager.orderadministratorrecharge.param.AdminRechargeParam;

/**
 * 人工充值
 *
 * @author lpp
 * @email xxxxx
 * @date 2019-01-17 16:48:50
 */
public interface OrderAdministratorRechargeService extends IService<OrderAdministratorRechargeEntity> {

	/**
	* 统计
	* */
	Map<String,Object> getCount(OrderAdministratorRechargeEntity entity);

	/**
	 * 创建人工充值
	 *
	 * @param adminRecharge
	 * @param sysUserId
	 * @param sysUserName
	 */
	List<RechargeRebateMessage> createAdminRecharge(AdminRechargeParam adminRecharge, String ip, Long sysUserId, String sysUserName);
	/**
	 * 创建人工充值V2
	 * @param adminRecharge
	 * @param ip
	 * @param sysUserId
	 * @param sysUserName
	 * @return
	 */
	RechargeRebateMessage createAdminRechargeV2(AdminRechargeParam adminRecharge, String ip, Long sysUserId, String sysUserName);
	/**
	 * 创建人工取款
	 *
	 * @param adminRecharge
	 * @param sysUserId
	 * @param sysUserName
	 */
	void createAdminTakeMoney(AdminRechargeParam adminRecharge, String ip, Long sysUserId, String sysUserName);
	List<Map<String,Object>> selectListRecharge(@Param("orderAdministratorRecharge") OrderAdministratorRechargeEntity orderAdministratorRecharge);
}

package com.xmsy.server.zxyy.webhome.modules.manager.orderadministratorrecharge.service;

import com.baomidou.mybatisplus.service.IService;
import com.xmsy.server.zxyy.webhome.modules.manager.orderadministratorrecharge.entity.OrderAdministratorRechargeEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.orderadministratorrecharge.param.AdminRechargeParam;

/**
 * 人工充值
 *
 * @author lpp
 * @email xxxxx
 * @date 2019-01-17 16:48:50
 */
public interface OrderAdministratorRechargeService extends IService<OrderAdministratorRechargeEntity> {

	/**
	 * 创建人工充值
	 * 
	 * @param adminRecharge
	 * @param sysUserId
	 * @param sysUserName
	 */
	void createAdminRecharge(AdminRechargeParam adminRecharge, String ip, Long sysUserId, String sysUserName);
	/**
	 * 创建人工取款
	 * 
	 * @param adminRecharge
	 * @param sysUserId
	 * @param sysUserName
	 */
	void createAdminTakeMoney(AdminRechargeParam adminRecharge, String ip, Long sysUserId, String sysUserName);
}

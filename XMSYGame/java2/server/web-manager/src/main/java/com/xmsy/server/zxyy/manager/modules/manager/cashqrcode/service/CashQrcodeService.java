package com.xmsy.server.zxyy.manager.modules.manager.cashqrcode.service;

import com.baomidou.mybatisplus.service.IService;
import com.xmsy.server.zxyy.manager.modules.app.recharge.result.AppQrcodeRechargeResult;
import com.xmsy.server.zxyy.manager.modules.manager.cashqrcode.entity.CashQrcodeEntity;

/**
 * 收款二维码
 *
 * @author aleng
 * @email xxxxx
 * @date 2019-04-22 11:02:28
 */
public interface CashQrcodeService extends IService<CashQrcodeEntity> {

	/**
	 * web端调用
	 * @param hierarchyId
	 * @param type
	 * @return
	 */
	CashQrcodeEntity findCashQrcodeByHierarchyId(Long hierarchyId, Long type);
	
	/**
	 * pc端调用
	 * @param hierarchyId
	 * @param type
	 * @return
	 */
	AppQrcodeRechargeResult findCashQrcode(Long hierarchyId, Long type);

}

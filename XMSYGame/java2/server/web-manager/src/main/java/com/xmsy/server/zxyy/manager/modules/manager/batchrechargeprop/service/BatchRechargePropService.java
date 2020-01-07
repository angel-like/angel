package com.xmsy.server.zxyy.manager.modules.manager.batchrechargeprop.service;


import com.baomidou.mybatisplus.service.IService;
import com.xmsy.server.zxyy.manager.modules.manager.batchrechargeprop.entity.BatchRechargePropEntity;


/**
 * 批量充值会员道具
 *
 * @author xiaoling
 * @email xxxxx
 * @date 2019-09-27 11:41:20
 */
public interface BatchRechargePropService extends IService<BatchRechargePropEntity> {
	
	/**
	 * 创建人工批量充值会员道具
	 * 
	 * @param adminRecharge
	 * @param sysUserId
	 * @param sysUserName
	 */
	void createBatchrechargeprop(BatchRechargePropEntity batchrechargeprop, Long sysUserId, String sysUserName);

}


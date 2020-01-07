package com.xmsy.server.zxyy.webhome.modules.manager.userriskconfig.service;

import com.baomidou.mybatisplus.service.IService;
import com.xmsy.server.zxyy.webhome.modules.manager.userriskconfig.entity.UserRiskConfigEntity;


/**
 * 用户风控配置表
 *
 * @author xiaoling
 * @email xxxxx
 * @date 2019-05-05 11:28:56
 */
public interface UserRiskConfigService extends IService<UserRiskConfigEntity> {
	/**
	 * 删除（真删除）
	 * @param id
	 * @return
	 */
	Long physicalDelete(Long id);
	
}


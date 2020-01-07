package com.xmsy.server.zxyy.manager.modules.manager.domainmanagement.service;

import com.baomidou.mybatisplus.service.IService;
import com.xmsy.server.zxyy.manager.modules.manager.domainmanagement.entity.DomainManagementEntity;

/**
 * 域名管理
 *
 * @author aleng
 * @email xxxxx
 * @date 2019-01-06 14:09:43
 */
public interface DomainManagementService extends IService<DomainManagementEntity> {

	public String getOfficalDomain();

	public String getRecommendationDomain();

}

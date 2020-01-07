package com.xmsy.server.zxyy.calculate.modules.manager.orderpreferential.service;

import java.util.List;

import com.baomidou.mybatisplus.service.IService;
import com.xmsy.server.zxyy.calculate.modules.manager.orderpreferential.entity.OrderPreferentialEntity;

/**
 * 充值优惠
 *
 * @author aleng
 * @email xxxxx
 * @date 2019-01-25 18:46:23
 */
public interface OrderPreferentialService extends IService<OrderPreferentialEntity> {

	//获取层级对应的充值优惠
	List<OrderPreferentialEntity> getPreferentialsByHierarchyId(Long hierarchyId);
}

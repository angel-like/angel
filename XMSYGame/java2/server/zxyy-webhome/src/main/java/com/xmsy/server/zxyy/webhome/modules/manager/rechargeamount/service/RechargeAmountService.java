package com.xmsy.server.zxyy.webhome.modules.manager.rechargeamount.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.xmsy.common.define.page.PageParam;
import com.xmsy.server.zxyy.webhome.modules.manager.rechargeamount.entity.RechargeAmountEntity;


/**
 * 充值内容（金额）
 *
 * @author xiaoliu
 * @email xxxxx
 * @date 2019-03-27 10:16:53
 */
public interface RechargeAmountService extends IService<RechargeAmountEntity> {

	Page<RechargeAmountEntity> selectNewPage(RechargeAmountEntity rechargechannelchild,
			PageParam pageParam);

}


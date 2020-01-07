package com.xmsy.server.zxyy.manager.modules.manager.pooldispatchdetaillist.service;

import com.baomidou.mybatisplus.service.IService;
import com.xmsy.server.zxyy.manager.common.utils.PageUtils;
import com.xmsy.server.zxyy.manager.modules.manager.pooldispatchdetaillist.entity.PoolDispatchDetailListEntity;

import java.util.Map;

/**
 * 派奖明细记录表
 *
 * @author lpp
 * @email xxxxx
 * @date 2019-01-21 09:46:00
 */
public interface PoolDispatchDetailListService extends IService<PoolDispatchDetailListEntity> {

    PageUtils queryPage(Map<String, Object> params);
}


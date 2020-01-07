package com.xmsy.server.zxyy.manager.modules.manager.pooldispatchtaskdetail.service;

import com.baomidou.mybatisplus.service.IService;
import com.xmsy.server.zxyy.manager.common.utils.PageUtils;
import com.xmsy.server.zxyy.manager.modules.manager.pooldispatchtaskdetail.entity.PoolDispatchTaskDetailEntity;

import java.util.Map;

/**
 * 派奖奖项明细表
 *
 * @author lpp
 * @email xxxxx
 * @date 2019-01-21 09:46:00
 */
public interface PoolDispatchTaskDetailService extends IService<PoolDispatchTaskDetailEntity> {

    PageUtils queryPage(Map<String, Object> params);
    Integer updateEntityByIdForTrim(PoolDispatchTaskDetailEntity entity);
}	



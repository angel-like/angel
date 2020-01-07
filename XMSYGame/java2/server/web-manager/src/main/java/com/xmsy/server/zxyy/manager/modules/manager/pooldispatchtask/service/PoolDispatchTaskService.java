package com.xmsy.server.zxyy.manager.modules.manager.pooldispatchtask.service;

import com.baomidou.mybatisplus.service.IService;
import com.xmsy.server.zxyy.manager.common.utils.PageUtils;
import com.xmsy.server.zxyy.manager.modules.manager.pooldispatchtask.entity.PoolDispatchTaskEntity;

import java.util.Map;

/**
 * 
 *
 * @author lpp
 * @email xxxxx
 * @date 2019-01-21 09:46:00
 */
public interface PoolDispatchTaskService extends IService<PoolDispatchTaskEntity> {

    PageUtils queryPage(Map<String, Object> params);
    
    void dispatch(Long id);
}


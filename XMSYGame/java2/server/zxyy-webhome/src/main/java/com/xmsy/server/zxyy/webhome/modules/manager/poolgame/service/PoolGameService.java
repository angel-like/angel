package com.xmsy.server.zxyy.webhome.modules.manager.poolgame.service;

import com.baomidou.mybatisplus.service.IService;
import com.xmsy.server.zxyy.webhome.common.utils.PageUtils;
import com.xmsy.server.zxyy.webhome.modules.manager.poolgame.entity.PoolGameEntity;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * 游戏奖池表
 *
 * @author lpp
 * @email xxxxx
 * @date 2019-01-21 09:46:00
 */
public interface PoolGameService extends IService<PoolGameEntity> {

    PageUtils queryPage(Map<String, Object> params);
    
    /**
     * 查询奖池集合
     * @return
     */
    List<Map<String, Object>> findPoolList();
    
    /**
     * 查询奖池的总金额
     * @return
     */
    BigDecimal sumAllPool();
    
    /**
     * 更新奖池
     * 根据游戏id统计
     * @param poolGame
     * @param startDate
     * @param endDate
     */
    void updatePoolByGameIdForStatistics(PoolGameEntity poolGame,String startDate,String endDate);
}


package com.xmsy.server.zxyy.manager.modules.manager.rankinglistday.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.service.IService;
import com.xmsy.server.zxyy.manager.common.utils.PageUtils;
import com.xmsy.server.zxyy.manager.modules.manager.rankinglistday.entity.RankingListDayEntity;
import com.xmsy.server.zxyy.manager.modules.manager.rankinglistweek.entity.RankingListWeekEntity;

/**
 * 日排行榜
 *
 * @author lpp
 * @email xxxxx
 * @date 2019-01-21 09:46:00
 */
public interface RankingListDayService extends IService<RankingListDayEntity> {
	/**
	 * 分页查询
	 * @param params
	 * @return
	 */
    PageUtils queryPage(Map<String, Object> params);
    /**
     * 保存
     * @param entity
     */
    void save(RankingListDayEntity entity) ;
    /**
     * 批量删除
     * @param ids
     */
    void batchDelete(Long[] ids);
    
    /**
     * 根据时间范围统计榜单
     * @param rankingListId
     * @param startDate
     * @param endDate
     * @return
     */
    List<RankingListWeekEntity> statisticsRankingListByDateRange(Long rankingListId,String startDate,String endDate); 
    
    /**
     * 日排行榜定时任务批量保存
     * @param entity
     */
    void saveBatchForTask(List<RankingListDayEntity> rankingListDayList,Long ranKingListId,Date yesterday);
}


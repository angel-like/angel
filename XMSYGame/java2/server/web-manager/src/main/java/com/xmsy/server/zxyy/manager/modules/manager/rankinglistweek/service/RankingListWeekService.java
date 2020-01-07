package com.xmsy.server.zxyy.manager.modules.manager.rankinglistweek.service;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.service.IService;
import com.xmsy.server.zxyy.manager.common.utils.PageUtils;
import com.xmsy.server.zxyy.manager.modules.manager.rankinglistweek.entity.RankingListWeekEntity;

/**
 * 周排行榜
 *
 * @author lpp
 * @email xxxxx
 * @date 2019-01-21 09:46:00
 */
public interface RankingListWeekService extends IService<RankingListWeekEntity> {
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
    void save(RankingListWeekEntity entity) ;
    /**
     * 批量删除
     * @param ids
     */
    void batchDelete(Long[] ids);
    
    /**
     * 
     * @param rankingListDayList
     * @param ranKingListId
     * @param weekNum
     */
    void saveBatchForTask(List<RankingListWeekEntity> rankingListWeekList,Long rankingListId,int weekNum);
}


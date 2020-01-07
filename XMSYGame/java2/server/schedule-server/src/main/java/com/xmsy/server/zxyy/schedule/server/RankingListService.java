package com.xmsy.server.zxyy.schedule.server;

import java.util.Date;
import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xmsy.server.zxyy.schedule.entity.RankingListDayEntity;
import com.xmsy.server.zxyy.schedule.entity.RankingListWeekEntity;

/**
 * 
 * @author adu
 *
 */
public interface RankingListService {
	/**
	 * 获取具体的排行榜榜单信息
	 * @param id
	 * @return
	 * @throws Exception
	 */
	JSONObject getRankingListById(Long id)throws Exception;
	/**
	 * 获取某天具体的日排行榜会员列表
	 * @param rankingListId
	 * @param countDate
	 * @return
	 * @throws Exception
	 */
	JSONArray  findRankingListByDay(Long rankingListId,Date countDate)throws Exception;
	/**
	 * 获取未返佣会员列表
	 * @param rankingListId
	 * @return
	 * @throws Exception
	 */
	JSONArray  findRankingListByNeed(Long rankingListId)throws Exception;
	/**
	 * 获取某一周具体的周排行榜会员列表
	 * @param rankingListId
	 * @param weekNum
	 * @return
	 * @throws Exception
	 */
	JSONArray  findRankingListWeekByWeekNum(Long rankingListId,int weekNum)throws Exception;
	/**
	 * 统计日期范围内的日排行榜
	 * 根据会员分组
	 * @param rankingListId
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	JSONArray  statisticsRankingListByDateRange(Long rankingListId,String startDate,String endDate);
	
	/**
	 * 批量保存到日排行榜
	 * 需要先排序
	 * @param rankingListDayList
	 * @param ranKingListId
	 * @param yesterday
	 */
	void saveBatchForTaskToDay(JSONArray rankingListDayList,Long ranKingListId,Date yesterday);
	/**
	 *  批量新增到日排行榜
	 * @param insertList
	 */
	void batchInsertToDay(List<RankingListDayEntity> insertList);
	/**
	 * 批量更新到日排行榜
	 * @param updateList
	 */
	void batchUpdateToDay(List<RankingListDayEntity> updateList);
	
	/**
	 *  批量保存到周排行榜
	 *  需要先排序
	 * @param rankingList
	 * @param ranKingListId
	 * @param weekNum
	 */
	void saveBatchForTaskToWeek(JSONArray rankingList,Long ranKingListId,int weekNum);
	/**
	 * 批量新增到周排行榜
	 * @param insertList
	 */
	void batchInsertToWeek(List<RankingListWeekEntity> insertList);
	/**
	 * 批量更新到周排行榜
	 * @param insertList
	 */
	void batchUpdateToWeek(List<RankingListWeekEntity> updateList);
}


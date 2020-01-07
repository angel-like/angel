package com.xmsy.server.zxyy.schedule.server;

import com.alibaba.fastjson.JSONArray;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 
 * @author adu
 *
 */
public interface GameRecordService {
	/**
	 * 按照某个时间段计算用户游戏下注 返水等
	 * 
	 * @param startTime
	 * @param endTime
	 * @return
	 * @throws Exception
	 */
	JSONArray getUserGameRecordByDay(String startTime, String endTime) throws Exception;

	/**
	 * 按照某个时间段计算每款游戏的下注盈利等情况等
	 * 
	 * @param startTime
	 * @param endTime
	 * @return
	 * @throws Exception
	 */
	JSONArray getGameAnalysisByDay(String startTime, String endTime, boolean robot) throws Exception;

	/**
	 * 批量新增用户的某天的游戏总的下注 返水等
	 * 
	 * @param data
	 * @param thatDay
	 */
	void batchInsertGameRecordDaily(JSONArray data, Date thatDay, BigDecimal userWaterRate);

	/**
	 * 批量统计每款游戏每天总的下注，盈利等
	 * 
	 * @param data
	 * @param thatDay
	 */
	void batchInsertGameAnalysisDaily(JSONArray data, Date thatDay, boolean robot);

	/**
	 * 查询未处理用户返水记录
	 * 
	 * @param countDay
	 * @return
	 */
	JSONArray findUserReturnWaterByDay();

	/**
	 * 查询未处理用户返水记录
	 * 
	 * @param countDay
	 * @return
	 */
	JSONArray findUserCommissionList();
	
	/**
	 * 计算开始时间到当前玩的游戏次数
	 * 对局数满奖励未达到最大次数
	 * @param startTime
	 * @param type
	 * @param maxNum
	 * @return
	 * @throws Exception
	 */
	JSONArray getUserGameNumByDay(String startTime,int type,int num ,int maxNum) throws Exception;

	JSONArray queryByGameRoundNo(String gameId, int i);


	void save(Long userId, String userAccount, Long gameId, Long gradeId, Long roomId,String roomName,
			  String gameRoundNo, BigDecimal waterProfit, Long betCoins, Long validBet, Long prizeCoins, Long profitCoins, Integer round,boolean isGmUser);
}

package com.xmsy.server.zxyy.schedule.server;

import com.alibaba.fastjson.JSONObject;

import java.util.Date;

/**
 * 开元游戏登录
 *
 * @author youyou
 * @email xxxxx
 * @date 2019-11-26 11:18:25
 */
public interface KaiyuanGameService {

  boolean isExistGameRecord(String  gameId);
   JSONObject getRecord() throws  Exception;
   void save(String gameId, Long userId, Long    serverId, Long kindId, Long   tableId, Long chairId, Long    userCount, String cardValue,
                    Long cellScore, Long allBet, Long profit, Long revenue, Date gameStartTime, Date gameEndTime, Long channelId, String lineCode, String accounts,String roomName);

}

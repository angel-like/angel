package com.xmsy.server.zxyy.schedule.server.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xmsy.server.zxyy.schedule.jdbc.WebHomeJdbcUtil;
import com.xmsy.server.zxyy.schedule.server.ReportPlayerGameDailyService;
import com.xmsy.server.zxyy.schedule.utils.MathUtil;

import lombok.extern.slf4j.Slf4j;
@Slf4j
@Service("reportPlayerGameDailyService")
public class ReportPlayerGameDailyServiceImpl implements ReportPlayerGameDailyService {
	@Autowired
	private WebHomeJdbcUtil jdbcUtil;
	@Override
	public JSONArray findPlayerGameInvestmentAndOutputByDate(String startTime, String endTime) {
		StringBuffer sql=new StringBuffer();
		sql.append(" SELECT game_id gameId,room_id roomId,grade_id gradeId,user_id userId,DATE_FORMAT(create_time,'%Y-%m-%d') countDay,");
		sql.append(" user_account userAccount,game_name gameName,room_name roomName,grade_name gradeName,");
		sql.append(" sum(CASE  WHEN prize_coins<0 THEN prize_coins ELSE 0 END) investmentTotal,");
		sql.append(" sum(CASE  WHEN prize_coins>0 THEN prize_coins ELSE 0 END) outputTotal");
		sql.append(" FROM game_record");
		sql.append(" where create_time>=?");
		sql.append("  and create_time<? and gm_user =0 ");
		sql.append("  and room_id  != 2 ");
		sql.append("  group by game_id,room_id,grade_id,user_id,DATE_FORMAT(create_time,'%Y-%m-%d'),user_account,game_name,room_name,grade_name");
		JSONArray data=null;
		try {
			data = jdbcUtil.selectByParamReturnJsonArray(sql.toString(), startTime,endTime);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("getStatisticsGameWinLoseCoin error {}",e);
		}
		return data;
	}
	
	public void saveDataTransaction(JSONObject playerGameData) {
		if( playerGameData ==null || playerGameData.isEmpty()) {
			return;
		}
		Long id = getPlayerCountDayId( playerGameData);
		if(id<=0) {
			log.info("统计日期玩家游戏统计失败  playerGameData:{}",playerGameData);
			return;
		}
//		总投入
		Long investmentTotal = Math.abs(MathUtil.getBigDecimal(playerGameData.get("investmentTotal")).longValue());
//		总产出
		Long outputTotal =MathUtil.getBigDecimal(playerGameData.get("outputTotal")).longValue();
		
		StringBuffer updateSql=new StringBuffer();
		updateSql.append(" UPDATE report_player_game_daily SET investment_total=?,");
		updateSql.append("  output_total=?,win_total=?,");
		updateSql.append("  update_time=now()");
		updateSql.append("  WHERE  id = ?");
		
		jdbcUtil.update(updateSql.toString(), investmentTotal,outputTotal,investmentTotal-outputTotal,id);
	}
	
	private Long getPlayerCountDayId(JSONObject playerGameData) {
		StringBuffer sql=new StringBuffer();
		sql.append(" SELECT *");
		sql.append(" FROM report_player_game_daily");
		sql.append(" where count_day = ? and user_id = ? and game_id= ? and grade_id = ? and room_id = ?");
		JSONObject data=null;
		Long id = 0L;
		try {
			data = jdbcUtil.selectByParamReturnJsonObject(sql.toString(),playerGameData.get("countDay"),playerGameData.getLong("userId")
					,playerGameData.getLong("gameId"),playerGameData.getLong("gradeId"),playerGameData.getLong("roomId"));
			if(data==null || data.isEmpty()) {
				StringBuffer insertSql=new StringBuffer();
				insertSql.append(" INSERT into report_player_game_daily(create_time,update_time,count_day,");
				insertSql.append(" user_id,game_id,grade_id,room_id,user_account,game_name,grade_name,room_name)");
				insertSql.append("  VALUES(NOW(),NOW(),?,?,?,?,?,?,?,?,?)");
				id= jdbcUtil.insertReturnId(insertSql.toString(), playerGameData.get("countDay"),playerGameData.getLong("userId")
						,playerGameData.getLong("gameId"),MathUtil.getBigDecimal(playerGameData.get("gradeId")).longValue(),playerGameData.getLong("roomId")
						,playerGameData.get("userAccount"),playerGameData.get("gameName"),
						playerGameData.get("gradeName"),playerGameData.get("roomName"));
			}else {
				id=data.getLong("id");
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error("getStatisticsGameWinLoseNum error {}",e);
		}
		return id;
	}

}

package com.xmsy.server.zxyy.schedule.server.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xmsy.server.zxyy.schedule.jdbc.WebHomeJdbcUtil;
import com.xmsy.server.zxyy.schedule.server.ReportGameDailyService;
import com.xmsy.server.zxyy.schedule.utils.MathUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service("reportGameDailyService")
public class ReportGameDailyServiceImpl implements ReportGameDailyService {
	@Autowired
	private WebHomeJdbcUtil jdbcUtil;

	@Override
	public JSONArray findGameGradeInvestmentAndOutputByDate(String startTime, String endTime) {
		StringBuffer sql=new StringBuffer();
		sql.append(" SELECT game_id gameId,room_id roomId,grade_id gradeId,DATE_FORMAT(create_time,'%Y-%m-%d') countDay,");
		sql.append(" game_name gameName,room_name roomName,grade_name gradeName,");
		sql.append(" COUNT(DISTINCT user_id) participateNum,");
		sql.append(" sum(CASE  WHEN prize_coins<0 THEN prize_coins ELSE 0 END) investmentTotal,");
		sql.append(" sum(CASE  WHEN prize_coins>0 THEN prize_coins ELSE 0 END) outputTotal");
		sql.append(" FROM game_record");
		sql.append(" where create_time>=?");
		sql.append("  and room_id  != 2 ");
		sql.append("  and create_time<? and gm_user =0");
		sql.append("  group by game_id,room_id,grade_id,DATE_FORMAT(create_time,'%Y-%m-%d'),game_name,room_name,grade_name");
		JSONArray data=null;
		try {
			data = jdbcUtil.selectByParamReturnJsonArray(sql.toString(), startTime,endTime);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("getStatisticsGameWinLoseCoin error {}",e);
		}
		return data;
	}


	@Override
	public void saveGameGradeData(JSONObject gameGradeData) {
		if( gameGradeData ==null || gameGradeData.isEmpty()) {
			return;
		}
		Long id = getGameGradeCountDayId( gameGradeData);
		if(id<=0) {
			log.info("统计日期游戏场次统计失败  gameGradeData:{}",gameGradeData);
			return;
		}
//		参与人数
		Long participateNum = Math.abs(MathUtil.getBigDecimal(gameGradeData.get("participateNum")).longValue());
//		总投入
		Long investmentTotal = Math.abs(MathUtil.getBigDecimal(gameGradeData.get("investmentTotal")).longValue());
//		总产出
		Long outputTotal =MathUtil.getBigDecimal(gameGradeData.get("outputTotal")).longValue();
		
		StringBuffer updateSql=new StringBuffer();
		updateSql.append(" UPDATE report_game_grade_daily SET participate_num=?,investment_total=?,");
		updateSql.append("  output_total=?,win_total=?,");
		updateSql.append("  update_time=now()");
		updateSql.append("  WHERE  id = ?");
		
		jdbcUtil.update(updateSql.toString(),participateNum,investmentTotal,outputTotal,investmentTotal-outputTotal,id);
	}

	@Override
	public JSONArray findProviderInvestmentAndOutputByDate(String startTime, String endTime) {
		StringBuffer sql=new StringBuffer();
		sql.append(" SELECT  DATE_FORMAT(create_time,'%Y-%m-%d') countDay,");
		sql.append(" platform_code providerCode,");
		sql.append(" COUNT(DISTINCT user_id) participateNum,");
		sql.append(" sum(CASE  WHEN prize_coins<0 THEN prize_coins ELSE 0 END) investmentTotal,");
		sql.append(" sum(CASE  WHEN prize_coins>0 THEN prize_coins ELSE 0 END) outputTotal");
		sql.append(" FROM game_record");
		sql.append(" where create_time>=?");
		sql.append("  and create_time<?");
		sql.append("  and room_id  != 2 ");
		sql.append("  group by platform_code,DATE_FORMAT(create_time,'%Y-%m-%d')");
		JSONArray data=null;
		try {
			data = jdbcUtil.selectByParamReturnJsonArray(sql.toString(), startTime,endTime);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("getStatisticsGameWinLoseCoin error {}",e);
		}
		return data;
	}
	@Override
	public JSONArray findGameInvestmentAndOutputByDate(String startTime, String endTime) {
		StringBuffer sql=new StringBuffer();
		sql.append(" SELECT game_id gameId,DATE_FORMAT(create_time,'%Y-%m-%d') countDay,");
		sql.append(" game_name gameName,");
		sql.append(" COUNT(DISTINCT user_id) participateNum,");
		sql.append(" sum(CASE  WHEN prize_coins<0 THEN prize_coins ELSE 0 END) investmentTotal,");
		sql.append(" sum(CASE  WHEN prize_coins>0 THEN prize_coins ELSE 0 END) outputTotal");
		sql.append(" FROM game_record");
		sql.append(" where create_time>=?");
		sql.append("  and create_time<?");
		sql.append("  and room_id  != 2 ");
		sql.append("  group by game_id,DATE_FORMAT(create_time,'%Y-%m-%d'),game_name");
		JSONArray data=null;
		try {
			data = jdbcUtil.selectByParamReturnJsonArray(sql.toString(), startTime,endTime);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("getStatisticsGameWinLoseCoin error {}",e);
		}
		return data;
	}

	@Override
	public void saveGameData(JSONObject gameData) {
		if( gameData ==null || gameData.isEmpty()) {
			return;
		}
		Long id = getGameCountDayId( gameData);
		if(id<=0) {
			log.info("统计日期游戏统计失败  gameData:{}",gameData);
			return;
		}
//		参与人数
		Long participateNum = Math.abs(MathUtil.getBigDecimal(gameData.get("participateNum")).longValue());
//		总投入
		Long investmentTotal = Math.abs(MathUtil.getBigDecimal(gameData.get("investmentTotal")).longValue());
//		总产出
		Long outputTotal =MathUtil.getBigDecimal(gameData.get("outputTotal")).longValue();
		
		StringBuffer updateSql=new StringBuffer();
		updateSql.append(" UPDATE report_game_daily SET participate_num=?,investment_total=?,");
		updateSql.append("  output_total=?,win_total=?,");
		updateSql.append("  update_time=now()");
		updateSql.append("  WHERE  id = ?");
		
		jdbcUtil.update(updateSql.toString(),participateNum,investmentTotal,outputTotal,investmentTotal-outputTotal,id);
	}


	@Override
	public void saveProviderData(JSONObject gameProviderData) {
		if( gameProviderData ==null || gameProviderData.isEmpty()) {
			return;
		}
		Long id = getProviderCountDayId( gameProviderData);
		if(id<=0) {
			log.info("统计日期游戏统计失败  gameData:{}",gameProviderData);
			return;
		}
//		参与人数
		Long participateNum = Math.abs(MathUtil.getBigDecimal(gameProviderData.get("participateNum")).longValue());
//		总投入
		Long investmentTotal = Math.abs(MathUtil.getBigDecimal(gameProviderData.get("investmentTotal")).longValue());
//		总产出
		Long outputTotal =MathUtil.getBigDecimal(gameProviderData.get("outputTotal")).longValue();

		StringBuffer updateSql=new StringBuffer();
		updateSql.append(" UPDATE report_provider_daily SET participate_num=?,investment_total=?,");
		updateSql.append("  output_total=?,win_total=?,");
		updateSql.append("  update_time=now()");
		updateSql.append("  WHERE  id = ?");

		jdbcUtil.update(updateSql.toString(),participateNum,investmentTotal,outputTotal,investmentTotal-outputTotal,id);
	}


	private Long getProviderCountDayId(JSONObject gameProviderData) {
		StringBuffer sql=new StringBuffer();
		sql.append(" SELECT *");
		sql.append(" FROM report_provider_daily");
		sql.append(" where count_day = ? and  provider_code= ? ");
		JSONObject data=null;
		Long id = 0L;
		try {
			data = jdbcUtil.selectByParamReturnJsonObject(sql.toString(),gameProviderData.get("countDay"),
					gameProviderData.getLong("providerCode"));
			if(data==null || data.isEmpty()) {
				StringBuffer insertSql=new StringBuffer();
				insertSql.append(" INSERT into report_provider_daily(create_time,update_time,count_day,");
				insertSql.append(" provider_code)");
				insertSql.append("  VALUES(NOW(),NOW(),?,?)");
				id= jdbcUtil.insertReturnId(insertSql.toString(), gameProviderData.get("countDay"),
						gameProviderData.getLong("providerCode")
						);
			}else {
				id=data.getLong("id");
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error("getGameCountDayId error {}",e);
		}
		return id;
	}

	private Long getGameGradeCountDayId(JSONObject gameGradeData) {
		StringBuffer sql=new StringBuffer();
		sql.append(" SELECT *");
		sql.append(" FROM report_game_grade_daily");
		sql.append(" where count_day = ? and  game_id= ? and grade_id = ? and room_id = ?");
		JSONObject data=null;
		Long id = 0L;
		try {
			data = jdbcUtil.selectByParamReturnJsonObject(sql.toString(),gameGradeData.get("countDay"),
					gameGradeData.getLong("gameId"),gameGradeData.getLong("gradeId"),gameGradeData.getLong("roomId"));
			if(data==null || data.isEmpty()) {
				StringBuffer insertSql=new StringBuffer();
				insertSql.append(" INSERT into report_game_grade_daily(create_time,update_time,count_day,");
				insertSql.append(" game_id,grade_id,room_id,game_name,grade_name,room_name)");
				insertSql.append("  VALUES(NOW(),NOW(),?,?,?,?,?,?,?)");
				id= jdbcUtil.insertReturnId(insertSql.toString(), gameGradeData.get("countDay"),
						gameGradeData.getLong("gameId"),MathUtil.getBigDecimal(gameGradeData.getLong("gradeId")).longValue(),gameGradeData.getLong("roomId")
						,gameGradeData.get("gameName"),
						gameGradeData.get("gradeName"),gameGradeData.get("roomName"));
			}else {
				id=data.getLong("id");
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error("getGameGradeCountDayId error {}",e);
		}
		return id;
	}



	
	private Long getGameCountDayId(JSONObject gameGradeData) {
		StringBuffer sql=new StringBuffer();
		sql.append(" SELECT *");
		sql.append(" FROM report_game_daily");
		sql.append(" where count_day = ? and  game_id= ? ");
		JSONObject data=null;
		Long id = 0L;
		try {
			data = jdbcUtil.selectByParamReturnJsonObject(sql.toString(),gameGradeData.get("countDay"),
					gameGradeData.getLong("gameId"));
			if(data==null || data.isEmpty()) {
				StringBuffer insertSql=new StringBuffer();
				insertSql.append(" INSERT into report_game_daily(create_time,update_time,count_day,");
				insertSql.append(" game_id,game_name)");
				insertSql.append("  VALUES(NOW(),NOW(),?,?,?)");
				id= jdbcUtil.insertReturnId(insertSql.toString(), gameGradeData.get("countDay"),
						gameGradeData.getLong("gameId")
						,gameGradeData.get("gameName"));
			}else {
				id=data.getLong("id");
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error("getGameCountDayId error {}",e);
		}
		return id;
	}

}

package com.xmsy.server.zxyy.schedule.server.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xmsy.server.zxyy.schedule.jdbc.RobotJdbcUtil;
import com.xmsy.server.zxyy.schedule.jdbc.WebHomeJdbcUtil;
import com.xmsy.server.zxyy.schedule.server.GameRecordService;
import com.xmsy.server.zxyy.schedule.utils.Constant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Slf4j
@Service("gameRecordService")
public class GameRecordServiceImpl implements GameRecordService {
	@Autowired
	private WebHomeJdbcUtil webHomeJdbcUtil;
	@Autowired
	private RobotJdbcUtil robotJdbcUtil;

	@Override
	public JSONArray getUserGameRecordByDay(String startTime, String endTime) throws Exception {
		StringBuilder sqlSB = new StringBuilder();
		sqlSB.append(" SELECT user_id userId,user_account userAccount,");
		sqlSB.append(" IFNULL(sum(valid_bet), 0) validBet,");
		sqlSB.append(" IFNULL(sum(prize_coins), 0) prizeCoins,");
		sqlSB.append(" IFNULL(sum(water_profit), 0) waterProfit,");
		sqlSB.append(" IFNULL(sum(profit_coins), 0) profitCoins");
		sqlSB.append(" FROM game_record");
		sqlSB.append(" where    robot = 0 and gm_user =0");
		sqlSB.append(" and create_time>=?");
		sqlSB.append(" and create_time<=?");
		sqlSB.append(" GROUP BY  user_id,user_account");
		return webHomeJdbcUtil.selectByParamReturnJsonArray(sqlSB.toString(), startTime, endTime);
	}

	@Override
	public void batchInsertGameRecordDaily(JSONArray data, Date thatDay, BigDecimal userWaterRate) {
		StringBuilder insertSql = new StringBuilder();
		insertSql.append("INSERT INTO game_record_daily(");
		insertSql.append("user_id,user_account,valid_bet,prize_coins,water_profit,");
		insertSql.append("profit_coins,count_day,user_water_profit,user_water_rate,version,delete_status,create_time");
		insertSql.append(")");
		insertSql.append(" VALUES(?,?,?,?,?,?,?,?,?,0,0,now())");
		List<Object[]> paramsList = new ArrayList<>();
		for (int i = 0; i < data.size(); i++) {
			JSONObject obj = data.getJSONObject(i);
			Object[] param = new Object[9];
			param[0] = obj.get("userId");
			param[1] = obj.get("userAccount");
			param[2] = obj.get("validBet");
			param[3] = obj.get("prizeCoins");
			param[4] = obj.get("waterProfit");
			param[5] = obj.get("profitCoins");
			param[6] = thatDay;
			param[7] = obj.getBigDecimal("validBet").multiply(userWaterRate);
			param[8] = userWaterRate;
			paramsList.add(param);
		}
		log.info("paramsList size : {}", paramsList.size());
		webHomeJdbcUtil.insertBatch(insertSql.toString(), paramsList);
	}

	@Override
	public JSONArray findUserReturnWaterByDay() {
		StringBuilder sqlSB = new StringBuilder();
		sqlSB.append(" SELECT id,user_id userId,user_account userAccount,");
		sqlSB.append(" user_water_profit userWaterProfit, ");
		sqlSB.append(" user_water_rate userWaterRate,");
		sqlSB.append(" user_return userReturn");
		sqlSB.append(" FROM game_record_daily");
		sqlSB.append(" where  user_return = 0 and abnormal = 0");
		sqlSB.append(" and user_water_profit > 0");
		JSONArray dataArray = null;
		try {
			dataArray = webHomeJdbcUtil.selectByParamReturnJsonArray(sqlSB.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dataArray;
	}

	@Override
	public JSONArray findUserCommissionList() {
		StringBuilder sqlSB = new StringBuilder();
		sqlSB.append(" SELECT id,user_id userId,user_account userAccount,");
		sqlSB.append(" valid_bet validBet");
		sqlSB.append(" FROM game_record_daily");
		sqlSB.append(" where  agent_return = 0 and agent_abnormal = 0");
		sqlSB.append(" and valid_bet > 0");
		JSONArray dataArray = null;
		try {
			dataArray = webHomeJdbcUtil.selectByParamReturnJsonArray(sqlSB.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dataArray;
	}

	@Override
	public JSONArray getGameAnalysisByDay(String startTime, String endTime, boolean robot) throws Exception {
		StringBuilder sqlSB = new StringBuilder();
		sqlSB.append(" select");
		sqlSB.append(" game_id as gameId,");
		sqlSB.append(" room_id as roomId,");
		sqlSB.append(" grade_id as gradeId,");
		sqlSB.append(" sum(bet_coins) AS betCoins,");
		sqlSB.append(" sum(prize_coins) AS prizeCoins,");
		sqlSB.append(" sum(profit_coins) AS profitCoins,");
		sqlSB.append(" sum(valid_bet) AS validBet,");
		sqlSB.append(" sum(water_profit) AS waterProfit,");
		sqlSB.append(" max(water_rate) AS waterRate,");
		sqlSB.append(" count(user_id) AS userNum");
		sqlSB.append(" FROM game_record");
		sqlSB.append(" where ");
		sqlSB.append(" create_time>=?");
		sqlSB.append(" and create_time<=?");
		if (!robot) {
			sqlSB.append(" and gm_user=0");
		}
		sqlSB.append(" GROUP BY  game_id,grade_id,room_id");
		if (robot) {
			return robotJdbcUtil.selectByParamReturnJsonArray(sqlSB.toString(), startTime, endTime);
		}
		return webHomeJdbcUtil.selectByParamReturnJsonArray(sqlSB.toString(), startTime, endTime);
	}

	@Override
	public void batchInsertGameAnalysisDaily(JSONArray data, Date thatDay, boolean robot) {
		Date now = new Date();
		StringBuilder insertSql = new StringBuilder();
		insertSql.append("INSERT INTO game_analysis_daily(");
		insertSql.append("room_id,game_id,grade_id,valid_bet,prize_coins,water_profit,");
		insertSql.append("profit_coins,count_day,water_rate,user_num,create_time,update_time");
		if (robot) {
			insertSql.append(",robot");
		}
		insertSql.append(")");
		insertSql.append(" VALUES(?,?,?,?,?,?,?,?,?,?,?,?");
		if (robot) {
			insertSql.append(",?");
		}
		insertSql.append(")");
		List<Object[]> paramsList = new ArrayList<>();
		for (int i = 0; i < data.size(); i++) {
			JSONObject obj = data.getJSONObject(i);
			Object[] param = new Object[12];
			if (robot) {
				param = new Object[13];
			}
			param[0] = obj.get("roomId");
			param[1] = obj.get("gameId");
			param[2] = obj.get("gradeId");
			param[3] = obj.get("validBet");
			param[4] = obj.get("prizeCoins");
			param[5] = obj.get("waterProfit");
			param[6] = obj.get("profitCoins");
			param[7] = thatDay;
			param[8] = obj.get("waterRate");
			param[9] = obj.get("userNum");
			param[10] = now;
			param[11] = now;
			if (robot) {
				param[12] = true;
			}
			paramsList.add(param);
		}
		log.info("paramsList size : {}", paramsList.size());
		webHomeJdbcUtil.insertBatch(insertSql.toString(), paramsList);
	}

	@Override
	public JSONArray getUserGameNumByDay(String startTime,int detailType,int num,int maxNum) throws Exception {

		
		StringBuilder sqlSB = new StringBuilder();
		sqlSB.append(" SELECT a.user_id userId,a.user_account userAccount,num,IFNULL(b.fnum,0) fnum FROM");
		sqlSB.append(" (SELECT	user_id,user_account,COUNT(1) num	FROM	game_record ");
		sqlSB.append(" WHERE	create_time >=? GROUP BY user_id,user_account) a");
		sqlSB.append(" LEFT JOIN (	SELECT	user_id, count(1) fnum	FROM user_gift_record ");
		sqlSB.append(" where create_time>=? and type =?  and detail_type =? ");
		sqlSB.append(" GROUP BY user_id )b ON a.user_id = b.user_id");
		sqlSB.append(" where IFNULL(b.fnum,0)<?  ");
		JSONArray dataArray = null;
		try {
			dataArray = webHomeJdbcUtil.selectByParamReturnJsonArray(sqlSB.toString(),startTime,startTime,
					Constant.ACTIVITYTYPE_1,detailType,maxNum);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dataArray;
	}

	@Override
	public JSONArray queryByGameRoundNo(String gameId, int i) {
		StringBuilder sqlSB = new StringBuilder();
		sqlSB.append(" select user_id as userId , prize_coins as prizeCoins from game_record where delete_status = 0 and round= ? and  game_round_no =?");
		JSONArray dataArray = null;
		try {
			dataArray = webHomeJdbcUtil.selectByParamReturnJsonArray(sqlSB.toString(),i,gameId
					);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dataArray;
	}

	@Override
	public void save(Long userId, String userAccount, Long gameId, Long gradeId, Long roomId,String roomName,
					 String gameRoundNo,BigDecimal waterProfit, Long betCoins, Long validBet, Long prizeCoins, Long profitCoins, Integer round,boolean isGmUser) {

		StringBuffer sql=new StringBuffer();
		sql.append(" INSERT INTO game_record(");
		sql.append(" user_id,user_account,game_id,grade_id,");
		sql.append(" room_id,room_name,game_round_no,water_profit,bet_coins,valid_bet,prize_coins,");
		sql.append(" profit_coins,round,gm_user,create_time,update_time,platform_code)");

		sql.append("VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,now(),now(),1)");
		try {

			webHomeJdbcUtil.insert(sql.toString(),userId,userAccount,gameId,gradeId,roomId,roomName,gameRoundNo,waterProfit,betCoins,validBet,
					prizeCoins,profitCoins,round,isGmUser);
		} catch (Exception e) {
			log.error("saveBalanceRateYesterday error ",e);
		}
	}
}

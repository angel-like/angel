package com.xmsy.server.zxyy.schedule.server.impl;

import java.math.BigDecimal;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.xmsy.server.zxyy.schedule.jdbc.WebHomeJdbcUtil;
import com.xmsy.server.zxyy.schedule.server.ReportDataService;
import com.xmsy.server.zxyy.schedule.server.ScheduleService;
import com.xmsy.server.zxyy.schedule.utils.Constant;
import com.xmsy.server.zxyy.schedule.utils.DateUtils;
import com.xmsy.server.zxyy.schedule.utils.MathUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service("reportDataService")
public class ReportDataServiceImpl implements ReportDataService {
	@Autowired
	private WebHomeJdbcUtil jdbcUtil;
	@Autowired
	private ScheduleService scheduleService;
	@Override
	public void statisticsDataReport() {
		JSONObject schedule = scheduleService.getScheduleByName(Constant.REPORT_DATA_SCHEDULE);
		Date lastUpdateTime = null;
		if(schedule!=null && schedule.getDate("updateTime")!=null) {
			lastUpdateTime=schedule.getDate("updateTime");
		}
		Date nowDate = new Date();
		String countDay="";
		if(lastUpdateTime==null) {
			lastUpdateTime=DateUtils.addDayZeroPoint(nowDate,0);
			countDay=DateUtils.format(lastUpdateTime);
			saveDataTransaction(countDay, DateUtils.formatTime(lastUpdateTime), DateUtils.formatTime(nowDate));
		}else {
			int day = DateUtils.differenceDay(lastUpdateTime, nowDate);
			if(day==0) {
				lastUpdateTime=DateUtils.addDayZeroPoint(nowDate,0);
				countDay=DateUtils.format(lastUpdateTime);
				saveDataTransaction(countDay, DateUtils.formatTime(lastUpdateTime), DateUtils.formatTime(nowDate));
			}else if(day==1 && DateUtils.isZero(nowDate)){
				lastUpdateTime=DateUtils.addDayZeroPoint(nowDate,0);
				countDay=DateUtils.format(lastUpdateTime);
				saveDataTransaction(countDay, DateUtils.formatTime(lastUpdateTime), DateUtils.formatTime(nowDate));
			}else {
				 Date endDate=null;
				for(int i=0;i<=day;i++) {
					if(i==day) {
						countDay=DateUtils.format(lastUpdateTime);
						saveDataTransaction(countDay, DateUtils.formatTime(lastUpdateTime), DateUtils.formatTime(nowDate));
					}else {
						if(i==0) {
							lastUpdateTime=DateUtils.addDayZeroPoint(lastUpdateTime,0);
						}
						endDate=DateUtils.addDayZeroPoint(lastUpdateTime, 1);
						countDay=DateUtils.format(lastUpdateTime);
						saveDataTransaction(countDay, DateUtils.formatTime(lastUpdateTime), DateUtils.formatTime(endDate));
						lastUpdateTime=endDate;
					}
				}
			}
		}
		
	}
	private void saveDataTransaction(String countDay,String startTime,String endTime) {
		if(StringUtils.isBlank(countDay) || StringUtils.isBlank(startTime) || StringUtils.isBlank(endTime)) {
			return;
		}
		if(!checkCountDay(countDay)) {
			log.info("统计日期记录查询失败 countDay：{}",countDay);
			return;
		}
		JSONObject rechargeData = getStatisticsRecharge(startTime, endTime);
		Long registereNum = getStatisticsUser(startTime, endTime);
		JSONObject gameWinLoseCoin = getStatisticsGameWinLoseCoin(startTime, endTime);
		JSONObject gameWinLoseNum = getStatisticsGameWinLoseNum(startTime, endTime);
		//充值总额
		BigDecimal rechargeTotal =BigDecimal.ZERO;
		//充值人数
		Long rechargeNum = 0l;
		if(rechargeData!=null && !rechargeData.isEmpty()) {
			rechargeTotal = MathUtil.getBigDecimal(rechargeData.get("rechargeTotal"));
			rechargeNum = MathUtil.getBigDecimal(rechargeData.get("rechargeNum")).longValue();
		}
		//活跃人数
		Long activeNum = 0l;
//		总投入
		Long investmentTotal =0l;
//		总产出
		Long outputTotal =0l;
		if(gameWinLoseCoin!=null && !gameWinLoseCoin.isEmpty()) {
			investmentTotal =Math.abs(MathUtil.getBigDecimal(gameWinLoseCoin.get("investmentTotal")).longValue());
			outputTotal = MathUtil.getBigDecimal(gameWinLoseCoin.get("outputTotal")).longValue();
			activeNum = MathUtil.getBigDecimal(gameWinLoseCoin.get("activeNum")).longValue();
		}
		//赢钱人数
		Long winNum = 0l;
		//输钱人数
		Long loseNum = 0l;
		if(gameWinLoseNum!=null && !gameWinLoseNum.isEmpty()) {
			winNum = MathUtil.getBigDecimal(gameWinLoseNum.get("winNum")).longValue();
			loseNum = MathUtil.getBigDecimal(gameWinLoseNum.get("loseNum")).longValue();
		}
		 
		
		StringBuffer updateSql=new StringBuffer();
		updateSql.append(" UPDATE report_data_daily SET recharge_total=?,");
		updateSql.append("  recharge_num=?,registere_num=?,");
		updateSql.append("  active_num=?,win_num=?,lose_num=?,");
		updateSql.append("  investment_total=?,output_total=?,");
		updateSql.append("  win_total=?,update_time=now()");
		updateSql.append("  WHERE  count_day = ?");
		
		StringBuffer updateScheduleSql=new StringBuffer();
		updateScheduleSql.append(" UPDATE sys_schedule");
		updateScheduleSql.append("  SET update_time = ?");
		updateScheduleSql.append(" where schedule_name=?");
		
		String sqlArray[] =new String[2];
		sqlArray[0]=updateSql.toString();
		Object param0[] =new Object[10];
		param0[0]=rechargeTotal;
		param0[1]=rechargeNum;
		param0[2]=registereNum;
		param0[3]=activeNum;
		param0[4]=winNum;
		param0[5]=loseNum;
		param0[6]=investmentTotal;
		param0[7]=outputTotal;
		param0[8]=investmentTotal-outputTotal;
		param0[9]=countDay;
		
		sqlArray[1]=updateScheduleSql.toString();
		Object param1[] =new Object[2];
		param1[0]=endTime;
		param1[1]=Constant.REPORT_DATA_SCHEDULE;
		jdbcUtil.transactionExecuteUpdate(sqlArray,param0,param1);
	}
	/**
	 * 根据时间段统计充值
	 * @param startTime
	 * @param endTime
	 * @return rechargeTotal rechargeNum
	 */
	private JSONObject getStatisticsRecharge(String startTime, String endTime ) {
		StringBuffer sql=new StringBuffer();
		sql.append(" SELECT sum(amount) rechargeTotal , COUNT(DISTINCT user_id) rechargeNum from order_recharge");
		sql.append(" where status=2 and amount >0  and recharge_time>=?");
		sql.append("  and recharge_time<? and gm_user =0");
		JSONObject data=null;
		try {
			data = jdbcUtil.selectByParamReturnJsonObject(sql.toString(), startTime,endTime);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("getStatisticsRecharge error {}",e);
		}
		return data;
	}
	
	/**
	 * 根据时间段统计注册人数
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	private Long getStatisticsUser(String startTime, String endTime ) {
		StringBuffer sql=new StringBuffer();
		sql.append(" SELECT COUNT(id) registereNum from user ");
		sql.append(" where  create_time>=?");
//		sql.append("  and create_time<?");
		sql.append("  and create_time<? and user_type = ? ");
		JSONObject data=null;
		Long registereNum=0l;
		try {
			data = jdbcUtil.selectByParamReturnJsonObject(sql.toString(), startTime,endTime,"USER");
			if(data!=null) {
				registereNum = MathUtil.getBigDecimal(data.get("registereNum")).longValue();
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error("getStatisticsUser error {}",e);
		}
		return registereNum;
	}
	
	/**
	 * 根据时间段统计游戏活跃人数和 总投入、总产出
	 * @param startTime
	 * @param endTime
	 * @return activeNum investmentTotal outputTotal
	 */
	private JSONObject getStatisticsGameWinLoseCoin(String startTime, String endTime ) {
		StringBuffer sql=new StringBuffer();
		sql.append(" SELECT COUNT(DISTINCT user_id) activeNum,");
		sql.append(" sum(CASE  WHEN prize_coins<0 THEN prize_coins ELSE 0 END) investmentTotal,");
		sql.append(" sum(CASE  WHEN prize_coins>0 THEN prize_coins ELSE 0 END) outputTotal");
		sql.append(" FROM game_record");
		sql.append(" where create_time>=?");
		sql.append("  and room_id  != 2 ");
		sql.append("  and create_time<? and gm_user =0");
		JSONObject data=null;
		try {
			data = jdbcUtil.selectByParamReturnJsonObject(sql.toString(), startTime,endTime);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("getStatisticsGameWinLoseCoin error {}",e);
		}
		return data;
	}
	
	/**
	 * 根据时间段统计游戏赢钱人数和 输钱人数
	 * @param startTime
	 * @param endTime
	 * @return loseNum winNum
	 */
	private JSONObject getStatisticsGameWinLoseNum(String startTime, String endTime ) {
		StringBuffer sql=new StringBuffer();
		sql.append(" SELECT sum(CASE  WHEN prize_coins<0 THEN 1 ELSE 0 END) loseNum,");
		sql.append(" sum(CASE  WHEN prize_coins>0 THEN 1 ELSE 0 END) winNum");
		sql.append(" FROM ( SELECT user_id, sum( prize_coins) prize_coins");
		sql.append(" FROM game_record");
		sql.append(" where create_time>=?");
		sql.append("  and create_time<? and gm_user =0");
		sql.append("  GROUP BY user_id )tmp");
		JSONObject data=null;
		try {
			data = jdbcUtil.selectByParamReturnJsonObject(sql.toString(), startTime,endTime);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("getStatisticsGameWinLoseNum error {}",e);
		}
		return data;
	}
	
	/**
	 * 根据统计日期是否存在这条记录
	 * 没有新增一条
	 * @param countDay
	 * @return
	 */
	private Boolean checkCountDay(String countDay) {
		StringBuffer sql=new StringBuffer();
		sql.append(" SELECT *");
		sql.append(" FROM report_data_daily");
		sql.append(" where count_day=?");
		JSONObject data=null;
		Boolean isOk = false;
		try {
			data = jdbcUtil.selectByParamReturnJsonObject(sql.toString(), countDay);
			if(data==null || data.isEmpty()) {
				StringBuffer insertSql=new StringBuffer();
				insertSql.append(" INSERT into report_data_daily(create_time,update_time,count_day)");
				insertSql.append("  VALUES(NOW(),NOW(),?)");
				int i= jdbcUtil.insert(insertSql.toString(), countDay);
				isOk = i>0;
			}else {
				isOk = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error("getStatisticsGameWinLoseNum error {}",e);
		}
		return isOk;
	}
	
	

}

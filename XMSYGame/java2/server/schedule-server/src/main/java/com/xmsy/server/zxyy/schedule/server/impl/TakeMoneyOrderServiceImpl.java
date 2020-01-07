package com.xmsy.server.zxyy.schedule.server.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.xmsy.server.zxyy.schedule.jdbc.WebHomeJdbcUtil;
import com.xmsy.server.zxyy.schedule.server.TakeMoneyOrderService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service("takeMoneyOrderService")
public class TakeMoneyOrderServiceImpl implements TakeMoneyOrderService {

	@Autowired
	private WebHomeJdbcUtil jdbcUtil;
	
	@Override
	public JSONArray statisticsTakeMoneyByMonth() {
		StringBuffer sql=new StringBuffer();
		sql.append(" SELECT type takeType,countMonth,");
		sql.append(" SUM( CASE WHEN  STATUS = 0 THEN 	obtainAmount ELSE 	0 END ) as unconfirmedObtainAmount,");
		sql.append(" SUM( CASE WHEN  STATUS = 1 THEN 	obtainAmount ELSE 	0 END ) as cancelObtainAmount,,");
		sql.append(" SUM( CASE WHEN  STATUS = 2 THEN 	obtainAmount ELSE 	0 END ) as confirmedObtainAmount,");
		sql.append(" SUM( CASE WHEN  STATUS = 0 THEN 	takeAmount ELSE 	0 END ) AS unconfirmedAmount,");
		sql.append(" SUM( CASE WHEN  STATUS = 1 THEN 	takeAmount ELSE 	0 END ) AS cancelAmount,");
		sql.append(" SUM( CASE WHEN  STATUS = 2 THEN 	takeAmount ELSE 	0 END ) AS confirmedAmount,");
		sql.append(" SUM( CASE WHEN  STATUS = 0 THEN 	poundage ELSE 	0 END ) AS unconfirmedPoundage,");
		sql.append(" SUM( CASE WHEN  STATUS = 1 THEN 	poundage ELSE 	0 END ) AS cancelPoundage,");
		sql.append(" SUM( CASE WHEN  STATUS = 2 THEN 	poundage ELSE 	0 END ) AS confirmedPoundage,");
		sql.append(" SUM( CASE WHEN  STATUS = 0 THEN 	num ELSE 	0 END ) AS unconfirmedNum,");
		sql.append(" SUM( CASE WHEN  STATUS = 1 THEN 	num ELSE 	0 END ) AS cancelNum,");
		sql.append(" SUM( CASE WHEN  STATUS = 2 THEN 	num ELSE 	0 END ) AS confirmedNum");
		sql.append(" from (");
		sql.append(" SELECT type,`status`,SUM(obtain_amount) obtainAmount,SUM(poundage) poundage,SUM(take_amount) takeAmount,COUNT(1) num ");
		sql.append(" from order_take_money");
		sql.append(" where delete_status = 0 and STATUS != 3 and gm_user =0");
		sql.append(" AND date_format(create_time, '%Y-%m') = date_format(DATE_SUB(curdate(), INTERVAL 1 MONTH),'%Y-%m')");
		sql.append(" GROUP BY `status`,type,date_format(create_time, '%Y-%m')");
		sql.append(" ) tem ");
		sql.append(" GROUP BY type,countMonth");
		sql.append(" ORDER BY type");
		JSONArray data=null;
		try {
			data = jdbcUtil.selectByParamReturnJsonArray(sql.toString());
		} catch (Exception e) {
			e.printStackTrace();
			log.error("statisticsRechargeByMonth error ",e);
		}
		return data;
	}

}

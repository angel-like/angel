package com.xmsy.server.zxyy.schedule.server.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.xmsy.server.zxyy.schedule.jdbc.WebHomeJdbcUtil;
import com.xmsy.server.zxyy.schedule.server.RechargeOrderService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service("rechargeOrderService")
public class RechargeOrderServiceImpl implements RechargeOrderService {
	@Autowired
	private WebHomeJdbcUtil jdbcUtil;
	@Override
	public JSONArray statisticsRechargeByDay(String startDate, String endDate) {
		
		StringBuffer sql=new StringBuffer();
		sql.append(" SELECT user_id userId,user_account userAccount,SUM(amount)  amount");
		sql.append(" from order_recharge");
		sql.append(" where `status`= 2 and recharge_time >=?");
		sql.append(" and recharge_time <=? and gm_user =0");
		sql.append(" GROUP BY user_id,user_account");
		sql.append(" order by amount DESC");
		JSONArray data=null;
		try {
			data = jdbcUtil.selectByParamReturnJsonArray(sql.toString(), startDate,endDate);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("statisticsRechargeByDay error {}",e);
		}
		return data;
	}
	@Override
	public JSONArray statisticsRechargeByMonth() {
		StringBuffer sql=new StringBuffer();
		sql.append(" SELECT recharge_platform rechargePlatform,countMonth,");
		sql.append(" recharge_type rechargeType,");
		sql.append(" sum( CASE WHEN  STATUS = 0 THEN 	amount ELSE 	0 END ) AS unconfirmedAmount,");
		sql.append(" sum( CASE WHEN  STATUS = 1 THEN 	amount ELSE 	0 END ) AS cancelAmount,");
		sql.append(" sum( CASE WHEN  STATUS = 2 THEN 	amount ELSE 	0 END ) AS confirmedAmount,");
		sql.append(" sum( CASE WHEN  STATUS = 0 THEN 	discountAmount ELSE 	0 END ) AS unconfirmedDiscountAmount,");
		sql.append(" sum( CASE WHEN  STATUS = 1 THEN 	discountAmount ELSE 	0 END ) AS cancelDiscountAmount,");
		sql.append(" sum( CASE WHEN  STATUS = 2 THEN 	discountAmount ELSE 	0 END ) AS confirmedDiscountAmount,");
		sql.append(" sum( CASE WHEN  STATUS = 0 THEN 	num ELSE 	0 END ) AS unconfirmedNum,");
		sql.append(" sum( CASE WHEN  STATUS = 1 THEN 	num ELSE 	0 END ) AS cancelNum,");
		sql.append(" sum( CASE WHEN  STATUS = 2 THEN 	num ELSE 	0 END ) AS confirmedNum");
		sql.append(" from (");
		sql.append(" SELECT date_format(recharge_time, '%Y-%m') countMonth,recharge_platform,`status`,recharge_type,SUM(discount_amount) discountAmount,SUM(amount) amount,COUNT(1) num");
		sql.append(" from order_recharge");
		sql.append(" where delete_status = 0 and STATUS != 3 and gm_user =0");
		sql.append(" AND date_format(recharge_time, '%Y-%m') = date_format(DATE_SUB(curdate(), INTERVAL 1 MONTH),'%Y-%m')");
		sql.append(" GROUP BY recharge_platform,`status`,recharge_type,date_format(recharge_time, '%Y-%m')");
		sql.append(" ) tem ");
		sql.append(" GROUP BY recharge_platform,recharge_type,countMonth");
		sql.append(" ORDER BY recharge_type,recharge_platform");
		JSONArray data=null;
		try {
			data = jdbcUtil.selectByParamReturnJsonArray(sql.toString());
		} catch (Exception e) {
			e.printStackTrace();
			log.error("statisticsRechargeByMonth error ",e);
		}
		return data;
	}
	@Override
	public JSONArray findUserRechargeLast() {
		
		StringBuffer sql=new StringBuffer();
		sql.append(" SELECT a.user_id userId,a.user_account userAccount, a.amount,u.money, u.coin,a.pre_money preMoney,u.risk_hierarchy_id riskHierarchyId");
		sql.append(" FROM order_recharge a,`user` u");
		sql.append(" WHERE a.user_id = u.id  and  no_scan = 0  ");
		sql.append(" and u.game_info_id>0 and a.id in (");
		sql.append(" SELECT  MAX(id) id");
		sql.append(" FROM order_recharge");
		sql.append(" WHERE `status` = 2 GROUP BY user_id ) ");
		JSONArray data=null;
		try {
			data = jdbcUtil.selectByParamReturnJsonArray(sql.toString());
		} catch (Exception e) {
			e.printStackTrace();
			log.error("findUserRechargeLast error ",e);
		}
		return data;
	}

}

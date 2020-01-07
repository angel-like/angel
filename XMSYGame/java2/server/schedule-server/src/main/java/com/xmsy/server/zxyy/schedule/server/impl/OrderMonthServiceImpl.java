package com.xmsy.server.zxyy.schedule.server.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xmsy.server.zxyy.schedule.jdbc.WebHomeJdbcUtil;
import com.xmsy.server.zxyy.schedule.server.OrderMonthService;
import com.xmsy.server.zxyy.schedule.utils.Constant;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service("orderMonthService")
public class OrderMonthServiceImpl implements OrderMonthService {

	@Autowired
	private WebHomeJdbcUtil jdbcUtil;
	
	@Override
	public void saveRechargeOrderMonth(JSONArray dataList) {
		log.info("dataList {}",dataList);
		StringBuilder insertSql=new StringBuilder();
		insertSql.append("INSERT INTO  order_recharge_month(");
		insertSql.append("recharge_platform,recharge_type,recharge_type_name,recharge_platform_name,");
		insertSql.append("unconfirmed_amount,cancel_amount,confirmed_amount,");
		insertSql.append("unconfirmed_discount_amount,cancel_discount_amount,confirmed_discount_amount,");
		insertSql.append("unconfirmed_num,cancel_num,confirmed_num,");
		insertSql.append("count_month,create_time,update_time)");
		insertSql.append("VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,now(),now())");
		List<Object[]> paramsList =new ArrayList<>();
		for(int i=0;i<dataList.size();i++) {
			JSONObject data = dataList.getJSONObject(i);
			Object[] param= new Object[14];
			param[0] =data.get("rechargePlatform");
			param[1] =data.getIntValue("rechargeType");
			if(data.getIntValue("rechargeType")==Constant.RechargeType.ADMIN.getValue()) {
				param[2] ="人工充值";
				param[3] ="";
			}else if(data.getIntValue("rechargeType")==Constant.RechargeType.THIRD.getValue()) {
				param[2] ="第三方充值";
				param[3] =getRechargePlatformName(data.get("rechargePlatform"));
			}else if(data.getIntValue("rechargeType")==Constant.RechargeType.BANK.getValue()) {
				param[2] ="线下存款";
				param[3] ="";
			}
			param[4] = data.get("unconfirmedAmount");
			param[5] = data.get("cancelAmount");
			param[6] = data.get("confirmedAmount");
			param[7] = data.get("unconfirmedDiscountAmount");
			param[8] = data.get("cancelDiscountAmount");
			param[9] = data.get("confirmedDiscountAmount");
			param[10] = data.get("unconfirmedNum");
			param[11] = data.get("cancelNum");
			param[12] = data.get("confirmedNum");
			param[13] = data.get("countMonth");
			paramsList.add(param);
		}
		jdbcUtil.insertBatch(insertSql.toString(), paramsList);

	}
	/**
	 * 取得交易平台名称
	 * @param obj
	 * @return
	 */
	private String getRechargePlatformName(Object obj) {
		if(obj ==null || StringUtils.isBlank(obj.toString().trim())) {
			return "";
		}
		String name="";
		StringBuilder sql=new StringBuilder();
		sql.append(" select id,name from pay_config");
		sql.append(" where id = ?");
		try {
			JSONObject data = jdbcUtil.selectByParamReturnJsonObject(sql.toString(), Long.parseLong(obj.toString()));
			if(data!=null) {
				name=data.getString("name");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return name;
	}

	@Override
	public void saveTakeMoneyOrderMonth(JSONArray dataList) {
		StringBuilder insertSql=new StringBuilder();
		insertSql.append("INSERT INTO  order_recharge_month(");
		insertSql.append("take_type,take_type_name,");
		insertSql.append("unconfirmed_amount,cancel_amount,confirmed_amount,");
		insertSql.append("unconfirmed_poundage,cancel_poundage,confirmed_poundage,");
		insertSql.append("unconfirmed_obtain_amount,cancel_obtain_amount,confirmed_obtain_amount,");
		insertSql.append("unconfirmed_num,cancel_num,confirmed_num,");
		insertSql.append("count_month,create_time,update_time)");
		insertSql.append("VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,now(),now())");
		List<Object[]> paramsList =new ArrayList<>();
		for(int i=0;i<dataList.size();i++) {
			JSONObject data = dataList.getJSONObject(i);
			Object[] param= new Object[15];
			param[0] =data.get("takeType");
			if(data.getIntValue("takeType")==Constant.TakeMoneyType.TAKE_MONEY.getValue()) {
				param[1] ="余额取款";
			}else if(data.getIntValue("takeType")==Constant.TakeMoneyType.COMMISSION.getValue()) {
				param[1] ="佣金取款";
			}
			param[2] = data.get("unconfirmedAmount");
			param[3] = data.get("cancelAmount");
			param[4] = data.get("confirmedAmount");
			param[5] = data.get("unconfirmedPoundage");
			param[6] = data.get("cancelPoundage");
			param[7] = data.get("confirmedPoundage");
			param[8] = data.get("unconfirmedObtainAmount");
			param[9] = data.get("cancelObtainAmount");
			param[10] = data.get("confirmedObtainAmount");
			param[11] = data.get("unconfirmedNum");
			param[12] = data.get("cancelNum");
			param[13] = data.get("confirmedNum");
			param[14] = data.get("countMonth");
			paramsList.add(param);
		}
		jdbcUtil.insertBatch(insertSql.toString(), paramsList);
	}

}

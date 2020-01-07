package com.xmsy.server.zxyy.schedule.server.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.xmsy.server.zxyy.schedule.jdbc.WebHomeJdbcUtil;
import com.xmsy.server.zxyy.schedule.server.PieAwardService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service("pieAwardService")
public class PieAwardServiceImpl implements PieAwardService{
	@Autowired
	private WebHomeJdbcUtil jdbcUtil;
	@Override
	public JSONArray findPieAwardByDay(String startDate, String endDate) {
		StringBuffer sql=new StringBuffer();
		sql.append(" SELECT user_id userId,user_account userAccount,SUM(amount)  amount");
		sql.append(" from pool_dispatch_detail_list");
		sql.append(" where dispatch_time >=?");
		sql.append(" and dispatch_time <=?");
		sql.append(" GROUP BY user_id,user_account");
		sql.append(" order by amount DESC");
		JSONArray data=null;
		try {
			data = jdbcUtil.selectByParamReturnJsonArray(sql.toString(), startDate,endDate);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("findPieAwardByDay error {}",e);
		}
		return data;
	}

}

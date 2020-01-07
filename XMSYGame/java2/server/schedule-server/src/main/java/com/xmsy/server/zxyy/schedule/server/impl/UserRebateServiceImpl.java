package com.xmsy.server.zxyy.schedule.server.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xmsy.server.zxyy.schedule.jdbc.WebHomeJdbcUtil;
import com.xmsy.server.zxyy.schedule.server.UserRebateService;
import com.xmsy.server.zxyy.schedule.utils.Constant;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service("userRebateService")
public class UserRebateServiceImpl implements UserRebateService {
	@Autowired
	private WebHomeJdbcUtil jdbcUtil;
	
	@Override
	public JSONObject getUserReBateByType(int type) throws Exception {
		String sql="select type,coin,water_rate waterRate,code_multiple codeMultiple from user_rebate"
				+ " where type=? and delete_status=0  "
				+ " order by create_time desc limit 1 ";
		return jdbcUtil.selectByParamReturnJsonObject(sql, type);
	}

	@Override
	public JSONArray statisticsWealthByDay()  {
		StringBuilder sqlSB=new StringBuilder();
		sqlSB.append(" SELECT id userId,account userAccount,");
		sqlSB.append(" money*? +coin amount");
		sqlSB.append(" FROM `user`");
		sqlSB.append(" where user_type ='USER' ");
		sqlSB.append(" and forbidden_enable = 0 ");
		sqlSB.append(" and delete_status=0 ");
		sqlSB.append(" order by amount DESC;");
		JSONArray data=null;
		try {
			log.debug("statisticsWealthByDay sql:{}",sqlSB.toString());
			data = jdbcUtil.selectByParamReturnJsonArray(sqlSB.toString(), Constant.COIN_RATE);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("statisticsWealthByDay error {}",e);
		}
		return data;
	}

}

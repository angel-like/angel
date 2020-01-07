package com.xmsy.server.zxyy.schedule.server.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xmsy.server.zxyy.schedule.jdbc.WebHomeJdbcUtil;
import com.xmsy.server.zxyy.schedule.server.UserRiskConfigService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service("userRiskConfigService")
public class UserRiskConfigServiceImpl implements UserRiskConfigService {
	@Autowired
	private WebHomeJdbcUtil jdbcUtil;
	@Override
	public JSONObject getDefHierarchy() {
		StringBuffer sql=new StringBuffer();
		sql.append(" SELECT id,`name`");
		sql.append(" from user_hierarchy");
		sql.append(" where delete_status = 0 and type =1");
		JSONObject data=null;
		try {
			data = jdbcUtil.selectByParamReturnJsonObject(sql.toString());
		} catch (Exception e) {
			e.printStackTrace();
			log.error("statisticsRechargeByDay error {}",e);
		}
		return data;
	}

	@Override
	public JSONArray findRiskConfigList(String riskType) {
		StringBuffer sql=new StringBuffer();
		sql.append(" SELECT hierarchy_id hierarchyId,risk_type riskType,risk_val  riskVal");
		sql.append(" from user_risk_config");
		sql.append(" where delete_status= 0 and risk_type =?");
		sql.append(" order by risk_val");
		JSONArray data=null;
		try {
			data = jdbcUtil.selectByParamReturnJsonArray(sql.toString(), riskType);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("statisticsRechargeByDay error {}",e);
		}
		return data;
	}

}

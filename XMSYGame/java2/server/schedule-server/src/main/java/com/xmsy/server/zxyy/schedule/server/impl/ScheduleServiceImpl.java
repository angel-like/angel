package com.xmsy.server.zxyy.schedule.server.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.xmsy.server.zxyy.schedule.jdbc.WebHomeJdbcUtil;
import com.xmsy.server.zxyy.schedule.server.ScheduleService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service("scheduleService")
public class ScheduleServiceImpl implements ScheduleService {
	@Autowired
	private WebHomeJdbcUtil jdbcUtil;
	@Override
	public JSONObject getScheduleByName(String name) {
		
		StringBuffer sql=new StringBuffer();
		sql.append(" SELECT id,update_time updateTime,schedule_name scheduleName");
		sql.append(" from sys_schedule");
		sql.append(" where schedule_name=?");
		JSONObject data=null;
		try {
			data = jdbcUtil.selectByParamReturnJsonObject(sql.toString(), name);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("getScheduleByName error {}",e);
		}
		if(data==null || data.isEmpty()) {
			StringBuffer insertsql=new StringBuffer();
			insertsql.append(" INSERT INTO sys_schedule (create_time, update_time,schedule_name)  ");
			insertsql.append(" VALUES ( NOW(), NOW(),?);");
			jdbcUtil.insert(insertsql.toString(), name);
		}
		return data;
	}
	@Override
	public void updateLastTime(Date lastTime, String name) {
		StringBuffer updateScheduleSql=new StringBuffer();
		updateScheduleSql.append(" UPDATE sys_schedule");
		updateScheduleSql.append("  SET update_time = ?");
		updateScheduleSql.append(" where schedule_name=?");
		
		jdbcUtil.update(updateScheduleSql.toString(), lastTime,name);
		
	}

}

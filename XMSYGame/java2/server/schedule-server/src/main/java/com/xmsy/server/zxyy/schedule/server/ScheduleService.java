package com.xmsy.server.zxyy.schedule.server;

import java.util.Date;

import com.alibaba.fastjson.JSONObject;

public interface ScheduleService {
	
	/**
	 * 根据名称取得定时任务最后执行时间
	 * @param name
	 * @return
	 */
	JSONObject getScheduleByName(String name);
	/**
	 * 根据名称更新定时任务最后执行时间
	 * @param lastTime
	 * @param name
	 */
	void updateLastTime(Date lastTime,String name);

}

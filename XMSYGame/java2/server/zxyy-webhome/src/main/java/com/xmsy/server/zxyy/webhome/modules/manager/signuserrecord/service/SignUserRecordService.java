package com.xmsy.server.zxyy.webhome.modules.manager.signuserrecord.service;

import java.util.Date;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.service.IService;
import com.xmsy.server.zxyy.webhome.modules.manager.signuserrecord.entity.SignUserRecordEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.user.entity.UserEntity;


/**
 * 用户签到记录表
 *
 * @author aleng
 * @email xxxxx
 * @date 2019-05-13 15:50:02
 */
public interface SignUserRecordService extends IService<SignUserRecordEntity> {

	JSONObject insertRecord(Long userId) throws Exception;
	
	JSONObject insertRecordTwo(UserEntity user) throws Exception;
	
	/**
	 * 获取签到次数
	 * @param userId
	 * @param signDate
	 * @return
	 */
	Integer continuousNum(Long userId,Date signDate);
	
	/**
	 * 今日是否签到
	 * @param userId
	 * @param signDate
	 * @return
	 */
	Integer todaySign(Long userId,String signDate);
	


}


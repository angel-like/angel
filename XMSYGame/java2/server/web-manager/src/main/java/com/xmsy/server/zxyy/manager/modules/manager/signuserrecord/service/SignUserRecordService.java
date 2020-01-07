package com.xmsy.server.zxyy.manager.modules.manager.signuserrecord.service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.service.IService;
import com.xmsy.server.zxyy.manager.modules.manager.signuserrecord.entity.SignUserRecordEntity;


/**
 * 用户签到记录表
 *
 * @author aleng
 * @email xxxxx
 * @date 2019-05-13 15:50:02
 */
public interface SignUserRecordService extends IService<SignUserRecordEntity> {

	JSONObject insertRecord(Long userId) throws Exception;

}


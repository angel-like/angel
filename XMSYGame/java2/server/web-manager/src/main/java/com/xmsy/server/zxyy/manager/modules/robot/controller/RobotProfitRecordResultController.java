package com.xmsy.server.zxyy.manager.modules.robot.controller;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.xmsy.server.zxyy.manager.modules.robot.service.RobotProfitRecordResultService;


/**
 * 每日统计结果表
 *
 * @author axiong
 * @email xxxxx
 * @date 2019-10-02 16:29:12
 */
@RestController
@RequestMapping("robotprofitrecordresult/robotprofitrecordresult")
public class RobotProfitRecordResultController {
	@Autowired
	private RobotProfitRecordResultService robotProfitRecordResultService;
	/**
     * 机器人游戏管理列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("robotprofitrecordresult:robotprofitrecordresult:list")
    public JSONObject list(@RequestBody JSONObject RobotProfitRecordResult){
		return  robotProfitRecordResultService.getRobotProfitRecordResultList(RobotProfitRecordResult); 
    }
    
    /**
	 * 重新统计
	 */
	@RequestMapping("/sumrobotRecord")
	 public JSONObject sumrobotRecord() {
		return  robotProfitRecordResultService.sumrobotRecord();
	}
}

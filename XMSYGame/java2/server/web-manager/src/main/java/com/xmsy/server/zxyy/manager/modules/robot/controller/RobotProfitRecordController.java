package com.xmsy.server.zxyy.manager.modules.robot.controller;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.xmsy.server.zxyy.manager.modules.robot.service.RobotProfitRecordService;

/**
 * 机器人盈利报表统计
 *
 * @author axiong
 * @email xxxxx
 * @date 2019-10-02 10:37:36
 */
@RestController
@RequestMapping("robotprofitrecord/robotprofitrecord")
public class RobotProfitRecordController {
	
	@Autowired
    private RobotProfitRecordService robotProfitRecordService;
	/**
     * 列表  
     */
    @RequestMapping("/list")
    @RequiresPermissions("robotprofitrecord:robotprofitrecord:list")
    public JSONObject list(@RequestBody JSONObject robotProfitRecord){
    	
    	return robotProfitRecordService.getRobotProfitRecordList(robotProfitRecord);
    }
}

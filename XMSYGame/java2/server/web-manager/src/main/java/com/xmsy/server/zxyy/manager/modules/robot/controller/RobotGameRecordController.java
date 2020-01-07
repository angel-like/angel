package com.xmsy.server.zxyy.manager.modules.robot.controller;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.xmsy.server.zxyy.manager.modules.robot.service.RobotGameRecordService;

/**
 * 机器人游戏记录
 *
 * @author axiong
 * @email xxxxx
 * @date 2019-09-30 14:58:45
 */
@RestController
@RequestMapping("robotgamerecord/robotgamerecord")
public class RobotGameRecordController {
	@Autowired
    private RobotGameRecordService robotGameRecordService;
	 /**
     * 机器人游戏记录列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("robotgamerecord:robotgamerecord:list")
    public JSONObject list(@RequestBody JSONObject robotgamerecord){
		return  robotGameRecordService.getRobotGameRecordList(robotgamerecord); 
    }
}

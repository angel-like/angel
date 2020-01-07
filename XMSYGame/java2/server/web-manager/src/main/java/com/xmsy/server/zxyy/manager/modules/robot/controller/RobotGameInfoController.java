package com.xmsy.server.zxyy.manager.modules.robot.controller;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.xmsy.server.zxyy.manager.modules.robot.service.RobotGameInfoService;
import com.alibaba.fastjson.JSONObject;

/**
 * 机器人游戏管理
 *
 * @author axiong
 * @email xxxxx
 * @date 2019-09-30 14:58:45
 */
@RestController
@RequestMapping("gameinfo/gameinfo")
public class RobotGameInfoController {
	
	@Autowired
    private RobotGameInfoService robotGameInfoService;
	 /**
     * 机器人游戏管理列表
     */
    @RequestMapping("/robotGameList")
    @RequiresPermissions("gameinfo:gameinfo:robotGameList")
    public JSONObject list(@RequestBody JSONObject robotgameinfo){
		return  robotGameInfoService.getRobotGameInfoList(robotgameinfo); 
    }
    
    /**
     * 重置人数配置  ----关闭游戏机器人
     */
    @RequestMapping("/close")
    @RequiresPermissions("gameinfo:gameinfo:close")
    public JSONObject close(){
		return  robotGameInfoService.closeAllRobot(); 
    }
    
    /**
     * 强制回收机器人 ---强制回收指定游戏机器人
     */
    @RequestMapping("/forceTaskRobot")
    @RequiresPermissions("gameinfo:gameinfo:forceTaskRobot")
    public JSONObject forceTaskRobot(@RequestBody Long[] ids ) {
    	return  robotGameInfoService.forceTaskRobot(ids); 
    }
    
    /**
     * 还原机器人 ---强制还原指定游戏机器人
     */
    @RequestMapping("/forceOnTaskRobot")
    @RequiresPermissions("gameinfo:gameinfo:forceOnTaskRobot")
    public JSONObject forceOnTaskRobot(@RequestBody Long[] ids ) {
    	return  robotGameInfoService.forceOnTaskRobot(ids); 
    }
    /**
	 * 获取游戏机器人配置
	 */
    @RequestMapping("/robotGameConfig")
	@RequiresPermissions("gameinfo:gameinfo:robotGameConfig")
    public JSONObject robotGameConfig(@RequestParam("gameId") String gameId ) {
    	return  robotGameInfoService.robotGameConfig(gameId); 
    }
    /**
	 * 提交机器人配置
	 */
    @RequestMapping("/saveConfig")
    @RequiresPermissions("gameinfo:gameinfo:saveConfig")
    public JSONObject saveConfig(@RequestBody JSONObject json) {
    	return  robotGameInfoService.saveConfig(json); 
    }
}




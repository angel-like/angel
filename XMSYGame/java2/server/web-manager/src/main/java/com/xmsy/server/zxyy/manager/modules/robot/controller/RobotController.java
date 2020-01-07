package com.xmsy.server.zxyy.manager.modules.robot.controller;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.xmsy.server.zxyy.manager.common.utils.R;
import com.xmsy.server.zxyy.manager.modules.robot.service.RobotService;



/**
 * 机器人管理
 *
 * @author adu
 * @email xxxxx
 * @date 2019-09-25 14:58:45
 */
@RestController
@RequestMapping("robot/robot")
public class RobotController {
    @Autowired
    private RobotService robotService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("robot:robot:list")
    public JSONObject list(@RequestBody JSONObject robot){
		return robotService.getRobotList(robot); 
//		return R.ok().put("page", new PageUtils(result.getRecords(), result.getTotal(), result.getSize(),
//				result.getCurrent(), result.getPages())).put("countProfit", countProfit);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("robot:robot:info")
    public R info(@PathVariable("id") Long id){
        return R.ok().put("robot", null);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("robot:robot:save")
    public R save(@RequestBody JSONObject robot){
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("robot:robot:update")
    public R update(@RequestBody JSONObject robot){
        return R.ok();
    }
    
    /**
     * 启用
     */
    @RequestMapping("/enable/{id}")
    @RequiresPermissions("robot:robot:enable")
    public R enable(@PathVariable("id") Long id){
        return R.ok().put("robot", null);
    }
    /**
     * 结算
     */
    @RequestMapping("/disable/{id}")
    @RequiresPermissions("robot:robot:disable")
    public JSONObject disable(@PathVariable("id") Long id){
        return robotService.disable(id);
    }
    
    /**
     * 机器人回收
     */
    @RequestMapping("/taskRecyclingRobot")
    @RequiresPermissions("robot:robot:taskRecyclingRobot")
    public JSONObject taskRecyclingRobot(@RequestBody JSONObject jsonObj ){
    	
        return robotService.taskRecyclingRobot(jsonObj);
    }
   
}

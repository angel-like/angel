package com.xmsy.server.zxyy.robot.modules.manager.robot.controller;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.util.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.xmsy.common.define.constant.ResultDef;
import com.xmsy.common.define.page.PageParam;
import com.xmsy.server.zxyy.robot.common.utils.PageUtils;
import com.xmsy.server.zxyy.robot.common.utils.R;
import com.xmsy.server.zxyy.robot.constant.SysConstant;
import com.xmsy.server.zxyy.robot.modules.gamemanage.GameInfoInterface;
import com.xmsy.server.zxyy.robot.modules.manager.robot.entity.RobotEntity;
import com.xmsy.server.zxyy.robot.modules.manager.robot.service.RobotService;



/**
 * 机器人管理
 *
 * @author xiaoliu
 * @email xxxxx
 * @date 2019-03-18 14:58:45
 */
@RestController
@RequestMapping("robot/robot")
public class RobotController {
    @Autowired
    private RobotService robotService;

    /**
     * 列表     为了获取游戏名称
     */
    @RequestMapping("/list")
//    @RequiresPermissions("robot:robot:list")
    public R list(RobotEntity robot, PageParam pageParam){
    	// 获取游戏大厅接口，获取到游戏列表
    	JSONObject returnObj = GameInfoInterface.gameSelectForRobot();
        Page<RobotEntity> result = new Page<RobotEntity>(pageParam.getPage(), pageParam.getLimit());
		Wrapper<RobotEntity> entityWrapper = new EntityWrapper<RobotEntity>(robot);
		entityWrapper.orderBy(pageParam.getSort(), pageParam.getDirection()).orderBy("update_time", false);
		robot.selectPage(result, entityWrapper);
		Long countProfit=0L;//总盈利
		if (!CollectionUtils.isEmpty(result.getRecords())) {
			// 便利获取每条数据ID
			for (RobotEntity entity : result.getRecords()) {
				// 如果游戏ID不为空
				if (entity.getGameId() != null && entity.getGameId() != 0&&entity.getGradeId() != null && entity.getGradeId() != 0) {
					// 如果游戏大厅接口返回成功
					if (returnObj.getInteger("code") != null && returnObj.getInteger("code") == ResultDef.SUCCESS) {
						// 获取到游戏接口
						JSONArray gameJson = returnObj.getJSONArray("data");
						// 便利游戏列表
						for (int i = 0; i < gameJson.size(); i++) {
							// 每个游戏对象
							JSONObject gameObj = (JSONObject) gameJson.get(i);
							// 如果游戏对象不为空，游戏ID和场次Id不为空
							if (null != gameObj && null != gameObj.getLong("gameId")&&null != gameObj.getLong("gradeId")) {
								// 如果游戏ID和场次Id相同就将游戏名称取出，并跳出游戏列表gameJson的循环
								if (gameObj.getLong("gameId") == entity.getGameId()&&gameObj.getLong("gradeId") == entity.getGradeId()) {
									entity.setGameName(gameObj.getString("gameName")+"/"+gameObj.getString("gradeName"));
									break;
								}
							}
						}
					}
				}

			}
		}
		countProfit=robotService.countProfitCoin(robot);//机器人今日盈利
		return R.ok().put("page", new PageUtils(result.getRecords(), result.getTotal(), result.getSize(),
				result.getCurrent(), result.getPages())).put("countProfit", countProfit);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("robot:robot:info")
    public R info(@PathVariable("id") Long id){
			RobotEntity robot = robotService.selectById(id);
        return R.ok().put("robot", robot);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("robot:robot:save")
    public R save(@RequestBody RobotEntity robot){
			robotService.insert(robot);
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("robot:robot:update")
    public R update(@RequestBody RobotEntity robot){
			robotService.updateById(robot);
        return R.ok();
    }
    
    /**
     * 启用
     */
    @RequestMapping("/enable/{id}")
    @RequiresPermissions("robot:robot:enable")
    public R enable(@PathVariable("id") Long id){
    	RobotEntity robot =new RobotEntity();
    	robot.setId(id);
    	robot.setEnable(SysConstant.ROBOT_ENABLE_TRUE);
    	robotService.updateById(robot);
        return R.ok().put("robot", robot);
    }
    /**
     * 结算
     */
    @RequestMapping("/disable/{id}")
    //@RequiresPermissions("robot:robot:disable")
    public R disable(@PathVariable("id") Long id){
    	RobotEntity robot=robotService.selectById(id);
    	String[] ids =id.toString().split(",");
    	robotService.taskRobot(ids,robot.getGameId(),robot.getGradeId());
        return R.ok();
    }
    
    /**
     * 机器人回收
     */
    @RequestMapping("/taskRecyclingRobot")
   // @RequiresPermissions("robot:robot:taskRecyclingRobot")
    public R taskRecyclingRobot(@RequestBody JSONObject jsonObj ){
    	Long gameId=jsonObj.getLong("gameId");
    	Long gradeId=jsonObj.getLong("gradeId");
    	String str=jsonObj.getString("ids").replace("[","").replace("]","");
    	String[] ids =str.split(",");
    	robotService.taskRobot(ids,gameId,gradeId);
        return R.ok();
    }
   
}

package com.xmsy.server.zxyy.robot.modules.manager.robotprofitrecord.controller;

import org.apache.shiro.util.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.xmsy.common.define.constant.ResultDef;
import com.xmsy.common.define.page.PageParam;
import com.xmsy.server.zxyy.robot.common.utils.PageUtils;
import com.xmsy.server.zxyy.robot.common.utils.R;
import com.xmsy.server.zxyy.robot.modules.gamemanage.GameInfoInterface;
import com.xmsy.server.zxyy.robot.modules.manager.robotprofitrecord.entity.RobotProfitRecordEntity;
import com.xmsy.server.zxyy.robot.modules.manager.robotprofitrecord.service.RobotProfitRecordService;



/**
 * 机器人管理
 *
 * @author aleng
 * @email xxxxx
 * @date 2019-04-18 10:37:36
 */
@RestController
@RequestMapping("robotprofitrecord/robotprofitrecord")
public class RobotProfitRecordController {
    @Autowired
    private RobotProfitRecordService robotProfitRecordService;

    /**
     * 列表     为了获取游戏名称 跟 场次名称
     */
    @RequestMapping("/list")
    //@RequiresPermissions("robotprofitrecord:robotprofitrecord:list")
    public R list(RobotProfitRecordEntity robotprofitrecord, PageParam pageParam){
    	// 获取游戏大厅接口，获取到游戏列表
    	JSONObject returnObj = GameInfoInterface.gameSelectForRobot();
    	
    	Page<RobotProfitRecordEntity> result = new Page<RobotProfitRecordEntity>(pageParam.getPage(), pageParam.getLimit());
		Wrapper<RobotProfitRecordEntity> entityWrapper = new EntityWrapper<RobotProfitRecordEntity>(robotprofitrecord)
				.ge(!StringUtils.isEmpty(robotprofitrecord.getStartTime()),"record_time", robotprofitrecord.getStartTime())
				.le(!StringUtils.isEmpty(robotprofitrecord.getEndTime()),"record_time", robotprofitrecord.getEndTime())
				.orderBy("record_time",false);
		entityWrapper.orderBy(pageParam.getSort(), pageParam.getDirection());
		robotprofitrecord.selectPage(result, entityWrapper);
		Long countProfit=0L;//总盈利
		if (!CollectionUtils.isEmpty(result.getRecords())) {
			// 便利获取每条数据ID
			for (RobotProfitRecordEntity entity : result.getRecords()) {
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
									entity.setGameName(gameObj.getString("gameName"));
									entity.setGradeName(gameObj.getString("gradeName"));
									break;
								}
							}
						}
					}
				}

			}
		}
		countProfit=robotProfitRecordService.countProfitCoin(robotprofitrecord);//机器人总盈利
		return R.ok().put("page", new PageUtils(result.getRecords(), result.getTotal(), result.getSize(),
				result.getCurrent(), result.getPages())).put("countProfit", countProfit);
    }
    
    /**
     * 列表
     */
    @RequestMapping("/gameRecordlist")
    //@RequiresPermissions("robotprofitrecord:robotprofitrecord:gameRecordlist")
    public R gameRecordlist(RobotProfitRecordEntity robotprofitrecord, PageParam pageParam){
        Page<RobotProfitRecordEntity> result = new Page<RobotProfitRecordEntity>(pageParam.getPage(), pageParam.getLimit());
		Wrapper<RobotProfitRecordEntity> entityWrapper = new EntityWrapper<RobotProfitRecordEntity>(robotprofitrecord)
				.ge(!StringUtils.isEmpty(robotprofitrecord.getStartTime()),"record_time", robotprofitrecord.getStartTime())
				.le(!StringUtils.isEmpty(robotprofitrecord.getEndTime()),"record_time", robotprofitrecord.getEndTime())
				.orderBy("record_time",false);
		entityWrapper.orderBy(pageParam.getSort(), pageParam.getDirection());
		robotprofitrecord.selectPage(result, entityWrapper);
		return R.ok().put("page", new PageUtils(result.getRecords(), result.getTotal(), result.getSize(),
				result.getCurrent(), result.getPages()));
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    //@RequiresPermissions("robotprofitrecord:robotprofitrecord:info")
    public R info(@PathVariable("id") Long id){
			RobotProfitRecordEntity robotProfitRecord = robotProfitRecordService.selectById(id);
        return R.ok().put("robotprofitrecord", robotProfitRecord);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("robotprofitrecord:robotprofitrecord:save")
    public R save(@RequestBody RobotProfitRecordEntity robotprofitrecord){
			robotProfitRecordService.insert(robotprofitrecord);
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
   // @RequiresPermissions("robotprofitrecord:robotprofitrecord:update")
    public R update(@RequestBody RobotProfitRecordEntity robotprofitrecord){
			robotProfitRecordService.updateById(robotprofitrecord);
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
   //@RequiresPermissions("robotprofitrecord:robotprofitrecord:delete")
    public R delete(@RequestBody Long[] ids){
    for (Long id : ids) {
			robotProfitRecordService.deleteById(id);
	}
        return R.ok();
    }

}

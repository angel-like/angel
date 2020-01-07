package com.xmsy.server.zxyy.robot.modules.manager.gamerecord.controller;

import org.apache.shiro.authz.annotation.RequiresPermissions;
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
import com.xmsy.server.zxyy.robot.constant.SysConstant;
import com.xmsy.server.zxyy.robot.modules.gamemanage.GameInfoInterface;
import com.xmsy.server.zxyy.robot.modules.manager.gamerecord.entity.GameRecordEntity;
import com.xmsy.server.zxyy.robot.modules.manager.gamerecord.service.GameRecordService;



/**
 * 游戏记录
 *
 * @author xiaoliu
 * @email xxxxx
 * @date 2019-04-02 11:32:49
 */
@RestController
@RequestMapping("gamerecord/gamerecord")
public class GameRecordController {
    @Autowired
    private GameRecordService gameRecordService;

    /**
     * 列表     为了获取游戏名称 跟 场次名称
     */
    @RequestMapping("/list")
   // @RequiresPermissions("gamerecord:gamerecord:list")
    public R list(GameRecordEntity gamerecord, PageParam pageParam){
    	// 获取游戏大厅接口，获取到游戏列表
    	JSONObject returnObj = GameInfoInterface.gameSelectForRobot();
        Page<GameRecordEntity> result = new Page<GameRecordEntity>(pageParam.getPage(), pageParam.getLimit());
		Wrapper<GameRecordEntity> entityWrapper = new EntityWrapper<GameRecordEntity>(gamerecord)
				.ge(!StringUtils.isEmpty(gamerecord.getQueryTime()), "create_time", // 存款时间查询
						gamerecord.getQueryTime() + SysConstant.START_TIME)
				.le(!StringUtils.isEmpty(gamerecord.getQueryTime()), "create_time",
				gamerecord.getQueryTime() + SysConstant.END_TIME)
				.orderBy("create_time", false);
		entityWrapper.orderBy(pageParam.getSort(), pageParam.getDirection());
		gamerecord.selectPage(result, entityWrapper);
		Long countProfit=0L;//总盈利
		if (!CollectionUtils.isEmpty(result.getRecords())) {
			// 便利获取每条数据ID
			for (GameRecordEntity entity : result.getRecords()) {
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
								if (gameObj.getLong("gameId") .equals(entity.getGameId()) &&gameObj.getLong("gradeId") .equals(entity.getGradeId()) ) {
									entity.setGameName(gameObj.getString("gameName"));
									entity.setGradeName(gameObj.getString("gradeName"));
									break; 
								}
							}
						}
					}
				}
				//countProfit+=entity.getProfitCoins();
			}
			countProfit=gameRecordService.sumCountProfit(gamerecord);
		}
		return R.ok().put("page", new PageUtils(result.getRecords(), result.getTotal(), result.getSize(),
				result.getCurrent(), result.getPages())).put("countProfit", countProfit);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("gamerecord:gamerecord:info")
    public R info(@PathVariable("id") Long id){
			GameRecordEntity gameRecord = gameRecordService.selectById(id);
        return R.ok().put("gamerecord", gameRecord);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
 //   @RequiresPermissions("gamerecord:gamerecord:save")
    public R save(@RequestBody GameRecordEntity gamerecord){
			gameRecordService.insert(gamerecord);
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("gamerecord:gamerecord:update")
    public R update(@RequestBody GameRecordEntity gamerecord){
			gameRecordService.updateById(gamerecord);
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("gamerecord:gamerecord:delete")
    public R delete(@RequestBody Long[] ids){
    for (Long id : ids) {
			gameRecordService.deleteById(id);
	}
        return R.ok();
    }

}

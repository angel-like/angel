package com.xmsy.server.zxyy.robot.modules.manager.statistics.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xmsy.common.define.constant.ResultDef;
import com.xmsy.server.zxyy.robot.common.utils.Constant;
import com.xmsy.server.zxyy.robot.common.utils.DateUtils;
import com.xmsy.server.zxyy.robot.common.utils.R;
import com.xmsy.server.zxyy.robot.constant.SysConstant;
import com.xmsy.server.zxyy.robot.modules.gamemanage.GameInfoInterface;
import com.xmsy.server.zxyy.robot.modules.manager.gamerecord.dao.GameRecordDao;
import com.xmsy.server.zxyy.robot.modules.manager.gamerecord.entity.GameRecordSumEntity;

/**
 * 统计
 *
 * @author xiaoliu
 * @email xxxxx
 * @date 2019-05-11 10:29:12
 */
// @Slf4j
@RestController
@RequestMapping("statistics/statistics")
public class StatisticsController {
	@Autowired
	private GameRecordDao gameRecordDao;

	/**
	 * 总盈利
	 */
	@RequestMapping("/sumProfitForGame")
	@RequiresPermissions("statistics:statistics:sumProfitForGame")
	public R sumProfitForGame() {
		// 获取到匹配场的所有游戏ID
		JSONObject returnObj = GameInfoInterface.gameSelectForRobot(SysConstant.ROOMID);
		List<Long> gameIds = new ArrayList<Long>();
		// 如果游戏大厅接口返回成功
		if (returnObj.getInteger("code") != null && returnObj.getInteger("code") == ResultDef.SUCCESS) {
			// 获取到游戏接口
			JSONArray gameJson = returnObj.getJSONArray("data");
			// 便利游戏列表
			for (int i = 0; i < gameJson.size(); i++) {
				// 每个游戏对象
				JSONObject gameObj = (JSONObject) gameJson.get(i);
				// 如果游戏对象不为空，游戏ID和场次Id不为空
				if (null != gameObj && null != gameObj.getLong("gameId") && null != gameObj.getLong("gradeId")) {
					gameIds.add(gameObj.getLong("gameId"));
				}
			}
		}
		Long todayProfit = gameRecordDao.sumProfitTodayForGame(gameIds);// 今日盈利
		Long sumProfit = gameRecordDao.sumProfitForGame(gameIds, null) + todayProfit;// 总盈利
		Long weekProfit = gameRecordDao.sumProfitForGame(gameIds,
				DateUtils.format(DateUtils.addDateDays(new Date(), -6))) + todayProfit;// 本周盈利
		Long yesterDayProfit = gameRecordDao.sumProfitForGame(gameIds, DateUtils.format(DateUtils.getYesterday()));// 昨日盈利
		Map<String, Long> map = new HashMap<String, Long>();
		map.put("todayProfit", todayProfit);
		map.put("weekProfit", weekProfit);
		map.put("yesterDayProfit", yesterDayProfit);
		map.put("sumProfit", sumProfit);
		return R.ok().put("map", map);
	}

	/**
	 * 每个游戏总盈利
	 */
	@RequestMapping("/sumProfitForRecord")
	@RequiresPermissions("statistics:statistics:sumProfitForRecord")
	public R sumProfitForRecord(@RequestParam(required = false, name = "queryStartTime") String queryStartTime,
			@RequestParam(required = false, name = "queryEndTime") String queryEndTime) {
		// 获取到匹配场的所有游戏ID
		JSONObject returnObj = GameInfoInterface.gameSelectForRobot(SysConstant.ROOMID);
		List<Long> gameIds = new ArrayList<Long>();
		// 如果游戏大厅接口返回成功
		if (returnObj.getInteger("code") != null && returnObj.getInteger("code") == ResultDef.SUCCESS) {
			// 获取到游戏接口
			JSONArray gameJson = returnObj.getJSONArray("data");
			// 便利游戏列表
			for (int i = 0; i < gameJson.size(); i++) {
				// 每个游戏对象
				JSONObject gameObj = (JSONObject) gameJson.get(i);
				// 如果游戏对象不为空，游戏ID和场次Id不为空
				if (null != gameObj && null != gameObj.getLong("gameId") && null != gameObj.getLong("gradeId")) {
					gameIds.add(gameObj.getLong("gameId"));
				}
			}
		}
		Boolean timeStatus = true;
		if (!StringUtils.isEmpty(queryEndTime)&&DateUtils.stringToDate(queryEndTime, DateUtils.DATE_PATTERN)
				.before(DateUtils.stringToDate(DateUtils.todayString(), DateUtils.DATE_PATTERN))) {
			timeStatus = false;
		}
		List<GameRecordSumEntity> list = gameRecordDao.sumProfitForRecord(gameIds, queryStartTime, queryEndTime,
				timeStatus);
		if (!CollectionUtils.isEmpty(list)) {
			for (GameRecordSumEntity record : list) {
				// 如果游戏大厅接口返回成功
				if (returnObj.getInteger("code") != null && returnObj.getInteger("code") == ResultDef.SUCCESS) {
					// 获取到游戏接口
					JSONArray gameJson = returnObj.getJSONArray("data");
					// 便利游戏列表
					for (int i = 0; i < gameJson.size(); i++) {
						// 每个游戏对象
						JSONObject gameObj = (JSONObject) gameJson.get(i);
						// 如果游戏对象不为空，游戏ID和场次Id不为空
						if (null != gameObj && null != gameObj.getLong("gameId") && null != gameObj.getLong("gradeId")
								&& null != record.getGameId() && record.getGameId().equals(gameObj.getLong("gameId"))) {
							record.setGameName(gameObj.getString("gameName"));
						}
					}
				}
			}
		}

		return R.ok().put("list", list);
	}

	/**
	 * 每个场次总盈利
	 */
	@RequestMapping("/sumProfitForGradeRecord")
	@RequiresPermissions("statistics:statistics:sumProfitForGradeRecord")
	public R sumProfitForGradeRecord(Long gameId,
			@RequestParam(required = false, name = "queryStartTime") String queryStartTime,
			@RequestParam(required = false, name = "queryEndTime") String queryEndTime) {
		// 获取到匹配场的所有游戏ID
		Boolean timeStatus = true;
		if (DateUtils.stringToDate(queryEndTime, DateUtils.DATE_PATTERN)
				.before(DateUtils.stringToDate(DateUtils.todayString(), DateUtils.DATE_PATTERN))) {
			timeStatus = false;
		}
		List<GameRecordSumEntity> list = gameRecordDao.sumProfitForGradeRecord(gameId, queryStartTime, queryEndTime,
				timeStatus);
		if (!CollectionUtils.isEmpty(list)) {
			for (GameRecordSumEntity entity : list) {
				if (entity.getGradeId().equals(Constant.gameGradeType.ELEMENTARY.getValue())) {
					entity.setGradeName(Constant.gameGradeType.ELEMENTARY.getName());
				}
				if (entity.getGradeId().equals(Constant.gameGradeType.MIDDLE.getValue())) {
					entity.setGradeName(Constant.gameGradeType.MIDDLE.getName());
				}
				if (entity.getGradeId().equals(Constant.gameGradeType.HIGH.getValue())) {
					entity.setGradeName(Constant.gameGradeType.HIGH.getName());
				}
			}
		}
		return R.ok().put("list", list);
	}


}

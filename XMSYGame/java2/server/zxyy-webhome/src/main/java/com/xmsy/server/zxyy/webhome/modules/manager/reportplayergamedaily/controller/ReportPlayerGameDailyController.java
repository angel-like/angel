package com.xmsy.server.zxyy.webhome.modules.manager.reportplayergamedaily.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.toolkit.CollectionUtils;
import com.xmsy.common.define.page.PageParam;
import com.xmsy.server.zxyy.webhome.common.utils.Constant;
import com.xmsy.server.zxyy.webhome.common.utils.MathUtil;
import com.xmsy.server.zxyy.webhome.common.utils.PageUtils;
import com.xmsy.server.zxyy.webhome.common.utils.R;
import com.xmsy.server.zxyy.webhome.modules.manager.gamerecord.entity.GameRecordEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.gamerecord.service.GameRecordService;
import com.xmsy.server.zxyy.webhome.modules.manager.reportplayergamedaily.entity.ReportPlayerGameDailyEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.reportplayergamedaily.service.ReportPlayerGameDailyService;

/**
 * 每日玩家游戏投入产出报表
 *
 * @author xiaoling
 * @email xxxxx
 * @date 2019-05-11 09:26:16
 */
@RestController
@RequestMapping("reportplayergamedaily/reportplayergamedaily")
public class ReportPlayerGameDailyController {
	@Autowired
	private ReportPlayerGameDailyService reportPlayerGameDailyService;

	@Autowired
	private GameRecordService gameRecordService;

	/**
	 * 列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("reportplayergamedaily:reportplayergamedaily:list")
	public R list(ReportPlayerGameDailyEntity reportplayergamedaily, PageParam pageParam) {
		Page<ReportPlayerGameDailyEntity> result = new Page<ReportPlayerGameDailyEntity>(pageParam.getPage(),
				pageParam.getLimit());
		Wrapper<ReportPlayerGameDailyEntity> entityWrapper = new EntityWrapper<ReportPlayerGameDailyEntity>(
				reportplayergamedaily);
		entityWrapper.orderBy(pageParam.getSort(), pageParam.getDirection());
		reportplayergamedaily.selectPage(result, entityWrapper);
		Map<String, Object> map = new HashMap<>();
		List<ReportPlayerGameDailyEntity> dataList = result.getRecords();
		for (ReportPlayerGameDailyEntity data : dataList) {
			map = reportPlayerGameDailyService.querySum(data.getUserId(), data.getGradeId(), data.getGameId());
			data.setInvestmentTotalSum(MathUtil.getBigDecimal(map.get("investmentTotalSum")));
			data.setOutputTotalSum(MathUtil.getBigDecimal(map.get("outputTotalSum")));
			data.setWinTotalSum(MathUtil.getBigDecimal(map.get("winTotalSum")));
		}
		return R.ok().put("page", new PageUtils(result.getRecords(), result.getTotal(), result.getSize(),
				result.getCurrent(), result.getPages()));
	}

	/**
	 * 列表
	 */
	@RequestMapping("/sceneList")
	@RequiresPermissions("reportplayergamedaily:reportplayergamedaily:list")
	public R sceneList(PageParam pageParam, GameRecordEntity gameRecord) {
		Page<GameRecordEntity> result = gameRecordService.queryField(pageParam, gameRecord.getCountDay(),
				gameRecord.getUserId(), gameRecord.getGradeId(), gameRecord.getGameId(), gameRecord.getUserAccount());
		
		if(result.getTotal() <= 0) {
			return R.ok().put("page", new PageUtils(result.getRecords(), result.getTotal(), result.getSize(),
					result.getCurrent(), result.getPages()));
		}
		List<GameRecordEntity> gameRecordList = null;
		//定义一个StringBuffer去拼接（同一局号不同玩家的玩家id和玩家输赢金币）
		StringBuffer stitching = new StringBuffer();
		for (GameRecordEntity list : result.getRecords()) {
			gameRecordList = gameRecordService.queryByGameRoundNo(list.getGameRoundNo(),list.getRound());
			if (CollectionUtils.isEmpty(gameRecordList)) {
				continue;
			}
			for (GameRecordEntity gameRoundNo : gameRecordList) {
				if(gameRoundNo.getUserId().equals(list.getUserId())) {
					continue;
				}
				stitching.append(gameRoundNo.getUserId() + ":") ;
				stitching.append(gameRoundNo.getPrizeCoins() / Constant.CLIENT_COIN_RATE + "\t") ;
			}
			list.setUserIdPrizeCoins(stitching.toString());
			stitching = new StringBuffer();

		}
		return R.ok().put("page", new PageUtils(result.getRecords(), result.getTotal(), result.getSize(),
				result.getCurrent(), result.getPages()));

	}

	/**
	 * 信息
	 */
	@RequestMapping("/info/{id}")
	@RequiresPermissions("reportplayergamedaily:reportplayergamedaily:info")
	public R info(@PathVariable("id") Long id) {
		ReportPlayerGameDailyEntity reportPlayerGameDaily = reportPlayerGameDailyService.selectById(id);
		return R.ok().put("reportplayergamedaily", reportPlayerGameDaily);
	}

	/**
	 * 保存
	 */
	@RequestMapping("/save")
	@RequiresPermissions("reportplayergamedaily:reportplayergamedaily:save")
	public R save(@RequestBody ReportPlayerGameDailyEntity reportplayergamedaily) {
		reportPlayerGameDailyService.insert(reportplayergamedaily);
		return R.ok();
	}

	/**
	 * 修改
	 */
	@RequestMapping("/update")
	@RequiresPermissions("reportplayergamedaily:reportplayergamedaily:update")
	public R update(@RequestBody ReportPlayerGameDailyEntity reportplayergamedaily) {
		reportPlayerGameDailyService.updateById(reportplayergamedaily);
		return R.ok();
	}

	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	@RequiresPermissions("reportplayergamedaily:reportplayergamedaily:delete")
	public R delete(@RequestBody Long[] ids) {
		for (Long id : ids) {
			reportPlayerGameDailyService.deleteById(id);
		}
		return R.ok();
	}

}

package com.xmsy.server.zxyy.webhome.modules.manager.gamerecord.controller;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.plugins.Page;
import com.xmsy.common.define.page.PageParam;
import com.xmsy.server.zxyy.webhome.common.utils.PageUtils;
import com.xmsy.server.zxyy.webhome.common.utils.R;
import com.xmsy.server.zxyy.webhome.modules.manager.gamerecord.entity.GameRecordEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.gamerecord.service.GameRecordService;

/**
 * 
 *
 * @author aleng
 * @email xxxxx
 * @date 2019-01-22 11:12:08
 */
@RestController
@RequestMapping("gamerecord/gamerecord")
public class GameRecordController {
	@Autowired
	private GameRecordService gameRecordService;

	/**
	 * 连表查询列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("gamerecord:gamerecord:list")
	public R list(@RequestParam(required = false, value = "startTime") String startTime,
			@RequestParam(required = false, value = "endTime") String endTime, GameRecordEntity gameRecordEntity,
			PageParam pageParam) {
		Page<GameRecordEntity> result = gameRecordService.getGameRecords(gameRecordEntity, pageParam, startTime,
				endTime);
		return R.ok().put("page", new PageUtils(result.getRecords(), result.getTotal(), result.getSize(),
				result.getCurrent(), result.getPages()));
	}

	/**
	 * 信息
	 */
	@RequestMapping("/info/{id}")
	@RequiresPermissions("gamerecord:gamerecord:info")
	public R info(@PathVariable("id") Long id) {
		GameRecordEntity gameRecord = gameRecordService.selectById(id);
		return R.ok().put("gamerecord", gameRecord);
	}

	/**
	 * 保存
	 */
	@RequestMapping("/save")
	@RequiresPermissions("gamerecord:gamerecord:save")
	public R save(@RequestBody GameRecordEntity gameRecord) {
		gameRecordService.insert(gameRecord);
		return R.ok();
	}

	/**
	 * 修改
	 */
	@RequestMapping("/update")
	@RequiresPermissions("gamerecord:gamerecord:update")
	public R update(@RequestBody GameRecordEntity gameRecord) {
		gameRecordService.updateById(gameRecord);
		return R.ok();
	}

	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	@RequiresPermissions("gamerecord:gamerecord:delete")
	public R delete(@RequestBody Long[] ids) {
		for (Long id : ids) {
			gameRecordService.deleteById(id);
		}
		return R.ok();
	}

	
}

package com.xmsy.server.zxyy.manager.modules.manager.gamerecordunderseatreasure.controller;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.xmsy.common.define.page.PageParam;
import com.xmsy.server.zxyy.manager.common.utils.PageUtils;
import com.xmsy.server.zxyy.manager.common.utils.R;
import com.xmsy.server.zxyy.manager.modules.manager.gamerecordunderseatreasure.entity.GameRecordUnderseaTreasureEntity;
import com.xmsy.server.zxyy.manager.modules.manager.gamerecordunderseatreasure.service.GameRecordUnderseaTreasureService;

/**
 * 游戏记录-海底宝藏
 *
 * @author aleng
 * @email xxxxx
 * @date 2019-04-19 11:41:54
 */
@RestController
@RequestMapping("gamerecordunderseatreasure/gamerecordunderseatreasure")
public class GameRecordUnderseaTreasureController {
	@Autowired
	private GameRecordUnderseaTreasureService gameRecordUnderseaTreasureService;

	/**
	 * 列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("gamerecordunderseatreasure:gamerecordunderseatreasure:list")
	public R list(GameRecordUnderseaTreasureEntity param, PageParam pageParam) {
		Page<GameRecordUnderseaTreasureEntity> result = new Page<GameRecordUnderseaTreasureEntity>(pageParam.getPage(),
				pageParam.getLimit());
		Wrapper<GameRecordUnderseaTreasureEntity> entityWrapper = new EntityWrapper<GameRecordUnderseaTreasureEntity>(
				param);
		entityWrapper.ge(!StringUtils.isBlank(param.getStartTime()), "create_time", param.getStartTime());
		entityWrapper.le(!StringUtils.isBlank(param.getEndTime()), "create_time", param.getEndTime());
		if (StringUtils.isEmpty(pageParam.getSort())) {
			pageParam.setSort("id");
		}
		if (null != pageParam.getDirection()) {
			pageParam.setDirection(false);
		}
		entityWrapper.orderBy(pageParam.getSort(), pageParam.getDirection());
		param.selectPage(result, entityWrapper);
		return R.ok().put("page", new PageUtils(result.getRecords(), result.getTotal(), result.getSize(),
				result.getCurrent(), result.getPages()));
	}

	/**
	 * 信息
	 */
	@RequestMapping("/info/{id}")
	@RequiresPermissions("gamerecordunderseatreasure:gamerecordunderseatreasure:info")
	public R info(@PathVariable("id") Long id) {
		GameRecordUnderseaTreasureEntity gameRecordUnderseaTreasure = gameRecordUnderseaTreasureService.selectById(id);
		return R.ok().put("gamerecordunderseatreasure", gameRecordUnderseaTreasure);
	}

	/**
	 * 保存
	 */
	@RequestMapping("/save")
	@RequiresPermissions("gamerecordunderseatreasure:gamerecordunderseatreasure:save")
	public R save(@RequestBody GameRecordUnderseaTreasureEntity gamerecordunderseatreasure) {
		gameRecordUnderseaTreasureService.insert(gamerecordunderseatreasure);
		return R.ok();
	}

	/**
	 * 修改
	 */
	@RequestMapping("/update")
	@RequiresPermissions("gamerecordunderseatreasure:gamerecordunderseatreasure:update")
	public R update(@RequestBody GameRecordUnderseaTreasureEntity gamerecordunderseatreasure) {
		gameRecordUnderseaTreasureService.updateById(gamerecordunderseatreasure);
		return R.ok();
	}

	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	@RequiresPermissions("gamerecordunderseatreasure:gamerecordunderseatreasure:delete")
	public R delete(@RequestBody Long[] ids) {
		for (Long id : ids) {
			gameRecordUnderseaTreasureService.deleteById(id);
		}
		return R.ok();
	}

}

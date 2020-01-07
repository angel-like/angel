package com.xmsy.server.zxyy.game.modules.manager.gamegrade.controller;

import java.util.List;

import org.apache.shiro.util.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.xmsy.common.define.page.PageParam;
import com.xmsy.server.zxyy.game.common.utils.PageUtils;
import com.xmsy.server.zxyy.game.common.utils.R;
import com.xmsy.server.zxyy.game.modules.manager.game.entity.GameInfoEntity;
import com.xmsy.server.zxyy.game.modules.manager.game.service.GameInfoService;
import com.xmsy.server.zxyy.game.modules.manager.gamegrade.entity.GameGradeEntity;
import com.xmsy.server.zxyy.game.modules.manager.gamegrade.service.GameGradeService;

/**
 * 游戏场次等级
 *
 * @author aleng
 * @email xxxxx
 * @date 2019-02-19 13:38:51
 */
@RestController
@RequestMapping("gamegrade/gamegrade")
public class GameGradeController {
	@Autowired
	private GameGradeService gameGradeService;
	@Autowired
	private GameInfoService gameInfoService;

	/**
	 * 列表
	 */
	@RequestMapping("/list")
//	@RequiresPermissions("gamegrade:gamegrade:list")
	public R list(GameGradeEntity gamegrade, PageParam pageParam) {
		Page<GameGradeEntity> result = new Page<GameGradeEntity>(pageParam.getPage(), pageParam.getLimit());
		Wrapper<GameGradeEntity> entityWrapper = new EntityWrapper<GameGradeEntity>(gamegrade);
		entityWrapper.orderBy(pageParam.getSort(), pageParam.getDirection());
		gamegrade.selectPage(result, entityWrapper);
		return R.ok().put("page", new PageUtils(result.getRecords(), result.getTotal(), result.getSize(),
				result.getCurrent(), result.getPages()));
	}

	/**
	 * 获取场次列表
	 */
	@RequestMapping("GradeList")
//	@RequiresPermissions("gamegrade:gamegrade:list")
	public R getGradeList() {
		return R.ok().put("gradeList",
				gameGradeService.selectList(new EntityWrapper<GameGradeEntity>(new GameGradeEntity())));
	}

	/**
	 * 信息
	 */
	@RequestMapping("/info/{id}")
//	@RequiresPermissions("gamegrade:gamegrade:info")
	public R info(@PathVariable("id") Long id) {
		GameGradeEntity gameGrade = gameGradeService.selectById(id);
		return R.ok().put("gamegrade", gameGrade);
	}

	/**
	 * 保存
	 */
	@RequestMapping("/save")
//	@RequiresPermissions("gamegrade:gamegrade:save")
	public R save(@RequestBody GameGradeEntity gamegrade) {
		gameGradeService.insert(gamegrade);
		return R.ok();
	}

	/**
	 * 修改
	 */
	@RequestMapping("/update")
//	@RequiresPermissions("gamegrade:gamegrade:update")
	public R update(@RequestBody GameGradeEntity gamegrade) {
		gameGradeService.updateById(gamegrade);
		return R.ok();
	}

	/**
	 * 删除
	 * 
	 * @param gradeId
	 */
	@RequestMapping("/delete")
//	@RequiresPermissions("gamegrade:gamegrade:delete")
	public R delete(@RequestBody Long id) {
		List<GameInfoEntity> entity = gameInfoService.queryconfig(id);
		if (!CollectionUtils.isEmpty(entity)) {
			return R.error("有关联的游戏，不能删除该场次");
		}
		gameGradeService.deleteById(id);
		return R.ok();
	}

}

package com.xmsy.server.zxyy.webhome.modules.manager.poolgame.controller;

import java.util.List;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.xmsy.server.zxyy.webhome.common.annotation.SysLog;
import com.xmsy.server.zxyy.webhome.common.utils.PageUtils;
import com.xmsy.server.zxyy.webhome.common.utils.R;
import com.xmsy.server.zxyy.webhome.modules.gamemanage.GameSelectParam;
import com.xmsy.server.zxyy.webhome.modules.gamemanage.gameinfo.server.GameInfoInterface;
import com.xmsy.server.zxyy.webhome.modules.manager.poolgame.entity.PoolGameEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.poolgame.service.PoolGameService;

/**
 * 游戏奖池表
 *
 * @author lpp
 * @email xxxxx
 * @date 2019-01-21 09:46:00
 */
@RestController
@RequestMapping("poolgame/poolgame")
public class PoolGameController {
	@Autowired
	private PoolGameService poolGameService;

	// @Autowired
	// private GameInfoService gameInfoService;

	/**
	 * 列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("poolgame:poolgame:list")
	public R list(@RequestParam Map<String, Object> params) {
		PageUtils page = poolGameService.queryPage(params);
		return R.ok().put("page", page);
	}

	/**
	 * 信息
	 */
	@RequestMapping("/info/{id}")
	@RequiresPermissions("poolgame:poolgame:info")
	public R info(@PathVariable("id") Long id) {
		PoolGameEntity poolGame = poolGameService.selectById(id);
		return R.ok().put("poolgame", poolGame);
	}

	/**
	 * 游戏信息
	 */
	@RequestMapping("/gameList")
	@RequiresPermissions("poolgame:poolgame:info")
	public R gameList() {
		// 获取游戏大厅接口，获取到游戏列表
		List<GameSelectParam> gameList = GameInfoInterface.gameSelect();
		return R.ok().put("gameList", gameList);
	}

	/**
	 * 保存
	 */
	@SysLog("新增游戏奖池")
	@RequestMapping("/save")
	@RequiresPermissions("poolgame:poolgame:save")
	public R save(@RequestBody PoolGameEntity poolGame) {
		poolGameService.insert(poolGame);
		return R.ok();
	}

	/**
	 * 修改
	 */
	@SysLog("修改游戏奖池")
	@RequestMapping("/update")
	@RequiresPermissions("poolgame:poolgame:update")
	public R update(@RequestBody PoolGameEntity poolGame) {
		poolGameService.updateById(poolGame);
		return R.ok();
	}

	/**
	 * 删除
	 */
	@SysLog("删除游戏奖池")
	@RequestMapping("/delete")
	@RequiresPermissions("poolgame:poolgame:delete")
	public R delete(@RequestBody Long[] ids) {
		for (Long id : ids) {
			poolGameService.deleteById(id);
		}
		return R.ok();
	}

}

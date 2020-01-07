package com.xmsy.server.zxyy.manager.modules.manager.apppopulargames.controller;

import java.util.List;

import org.apache.shiro.authz.annotation.RequiresPermissions;
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
import com.xmsy.server.zxyy.manager.common.utils.PageUtils;
import com.xmsy.server.zxyy.manager.common.utils.R;
import com.xmsy.server.zxyy.manager.modules.gamemanage.GameSelectParam;
import com.xmsy.server.zxyy.manager.modules.gamemanage.gameinfo.server.GameInfoInterface;
import com.xmsy.server.zxyy.manager.modules.manager.apppopulargames.entity.AppPopularGamesEntity;
import com.xmsy.server.zxyy.manager.modules.manager.apppopulargames.service.AppPopularGamesService;

/**
 * APP热门游戏
 *
 * @author aleng
 * @email xxxxx
 * @date 2019-05-04 11:37:38
 */
@RestController
@RequestMapping("apppopulargames/apppopulargames")
public class AppPopularGamesController {
	@Autowired
	private AppPopularGamesService appPopularGamesService;

	/**
	 * 列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("apppopulargames:apppopulargames:list")
	public R list(AppPopularGamesEntity apppopulargames, PageParam pageParam) {
		// 获取游戏大厅接口，获取到游戏列表
		List<GameSelectParam> gameList = GameInfoInterface.gameSelect();
		Page<AppPopularGamesEntity> result = new Page<AppPopularGamesEntity>(pageParam.getPage(), pageParam.getLimit());
		Wrapper<AppPopularGamesEntity> entityWrapper = new EntityWrapper<AppPopularGamesEntity>(apppopulargames).orderBy("sort");
		entityWrapper.orderBy(pageParam.getSort(), pageParam.getDirection());
		apppopulargames.selectPage(result, entityWrapper);
		if (CollectionUtils.isEmpty(result.getRecords())) {
			return R.ok().put("page", new PageUtils(result.getRecords(), result.getTotal(), result.getSize(),
					result.getCurrent(), result.getPages()));
		}
		// 便利获取每条数据ID
		for (AppPopularGamesEntity entity : result.getRecords()) {
			// 如果游戏ID不为空
			if (entity.getGameId() == null || entity.getGameId() == 0) {
				// 如果游戏大厅接口返回成功
				continue;
			}
			// 便利游戏列表
			for (GameSelectParam gameParam : gameList) {
				// 如果游戏对象不为空，游戏ID不为空
				if (null == gameParam || null == gameParam.getId()) {
					continue;
				}
				// 如果游戏ID相同就将游戏名称取出，并跳出游戏列表gameJson的循环
				if (gameParam.getId().equals(entity.getGameId())) {
					entity.setGameName(gameParam.getName());
					break;
				}
			}
		}
		return R.ok().put("page", new PageUtils(result.getRecords(), result.getTotal(), result.getSize(),
				result.getCurrent(), result.getPages()));
	}

	/**
	 * 信息
	 */
	@RequestMapping("/info/{id}")
	@RequiresPermissions("apppopulargames:apppopulargames:info")
	public R info(@PathVariable("id") Long id) {
		AppPopularGamesEntity appPopularGames = appPopularGamesService.selectById(id);
		return R.ok().put("apppopulargames", appPopularGames);
	}

	/**
	 * 保存
	 */
	@RequestMapping("/save")
	@RequiresPermissions("apppopulargames:apppopulargames:save")
	public R save(@RequestBody AppPopularGamesEntity apppopulargames) {
		appPopularGamesService.insert(apppopulargames);
		return R.ok();
	}

	/**
	 * 修改
	 */
	@RequestMapping("/update")
	@RequiresPermissions("apppopulargames:apppopulargames:update")
	public R update(@RequestBody AppPopularGamesEntity apppopulargames) {
		appPopularGamesService.updateById(apppopulargames);
		return R.ok();
	}

	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	@RequiresPermissions("apppopulargames:apppopulargames:delete")
	public R delete(@RequestBody Long[] ids) {
		for (Long id : ids) {
			appPopularGamesService.deleteById(id);
		}
		return R.ok();
	}

}

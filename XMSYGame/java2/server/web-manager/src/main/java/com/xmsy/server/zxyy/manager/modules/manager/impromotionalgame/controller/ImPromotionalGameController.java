package com.xmsy.server.zxyy.manager.modules.manager.impromotionalgame.controller;

import java.util.List;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.util.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.toolkit.StringUtils;
import com.xmsy.common.define.page.PageParam;
import com.xmsy.server.zxyy.manager.common.utils.PageUtils;
import com.xmsy.server.zxyy.manager.common.utils.R;
import com.xmsy.server.zxyy.manager.modules.gamemanage.GameSelectParam;
import com.xmsy.server.zxyy.manager.modules.gamemanage.gameinfo.server.GameInfoInterface;
import com.xmsy.server.zxyy.manager.modules.manager.impromotionalgame.entity.ImPromotionalGameEntity;
import com.xmsy.server.zxyy.manager.modules.manager.impromotionalgame.service.ImPromotionalGameService;

/**
 * 33推荐游戏管理
 *
 * @author xiaoliu
 * @email xxxxx
 * @date 2019-06-21 15:37:14
 */
@RestController
@RequestMapping("impromotionalgame/impromotionalgame")
public class ImPromotionalGameController {
	@Autowired
	private ImPromotionalGameService imPromotionalGameService;

	/**
	 * 列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("impromotionalgame:impromotionalgame:list")
	public R list(ImPromotionalGameEntity impromotionalgame, PageParam pageParam) {
		// 获取游戏大厅接口，获取到游戏列表
		List<GameSelectParam> gameList = GameInfoInterface.gameSelect();
		Page<ImPromotionalGameEntity> result = new Page<ImPromotionalGameEntity>(pageParam.getPage(),
				pageParam.getLimit());
		Wrapper<ImPromotionalGameEntity> entityWrapper = new EntityWrapper<ImPromotionalGameEntity>(impromotionalgame);
		if (StringUtils.isEmpty(pageParam.getSort())) {
			entityWrapper.orderBy("id", false);
		} else {
			entityWrapper.orderBy(pageParam.getSort(), pageParam.getDirection());
		}
		impromotionalgame.selectPage(result, entityWrapper.orderBy("enable", false).orderBy("order_no"));
		if (CollectionUtils.isEmpty(result.getRecords())) {
			return R.ok().put("page", new PageUtils(result.getRecords(), result.getTotal(), result.getSize(),
					result.getCurrent(), result.getPages()));
		}
		// 便利获取每条数据ID
		for (ImPromotionalGameEntity entity : result.getRecords()) {
			// 如果游戏ID为空
			if (entity.getGameId() == null || entity.getGameId() == 0) {
				continue;
			}
			// 便利游戏列表
			for (GameSelectParam game : gameList) {
				// 如果游戏对象不为空，游戏ID不为空
				if (null == game || null == game.getId()) {
					continue;
				}
				// 如果游戏ID相同就将游戏名称取出，并跳出游戏列表gameJson的循环
				if (game.getId().equals(entity.getGameId())) {
					entity.setGameName(game.getName());
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
	@RequiresPermissions("impromotionalgame:impromotionalgame:info")
	public R info(@PathVariable("id") Long id) {
		ImPromotionalGameEntity imPromotionalGame = imPromotionalGameService.selectById(id);
		return R.ok().put("impromotionalgame", imPromotionalGame);
	}

	/**
	 * 保存
	 */
	@RequestMapping("/save")
	@RequiresPermissions("impromotionalgame:impromotionalgame:save")
	public R save(@RequestBody ImPromotionalGameEntity impromotionalgame) {
		imPromotionalGameService.insert(impromotionalgame);
		return R.ok();
	}

	/**
	 * 修改
	 */
	@RequestMapping("/update")
	@RequiresPermissions("impromotionalgame:impromotionalgame:update")
	public R update(@RequestBody ImPromotionalGameEntity impromotionalgame) {
		imPromotionalGameService.updateById(impromotionalgame);
		return R.ok();
	}

	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	@RequiresPermissions("impromotionalgame:impromotionalgame:delete")
	public R delete(@RequestBody Long[] ids) {
		for (Long id : ids) {
			imPromotionalGameService.deleteById(id);
		}
		return R.ok();
	}

	/**
	 * 修改状态
	 */
	@RequestMapping("/updateEnable")
	@RequiresPermissions("impromotionalgame:impromotionalgame:updateEnable")
	public R updateEnable(@RequestParam("id") Long id, @RequestParam("enable") Boolean enable) {
		ImPromotionalGameEntity entity = new ImPromotionalGameEntity();
		entity.setId(id);
		entity.setEnable(enable);
		imPromotionalGameService.updateById(entity);
		return R.ok();
	}

}

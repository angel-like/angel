package com.xmsy.server.zxyy.manager.modules.manager.imshuffling.controller;

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
import com.xmsy.server.zxyy.manager.modules.manager.imshuffling.entity.ImShufflingEntity;
import com.xmsy.server.zxyy.manager.modules.manager.imshuffling.service.ImShufflingService;

/**
 * 33轮播图管理
 *
 * @author xiaoliu
 * @email xxxxx
 * @date 2019-06-21 11:09:52
 */
@RestController
@RequestMapping("imshuffling/imshuffling")
public class ImShufflingController {
	@Autowired
	private ImShufflingService imShufflingService;

	/**
	 * 列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("imshuffling:imshuffling:list")
	public R list(ImShufflingEntity imshuffling, PageParam pageParam) {
		// 获取游戏大厅接口，获取到游戏列表
		List<GameSelectParam> gameList = GameInfoInterface.gameSelect();
		Page<ImShufflingEntity> result = new Page<ImShufflingEntity>(pageParam.getPage(), pageParam.getLimit());
		Wrapper<ImShufflingEntity> entityWrapper = new EntityWrapper<ImShufflingEntity>(imshuffling);
		if (StringUtils.isEmpty(pageParam.getSort())) {
			entityWrapper.orderBy("id", false);
		} else {
			entityWrapper.orderBy(pageParam.getSort(), pageParam.getDirection());
		}
		imshuffling.selectPage(result, entityWrapper.orderBy("enable", false).orderBy("order_no"));
		if (CollectionUtils.isEmpty(result.getRecords())) {
			return R.ok().put("page", new PageUtils(result.getRecords(), result.getTotal(), result.getSize(),
					result.getCurrent(), result.getPages()));
		}
		// 便利获取每条数据ID
		for (ImShufflingEntity entity : result.getRecords()) {
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
	@RequiresPermissions("imshuffling:imshuffling:info")
	public R info(@PathVariable("id") Long id) {
		ImShufflingEntity imShuffling = imShufflingService.selectById(id);
		return R.ok().put("imshuffling", imShuffling);
	}

	/**
	 * 保存
	 */
	@RequestMapping("/save")
	@RequiresPermissions("imshuffling:imshuffling:save")
	public R save(@RequestBody ImShufflingEntity imshuffling) {
		imShufflingService.insert(imshuffling);
		return R.ok();
	}

	/**
	 * 修改
	 */
	@RequestMapping("/update")
	@RequiresPermissions("imshuffling:imshuffling:update")
	public R update(@RequestBody ImShufflingEntity imshuffling) {
		imShufflingService.updateById(imshuffling);
		return R.ok();
	}

	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	@RequiresPermissions("imshuffling:imshuffling:delete")
	public R delete(@RequestBody Long[] ids) {
		for (Long id : ids) {
			imShufflingService.deleteById(id);
		}
		return R.ok();
	}

	/**
	 * 修改状态
	 */
	@RequestMapping("/updateEnable")
	@RequiresPermissions("imshuffling:imshuffling:updateEnable")
	public R updateEnable(@RequestParam("id") Long id, @RequestParam("enable") Boolean enable) {
		ImShufflingEntity entity = new ImShufflingEntity();
		entity.setId(id);
		entity.setEnable(enable);
		imShufflingService.updateById(entity);
		return R.ok();
	}

}

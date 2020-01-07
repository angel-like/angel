package com.xmsy.server.zxyy.manager.modules.manager.webhomemenu.controller;

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
import com.xmsy.server.zxyy.manager.cache.LocalContentCache;
import com.xmsy.server.zxyy.manager.common.annotation.SysLog;
import com.xmsy.server.zxyy.manager.common.utils.PageUtils;
import com.xmsy.server.zxyy.manager.common.utils.R;
import com.xmsy.server.zxyy.manager.constant.SysConstant;
import com.xmsy.server.zxyy.manager.modules.gamemanage.GameSelectParam;
import com.xmsy.server.zxyy.manager.modules.gamemanage.gameinfo.server.GameInfoInterface;
import com.xmsy.server.zxyy.manager.modules.manager.webhomemenu.entity.WebhomeMenuEntity;
import com.xmsy.server.zxyy.manager.modules.manager.webhomemenu.service.WebhomeMenuService;

/**
 * 首页菜单管理
 *
 * @author aleng
 * @email xxxxx
 * @date 2019-01-02 14:39:28
 */
@RestController
@RequestMapping("webhomemenu/webhomemenu")
public class WebhomeMenuController {
	@Autowired
	private WebhomeMenuService webhomeMenuService;
	@Autowired
	private LocalContentCache localContentCache;

	/**
	 * 列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("webhomemenu:webhomemenu:list")
	public R list(WebhomeMenuEntity webhomeMenuEntity, PageParam pageParam) {
		String name = webhomeMenuEntity.getName();
		webhomeMenuEntity.setName(null);
		Page<WebhomeMenuEntity> result = new Page<WebhomeMenuEntity>(pageParam.getPage(), pageParam.getLimit());
		Wrapper<WebhomeMenuEntity> entityWrapper = new EntityWrapper<WebhomeMenuEntity>(webhomeMenuEntity).like("name",
				name);
		entityWrapper.orderBy(pageParam.getSort(), pageParam.getDirection());
		webhomeMenuEntity.selectPage(result, entityWrapper.orderBy("order_num"));
		return R.ok().put("page", new PageUtils(result.getRecords(), result.getTotal(), result.getSize(),
				result.getCurrent(), result.getPages()));
	}

	/**
	 * 子菜单列表
	 */
	@RequestMapping("/childrenList")
	@RequiresPermissions("webhomemenu:webhomemenu:childrenList")
	public R childrenList(WebhomeMenuEntity webhomeMenuEntity, PageParam pageParam) {
		// 获取游戏大厅接口，获取到游戏列表
		List<GameSelectParam> gameList = GameInfoInterface.gameSelect();

		String name = webhomeMenuEntity.getName();
		webhomeMenuEntity.setName(null);
		Page<WebhomeMenuEntity> result = new Page<WebhomeMenuEntity>(pageParam.getPage(), pageParam.getLimit());
		Wrapper<WebhomeMenuEntity> entityWrapper = new EntityWrapper<WebhomeMenuEntity>(webhomeMenuEntity).like("name",
				name);
		entityWrapper.orderBy(pageParam.getSort(), pageParam.getDirection());
		webhomeMenuEntity.selectPage(result, entityWrapper.orderBy("order_num"));
		if (CollectionUtils.isEmpty(result.getRecords())) {
			return R.ok().put("page", new PageUtils(result.getRecords(), result.getTotal(), result.getSize(),
					result.getCurrent(), result.getPages()));
		}
		for (WebhomeMenuEntity menu : result.getRecords()) {
			if (menu.getParentId() == 0) {
				menu.setParentName("系统管理");
			} else {
				WebhomeMenuEntity menuEntity = webhomeMenuService.selectById(menu.getParentId());
				if (menuEntity != null) {
					menu.setParentName(menuEntity.getName());
				}
			}
			if (0 == menu.getGameId() || null == menu.getGameId()) {
				menu.setGameName("未关联游戏");
				continue;
			}
			// 便利游戏列表
			for (GameSelectParam gameParam : gameList) {
				// 如果游戏对象不为空，游戏ID不为空
				if (null == gameParam || null == gameParam.getId()) {
					continue;
				}
				// 如果游戏ID相同就将游戏名称取出，并跳出游戏列表的循环
				if (gameParam.getId().equals(menu.getGameId())) {
					menu.setGameName(gameParam.getName());
					break;
				}
			}

		}
		return R.ok().put("page", new PageUtils(result.getRecords(), result.getTotal(), result.getSize(),
				result.getCurrent(), result.getPages()));
	}

	/**
	 * 获取游戏类菜单列表
	 */
	@RequestMapping("/gameMenuList")
	public R gameMenuList() {
		List<WebhomeMenuEntity> list = webhomeMenuService.gameMenuList();
		return R.ok().put("data", list);
	}

	/**
	 * 信息
	 */
	@RequestMapping("/info/{id}")
	@RequiresPermissions("webhomemenu:webhomemenu:info")
	public R info(@PathVariable("id") Long id) {
		WebhomeMenuEntity webhomeMenu = webhomeMenuService.selectById(id);
		return R.ok().put("webhomeMenu", webhomeMenu);
	}

	/**
	 * 保存
	 */
	@SysLog("首页菜单新增")
	@RequestMapping("/save")
	@RequiresPermissions("webhomemenu:webhomemenu:save")
	public R save(@RequestBody WebhomeMenuEntity webhomeMenu) {
		localContentCache.remove(SysConstant.MENU);
		webhomeMenuService.insert(webhomeMenu);
		return R.ok();
	}

	/**
	 * 修改
	 */
	@SysLog("首页菜单修改")
	@RequestMapping("/update")
	@RequiresPermissions("webhomemenu:webhomemenu:update")
	public R update(@RequestBody WebhomeMenuEntity webhomeMenu) {
		webhomeMenuService.updateById(webhomeMenu);
		localContentCache.remove(SysConstant.MENU);
		return R.ok();
	}

	/**
	 * 删除
	 */
	@SysLog("首页菜单删除")
	@RequestMapping("/delete")
	@RequiresPermissions("webhomemenu:webhomemenu:delete")
	public R delete(@RequestBody Long[] ids) {
		localContentCache.remove(SysConstant.MENU);
		for (Long id : ids) {
			webhomeMenuService.deleteById(id);
		}
		return R.ok();
	}

	/**
	 * 获取1级菜单列表(下拉)
	 */
	@RequestMapping("/getMenuListForSelect")
	@RequiresPermissions("webhomemenu:webhomemenu:getMenuListForSelect")
	public R getMenuListForSelect() {
		WebhomeMenuEntity entity = new WebhomeMenuEntity();
		entity.setType(SysConstant.ONELEVEL);// 获取一级菜单
		List<WebhomeMenuEntity> list = webhomeMenuService.selectList(new EntityWrapper<WebhomeMenuEntity>(entity));
		return R.ok().put("list", list);
	}

}

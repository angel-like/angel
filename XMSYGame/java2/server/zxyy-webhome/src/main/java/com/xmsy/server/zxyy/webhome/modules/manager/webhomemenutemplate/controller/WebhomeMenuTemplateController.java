package com.xmsy.server.zxyy.webhome.modules.manager.webhomemenutemplate.controller;

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
import com.xmsy.server.zxyy.webhome.common.annotation.SysLog;
import com.xmsy.server.zxyy.webhome.common.utils.PageUtils;
import com.xmsy.server.zxyy.webhome.common.utils.R;
import com.xmsy.server.zxyy.webhome.modules.gamemanage.GameSelectParam;
import com.xmsy.server.zxyy.webhome.modules.gamemanage.gameinfo.server.GameInfoInterface;
import com.xmsy.server.zxyy.webhome.modules.manager.webhomemenutemplate.entity.WebhomeMenuTemplateEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.webhomemenutemplate.service.WebhomeMenuTemplateService;

/**
 * 菜单模板表
 *
 * @author aleng
 * @email xxxxx
 * @date 2019-01-02 19:32:58
 */
@RestController
@RequestMapping("webhomemenutemplate/webhomemenutemplate")
public class WebhomeMenuTemplateController {
	@Autowired
	private WebhomeMenuTemplateService webhomeMenuTemplateService;

	/**
	 * 列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("webhomemenutemplate:webhomemenutemplate:list")
	public R list(WebhomeMenuTemplateEntity webhomeMenuTemplate, PageParam pageParam) {

		// 获取游戏大厅接口，获取到游戏列表
		List<GameSelectParam> gameList = GameInfoInterface.gameSelect();

		Page<WebhomeMenuTemplateEntity> result = new Page<WebhomeMenuTemplateEntity>(pageParam.getPage(),
				pageParam.getLimit());
		Wrapper<WebhomeMenuTemplateEntity> entityWrapper = new EntityWrapper<WebhomeMenuTemplateEntity>(
				webhomeMenuTemplate);
		entityWrapper.orderBy(pageParam.getSort(), pageParam.getDirection());
		webhomeMenuTemplate.selectPage(result, entityWrapper);
		if (CollectionUtils.isEmpty(result.getRecords())) {
			return R.ok().put("page", new PageUtils(result.getRecords(), result.getTotal(), result.getSize(),
					result.getCurrent(), result.getPages()));
		}
		// 便利获取每条数据ID
		for (WebhomeMenuTemplateEntity MenuTemplate : result.getRecords()) {
			// 如果游戏ID不为空
			if (MenuTemplate.getGameId() == null || MenuTemplate.getGameId() == 0) {
				continue;
			}
			// 便利游戏列表
			for (GameSelectParam game : gameList) {
				// 如果游戏对象不为空，游戏ID不为空
				if (null == game || null == game.getId()) {
					continue;
				}
				// 如果游戏ID相同就将游戏名称取出，并跳出游戏列表gameJson的循环
				if (game.getId().equals(MenuTemplate.getGameId())) {
					MenuTemplate.setGameName(game.getName());
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
	@RequiresPermissions("webhomemenutemplate:webhomemenutemplate:info")
	public R info(@PathVariable("id") Long id) {
		WebhomeMenuTemplateEntity webhomeMenuTemplate = webhomeMenuTemplateService.selectById(id);
		return R.ok().put("webhomeMenuTemplate", webhomeMenuTemplate);
	}

	/**
	 * 保存
	 */
	@SysLog("菜单模板新增")
	@RequestMapping("/save")
	@RequiresPermissions("webhomemenutemplate:webhomemenutemplate:save")
	public R save(@RequestBody WebhomeMenuTemplateEntity webhomeMenuTemplate) {
		webhomeMenuTemplateService.insert(webhomeMenuTemplate);
		return R.ok();
	}

	/**
	 * 修改
	 */
	@SysLog("菜单模板修改")
	@RequestMapping("/update")
	@RequiresPermissions("webhomemenutemplate:webhomemenutemplate:update")
	public R update(@RequestBody WebhomeMenuTemplateEntity webhomeMenuTemplate) {
		webhomeMenuTemplateService.updateById(webhomeMenuTemplate);
		return R.ok();
	}

	/**
	 * 删除
	 */
	@SysLog("菜单模板删除")
	@RequestMapping("/delete")
	@RequiresPermissions("webhomemenutemplate:webhomemenutemplate:delete")
	public R delete(@RequestBody Long[] ids) {
		for (Long id : ids) {
			webhomeMenuTemplateService.deleteById(id);
		}
		return R.ok();
	}

}

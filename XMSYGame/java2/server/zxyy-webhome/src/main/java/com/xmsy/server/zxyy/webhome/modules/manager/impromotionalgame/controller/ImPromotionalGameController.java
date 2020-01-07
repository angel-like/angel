package com.xmsy.server.zxyy.webhome.modules.manager.impromotionalgame.controller;

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
import com.xmsy.server.zxyy.webhome.common.utils.PageUtils;
import com.xmsy.server.zxyy.webhome.common.utils.R;
import com.xmsy.server.zxyy.webhome.modules.manager.impromotionalgame.entity.ImPromotionalGameEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.impromotionalgame.service.ImPromotionalGameService;

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
		Page<ImPromotionalGameEntity> result = new Page<ImPromotionalGameEntity>(pageParam.getPage(),
				pageParam.getLimit());
		Wrapper<ImPromotionalGameEntity> entityWrapper = new EntityWrapper<ImPromotionalGameEntity>(impromotionalgame);
		entityWrapper.orderBy(pageParam.getSort(), pageParam.getDirection());
		impromotionalgame.selectPage(result, entityWrapper);
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

}

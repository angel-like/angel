package com.xmsy.server.zxyy.manager.modules.manager.imgameintroduction.controller;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.xmsy.common.define.page.PageParam;
import com.xmsy.server.zxyy.manager.common.utils.PageUtils;
import com.xmsy.server.zxyy.manager.common.utils.R;
import com.xmsy.server.zxyy.manager.modules.manager.imgameintroduction.entity.ImGameIntroductionEntity;
import com.xmsy.server.zxyy.manager.modules.manager.imgameintroduction.service.ImGameIntroductionService;

/**
 * 33推荐游戏介绍
 *
 * @author xiaoliu
 * @email xxxxx
 * @date 2019-06-22 19:50:09
 */
@RestController
@RequestMapping("imgameintroduction/imgameintroduction")
public class ImGameIntroductionController {
	@Autowired
	private ImGameIntroductionService imGameIntroductionService;

	/**
	 * 列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("imgameintroduction:imgameintroduction:list")
	public R list(ImGameIntroductionEntity imgameintroduction, PageParam pageParam) {
		Page<ImGameIntroductionEntity> result = new Page<ImGameIntroductionEntity>(pageParam.getPage(),
				pageParam.getLimit());
		Wrapper<ImGameIntroductionEntity> entityWrapper = new EntityWrapper<ImGameIntroductionEntity>(
				imgameintroduction);
		entityWrapper.orderBy(pageParam.getSort(), pageParam.getDirection());
		imgameintroduction.selectPage(result, entityWrapper.orderBy("enable", false).orderBy("order_no"));
		return R.ok().put("page", new PageUtils(result.getRecords(), result.getTotal(), result.getSize(),
				result.getCurrent(), result.getPages()));
	}

	/**
	 * 信息
	 */
	@RequestMapping("/info/{id}")
	@RequiresPermissions("imgameintroduction:imgameintroduction:info")
	public R info(@PathVariable("id") Long id) {
		ImGameIntroductionEntity imGameIntroduction = imGameIntroductionService.selectById(id);
		return R.ok().put("imgameintroduction", imGameIntroduction);
	}

	/**
	 * 保存
	 */
	@RequestMapping("/save")
	@RequiresPermissions("imgameintroduction:imgameintroduction:save")
	public R save(@RequestBody ImGameIntroductionEntity imgameintroduction) {
		imGameIntroductionService.insert(imgameintroduction);
		return R.ok();
	}

	/**
	 * 修改
	 */
	@RequestMapping("/update")
	@RequiresPermissions("imgameintroduction:imgameintroduction:update")
	public R update(@RequestBody ImGameIntroductionEntity imgameintroduction) {
		imGameIntroductionService.updateById(imgameintroduction);
		return R.ok();
	}

	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	@RequiresPermissions("imgameintroduction:imgameintroduction:delete")
	public R delete(@RequestBody Long[] ids) {
		for (Long id : ids) {
			imGameIntroductionService.deleteById(id);
		}
		return R.ok();
	}

	/**
	 * 修改状态
	 */
	@RequestMapping("/updateEnable")
	@RequiresPermissions("imgameintroduction:imgameintroduction:updateEnable")
	public R updateEnable(@RequestParam("id") Long id, @RequestParam("enable") Boolean enable) {
		ImGameIntroductionEntity entity = new ImGameIntroductionEntity();
		entity.setId(id);
		entity.setEnable(enable);
		imGameIntroductionService.updateById(entity);
		return R.ok();
	}

}

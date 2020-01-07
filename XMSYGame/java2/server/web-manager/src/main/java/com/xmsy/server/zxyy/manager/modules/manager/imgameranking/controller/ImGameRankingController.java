package com.xmsy.server.zxyy.manager.modules.manager.imgameranking.controller;

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
import com.xmsy.server.zxyy.manager.modules.manager.imgameranking.entity.ImGameRankingEntity;
import com.xmsy.server.zxyy.manager.modules.manager.imgameranking.service.ImGameRankingService;

/**
 * 33推荐热门游戏排行
 *
 * @author xiaoliu
 * @email xxxxx
 * @date 2019-06-24 11:10:56
 */
@RestController
@RequestMapping("imgameranking/imgameranking")
public class ImGameRankingController {
	@Autowired
	private ImGameRankingService imGameRankingService;

	/**
	 * 列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("imgameranking:imgameranking:list")
	public R list(ImGameRankingEntity imgameranking, PageParam pageParam) {
		Page<ImGameRankingEntity> result = new Page<ImGameRankingEntity>(pageParam.getPage(), pageParam.getLimit());
		Wrapper<ImGameRankingEntity> entityWrapper = new EntityWrapper<ImGameRankingEntity>(imgameranking);
		entityWrapper.orderBy(pageParam.getSort(), pageParam.getDirection());
		imgameranking.selectPage(result, entityWrapper.orderBy("enable", false).orderBy("order_no"));
		return R.ok().put("page", new PageUtils(result.getRecords(), result.getTotal(), result.getSize(),
				result.getCurrent(), result.getPages()));
	}

	/**
	 * 信息
	 */
	@RequestMapping("/info/{id}")
	@RequiresPermissions("imgameranking:imgameranking:info")
	public R info(@PathVariable("id") Long id) {
		ImGameRankingEntity imGameRanking = imGameRankingService.selectById(id);
		return R.ok().put("imgameranking", imGameRanking);
	}

	/**
	 * 保存
	 */
	@RequestMapping("/save")
	@RequiresPermissions("imgameranking:imgameranking:save")
	public R save(@RequestBody ImGameRankingEntity imgameranking) {
		imGameRankingService.insert(imgameranking);
		return R.ok();
	}

	/**
	 * 修改
	 */
	@RequestMapping("/update")
	@RequiresPermissions("imgameranking:imgameranking:update")
	public R update(@RequestBody ImGameRankingEntity imgameranking) {
		imGameRankingService.updateById(imgameranking);
		return R.ok();
	}

	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	@RequiresPermissions("imgameranking:imgameranking:delete")
	public R delete(@RequestBody Long[] ids) {
		for (Long id : ids) {
			imGameRankingService.deleteById(id);
		}
		return R.ok();
	}

	/**
	 * 修改状态
	 */
	@RequestMapping("/updateEnable")
	@RequiresPermissions("imgameranking:imgameranking:updateEnable")
	public R updateEnable(@RequestParam("id") Long id, @RequestParam("enable") Boolean enable) {
		ImGameRankingEntity entity = new ImGameRankingEntity();
		entity.setId(id);
		entity.setEnable(enable);
		imGameRankingService.updateById(entity);
		return R.ok();
	}

}

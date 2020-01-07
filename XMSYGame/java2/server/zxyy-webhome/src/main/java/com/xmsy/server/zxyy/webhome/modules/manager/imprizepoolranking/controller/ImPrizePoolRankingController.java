package com.xmsy.server.zxyy.webhome.modules.manager.imprizepoolranking.controller;

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
import com.xmsy.server.zxyy.webhome.modules.manager.imprizepoolranking.entity.ImPrizePoolRankingEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.imprizepoolranking.service.ImPrizePoolRankingService;

/**
 * 33推荐热门游戏排行
 *
 * @author xiaoliu
 * @email xxxxx
 * @date 2019-06-24 15:25:30
 */
@RestController
@RequestMapping("imprizepoolranking/imprizepoolranking")
public class ImPrizePoolRankingController {
	@Autowired
	private ImPrizePoolRankingService imPrizePoolRankingService;

	/**
	 * 列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("imprizepoolranking:imprizepoolranking:list")
	public R list(ImPrizePoolRankingEntity imprizepoolranking, PageParam pageParam) {
		Page<ImPrizePoolRankingEntity> result = new Page<ImPrizePoolRankingEntity>(pageParam.getPage(),
				pageParam.getLimit());
		Wrapper<ImPrizePoolRankingEntity> entityWrapper = new EntityWrapper<ImPrizePoolRankingEntity>(
				imprizepoolranking);
		entityWrapper.orderBy(pageParam.getSort(), pageParam.getDirection());
		imprizepoolranking.selectPage(result, entityWrapper);
		return R.ok().put("page", new PageUtils(result.getRecords(), result.getTotal(), result.getSize(),
				result.getCurrent(), result.getPages()));
	}

	/**
	 * 信息
	 */
	@RequestMapping("/info/{id}")
	@RequiresPermissions("imprizepoolranking:imprizepoolranking:info")
	public R info(@PathVariable("id") Long id) {
		ImPrizePoolRankingEntity imPrizePoolRanking = imPrizePoolRankingService.selectById(id);
		return R.ok().put("imprizepoolranking", imPrizePoolRanking);
	}

	/**
	 * 保存
	 */
	@RequestMapping("/save")
	@RequiresPermissions("imprizepoolranking:imprizepoolranking:save")
	public R save(@RequestBody ImPrizePoolRankingEntity imprizepoolranking) {
		imPrizePoolRankingService.insert(imprizepoolranking);
		return R.ok();
	}

	/**
	 * 修改
	 */
	@RequestMapping("/update")
	@RequiresPermissions("imprizepoolranking:imprizepoolranking:update")
	public R update(@RequestBody ImPrizePoolRankingEntity imprizepoolranking) {
		imPrizePoolRankingService.updateById(imprizepoolranking);
		return R.ok();
	}

	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	@RequiresPermissions("imprizepoolranking:imprizepoolranking:delete")
	public R delete(@RequestBody Long[] ids) {
		for (Long id : ids) {
			imPrizePoolRankingService.deleteById(id);
		}
		return R.ok();
	}

}

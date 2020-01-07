package com.xmsy.server.zxyy.webhome.modules.manager.imshuffling.controller;

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
import com.xmsy.server.zxyy.webhome.modules.manager.imshuffling.entity.ImShufflingEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.imshuffling.service.ImShufflingService;

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
		Page<ImShufflingEntity> result = new Page<ImShufflingEntity>(pageParam.getPage(), pageParam.getLimit());
		Wrapper<ImShufflingEntity> entityWrapper = new EntityWrapper<ImShufflingEntity>(imshuffling);
		entityWrapper.orderBy(pageParam.getSort(), pageParam.getDirection());
		imshuffling.selectPage(result, entityWrapper);
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

}

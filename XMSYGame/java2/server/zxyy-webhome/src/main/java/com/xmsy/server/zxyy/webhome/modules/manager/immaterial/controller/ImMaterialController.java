package com.xmsy.server.zxyy.webhome.modules.manager.immaterial.controller;

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
import com.xmsy.server.zxyy.webhome.modules.manager.immaterial.entity.ImMaterialEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.immaterial.service.ImMaterialService;

/**
 * 33娱乐
 *
 * @author aleng
 * @email xxxxx
 * @date 2019-06-17 15:27:58
 */
@RestController
@RequestMapping("immaterial/immaterial")
public class ImMaterialController {
	@Autowired
	private ImMaterialService imMaterialService;

	/**
	 * 列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("immaterial:immaterial:list")
	public R list(ImMaterialEntity immaterial, PageParam pageParam) {
		Page<ImMaterialEntity> result = new Page<ImMaterialEntity>(pageParam.getPage(), pageParam.getLimit());
		Wrapper<ImMaterialEntity> entityWrapper = new EntityWrapper<ImMaterialEntity>(immaterial);
		entityWrapper.orderBy(pageParam.getSort(), pageParam.getDirection());
		immaterial.selectPage(result, entityWrapper);
		return R.ok().put("page", new PageUtils(result.getRecords(), result.getTotal(), result.getSize(),
				result.getCurrent(), result.getPages()));
	}

	/**
	 * 信息
	 */
	@RequestMapping("/info/{id}")
	@RequiresPermissions("immaterial:immaterial:info")
	public R info(@PathVariable("id") Long id) {
		ImMaterialEntity imMaterial = imMaterialService.selectById(id);
		return R.ok().put("immaterial", imMaterial);
	}

	/**
	 * 保存
	 */
	@RequestMapping("/save")
	@RequiresPermissions("immaterial:immaterial:save")
	public R save(@RequestBody ImMaterialEntity immaterial) {
		imMaterialService.insert(immaterial);
		return R.ok();
	}

	/**
	 * 修改
	 */
	@RequestMapping("/update")
	@RequiresPermissions("immaterial:immaterial:update")
	public R update(@RequestBody ImMaterialEntity immaterial) {
		imMaterialService.updateById(immaterial);
		return R.ok();
	}

	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	@RequiresPermissions("immaterial:immaterial:delete")
	public R delete(@RequestBody Long[] ids) {
		for (Long id : ids) {
			imMaterialService.deleteById(id);
		}
		return R.ok();
	}

}

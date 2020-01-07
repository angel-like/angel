package com.xmsy.server.zxyy.webhome.modules.manager.sysregisternecessary.controller;

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
import com.xmsy.server.zxyy.webhome.modules.manager.sysregisternecessary.entity.SysRegisterNecessaryEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.sysregisternecessary.service.SysRegisterNecessaryService;

/**
 * 注册必填控制表
 *
 * @author aleng
 * @email xxxxx
 * @date 2018-12-28 10:51:51
 */
@RestController
@RequestMapping("sysregisternecessary/sysregisternecessary")
public class SysRegisterNecessaryController {
	@Autowired
	private SysRegisterNecessaryService sysRegisterNecessaryService;

	/**
	 * 列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("sysregisternecessary:sysregisternecessary:list")
	public R list(SysRegisterNecessaryEntity sysRegisterNecessaryEntity, PageParam pageParam) {
		String name=sysRegisterNecessaryEntity.getName();
		sysRegisterNecessaryEntity.setName(null);
		Page<SysRegisterNecessaryEntity> result = new Page<SysRegisterNecessaryEntity>(pageParam.getPage(),
				pageParam.getLimit());
		Wrapper<SysRegisterNecessaryEntity> entityWrapper = new EntityWrapper<SysRegisterNecessaryEntity>(
				sysRegisterNecessaryEntity);
		entityWrapper.orderBy(pageParam.getSort(), pageParam.getDirection());
		sysRegisterNecessaryEntity.selectPage(result, entityWrapper.like("name", name));
		return R.ok().put("page", new PageUtils(result.getRecords(), result.getTotal(), result.getSize(),
				result.getCurrent(), result.getPages()));

	}

	/**
	 * 信息
	 */
	@RequestMapping("/info/{id}")
	@RequiresPermissions("sysregisternecessary:sysregisternecessary:info")
	public R info(@PathVariable("id") Long id) {
		SysRegisterNecessaryEntity registerNecessary = sysRegisterNecessaryService.selectById(id);
		return R.ok().put("registerNecessary", registerNecessary);
	}

	/**
	 * 保存
	 */
	@RequestMapping("/save")
	@RequiresPermissions("sysregisternecessary:sysregisternecessary:save")
	public R save(@RequestBody SysRegisterNecessaryEntity registerNecessary) {
		sysRegisterNecessaryService.insert(registerNecessary);
		return R.ok();
	}

	/**
	 * 修改
	 */
	@RequestMapping("/update")
	@RequiresPermissions("sysregisternecessary:sysregisternecessary:update")
	public R update(@RequestBody SysRegisterNecessaryEntity registerNecessary) {
		sysRegisterNecessaryService.updateById(registerNecessary);
		return R.ok();
	}

	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	@RequiresPermissions("sysregisternecessary:sysregisternecessary:delete")
	public R delete(@RequestBody Long[] ids) {
		for (Long id : ids) {
			sysRegisterNecessaryService.deleteById(id);
		}
		return R.ok();
	}

}

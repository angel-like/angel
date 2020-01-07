package com.xmsy.server.zxyy.manager.modules.manager.domainmanagement.controller;

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
import com.xmsy.server.zxyy.manager.common.annotation.SysLog;
import com.xmsy.server.zxyy.manager.common.utils.PageUtils;
import com.xmsy.server.zxyy.manager.common.utils.R;
import com.xmsy.server.zxyy.manager.modules.manager.domainmanagement.entity.DomainManagementEntity;
import com.xmsy.server.zxyy.manager.modules.manager.domainmanagement.service.DomainManagementService;

/**
 * 域名管理
 *
 * @author aleng
 * @email xxxxx
 * @date 2019-01-06 14:09:43
 */
@RestController
@RequestMapping("domainmanagement/domainmanagement")
public class DomainManagementController {
	@Autowired
	private DomainManagementService domainManagementService;

	/**
	 * 列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("domainmanagement:domainmanagement:list")
	public R list(DomainManagementEntity domainManagement, PageParam pageParam) {
		Page<DomainManagementEntity> result = new Page<DomainManagementEntity>(pageParam.getPage(),
				pageParam.getLimit());
		Wrapper<DomainManagementEntity> entityWrapper = new EntityWrapper<DomainManagementEntity>(domainManagement);
		entityWrapper.orderBy(pageParam.getSort(), pageParam.getDirection());
		domainManagement.selectPage(result, entityWrapper);
		return R.ok().put("page", new PageUtils(result.getRecords(), result.getTotal(), result.getSize(),
				result.getCurrent(), result.getPages()));
	}

	/**
	 * 信息
	 */
	@RequestMapping("/info/{id}")
	@RequiresPermissions("domainmanagement:domainmanagement:info")
	public R info(@PathVariable("id") Long id) {
		DomainManagementEntity domainManagement = domainManagementService.selectById(id);
		return R.ok().put("domainmanagement", domainManagement);
	}

	/**
	 * 保存
	 */
	@SysLog("域名新增")
	@RequestMapping("/save")
	@RequiresPermissions("domainmanagement:domainmanagement:save")
	public R save(@RequestBody DomainManagementEntity domainManagement) {
		domainManagementService.insert(domainManagement);
		return R.ok();
	}

	/**
	 * 修改
	 */
	@SysLog("域名修改")
	@RequestMapping("/update")
	@RequiresPermissions("domainmanagement:domainmanagement:update")
	public R update(@RequestBody DomainManagementEntity domainManagement) {
		domainManagementService.updateById(domainManagement);
		return R.ok();
	}

	/**
	 * 删除
	 */
	@SysLog("域名删除")
	@RequestMapping("/delete")
	@RequiresPermissions("domainmanagement:domainmanagement:delete")
	public R delete(@RequestBody Long[] ids) {
		for (Long id : ids) {
			domainManagementService.deleteById(id);
		}
		return R.ok();
	}

}

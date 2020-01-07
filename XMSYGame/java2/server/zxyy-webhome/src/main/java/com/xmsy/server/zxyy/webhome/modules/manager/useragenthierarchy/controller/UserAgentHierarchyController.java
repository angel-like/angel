package com.xmsy.server.zxyy.webhome.modules.manager.useragenthierarchy.controller;

import java.util.List;

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
import com.xmsy.server.zxyy.webhome.common.annotation.SysLog;
import com.xmsy.server.zxyy.webhome.common.utils.PageUtils;
import com.xmsy.server.zxyy.webhome.common.utils.R;
import com.xmsy.server.zxyy.webhome.modules.manager.useragenthierarchy.entity.UserAgentHierarchyEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.useragenthierarchy.service.UserAgentHierarchyService;

/**
 * 用户代理层级设置
 *
 * @author aleng
 * @email xxxxx
 * @date 2019-01-24 19:54:08
 */
@RestController
@RequestMapping("useragenthierarchy/useragenthierarchy")
public class UserAgentHierarchyController {
	@Autowired
	private UserAgentHierarchyService userAgentHierarchyService;

	/**
	 * 列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("useragenthierarchy:useragenthierarchy:list")
	public R list(UserAgentHierarchyEntity userAgentHierarchy, PageParam pageParam) {
		Page<UserAgentHierarchyEntity> result = new Page<UserAgentHierarchyEntity>(pageParam.getPage(),
				pageParam.getLimit());
		Wrapper<UserAgentHierarchyEntity> entityWrapper = new EntityWrapper<UserAgentHierarchyEntity>(
				userAgentHierarchy);
		entityWrapper.orderBy(pageParam.getSort(), pageParam.getDirection());
		userAgentHierarchy.selectPage(result, entityWrapper);
		return R.ok().put("page", new PageUtils(result.getRecords(), result.getTotal(), result.getSize(),
				result.getCurrent(), result.getPages()));

	}

	/**
	 * 信息
	 */
	@RequestMapping("/info/{id}")
	@RequiresPermissions("useragenthierarchy:useragenthierarchy:info")
	public R info(@PathVariable("id") Long id) {
		UserAgentHierarchyEntity userAgentHierarchy = userAgentHierarchyService.selectById(id);
		return R.ok().put("useragenthierarchy", userAgentHierarchy);
	}

	/**
	 * 保存
	 */
	@SysLog("新增代理层级")
	@RequestMapping("/save")
	@RequiresPermissions("useragenthierarchy:useragenthierarchy:save")
	public R save(@RequestBody UserAgentHierarchyEntity userAgentHierarchy) {
		userAgentHierarchyService.insert(userAgentHierarchy);
		return R.ok();
	}

	/**
	 * 修改
	 */
	@SysLog("修改代理层级")
	@RequestMapping("/update")
	@RequiresPermissions("useragenthierarchy:useragenthierarchy:update")
	public R update(@RequestBody UserAgentHierarchyEntity userAgentHierarchy) {
		userAgentHierarchyService.updateById(userAgentHierarchy);
		return R.ok();
	}

	/**
	 * 设置默认
	 */
	@SysLog("修改默认代理层级")
	@RequestMapping("/setdefault/{id}")
	@RequiresPermissions("useragenthierarchy:useragenthierarchy:update")
	public R setDefault(@PathVariable("id") Long id) {
		userAgentHierarchyService.setDefault(id);
		return R.ok();
	}

	/**
	 * 删除
	 */
	@SysLog("删除代理层级")
	@RequestMapping("/delete")
	@RequiresPermissions("useragenthierarchy:useragenthierarchy:delete")
	public R delete(@RequestBody Long[] ids) {
		for (Long id : ids) {
			userAgentHierarchyService.deleteById(id);
		}
		return R.ok();
	}

	/**
	 * 下拉
	 */
	@RequestMapping("/select")
	public R select() {
		List<UserAgentHierarchyEntity> list = userAgentHierarchyService
				.selectList(new EntityWrapper<UserAgentHierarchyEntity>(null));
		return R.ok().put("list", list);
	}

}

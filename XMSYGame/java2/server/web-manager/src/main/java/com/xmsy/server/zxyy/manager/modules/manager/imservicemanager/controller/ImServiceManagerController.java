package com.xmsy.server.zxyy.manager.modules.manager.imservicemanager.controller;

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
import com.baomidou.mybatisplus.toolkit.StringUtils;
import com.xmsy.common.define.page.PageParam;
import com.xmsy.server.zxyy.manager.common.utils.PageUtils;
import com.xmsy.server.zxyy.manager.common.utils.R;
import com.xmsy.server.zxyy.manager.modules.manager.imservicemanager.entity.ImServiceManagerEntity;
import com.xmsy.server.zxyy.manager.modules.manager.imservicemanager.service.ImServiceManagerService;

/**
 * 33娱乐服务器管理
 *
 * @author aleng
 * @email xxxxx
 * @date 2019-06-20 16:35:33
 */
@RestController
@RequestMapping("imservicemanager/imservicemanager")
public class ImServiceManagerController {
	@Autowired
	private ImServiceManagerService imServiceManagerService;

	/**
	 * 列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("imservicemanager:imservicemanager:list")
	public R list(ImServiceManagerEntity imservicemanager, PageParam pageParam) {
		Page<ImServiceManagerEntity> result = new Page<ImServiceManagerEntity>(pageParam.getPage(),
				pageParam.getLimit());
		Wrapper<ImServiceManagerEntity> entityWrapper = new EntityWrapper<ImServiceManagerEntity>(imservicemanager);
		if (StringUtils.isEmpty(pageParam.getSort())) {
			entityWrapper.orderBy("id", false);
		} else {
			entityWrapper.orderBy(pageParam.getSort(), pageParam.getDirection());
		}
		imservicemanager.selectPage(result, entityWrapper.orderBy("enable", false).orderBy("order_no"));
		return R.ok().put("page", new PageUtils(result.getRecords(), result.getTotal(), result.getSize(),
				result.getCurrent(), result.getPages()));
	}

	/**
	 * 信息
	 */
	@RequestMapping("/info/{id}")
	@RequiresPermissions("imservicemanager:imservicemanager:info")
	public R info(@PathVariable("id") Long id) {
		ImServiceManagerEntity imServiceManager = imServiceManagerService.selectById(id);
		return R.ok().put("imservicemanager", imServiceManager);
	}

	/**
	 * 保存
	 */
	@RequestMapping("/save")
	@RequiresPermissions("imservicemanager:imservicemanager:save")
	public R save(@RequestBody ImServiceManagerEntity imservicemanager) {
		imServiceManagerService.insert(imservicemanager);
		return R.ok();
	}

	/**
	 * 修改
	 */
	@RequestMapping("/update")
	@RequiresPermissions("imservicemanager:imservicemanager:update")
	public R update(@RequestBody ImServiceManagerEntity imservicemanager) {
		imServiceManagerService.updateById(imservicemanager);
		return R.ok();
	}

	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	@RequiresPermissions("imservicemanager:imservicemanager:delete")
	public R delete(@RequestBody Long[] ids) {
		for (Long id : ids) {
			imServiceManagerService.deleteById(id);
		}
		return R.ok();
	}

	/**
	 * 修改状态
	 */
	@RequestMapping("/updateEnable")
	@RequiresPermissions("imservicemanager:imservicemanager:updateEnable")
	public R updateEnable(@RequestParam("id") Long id, @RequestParam("enable") Boolean enable) {
		ImServiceManagerEntity entity = new ImServiceManagerEntity();
		entity.setId(id);
		entity.setEnable(enable);
		imServiceManagerService.updateById(entity);
		return R.ok();
	}

}

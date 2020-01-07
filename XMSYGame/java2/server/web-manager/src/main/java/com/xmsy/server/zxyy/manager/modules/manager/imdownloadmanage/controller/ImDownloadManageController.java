package com.xmsy.server.zxyy.manager.modules.manager.imdownloadmanage.controller;

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
import com.xmsy.server.zxyy.manager.modules.manager.imdownloadmanage.entity.ImDownloadManageEntity;
import com.xmsy.server.zxyy.manager.modules.manager.imdownloadmanage.service.ImDownloadManageService;

/**
 * 33推荐下载管理
 *
 * @author xiaoliu
 * @email xxxxx
 * @date 2019-06-21 19:44:44
 */
@RestController
@RequestMapping("imdownloadmanage/imdownloadmanage")
public class ImDownloadManageController {
	@Autowired
	private ImDownloadManageService imDownloadManageService;

	/**
	 * 列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("imdownloadmanage:imdownloadmanage:list")
	public R list(ImDownloadManageEntity imdownloadmanage, PageParam pageParam) {
		Page<ImDownloadManageEntity> result = new Page<ImDownloadManageEntity>(pageParam.getPage(),
				pageParam.getLimit());
		Wrapper<ImDownloadManageEntity> entityWrapper = new EntityWrapper<ImDownloadManageEntity>(imdownloadmanage);
		entityWrapper.orderBy(pageParam.getSort(), pageParam.getDirection());
		imdownloadmanage.selectPage(result, entityWrapper.orderBy("enable", false).orderBy("order_no"));
		return R.ok().put("page", new PageUtils(result.getRecords(), result.getTotal(), result.getSize(),
				result.getCurrent(), result.getPages()));
	}

	/**
	 * 信息
	 */
	@RequestMapping("/info/{id}")
	@RequiresPermissions("imdownloadmanage:imdownloadmanage:info")
	public R info(@PathVariable("id") Long id) {
		ImDownloadManageEntity imDownloadManage = imDownloadManageService.selectById(id);
		return R.ok().put("imdownloadmanage", imDownloadManage);
	}

	/**
	 * 保存
	 */
	@RequestMapping("/save")
	@RequiresPermissions("imdownloadmanage:imdownloadmanage:save")
	public R save(@RequestBody ImDownloadManageEntity imdownloadmanage) {
		imDownloadManageService.insert(imdownloadmanage);
		return R.ok();
	}

	/**
	 * 修改
	 */
	@RequestMapping("/update")
	@RequiresPermissions("imdownloadmanage:imdownloadmanage:update")
	public R update(@RequestBody ImDownloadManageEntity imdownloadmanage) {
		imDownloadManageService.updateById(imdownloadmanage);
		return R.ok();
	}

	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	@RequiresPermissions("imdownloadmanage:imdownloadmanage:delete")
	public R delete(@RequestBody Long[] ids) {
		for (Long id : ids) {
			imDownloadManageService.deleteById(id);
		}
		return R.ok();
	}

	/**
	 * 修改状态
	 */
	@RequestMapping("/updateEnable")
	@RequiresPermissions("imdownloadmanage:imdownloadmanage:updateEnable")
	public R updateEnable(@RequestParam("id") Long id, @RequestParam("enable") Boolean enable) {
		ImDownloadManageEntity entity = new ImDownloadManageEntity();
		entity.setId(id);
		entity.setEnable(enable);
		imDownloadManageService.updateById(entity);
		return R.ok();
	}

}

package com.xmsy.server.zxyy.manager.modules.manager.noticemanagement.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.xmsy.server.zxyy.manager.common.annotation.SysLog;
import com.xmsy.server.zxyy.manager.common.utils.PageUtils;
import com.xmsy.server.zxyy.manager.common.utils.R;
import com.xmsy.server.zxyy.manager.modules.manager.noticemanagement.entity.NoticeManagementEntity;
import com.xmsy.server.zxyy.manager.modules.manager.noticemanagement.service.NoticeManagementService;
import com.xmsy.server.zxyy.manager.modules.manager.userhierarchy.entity.UserHierarchyEntity;
import com.xmsy.server.zxyy.manager.modules.manager.userhierarchy.service.UserHierarchyService;

/**
 * 消息管理
 *
 * @author aleng
 * @email xxxxx
 * @date 2019-01-03 17:26:35
 */
@RestController
@RequestMapping("noticemanagement/noticemanagement")
public class NoticeManagementController {
	@Autowired
	private NoticeManagementService noticeManagementService;
	@Autowired
	private UserHierarchyService userHierarchyService;

	/**
	 * 列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("noticemanagement:noticemanagement:list")
	public R list(@RequestParam Map<String, Object> params) {
		PageUtils page = noticeManagementService.queryPage(params);
		return R.ok().put("page", page);
	}

	/**
	 * 信息
	 */
	@RequestMapping("/info/{id}")
	@RequiresPermissions("noticemanagement:noticemanagement:info")
	public R info(@PathVariable("id") Long id) {
		NoticeManagementEntity noticeManagement = noticeManagementService.selectById(id);
		return R.ok().put("noticeManagement", noticeManagement);
	}

	/**
	 * 信息
	 */
	@RequestMapping("/getHierarchy")
	@RequiresPermissions("noticemanagement:noticemanagement:info")
	public R getHierarchy() {
		List<UserHierarchyEntity> dataList = userHierarchyService
				.selectList(new EntityWrapper<UserHierarchyEntity>(new UserHierarchyEntity()));
		List<Long> ids = new ArrayList<>();
		if (dataList != null && !dataList.isEmpty()) {
			for (UserHierarchyEntity data : dataList) {
				ids.add(data.getId());
			}
		}
		return R.ok().put("hierarchyList", dataList).put("ids", ids);
	}

	/**
	 * 保存
	 */
	@SysLog("消息管理保存")
	@RequestMapping("/save")
	@RequiresPermissions("noticemanagement:noticemanagement:save")
	public R save(@RequestBody NoticeManagementEntity noticeManagement) {
		noticeManagementService.insert(noticeManagement);
		return R.ok();
	}

	/**
	 * 修改
	 */
	@RequestMapping("/update")
	@RequiresPermissions("noticemanagement:noticemanagement:update")
	public R update(@RequestBody NoticeManagementEntity noticeManagement) {
		noticeManagementService.updateAllColumnById(noticeManagement);
		return R.ok();
	}

	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	@RequiresPermissions("noticemanagement:noticemanagement:delete")
	public R delete(@RequestBody Long[] ids) {
		for (Long id : ids) {
			noticeManagementService.deleteById(id);
		}
		return R.ok();
	}

}

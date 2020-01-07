package com.xmsy.server.zxyy.webhome.modules.manager.userbetrecord.controller;

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
import com.xmsy.server.zxyy.webhome.modules.manager.userbetrecord.entity.UserBetRecordEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.userbetrecord.service.UserBetRecordService;

/**
 * 用户每日有效下注
 *
 * @author aleng
 * @email xxxxx
 * @date 2019-06-08 10:51:52
 */
@RestController
@RequestMapping("userbetrecord/userbetrecord")
public class UserBetRecordController {
	@Autowired
	private UserBetRecordService userBetRecordService;

	/**
	 * 列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("userbetrecord:userbetrecord:list")
	public R list(UserBetRecordEntity userbetrecord, PageParam pageParam) {
		Page<UserBetRecordEntity> result = new Page<UserBetRecordEntity>(pageParam.getPage(), pageParam.getLimit());
		Wrapper<UserBetRecordEntity> entityWrapper = new EntityWrapper<UserBetRecordEntity>(userbetrecord);
		entityWrapper.orderBy(pageParam.getSort(), pageParam.getDirection());
		userbetrecord.selectPage(result, entityWrapper);
		return R.ok().put("page", new PageUtils(result.getRecords(), result.getTotal(), result.getSize(),
				result.getCurrent(), result.getPages()));
	}

	/**
	 * 信息
	 */
	@RequestMapping("/info/{id}")
	@RequiresPermissions("userbetrecord:userbetrecord:info")
	public R info(@PathVariable("id") Long id) {
		UserBetRecordEntity userBetRecord = userBetRecordService.selectById(id);
		return R.ok().put("userbetrecord", userBetRecord);
	}

	/**
	 * 保存
	 */
	@RequestMapping("/save")
	@RequiresPermissions("userbetrecord:userbetrecord:save")
	public R save(@RequestBody UserBetRecordEntity userbetrecord) {
		userBetRecordService.insert(userbetrecord);
		return R.ok();
	}

	/**
	 * 修改
	 */
	@RequestMapping("/update")
	@RequiresPermissions("userbetrecord:userbetrecord:update")
	public R update(@RequestBody UserBetRecordEntity userbetrecord) {
		userBetRecordService.updateById(userbetrecord);
		return R.ok();
	}

	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	@RequiresPermissions("userbetrecord:userbetrecord:delete")
	public R delete(@RequestBody Long[] ids) {
		for (Long id : ids) {
			userBetRecordService.deleteById(id);
		}
		return R.ok();
	}

}

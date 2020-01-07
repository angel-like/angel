package com.xmsy.server.zxyy.webhome.modules.manager.userrebatecommissionrecord.controller;

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
import com.xmsy.server.zxyy.webhome.modules.manager.userrebatecommissionrecord.entity.UserRebateCommissionRecordEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.userrebatecommissionrecord.service.UserRebateCommissionRecordService;

/**
 * 用户佣金返利记录
 *
 * @author aleng
 * @email xxxxx
 * @date 2019-06-12 14:38:44
 */
@RestController
@RequestMapping("userrebatecommissionrecord/userrebatecommissionrecord")
public class UserRebateCommissionRecordController {
	@Autowired
	private UserRebateCommissionRecordService userRebateCommissionRecordService;

	/**
	 * 列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("userrebatecommissionrecord:userrebatecommissionrecord:list")
	public R list(UserRebateCommissionRecordEntity userrebatecommissionrecord, PageParam pageParam) {
		Page<UserRebateCommissionRecordEntity> result = new Page<UserRebateCommissionRecordEntity>(pageParam.getPage(),
				pageParam.getLimit());
		Wrapper<UserRebateCommissionRecordEntity> entityWrapper = new EntityWrapper<UserRebateCommissionRecordEntity>(
				userrebatecommissionrecord);
		entityWrapper.orderBy(pageParam.getSort(), pageParam.getDirection());
		userrebatecommissionrecord.selectPage(result, entityWrapper);
		return R.ok().put("page", new PageUtils(result.getRecords(), result.getTotal(), result.getSize(),
				result.getCurrent(), result.getPages()));
	}

	/**
	 * 信息
	 */
	@RequestMapping("/info/{id}")
	@RequiresPermissions("userrebatecommissionrecord:userrebatecommissionrecord:info")
	public R info(@PathVariable("id") Long id) {
		UserRebateCommissionRecordEntity userRebateCommissionRecord = userRebateCommissionRecordService.selectById(id);
		return R.ok().put("userrebatecommissionrecord", userRebateCommissionRecord);
	}

	/**
	 * 保存
	 */
	@RequestMapping("/save")
	@RequiresPermissions("userrebatecommissionrecord:userrebatecommissionrecord:save")
	public R save(@RequestBody UserRebateCommissionRecordEntity userrebatecommissionrecord) {
		userRebateCommissionRecordService.insert(userrebatecommissionrecord);
		return R.ok();
	}

	/**
	 * 修改
	 */
	@RequestMapping("/update")
	@RequiresPermissions("userrebatecommissionrecord:userrebatecommissionrecord:update")
	public R update(@RequestBody UserRebateCommissionRecordEntity userrebatecommissionrecord) {
		userRebateCommissionRecordService.updateById(userrebatecommissionrecord);
		return R.ok();
	}

	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	@RequiresPermissions("userrebatecommissionrecord:userrebatecommissionrecord:delete")
	public R delete(@RequestBody Long[] ids) {
		for (Long id : ids) {
			userRebateCommissionRecordService.deleteById(id);
		}
		return R.ok();
	}

}

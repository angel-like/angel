package com.xmsy.server.zxyy.webhome.modules.manager.usertransactionrecord.controller;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.druid.util.StringUtils;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.xmsy.common.define.page.PageParam;
import com.xmsy.server.zxyy.webhome.common.utils.PageUtils;
import com.xmsy.server.zxyy.webhome.common.utils.R;
import com.xmsy.server.zxyy.webhome.modules.manager.usertransactionrecord.entity.UserTransactionRecordEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.usertransactionrecord.service.UserTransactionRecordService;

/**
 * 资金交易明细
 *
 * @author xiaoliu
 * @email xxxxx
 * @date 2019-03-31 09:45:25
 */
@RestController
@RequestMapping("usertransactionrecord/usertransactionrecord")
public class UserTransactionRecordController {
	@Autowired
	private UserTransactionRecordService userTransactionRecordService;

	/**
	 * 列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("usertransactionrecord:usertransactionrecord:list")
	public R list(UserTransactionRecordEntity usertransactionrecord, PageParam pageParam) {
		if(StringUtils.isEmpty(usertransactionrecord.getUserAccount())) {
			return R.error(100,"请输入会员账号");
		}
		Page<UserTransactionRecordEntity> result = new Page<UserTransactionRecordEntity>(pageParam.getPage(),
				pageParam.getLimit());
		Wrapper<UserTransactionRecordEntity> entityWrapper = new EntityWrapper<UserTransactionRecordEntity>(
				usertransactionrecord).ge(!StringUtils.isEmpty(usertransactionrecord.getStartTime()), "create_time", // 存款时间查询
						usertransactionrecord.getStartTime())
						.le(!StringUtils.isEmpty(usertransactionrecord.getEndTime()), "create_time",
								usertransactionrecord.getEndTime());
		entityWrapper.orderBy(pageParam.getSort(), pageParam.getDirection());
		usertransactionrecord.selectPage(result, entityWrapper);
		return R.ok().put("page", new PageUtils(result.getRecords(), result.getTotal(), result.getSize(),
				result.getCurrent(), result.getPages()));
	}

	/**
	 * 信息
	 */
	@RequestMapping("/info/{id}")
	@RequiresPermissions("usertransactionrecord:usertransactionrecord:info")
	public R info(@PathVariable("id") Long id) {
		UserTransactionRecordEntity userTransactionRecord = userTransactionRecordService.selectById(id);
		return R.ok().put("usertransactionrecord", userTransactionRecord);
	}

	/**
	 * 保存
	 */
	@RequestMapping("/save")
	@RequiresPermissions("usertransactionrecord:usertransactionrecord:save")
	public R save(@RequestBody UserTransactionRecordEntity usertransactionrecord) {
		userTransactionRecordService.insert(usertransactionrecord);
		return R.ok();
	}

	/**
	 * 修改
	 */
	@RequestMapping("/update")
	@RequiresPermissions("usertransactionrecord:usertransactionrecord:update")
	public R update(@RequestBody UserTransactionRecordEntity usertransactionrecord) {
		userTransactionRecordService.updateById(usertransactionrecord);
		return R.ok();
	}

	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	@RequiresPermissions("usertransactionrecord:usertransactionrecord:delete")
	public R delete(@RequestBody Long[] ids) {
		for (Long id : ids) {
			userTransactionRecordService.deleteById(id);
		}
		return R.ok();
	}

}

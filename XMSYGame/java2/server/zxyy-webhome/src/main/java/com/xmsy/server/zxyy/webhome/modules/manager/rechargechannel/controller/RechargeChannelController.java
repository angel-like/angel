package com.xmsy.server.zxyy.webhome.modules.manager.rechargechannel.controller;

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
import com.xmsy.server.zxyy.webhome.common.utils.PageUtils;
import com.xmsy.server.zxyy.webhome.common.utils.R;
import com.xmsy.server.zxyy.webhome.constant.SysConstant;
import com.xmsy.server.zxyy.webhome.modules.manager.rechargechannel.entity.RechargeChannelEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.rechargechannel.service.RechargeChannelService;

/**
 * 支付渠道
 *
 * @author aleng
 * @email xxxxx
 * @date 2019-03-27 10:16:53
 */
@RestController
@RequestMapping("rechargechannel/rechargechannel")
public class RechargeChannelController {
	@Autowired
	private RechargeChannelService rechargeChannelService;

	/**
	 * 列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("rechargechannel:rechargechannel:list")
	public R list(RechargeChannelEntity rechargechannel, PageParam pageParam) {
		Page<RechargeChannelEntity> result = new Page<RechargeChannelEntity>(pageParam.getPage(), pageParam.getLimit());
		Wrapper<RechargeChannelEntity> entityWrapper = new EntityWrapper<RechargeChannelEntity>(rechargechannel);
		entityWrapper.orderBy(pageParam.getSort(), pageParam.getDirection());
		rechargechannel.selectPage(result, entityWrapper);
		return R.ok().put("page", new PageUtils(result.getRecords(), result.getTotal(), result.getSize(),
				result.getCurrent(), result.getPages()));
	}

	/**
	 * 列表
	 */
	@RequestMapping("/Rechargechannels")
	@RequiresPermissions("rechargechannel:rechargechannel:list")
	public R rechargechannels(RechargeChannelEntity rechargechannel, PageParam pageParam) {
		Wrapper<RechargeChannelEntity> entityWrapper = new EntityWrapper<RechargeChannelEntity>(rechargechannel);
		return R.ok().put("list", rechargechannel.selectList(entityWrapper));
	}

	/**
	 * 信息
	 */
	@RequestMapping("/info/{id}")
	@RequiresPermissions("rechargechannel:rechargechannel:info")
	public R info(@PathVariable("id") Long id) {
		RechargeChannelEntity rechargeChannel = rechargeChannelService.selectById(id);
		return R.ok().put("rechargechannel", rechargeChannel);
	}

	/**
	 * 保存
	 */
	@RequestMapping("/save")
	@RequiresPermissions("rechargechannel:rechargechannel:save")
	public R save(@RequestBody RechargeChannelEntity rechargechannel) {
		rechargeChannelService.insert(rechargechannel);
		return R.ok();
	}

	/**
	 * 修改
	 */
	@RequestMapping("/update")
	@RequiresPermissions("rechargechannel:rechargechannel:update")
	public R update(@RequestBody RechargeChannelEntity rechargechannel) {
		rechargeChannelService.updateById(rechargechannel);
		return R.ok();
	}

	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	@RequiresPermissions("rechargechannel:rechargechannel:delete")
	public R delete(@RequestBody Long[] ids) {
		for (Long id : ids) {
			rechargeChannelService.deleteById(id);
		}
		return R.ok();
	}

	/**
	 * 下拉
	 */
	@RequestMapping("/select")
	public R select() {
		List<RechargeChannelEntity> list = rechargeChannelService.selectList(new EntityWrapper<RechargeChannelEntity>(
				new RechargeChannelEntity().setEnable(SysConstant.ENABLE_TRUE)));
		return R.ok().put("list", list);
	}

}

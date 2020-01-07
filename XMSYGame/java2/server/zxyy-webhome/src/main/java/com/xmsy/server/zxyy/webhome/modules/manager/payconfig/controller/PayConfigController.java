package com.xmsy.server.zxyy.webhome.modules.manager.payconfig.controller;

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
import com.xmsy.server.zxyy.webhome.modules.manager.payconfig.entity.PayConfigEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.payconfig.service.PayConfigService;

/**
 * 支付渠道配置
 *
 * @author aleng
 * @email xxxxx
 * @date 2019-03-25 10:09:06
 */
@RestController
@RequestMapping("payconfig/payconfig")
public class PayConfigController {
	@Autowired
	private PayConfigService payConfigService;

	/**
	 * 列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("payconfig:payconfig:list")
	public R list(PayConfigEntity payconfig, PageParam pageParam) {
		Page<PayConfigEntity> result = new Page<PayConfigEntity>(pageParam.getPage(), pageParam.getLimit());
		Wrapper<PayConfigEntity> entityWrapper = new EntityWrapper<PayConfigEntity>(payconfig);
		entityWrapper.orderBy(pageParam.getSort(), pageParam.getDirection());
		payconfig.selectPage(result, entityWrapper);
		return R.ok().put("page", new PageUtils(result.getRecords(), result.getTotal(), result.getSize(),
				result.getCurrent(), result.getPages()));
	}

	/**
	 * 列表
	 */
	@RequestMapping("/Payconfigs")
	@RequiresPermissions("payconfig:payconfig:list")
	public R payconfigs(PayConfigEntity payconfig, PageParam pageParam) {
		Wrapper<PayConfigEntity> entityWrapper = new EntityWrapper<PayConfigEntity>(payconfig);
		return R.ok().put("list", payconfig.selectList(entityWrapper));
	}

	/**
	 * 信息
	 */
	@RequestMapping("/info/{id}")
	@RequiresPermissions("payconfig:payconfig:info")
	public R info(@PathVariable("id") Long id) {
		PayConfigEntity payConfig = payConfigService.selectById(id);
		return R.ok().put("payconfig", payConfig);
	}

	/**
	 * 保存
	 */
	@RequestMapping("/save")
	@RequiresPermissions("payconfig:payconfig:save")
	public R save(@RequestBody PayConfigEntity payconfig) {
		payConfigService.insert(payconfig);
		return R.ok();
	}

	/**
	 * 修改
	 */
	@RequestMapping("/update")
	@RequiresPermissions("payconfig:payconfig:update")
	public R update(@RequestBody PayConfigEntity payconfig) {
		payConfigService.updateById(payconfig);
		return R.ok();
	}

	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	@RequiresPermissions("payconfig:payconfig:delete")
	public R delete(@RequestBody Long[] ids) {
		for (Long id : ids) {
			payConfigService.deleteById(id);
		}
		return R.ok();
	}

	/**
	 * 启用
	 */
	@RequestMapping("/enable/{id}")
	@RequiresPermissions("payconfig:payconfig:enable")
	public R enable(@PathVariable("id") Long id) {
		PayConfigEntity payConfig = new PayConfigEntity();
		payConfig.setId(id);
		payConfig.setEnable(SysConstant.ENABLE_TRUE);
		payConfigService.updateById(payConfig);
		return R.ok();
	}

	/**
	 * 禁用
	 */
	@RequestMapping("/disable/{id}")
	@RequiresPermissions("payconfig:payconfig:disable")
	public R disable(@PathVariable("id") Long id) {
		PayConfigEntity payConfig = new PayConfigEntity();
		payConfig.setId(id);
		payConfig.setEnable(SysConstant.ENABLE_FALSE);
		payConfigService.updateById(payConfig);
		return R.ok();
	}

	/**
	 * 设为首推
	 */
	@RequestMapping("/firstPush/{id}")
	@RequiresPermissions("payconfig:payconfig:firstPush")
	public R firstPush(@PathVariable("id") Long id) {
		payConfigService.firstPush(id);
		return R.ok();
	}

	/**
	 * 下拉
	 */
	@RequestMapping("/select")
	public R select() {
		List<PayConfigEntity> list = payConfigService.selectList(
				new EntityWrapper<PayConfigEntity>(new PayConfigEntity()));
		return R.ok().put("list", list);
	}

}

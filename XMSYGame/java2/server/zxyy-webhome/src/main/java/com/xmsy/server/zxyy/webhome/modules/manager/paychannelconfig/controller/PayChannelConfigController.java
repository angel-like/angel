package com.xmsy.server.zxyy.webhome.modules.manager.paychannelconfig.controller;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
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
import com.xmsy.server.zxyy.webhome.common.exception.RRException;
import com.xmsy.server.zxyy.webhome.common.utils.ErrorCode;
import com.xmsy.server.zxyy.webhome.common.utils.PageUtils;
import com.xmsy.server.zxyy.webhome.common.utils.R;
import com.xmsy.server.zxyy.webhome.modules.manager.paychannelconfig.entity.PayChannelConfigEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.paychannelconfig.service.PayChannelConfigService;

/**
 * 支付渠道配置
 *
 * @author aleng
 * @email xxxxx
 * @date 2019-04-10 14:20:07
 */
@RestController
@RequestMapping("paychannelconfig/paychannelconfig")
public class PayChannelConfigController {
	@Autowired
	private PayChannelConfigService payChannelConfigService;

	/**
	 * 分页
	 */
	@RequestMapping("/list")
	@RequiresPermissions("paychannelconfig:paychannelconfig:list")
	public R page(PayChannelConfigEntity paychannelconfig, PageParam pageParam) {
		Page<PayChannelConfigEntity> result = payChannelConfigService.getPayChannelConfigs(paychannelconfig, pageParam);
		return R.ok().put("page", new PageUtils(result.getRecords(), result.getTotal(), result.getSize(),
				result.getCurrent(), result.getPages()));
	}

	/**
	 * 列表
	 */
	@RequestMapping("/Paychannelconfigs")
	@RequiresPermissions("paychannelconfig:paychannelconfig:list")
	public R list(PayChannelConfigEntity paychannelconfig, PageParam pageParam) {
		Wrapper<PayChannelConfigEntity> entityWrapper = new EntityWrapper<PayChannelConfigEntity>(paychannelconfig);
		paychannelconfig.selectList(entityWrapper);
		return R.ok().put("list", paychannelconfig.selectList(entityWrapper));
	}

	/**
	 * 信息
	 */
	@RequestMapping("/info/{id}")
	@RequiresPermissions("paychannelconfig:paychannelconfig:info")
	public R info(@PathVariable("id") Long id) {
		PayChannelConfigEntity payChannelConfig = payChannelConfigService.selectById(id);
		return R.ok().put("paychannelconfig", payChannelConfig);
	}

	/**
	 * 保存
	 */
	@RequestMapping("/save")
	@RequiresPermissions("paychannelconfig:paychannelconfig:save")
	public R save(@RequestBody PayChannelConfigEntity paychannelconfig) {
		PayChannelConfigEntity param = new PayChannelConfigEntity();
		param.setChannelId(paychannelconfig.getChannelId());
		param.setPayId(paychannelconfig.getPayId());
		Wrapper<PayChannelConfigEntity> entityWrapper = new EntityWrapper<PayChannelConfigEntity>(param);
		List<PayChannelConfigEntity> list = payChannelConfigService.selectList(entityWrapper);
		if (!CollectionUtils.isEmpty(list)) {
			throw new RRException(ErrorCode.PayErrorCode.PAY_CHANNEL_REPEAT_ERROR.getErrMsg(),
					ErrorCode.PayErrorCode.PAY_CHANNEL_REPEAT_ERROR.getCode());
		}
		payChannelConfigService.insert(paychannelconfig);
		return R.ok();
	}

	/**
	 * 修改
	 */
	@RequestMapping("/update")
	@RequiresPermissions("paychannelconfig:paychannelconfig:update")
	public R update(@RequestBody PayChannelConfigEntity paychannelconfig) {
		payChannelConfigService.updateById(paychannelconfig);
		return R.ok();
	}

	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	@RequiresPermissions("paychannelconfig:paychannelconfig:delete")
	public R delete(@RequestBody Long[] ids) {
		for (Long id : ids) {
			payChannelConfigService.deleteById(id);
		}
		return R.ok();
	}

}

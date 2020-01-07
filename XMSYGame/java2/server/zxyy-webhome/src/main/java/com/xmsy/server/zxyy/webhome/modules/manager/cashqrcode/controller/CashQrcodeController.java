package com.xmsy.server.zxyy.webhome.modules.manager.cashqrcode.controller;

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
import com.xmsy.server.zxyy.webhome.modules.manager.cashqrcode.entity.CashQrcodeEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.cashqrcode.service.CashQrcodeService;

/**
 * 收款二维码
 *
 * @author aleng
 * @email xxxxx
 * @date 2019-04-22 11:02:28
 */
@RestController
@RequestMapping("cashqrcode/cashqrcode")
public class CashQrcodeController {
	@Autowired
	private CashQrcodeService cashQrcodeService;

	/**
	 * 列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("cashqrcode:cashqrcode:list")
	public R list(CashQrcodeEntity cashqrcode, PageParam pageParam) {
		Page<CashQrcodeEntity> result = new Page<CashQrcodeEntity>(pageParam.getPage(), pageParam.getLimit());
		Wrapper<CashQrcodeEntity> entityWrapper = new EntityWrapper<CashQrcodeEntity>(cashqrcode);
		entityWrapper.orderBy("id", false);
		cashqrcode.selectPage(result, entityWrapper);
		return R.ok().put("page", new PageUtils(result.getRecords(), result.getTotal(), result.getSize(),
				result.getCurrent(), result.getPages()));
	}

	/**
	 * 信息
	 */
	@RequestMapping("/info/{id}")
	@RequiresPermissions("cashqrcode:cashqrcode:info")
	public R info(@PathVariable("id") Long id) {
		CashQrcodeEntity cashQrcode = cashQrcodeService.selectById(id);
		return R.ok().put("cashqrcode", cashQrcode);
	}

	/**
	 * 保存
	 */
	@RequestMapping("/save")
	@RequiresPermissions("cashqrcode:cashqrcode:save")
	public R save(@RequestBody CashQrcodeEntity cashqrcode) {
		cashQrcodeService.insert(cashqrcode);
		return R.ok();
	}

	/**
	 * 修改
	 */
	@RequestMapping("/update")
	@RequiresPermissions("cashqrcode:cashqrcode:update")
	public R update(@RequestBody CashQrcodeEntity cashqrcode) {
		cashQrcodeService.updateById(cashqrcode);
		return R.ok();
	}

	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	@RequiresPermissions("cashqrcode:cashqrcode:delete")
	public R delete(@RequestBody Long[] ids) {
		for (Long id : ids) {
			cashQrcodeService.deleteById(id);
		}
		return R.ok();
	}

}

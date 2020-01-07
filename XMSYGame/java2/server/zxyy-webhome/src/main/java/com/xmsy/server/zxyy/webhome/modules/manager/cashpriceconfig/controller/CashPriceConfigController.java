package com.xmsy.server.zxyy.webhome.modules.manager.cashpriceconfig.controller;

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
import com.xmsy.server.zxyy.webhome.common.annotation.SysLog;
import com.xmsy.server.zxyy.webhome.common.utils.PageUtils;
import com.xmsy.server.zxyy.webhome.common.utils.R;
import com.xmsy.server.zxyy.webhome.modules.manager.cashpriceconfig.entity.CashPriceConfigEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.cashpriceconfig.service.CashPriceConfigService;



/**
 * 现金价格预设配置表（提供给用户充值、提现的预设金额）
 *
 * @author adu
 * @email xxxxx
 * @date 2019-02-18 10:27:10
 */
@RestController
@RequestMapping("cashpriceconfig/cashpriceconfig")
public class CashPriceConfigController {
    @Autowired
    private CashPriceConfigService cashPriceConfigService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("cashpriceconfig:cashpriceconfig:list")
    public R list(CashPriceConfigEntity cashpriceconfig, PageParam pageParam){
        Page<CashPriceConfigEntity> result = new Page<CashPriceConfigEntity>(pageParam.getPage(), pageParam.getLimit());
		Wrapper<CashPriceConfigEntity> entityWrapper = new EntityWrapper<CashPriceConfigEntity>(cashpriceconfig);
		entityWrapper.orderBy(pageParam.getSort(), pageParam.getDirection());
		cashpriceconfig.selectPage(result, entityWrapper);
		return R.ok().put("page", new PageUtils(result.getRecords(), result.getTotal(), result.getSize(),
				result.getCurrent(), result.getPages()));
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("cashpriceconfig:cashpriceconfig:info")
    public R info(@PathVariable("id") Long id){
			CashPriceConfigEntity cashPriceConfig = cashPriceConfigService.selectById(id);
        return R.ok().put("cashpriceconfig", cashPriceConfig);
    }

    /**
     * 保存
     */
    @SysLog("现金价格预设配置新增")
    @RequestMapping("/save")
    @RequiresPermissions("cashpriceconfig:cashpriceconfig:save")
    public R save(@RequestBody CashPriceConfigEntity cashpriceconfig){
			cashPriceConfigService.insert(cashpriceconfig);
        return R.ok();
    }

    /**
     * 修改
     */
    @SysLog("现金价格预设配置修改")
    @RequestMapping("/update")
    @RequiresPermissions("cashpriceconfig:cashpriceconfig:update")
    public R update(@RequestBody CashPriceConfigEntity cashpriceconfig){
			cashPriceConfigService.updateById(cashpriceconfig);
        return R.ok();
    }

    /**
     * 删除
     */
    @SysLog("现金价格预设配置删除")
    @RequestMapping("/delete")
    @RequiresPermissions("cashpriceconfig:cashpriceconfig:delete")
    public R delete(@RequestBody Long[] ids){
    for (Long id : ids) {
			cashPriceConfigService.deleteById(id);
	}
        return R.ok();
    }

}

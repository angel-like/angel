package com.xmsy.server.zxyy.webhome.modules.manager.proxyordertransactionrecord.controller;

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
import com.xmsy.server.zxyy.webhome.modules.manager.proxyordertransactionrecord.entity.ProxyOrderTransactionRecordEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.proxyordertransactionrecord.service.ProxyOrderTransactionRecordService;



/**
 * 代理交易订单表
 *
 * @author ahui
 * @email xxxxx
 * @date 2019-08-05 17:11:01
 */
@RestController
@RequestMapping("proxyordertransactionrecord/proxyordertransactionrecord")
public class ProxyOrderTransactionRecordController {
    @Autowired
    private ProxyOrderTransactionRecordService proxyOrderTransactionRecordService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("proxyordertransactionrecord:proxyordertransactionrecord:list")
    public R list(ProxyOrderTransactionRecordEntity proxyordertransactionrecord, PageParam pageParam){
        Page<ProxyOrderTransactionRecordEntity> result = new Page<ProxyOrderTransactionRecordEntity>(pageParam.getPage(), pageParam.getLimit());
		Wrapper<ProxyOrderTransactionRecordEntity> entityWrapper = new EntityWrapper<ProxyOrderTransactionRecordEntity>(proxyordertransactionrecord);
		entityWrapper.orderBy(pageParam.getSort(), pageParam.getDirection());
		proxyordertransactionrecord.selectPage(result, entityWrapper);
		return R.ok().put("page", new PageUtils(result.getRecords(), result.getTotal(), result.getSize(),
				result.getCurrent(), result.getPages()));
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("proxyordertransactionrecord:proxyordertransactionrecord:info")
    public R info(@PathVariable("id") Long id){
			ProxyOrderTransactionRecordEntity proxyOrderTransactionRecord = proxyOrderTransactionRecordService.selectById(id);
        return R.ok().put("proxyordertransactionrecord", proxyOrderTransactionRecord);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("proxyordertransactionrecord:proxyordertransactionrecord:save")
    public R save(@RequestBody ProxyOrderTransactionRecordEntity proxyordertransactionrecord){
			proxyOrderTransactionRecordService.insert(proxyordertransactionrecord);
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("proxyordertransactionrecord:proxyordertransactionrecord:update")
    public R update(@RequestBody ProxyOrderTransactionRecordEntity proxyordertransactionrecord){
			proxyOrderTransactionRecordService.updateById(proxyordertransactionrecord);
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("proxyordertransactionrecord:proxyordertransactionrecord:delete")
    public R delete(@RequestBody Long[] ids){
    for (Long id : ids) {
			proxyOrderTransactionRecordService.deleteById(id);
	}
        return R.ok();
    }

}

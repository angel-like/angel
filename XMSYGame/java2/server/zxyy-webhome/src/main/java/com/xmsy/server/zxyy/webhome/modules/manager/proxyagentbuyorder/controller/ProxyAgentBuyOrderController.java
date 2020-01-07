package com.xmsy.server.zxyy.webhome.modules.manager.proxyagentbuyorder.controller;

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
import com.xmsy.server.zxyy.webhome.modules.manager.proxyagentbuyorder.entity.ProxyAgentBuyOrderEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.proxyagentbuyorder.service.ProxyAgentBuyOrderService;



/**
 * 代理购买订单列表
 *
 * @author ahui
 * @email xxxxx
 * @date 2019-08-02 10:09:16
 */
@RestController
@RequestMapping("proxyagentbuyorder/proxyagentbuyorder")
public class ProxyAgentBuyOrderController {
    @Autowired
    private ProxyAgentBuyOrderService proxyAgentBuyOrderService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("proxyagentbuyorder:proxyagentbuyorder:list")
    public R list(ProxyAgentBuyOrderEntity proxyagentbuyorder, PageParam pageParam){
        Page<ProxyAgentBuyOrderEntity> result = new Page<ProxyAgentBuyOrderEntity>(pageParam.getPage(), pageParam.getLimit());
		Wrapper<ProxyAgentBuyOrderEntity> entityWrapper = new EntityWrapper<ProxyAgentBuyOrderEntity>(proxyagentbuyorder);
		entityWrapper.orderBy(pageParam.getSort(), pageParam.getDirection());
		proxyagentbuyorder.selectPage(result, entityWrapper);
		return R.ok().put("page", new PageUtils(result.getRecords(), result.getTotal(), result.getSize(),
				result.getCurrent(), result.getPages()));
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("proxyagentbuyorder:proxyagentbuyorder:info")
    public R info(@PathVariable("id") Long id){
			ProxyAgentBuyOrderEntity proxyAgentBuyOrder = proxyAgentBuyOrderService.selectById(id);
        return R.ok().put("proxyagentbuyorder", proxyAgentBuyOrder);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("proxyagentbuyorder:proxyagentbuyorder:save")
    public R save(@RequestBody ProxyAgentBuyOrderEntity proxyagentbuyorder){
			proxyAgentBuyOrderService.insert(proxyagentbuyorder);
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("proxyagentbuyorder:proxyagentbuyorder:update")
    public R update(@RequestBody ProxyAgentBuyOrderEntity proxyagentbuyorder){
			proxyAgentBuyOrderService.updateById(proxyagentbuyorder);
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("proxyagentbuyorder:proxyagentbuyorder:delete")
    public R delete(@RequestBody Long[] ids){
    for (Long id : ids) {
			proxyAgentBuyOrderService.deleteById(id);
	}
        return R.ok();
    }

}

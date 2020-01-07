package com.xmsy.server.zxyy.webhome.modules.manager.rechargeamount.controller;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.plugins.Page;
import com.xmsy.common.define.page.PageParam;
import com.xmsy.server.zxyy.webhome.common.utils.PageUtils;
import com.xmsy.server.zxyy.webhome.common.utils.R;
import com.xmsy.server.zxyy.webhome.modules.manager.rechargeamount.entity.RechargeAmountEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.rechargeamount.service.RechargeAmountService;



/**
 * 充值内容（金额）
 *
 * @author xiaoliu
 * @email xxxxx
 * @date 2019-03-27 10:16:53
 */
@RestController
@RequestMapping("rechargeamount/rechargeamount")
public class RechargeAmountController {
    @Autowired
    private RechargeAmountService rechargeAmountService;
   
    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("rechargeamount:rechargeamount:list")
    public R list(RechargeAmountEntity rechargeamount, PageParam pageParam){
    	Page<RechargeAmountEntity> result = rechargeAmountService.selectNewPage(rechargeamount,pageParam);
		return R.ok().put("page", new PageUtils(result.getRecords(), result.getTotal(), result.getSize(),
				result.getCurrent(), result.getPages()));
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("rechargeamount:rechargeamount:info")
    public R info(@PathVariable("id") Long id){
			RechargeAmountEntity rechargeAmount = rechargeAmountService.selectById(id);
        return R.ok().put("rechargeamount", rechargeAmount);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("rechargeamount:rechargeamount:save")
    public R save(@RequestBody RechargeAmountEntity rechargeamount){
			rechargeAmountService.insert(rechargeamount);
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("rechargeamount:rechargeamount:update")
    public R update(@RequestBody RechargeAmountEntity rechargeamount){
			rechargeAmountService.updateById(rechargeamount);
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("rechargeamount:rechargeamount:delete")
    public R delete(@RequestBody Long[] ids){
    for (Long id : ids) {
			rechargeAmountService.deleteById(id);
	}
        return R.ok();
    }

}

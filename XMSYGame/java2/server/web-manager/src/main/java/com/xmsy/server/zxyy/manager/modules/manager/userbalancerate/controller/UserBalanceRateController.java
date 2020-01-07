package com.xmsy.server.zxyy.manager.modules.manager.userbalancerate.controller;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.xmsy.common.define.page.PageParam;
import com.xmsy.server.zxyy.manager.common.utils.R;
import com.xmsy.server.zxyy.manager.modules.manager.userbalancerate.entity.UserBalanceRateEntity;
import com.xmsy.server.zxyy.manager.modules.manager.userbalancerate.service.UserBalanceRateService;



/**
 * 用户余额宝利率表
 *
 * @author axiong
 * @email xxxxx
 * @date 2019-06-17 15:56:57
 */
@RestController
@RequestMapping("userbalancerate/userbalancerate")
public class UserBalanceRateController {
    @Autowired
    private UserBalanceRateService userBalanceRateService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("userbalancerate:userbalancerate:list")
    public R list(UserBalanceRateEntity userbalancerate, PageParam pageParam){
    	return R.ok().put("page",userBalanceRateService.findUserRecordPageForTime(userbalancerate,pageParam));
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("userbalancerate:userbalancerate:info")
    public R info(@PathVariable("id") Long id){
			UserBalanceRateEntity userBalanceRate = userBalanceRateService.selectById(id);
        return R.ok().put("userbalancerate", userBalanceRate);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("userbalancerate:userbalancerate:save")
    public R save(@RequestBody UserBalanceRateEntity userbalancerate){
			userBalanceRateService.insert(userbalancerate);
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("userbalancerate:userbalancerate:update")
    public R update(@RequestBody UserBalanceRateEntity userbalancerate){
			userBalanceRateService.updateById(userbalancerate);
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("userbalancerate:userbalancerate:delete")
    public R delete(@RequestBody Long[] ids){
    for (Long id : ids) {
			userBalanceRateService.deleteById(id);
	}
        return R.ok();
    }

}

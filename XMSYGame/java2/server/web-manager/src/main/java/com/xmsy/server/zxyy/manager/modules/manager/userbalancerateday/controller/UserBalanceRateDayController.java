package com.xmsy.server.zxyy.manager.modules.manager.userbalancerateday.controller;

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
import com.xmsy.server.zxyy.manager.common.utils.PageUtils;
import com.xmsy.server.zxyy.manager.common.utils.R;
import com.xmsy.server.zxyy.manager.modules.manager.userbalancerateday.entity.UserBalanceRateDayEntity;
import com.xmsy.server.zxyy.manager.modules.manager.userbalancerateday.service.UserBalanceRateDayService;



/**
 * 用户余额宝每日利率表
 *
 * @author axiong
 * @email xxxxx
 * @date 2019-06-24 16:35:30
 */
@RestController
@RequestMapping("userbalancerateday/userbalancerateday")
public class UserBalanceRateDayController {
    @Autowired
    private UserBalanceRateDayService userBalanceRateDayService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("userbalancerateday:userbalancerateday:list")
    public R list(UserBalanceRateDayEntity userbalancerateday, PageParam pageParam){
        Page<UserBalanceRateDayEntity> result = new Page<UserBalanceRateDayEntity>(pageParam.getPage(), pageParam.getLimit());
		Wrapper<UserBalanceRateDayEntity> entityWrapper = new EntityWrapper<UserBalanceRateDayEntity>(userbalancerateday);
		entityWrapper.orderBy(pageParam.getSort(), pageParam.getDirection());
		userbalancerateday.selectPage(result, entityWrapper);
		return R.ok().put("page", new PageUtils(result.getRecords(), result.getTotal(), result.getSize(),
				result.getCurrent(), result.getPages()));
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("userbalancerateday:userbalancerateday:info")
    public R info(@PathVariable("id") Long id){
			UserBalanceRateDayEntity userBalanceRateDay = userBalanceRateDayService.selectById(id);
        return R.ok().put("userbalancerateday", userBalanceRateDay);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("userbalancerateday:userbalancerateday:save")
    public R save(@RequestBody UserBalanceRateDayEntity userbalancerateday){
			userBalanceRateDayService.insert(userbalancerateday);
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("userbalancerateday:userbalancerateday:update")
    public R update(@RequestBody UserBalanceRateDayEntity userbalancerateday){
			userBalanceRateDayService.updateById(userbalancerateday);
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("userbalancerateday:userbalancerateday:delete")
    public R delete(@RequestBody Long[] ids){
    for (Long id : ids) {
			userBalanceRateDayService.deleteById(id);
	}
        return R.ok();
    }

}

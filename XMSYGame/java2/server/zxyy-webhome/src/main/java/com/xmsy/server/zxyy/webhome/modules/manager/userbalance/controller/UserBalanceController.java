package com.xmsy.server.zxyy.webhome.modules.manager.userbalance.controller;

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
import com.xmsy.server.zxyy.webhome.modules.manager.userbalance.entity.UserBalanceEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.userbalance.service.UserBalanceService;



/**
 * 用户余额宝金额表
 *
 * @author axiong
 * @email xxxxx
 * @date 2019-06-18 09:18:59
 */
@RestController
@RequestMapping("userbalance/userbalance")
public class UserBalanceController {
    @Autowired
    private UserBalanceService userBalanceService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("userbalance:userbalance:list")
    public R list(UserBalanceEntity userbalance, PageParam pageParam){
        Page<UserBalanceEntity> result = new Page<UserBalanceEntity>(pageParam.getPage(), pageParam.getLimit());
		Wrapper<UserBalanceEntity> entityWrapper = new EntityWrapper<UserBalanceEntity>(userbalance);
		entityWrapper.orderBy(pageParam.getSort(), pageParam.getDirection());
		userbalance.selectPage(result, entityWrapper);
		return R.ok().put("page", new PageUtils(result.getRecords(), result.getTotal(), result.getSize(),
				result.getCurrent(), result.getPages()));
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("userbalance:userbalance:info")
    public R info(@PathVariable("id") Long id){
			UserBalanceEntity userBalance = userBalanceService.selectById(id);
        return R.ok().put("userbalance", userBalance);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("userbalance:userbalance:save")
    public R save(@RequestBody UserBalanceEntity userbalance){
			userBalanceService.insert(userbalance);
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("userbalance:userbalance:update")
    public R update(@RequestBody UserBalanceEntity userbalance){
			userBalanceService.updateById(userbalance);
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("userbalance:userbalance:delete")
    public R delete(@RequestBody Long[] ids){
    for (Long id : ids) {
			userBalanceService.deleteById(id);
	}
        return R.ok();
    }

}

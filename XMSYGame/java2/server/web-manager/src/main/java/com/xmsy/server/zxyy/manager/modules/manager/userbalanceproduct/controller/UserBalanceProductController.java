package com.xmsy.server.zxyy.manager.modules.manager.userbalanceproduct.controller;

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
import com.xmsy.server.zxyy.manager.modules.manager.userbalanceproduct.entity.UserBalanceProductEntity;
import com.xmsy.server.zxyy.manager.modules.manager.userbalanceproduct.service.UserBalanceProductService;



/**
 * 理财产品
 *
 * @author aye
 * @email xxxxx
 * @date 2019-11-28 11:26:59
 */
@RestController
@RequestMapping("userbalanceproduct/userbalanceproduct")
public class UserBalanceProductController {
    @Autowired
    private UserBalanceProductService userBalanceProductService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("userbalanceproduct:userbalanceproduct:list")
    public R list(UserBalanceProductEntity userbalanceproduct, PageParam pageParam){
        Page<UserBalanceProductEntity> result = new Page<UserBalanceProductEntity>(pageParam.getPage(), pageParam.getLimit());
		Wrapper<UserBalanceProductEntity> entityWrapper = new EntityWrapper<UserBalanceProductEntity>(userbalanceproduct);
		entityWrapper.orderBy("order_num", true);
		userbalanceproduct.selectPage(result, entityWrapper);
		return R.ok().put("page", new PageUtils(result.getRecords(), result.getTotal(), result.getSize(),
				result.getCurrent(), result.getPages()));
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("userbalanceproduct:userbalanceproduct:info")
    public R info(@PathVariable("id") Long id){
			UserBalanceProductEntity userBalanceProduct = userBalanceProductService.selectById(id);
        return R.ok().put("userbalanceproduct", userBalanceProduct);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("userbalanceproduct:userbalanceproduct:save")
    public R save(@RequestBody UserBalanceProductEntity userbalanceproduct){
    	//剩余可买金额  等于   发行金额
    	userbalanceproduct.setRemaindBuyNum(userbalanceproduct.getIssueNum());
			userBalanceProductService.insert(userbalanceproduct);
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("userbalanceproduct:userbalanceproduct:update")
    public R update(@RequestBody UserBalanceProductEntity userbalanceproduct){
			userBalanceProductService.updateById(userbalanceproduct);
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("userbalanceproduct:userbalanceproduct:delete")
    public R delete(@RequestBody Long[] ids){
    for (Long id : ids) {
			userBalanceProductService.deleteById(id);
	}
        return R.ok();
    }

}

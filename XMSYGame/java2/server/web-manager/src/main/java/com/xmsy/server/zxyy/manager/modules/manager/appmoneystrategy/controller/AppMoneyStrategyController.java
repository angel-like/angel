package com.xmsy.server.zxyy.manager.modules.manager.appmoneystrategy.controller;

import com.xmsy.common.define.page.PageParam;
import com.xmsy.server.zxyy.manager.common.utils.PageUtils;
import com.xmsy.server.zxyy.manager.common.utils.R;
import com.xmsy.server.zxyy.manager.modules.manager.appmoneystrategy.entity.AppMoneyStrategyEntity;
import com.xmsy.server.zxyy.manager.modules.manager.appmoneystrategy.service.AppMoneyStrategyService;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;



/**
 * 赚钱攻略表
 *
 * @author xiaoling
 * @email xxxxx
 * @date 2019-05-07 17:50:34
 */
@RestController
@RequestMapping("appmoneystrategy/appmoneystrategy")
public class AppMoneyStrategyController {
    @Autowired
    private AppMoneyStrategyService appMoneyStrategyService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("appmoneystrategy:appmoneystrategy:list")
    public R list(AppMoneyStrategyEntity appmoneystrategy, PageParam pageParam){
        Page<AppMoneyStrategyEntity> result = new Page<AppMoneyStrategyEntity>(pageParam.getPage(), pageParam.getLimit());
		Wrapper<AppMoneyStrategyEntity> entityWrapper = new EntityWrapper<AppMoneyStrategyEntity>(appmoneystrategy);
		entityWrapper.orderBy(pageParam.getSort(), pageParam.getDirection());
		appmoneystrategy.selectPage(result, entityWrapper);
		return R.ok().put("page", new PageUtils(result.getRecords(), result.getTotal(), result.getSize(),
				result.getCurrent(), result.getPages()));
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("appmoneystrategy:appmoneystrategy:info")
    public R info(@PathVariable("id") Long id){
			AppMoneyStrategyEntity appMoneyStrategy = appMoneyStrategyService.selectById(id);
        return R.ok().put("appmoneystrategy", appMoneyStrategy);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("appmoneystrategy:appmoneystrategy:save")
    public R save(@RequestBody AppMoneyStrategyEntity appmoneystrategy){
			appMoneyStrategyService.insert(appmoneystrategy);
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("appmoneystrategy:appmoneystrategy:update")
    public R update(@RequestBody AppMoneyStrategyEntity appmoneystrategy){
			appMoneyStrategyService.updateById(appmoneystrategy);
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("appmoneystrategy:appmoneystrategy:delete")
    public R delete(@RequestBody Long[] ids){
    for (Long id : ids) {
			appMoneyStrategyService.deleteById(id);
	}
        return R.ok();
    }

}

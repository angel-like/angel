package com.xmsy.server.zxyy.webhome.modules.manager.appcustomerservice.controller;

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
import com.xmsy.server.zxyy.webhome.modules.manager.appcustomerservice.entity.AppCustomerServiceEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.appcustomerservice.service.AppCustomerServiceService;



/**
 * app客服管理
 *
 * @author aleng
 * @email xxxxx
 * @date 2019-03-06 14:43:56
 */
@RestController
@RequestMapping("appcustomerservice/appcustomerservice")
public class AppCustomerServiceController {
    @Autowired
    private AppCustomerServiceService appCustomerServiceService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("appcustomerservice:appcustomerservice:list")
    public R list(AppCustomerServiceEntity appcustomerservice, PageParam pageParam){
        Page<AppCustomerServiceEntity> result = new Page<AppCustomerServiceEntity>(pageParam.getPage(), pageParam.getLimit());
		Wrapper<AppCustomerServiceEntity> entityWrapper = new EntityWrapper<AppCustomerServiceEntity>(appcustomerservice);
		entityWrapper.orderBy(pageParam.getSort(), pageParam.getDirection());
		appcustomerservice.selectPage(result, entityWrapper);
		return R.ok().put("page", new PageUtils(result.getRecords(), result.getTotal(), result.getSize(),
				result.getCurrent(), result.getPages()));
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("appcustomerservice:appcustomerservice:info")
    public R info(@PathVariable("id") Long id){
			AppCustomerServiceEntity appCustomerService = appCustomerServiceService.selectById(id);
        return R.ok().put("appcustomerservice", appCustomerService);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("appcustomerservice:appcustomerservice:save")
    public R save(@RequestBody AppCustomerServiceEntity appcustomerservice){
			appCustomerServiceService.insert(appcustomerservice);
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("appcustomerservice:appcustomerservice:update")
    public R update(@RequestBody AppCustomerServiceEntity appcustomerservice){
			appCustomerServiceService.updateById(appcustomerservice);
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("appcustomerservice:appcustomerservice:delete")
    public R delete(@RequestBody Long[] ids){
    for (Long id : ids) {
			appCustomerServiceService.deleteById(id);
	}
        return R.ok();
    }

}

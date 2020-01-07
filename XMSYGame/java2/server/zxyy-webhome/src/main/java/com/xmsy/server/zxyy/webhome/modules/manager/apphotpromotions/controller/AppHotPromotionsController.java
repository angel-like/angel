package com.xmsy.server.zxyy.webhome.modules.manager.apphotpromotions.controller;

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
import com.xmsy.server.zxyy.webhome.modules.manager.apphotpromotions.entity.AppHotPromotionsEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.apphotpromotions.service.AppHotPromotionsService;



/**
 * APP热门活动
 *
 * @author aleng
 * @email xxxxx
 * @date 2019-05-04 16:01:21
 */
@RestController
@RequestMapping("apphotpromotions/apphotpromotions")
public class AppHotPromotionsController {
    @Autowired
    private AppHotPromotionsService appHotPromotionsService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("apphotpromotions:apphotpromotions:list")
    public R list(AppHotPromotionsEntity apphotpromotions, PageParam pageParam){
        Page<AppHotPromotionsEntity> result = new Page<AppHotPromotionsEntity>(pageParam.getPage(), pageParam.getLimit());
		Wrapper<AppHotPromotionsEntity> entityWrapper = new EntityWrapper<AppHotPromotionsEntity>(apphotpromotions);
		entityWrapper.orderBy(pageParam.getSort(), pageParam.getDirection());
		apphotpromotions.selectPage(result, entityWrapper);
		return R.ok().put("page", new PageUtils(result.getRecords(), result.getTotal(), result.getSize(),
				result.getCurrent(), result.getPages()));
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("apphotpromotions:apphotpromotions:info")
    public R info(@PathVariable("id") Long id){
			AppHotPromotionsEntity appHotPromotions = appHotPromotionsService.selectById(id);
        return R.ok().put("apphotpromotions", appHotPromotions);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("apphotpromotions:apphotpromotions:save")
    public R save(@RequestBody AppHotPromotionsEntity apphotpromotions){
			appHotPromotionsService.insert(apphotpromotions);
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("apphotpromotions:apphotpromotions:update")
    public R update(@RequestBody AppHotPromotionsEntity apphotpromotions){
			appHotPromotionsService.updateById(apphotpromotions);
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("apphotpromotions:apphotpromotions:delete")
    public R delete(@RequestBody Long[] ids){
    for (Long id : ids) {
			appHotPromotionsService.deleteById(id);
	}
        return R.ok();
    }

}

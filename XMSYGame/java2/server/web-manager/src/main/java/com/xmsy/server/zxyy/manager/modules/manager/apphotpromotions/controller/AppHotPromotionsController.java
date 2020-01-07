package com.xmsy.server.zxyy.manager.modules.manager.apphotpromotions.controller;

import org.apache.commons.lang3.StringUtils;
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
import com.xmsy.server.zxyy.manager.modules.manager.apphotpromotions.entity.AppHotPromotionsEntity;
import com.xmsy.server.zxyy.manager.modules.manager.apphotpromotions.service.AppHotPromotionsService;

import java.util.List;


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
//    @Autowired
//    private AppPromotionsTypeService appPromotionsTypeService;
    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("apphotpromotions:apphotpromotions:list")
    public R list(AppHotPromotionsEntity apphotpromotions, PageParam pageParam){
        Page<AppHotPromotionsEntity> result = new Page<AppHotPromotionsEntity>(pageParam.getPage(), pageParam.getLimit());
		Wrapper<AppHotPromotionsEntity> entityWrapper = new EntityWrapper<AppHotPromotionsEntity>(apphotpromotions).orderBy("order_num");
		entityWrapper.orderBy(pageParam.getSort(), pageParam.getDirection());
		apphotpromotions.selectPage(result, entityWrapper);
        List<AppHotPromotionsEntity> records = result.getRecords();
        return R.ok().put("page", new PageUtils(records, result.getTotal(), result.getSize(),
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
        String content = apphotpromotions.getContent();
        if(!StringUtils.isNotBlank(content)){
            content="0";
            apphotpromotions.setContent(content);
        }
        appHotPromotionsService.insert(apphotpromotions);
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("apphotpromotions:apphotpromotions:update")
    public R update(@RequestBody AppHotPromotionsEntity apphotpromotions){
        String content = apphotpromotions.getContent();
        if(!StringUtils.isNotBlank(content)){
            content="0";
            apphotpromotions.setContent(content);
        }
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

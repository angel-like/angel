package com.xmsy.server.zxyy.webhome.modules.manager.apppromotions.controller;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.util.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.xmsy.common.define.page.PageParam;
import com.xmsy.server.zxyy.webhome.common.annotation.SysLog;
import com.xmsy.server.zxyy.webhome.common.utils.PageUtils;
import com.xmsy.server.zxyy.webhome.common.utils.R;
import com.xmsy.server.zxyy.webhome.modules.manager.apppromotions.entity.AppPromotionsEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.apppromotions.service.AppPromotionsService;
import com.xmsy.server.zxyy.webhome.modules.manager.apppromotionstype.entity.AppPromotionsTypeEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.apppromotionstype.service.AppPromotionsTypeService;



/**
 * 官网优惠活动
 *
 * @author aleng
 * @email xxxxx
 * @date 2019-01-28 16:22:11
 */
@RestController
@RequestMapping("apppromotions/apppromotions")
public class AppPromotionsController {
    @Autowired
    private AppPromotionsService appPromotionsService;
    @Autowired
    private AppPromotionsTypeService appPromotionsTypeService;
    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("apppromotions:apppromotions:list")
    public R list(AppPromotionsEntity apppromotions, PageParam pageParam){
        Page<AppPromotionsEntity> result = new Page<AppPromotionsEntity>(pageParam.getPage(), pageParam.getLimit());
		Wrapper<AppPromotionsEntity> entityWrapper = new EntityWrapper<AppPromotionsEntity>(apppromotions);
		entityWrapper.orderBy(pageParam.getSort(), pageParam.getDirection());
		apppromotions.selectPage(result, entityWrapper);
		if(!CollectionUtils.isEmpty(result.getRecords())) {
			for(AppPromotionsEntity entity:result.getRecords()) {
				if(entity.getTypeId()!=null||entity.getTypeId()!=0) {
					AppPromotionsTypeEntity promotionsType=appPromotionsTypeService.selectById(entity.getTypeId());
					entity.setTypeName(promotionsType.getName());
				}
			}
		}
		return R.ok().put("page", new PageUtils(result.getRecords(), result.getTotal(), result.getSize(),
				result.getCurrent(), result.getPages()));
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("apppromotions:apppromotions:info")
    public R info(@PathVariable("id") Long id){
			AppPromotionsEntity appPromotions = appPromotionsService.selectById(id);
        return R.ok().put("apppromotions", appPromotions);
    }

    /**
     * 保存
     */
    @SysLog("官网优惠活动新增")
    @RequestMapping("/save")
    @RequiresPermissions("apppromotions:apppromotions:save")
    public R save(@RequestBody AppPromotionsEntity apppromotions){
			appPromotionsService.insert(apppromotions);
        return R.ok();
    }

    /**
     * 修改
     */
    @SysLog("官网优惠活动修改")
    @RequestMapping("/update")
    @RequiresPermissions("apppromotions:apppromotions:update")
    public R update(@RequestBody AppPromotionsEntity apppromotions){
			appPromotionsService.updateById(apppromotions);
        return R.ok();
    }

    /**
     * 删除
     */
    @SysLog("官网优惠活动删除")
    @RequestMapping("/delete")
    @RequiresPermissions("apppromotions:apppromotions:delete")
    public R delete(@RequestBody Long[] ids){
    for (Long id : ids) {
			appPromotionsService.deleteById(id);
	}
        return R.ok();
    }

}

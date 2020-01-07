package com.xmsy.server.zxyy.manager.modules.manager.appalertcofig.controller;

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
import com.xmsy.server.zxyy.manager.modules.manager.appalertcofig.entity.AppAlertCofigEntity;
import com.xmsy.server.zxyy.manager.modules.manager.appalertcofig.service.AppAlertCofigService;



/**
 * app弹框配置
 *
 * @author aleng
 * @email xxxxx
 * @date 2019-03-07 14:27:10
 */
@RestController
@RequestMapping("appalertcofig/appalertcofig")
public class AppAlertCofigController {
    @Autowired
    private AppAlertCofigService appAlertCofigService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("appalertcofig:appalertcofig:list")
    public R list(AppAlertCofigEntity appalertcofig, PageParam pageParam){
        Page<AppAlertCofigEntity> result = new Page<AppAlertCofigEntity>(pageParam.getPage(), pageParam.getLimit());
		Wrapper<AppAlertCofigEntity> entityWrapper = new EntityWrapper<AppAlertCofigEntity>(appalertcofig);
		entityWrapper.orderBy(pageParam.getSort(), pageParam.getDirection());
		appalertcofig.selectPage(result, entityWrapper);
		return R.ok().put("page", new PageUtils(result.getRecords(), result.getTotal(), result.getSize(),
				result.getCurrent(), result.getPages()));
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("appalertcofig:appalertcofig:info")
    public R info(@PathVariable("id") Long id){
			AppAlertCofigEntity appAlertCofig = appAlertCofigService.selectById(id);
        return R.ok().put("appalertcofig", appAlertCofig);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("appalertcofig:appalertcofig:save")
    public R save(@RequestBody AppAlertCofigEntity appalertcofig){
			appAlertCofigService.insert(appalertcofig);
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("appalertcofig:appalertcofig:update")
    public R update(@RequestBody AppAlertCofigEntity appalertcofig){
			appAlertCofigService.updateById(appalertcofig);
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("appalertcofig:appalertcofig:delete")
    public R delete(@RequestBody Long[] ids){
    for (Long id : ids) {
			appAlertCofigService.deleteById(id);
	}
        return R.ok();
    }

}

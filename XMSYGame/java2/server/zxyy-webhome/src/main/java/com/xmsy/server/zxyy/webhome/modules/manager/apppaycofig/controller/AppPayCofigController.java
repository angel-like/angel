package com.xmsy.server.zxyy.webhome.modules.manager.apppaycofig.controller;

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
import com.xmsy.server.zxyy.webhome.constant.SysConstant;
import com.xmsy.server.zxyy.webhome.modules.manager.apppaycofig.entity.AppPayCofigEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.apppaycofig.service.AppPayCofigService;



/**
 * app支付配置
 *
 * @author aleng
 * @email xxxxx
 * @date 2019-03-04 10:32:45
 */
@RestController
@RequestMapping("apppaycofig/apppaycofig")
public class AppPayCofigController {
    @Autowired
    private AppPayCofigService appPayCofigService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("apppaycofig:apppaycofig:list")
    public R list(AppPayCofigEntity apppaycofig, PageParam pageParam){
        Page<AppPayCofigEntity> result = new Page<AppPayCofigEntity>(pageParam.getPage(), pageParam.getLimit());
		Wrapper<AppPayCofigEntity> entityWrapper = new EntityWrapper<AppPayCofigEntity>(apppaycofig);
		entityWrapper.orderBy(pageParam.getSort(), pageParam.getDirection());
		apppaycofig.selectPage(result, entityWrapper);
		return R.ok().put("page", new PageUtils(result.getRecords(), result.getTotal(), result.getSize(),
				result.getCurrent(), result.getPages()));
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("apppaycofig:apppaycofig:info")
    public R info(@PathVariable("id") Long id){
			AppPayCofigEntity appPayCofig = appPayCofigService.selectById(id);
        return R.ok().put("apppaycofig", appPayCofig);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("apppaycofig:apppaycofig:save")
    public R save(@RequestBody AppPayCofigEntity apppaycofig){
			appPayCofigService.insert(apppaycofig);
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("apppaycofig:apppaycofig:update")
    public R update(@RequestBody AppPayCofigEntity apppaycofig){
			appPayCofigService.updateById(apppaycofig);
        return R.ok();
    }
    /**
     * 启用
     */
    @RequestMapping("/openEnable/{id}")
    @RequiresPermissions("apppaycofig:apppaycofig:openEnable")
    public R openEnable(@PathVariable("id") Long id){
    	AppPayCofigEntity appPayCofig = new AppPayCofigEntity();
    	appPayCofig.setId(id);
    	appPayCofig.setEnable(SysConstant.ENABLE_TRUE);
		appPayCofigService.updateById(appPayCofig);
        return R.ok();
    }
    /**
     * 禁用
     */
    @RequestMapping("/closeEnable/{id}")
    @RequiresPermissions("apppaycofig:apppaycofig:closeEnable")
    public R closeEnable(@PathVariable("id") Long id){
    	AppPayCofigEntity appPayCofig = new AppPayCofigEntity();
    	appPayCofig.setId(id);
    	appPayCofig.setEnable(SysConstant.ENABLE_FALSE);
		appPayCofigService.updateById(appPayCofig);
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("apppaycofig:apppaycofig:delete")
    public R delete(@RequestBody Long[] ids){
    for (Long id : ids) {
			appPayCofigService.deleteById(id);
	}
        return R.ok();
    }

}

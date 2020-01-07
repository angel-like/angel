package com.xmsy.server.zxyy.webhome.modules.manager.webhomealertcofig.controller;

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
import com.xmsy.server.zxyy.webhome.modules.manager.webhomealertcofig.entity.WebhomeAlertCofigEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.webhomealertcofig.service.WebhomeAlertCofigService;



/**
 * 官网弹窗配置表

 *
 * @author xiaoliu
 * @email xxxxx
 * @date 2019-05-03 10:23:36
 */
@RestController
@RequestMapping("webhomealertcofig/webhomealertcofig")
public class WebhomeAlertCofigController {
    @Autowired
    private WebhomeAlertCofigService webhomeAlertCofigService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("webhomealertcofig:webhomealertcofig:list")
    public R list(WebhomeAlertCofigEntity webhomealertcofig, PageParam pageParam){
        Page<WebhomeAlertCofigEntity> result = new Page<WebhomeAlertCofigEntity>(pageParam.getPage(), pageParam.getLimit());
		Wrapper<WebhomeAlertCofigEntity> entityWrapper = new EntityWrapper<WebhomeAlertCofigEntity>(webhomealertcofig).orderBy("num");
		entityWrapper.orderBy(pageParam.getSort(), pageParam.getDirection());
		webhomealertcofig.selectPage(result, entityWrapper);
		return R.ok().put("page", new PageUtils(result.getRecords(), result.getTotal(), result.getSize(),
				result.getCurrent(), result.getPages()));
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("webhomealertcofig:webhomealertcofig:info")
    public R info(@PathVariable("id") Long id){
			WebhomeAlertCofigEntity webhomeAlertCofig = webhomeAlertCofigService.selectById(id);
        return R.ok().put("webhomealertcofig", webhomeAlertCofig);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("webhomealertcofig:webhomealertcofig:save")
    public R save(@RequestBody WebhomeAlertCofigEntity webhomealertcofig){
			webhomeAlertCofigService.insert(webhomealertcofig);
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("webhomealertcofig:webhomealertcofig:update")
    public R update(@RequestBody WebhomeAlertCofigEntity webhomealertcofig){
			webhomeAlertCofigService.updateById(webhomealertcofig);
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("webhomealertcofig:webhomealertcofig:delete")
    public R delete(@RequestBody Long[] ids){
    for (Long id : ids) {
			webhomeAlertCofigService.deleteById(id);
	}
        return R.ok();
    }

}

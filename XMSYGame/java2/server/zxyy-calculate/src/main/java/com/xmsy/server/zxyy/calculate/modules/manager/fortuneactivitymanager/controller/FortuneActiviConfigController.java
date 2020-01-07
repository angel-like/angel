package com.xmsy.server.zxyy.calculate.modules.manager.fortuneactivitymanager.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.xmsy.common.define.page.PageParam;
import com.xmsy.server.zxyy.calculate.common.utils.PageUtils;

import com.xmsy.server.zxyy.calculate.common.utils.R;
import com.xmsy.server.zxyy.calculate.modules.manager.fortuneactivitymanager.entity.FortuneActiviConfigEntity;
import com.xmsy.server.zxyy.calculate.modules.manager.fortuneactivitymanager.service.FortuneActiviConfigService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;


/**
 * 天降财神活动配置
 *
 * @author ahui
 * @email sunlightcs@gmail.com
 * @date 2019-12-12 11:38:04
 */
@RestController
@RequestMapping("fortuneactiviconfig/fortuneactiviconfig")
public class FortuneActiviConfigController {
    @Autowired
    private FortuneActiviConfigService fortuneActiviConfigService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("fortuneactiviconfig:fortuneactiviconfig:list")
    public R list(FortuneActiviConfigEntity fortuneActiviConfigEntity, PageParam pageParam){
        Page<FortuneActiviConfigEntity> result = new Page<FortuneActiviConfigEntity>(pageParam.getPage(), pageParam.getLimit());
        Wrapper<FortuneActiviConfigEntity> entityWrapper = new EntityWrapper<FortuneActiviConfigEntity>(fortuneActiviConfigEntity);
        entityWrapper.orderBy(pageParam.getSort(), pageParam.getDirection());
        fortuneActiviConfigEntity.selectPage(result, entityWrapper);
        return R.ok().put("page", new PageUtils(result.getRecords(), result.getTotal(), result.getSize(),
                result.getCurrent(), result.getPages()));
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("fortuneactiviconfig:fortuneactiviconfig:info")
    public R info(@PathVariable("id") Long id){
		FortuneActiviConfigEntity fortuneActiviConfig = fortuneActiviConfigService.selectById(id);

        return R.ok().put("fortuneActiviConfig", fortuneActiviConfig);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("fortuneactiviconfig:fortuneactiviconfig:save")
    public R save(@RequestBody FortuneActiviConfigEntity fortuneActiviConfig){
		fortuneActiviConfigService.insert(fortuneActiviConfig);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("fortuneactiviconfig:fortuneactiviconfig:update")
    public R update(@RequestBody FortuneActiviConfigEntity fortuneActiviConfig){
		fortuneActiviConfigService.updateById(fortuneActiviConfig);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("fortuneactiviconfig:fortuneactiviconfig:delete")
    public R delete(@RequestBody Long[] ids){
		fortuneActiviConfigService.deleteBatchIds(Arrays.asList(ids));

        return R.ok();
    }

}

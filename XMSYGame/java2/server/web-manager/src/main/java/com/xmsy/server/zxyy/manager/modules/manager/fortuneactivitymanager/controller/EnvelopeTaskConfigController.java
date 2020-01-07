package com.xmsy.server.zxyy.manager.modules.manager.fortuneactivitymanager.controller;

import java.util.Arrays;
import java.util.List;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.xmsy.common.define.page.PageParam;
import com.xmsy.server.zxyy.manager.common.utils.PageUtils;
import com.xmsy.server.zxyy.manager.common.utils.R;
import com.xmsy.server.zxyy.manager.modules.manager.fortuneactivitymanager.entity.EnvelopeTaskConfigEntity;
import com.xmsy.server.zxyy.manager.modules.manager.fortuneactivitymanager.service.EnvelopeTaskConfigService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;




/**
 * 红包任务后台配置
 *
 * @author ahui
 * @email sunlightcs@gmail.com
 * @date 2019-12-12 11:38:04
 */
@RestController
@RequestMapping("envelopetaskconfig/envelopetaskconfig")
public class EnvelopeTaskConfigController {
    @Autowired
    private EnvelopeTaskConfigService envelopeTaskConfigService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("envelopetaskconfig:envelopetaskconfig:list")
    public R list(EnvelopeTaskConfigEntity envelopeTaskConfigEntity, PageParam pageParam){
        Page<EnvelopeTaskConfigEntity> result = new Page<EnvelopeTaskConfigEntity>(pageParam.getPage(), pageParam.getLimit());
        Wrapper<EnvelopeTaskConfigEntity> entityWrapper = new EntityWrapper<EnvelopeTaskConfigEntity>(envelopeTaskConfigEntity);
        entityWrapper.orderBy(pageParam.getSort(), pageParam.getDirection());
        envelopeTaskConfigEntity.selectPage(result, entityWrapper);
        return R.ok().put("page", new PageUtils(result.getRecords(), result.getTotal(), result.getSize(),
                result.getCurrent(), result.getPages()));
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("envelopetaskconfig:envelopetaskconfig:info")
    public R info(@PathVariable("id") Long id){
		EnvelopeTaskConfigEntity envelopeTaskConfig = envelopeTaskConfigService.selectById(id);

        return R.ok().put("envelopeTaskConfig", envelopeTaskConfig);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("envelopetaskconfig:envelopetaskconfig:save")
    public R save(@RequestBody EnvelopeTaskConfigEntity envelopeTaskConfig){
		envelopeTaskConfigService.insert(envelopeTaskConfig);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("envelopetaskconfig:envelopetaskconfig:update")
    public R update(@RequestBody EnvelopeTaskConfigEntity envelopeTaskConfig){
		envelopeTaskConfigService.updateById(envelopeTaskConfig);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("envelopetaskconfig:envelopetaskconfig:delete")
    public R delete(@RequestBody Long[] ids){
        List<Long> longs = Arrays.asList(ids);
        for (Long id : longs) {
            envelopeTaskConfigService.deleteById(id);
        }


        return R.ok();
    }

}

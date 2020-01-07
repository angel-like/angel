package com.xmsy.server.zxyy.calculate.modules.manager.fortuneactivitymanager.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.xmsy.common.define.page.PageParam;
import com.xmsy.server.zxyy.calculate.common.utils.PageUtils;
import com.xmsy.server.zxyy.calculate.common.utils.R;
import com.xmsy.server.zxyy.calculate.modules.manager.fortuneactivitymanager.entity.EnvelopeRecordEntity;
import com.xmsy.server.zxyy.calculate.modules.manager.fortuneactivitymanager.service.EnvelopeRecordService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;


/**
 * 天降财神红包记录
 *
 * @author ahui
 * @email sunlightcs@gmail.com
 * @date 2019-12-12 11:38:04
 */
@RestController
@RequestMapping("enveloperecord/enveloperecord")
public class EnvelopeRecordController {
    @Autowired
    private EnvelopeRecordService envelopeRecordService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("enveloperecord:enveloperecord:list")
    public R list(EnvelopeRecordEntity envelopeRecordEntity, PageParam pageParam){
        Page<EnvelopeRecordEntity> result = new Page<EnvelopeRecordEntity>(pageParam.getPage(), pageParam.getLimit());
        Wrapper<EnvelopeRecordEntity> entityWrapper = new EntityWrapper<EnvelopeRecordEntity>(envelopeRecordEntity);
        entityWrapper.orderBy(pageParam.getSort(), pageParam.getDirection());
        envelopeRecordEntity.selectPage(result, entityWrapper);
        return R.ok().put("page", new PageUtils(result.getRecords(), result.getTotal(), result.getSize(),
                result.getCurrent(), result.getPages()));
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("enveloperecord:enveloperecord:info")
    public R info(@PathVariable("id") Long id){
		EnvelopeRecordEntity envelopeRecord = envelopeRecordService.selectById(id);

        return R.ok().put("envelopeRecord", envelopeRecord);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("enveloperecord:enveloperecord:save")
    public R save(@RequestBody EnvelopeRecordEntity envelopeRecord){
		envelopeRecordService.insert(envelopeRecord);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("enveloperecord:enveloperecord:update")
    public R update(@RequestBody EnvelopeRecordEntity envelopeRecord){
		envelopeRecordService.updateById(envelopeRecord);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("enveloperecord:enveloperecord:delete")
    public R delete(@RequestBody Long[] ids){
		envelopeRecordService.deleteBatchIds(Arrays.asList(ids));

        return R.ok();
    }

}

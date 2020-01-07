package com.xmsy.server.zxyy.webhome.modules.manager.adchannelconfig.controller;

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
import com.xmsy.server.zxyy.webhome.modules.manager.adchannelconfig.entity.AdChannelConfigEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.adchannelconfig.service.AdChannelConfigService;



/**
 * 渠道配置表
 *
 * @author axiong
 * @email xxxxx
 * @date 2019-07-02 17:05:09
 */
@RestController
@RequestMapping("adchannelconfig/adchannelconfig")
public class AdChannelConfigController {
    @Autowired
    private AdChannelConfigService adChannelConfigService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("adchannelconfig:adchannelconfig:list")
    public R list(AdChannelConfigEntity adchannelconfig, PageParam pageParam){
        Page<AdChannelConfigEntity> result = new Page<AdChannelConfigEntity>(pageParam.getPage(), pageParam.getLimit());
		Wrapper<AdChannelConfigEntity> entityWrapper = new EntityWrapper<AdChannelConfigEntity>(adchannelconfig);
		entityWrapper.orderBy(pageParam.getSort(), pageParam.getDirection());
		adchannelconfig.selectPage(result, entityWrapper);
		return R.ok().put("page", new PageUtils(result.getRecords(), result.getTotal(), result.getSize(),
				result.getCurrent(), result.getPages()));
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("adchannelconfig:adchannelconfig:info")
    public R info(@PathVariable("id") Long id){
			AdChannelConfigEntity adChannelConfig = adChannelConfigService.selectById(id);
        return R.ok().put("adchannelconfig", adChannelConfig);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("adchannelconfig:adchannelconfig:save")
    public R save(@RequestBody AdChannelConfigEntity adchannelconfig){
			adChannelConfigService.insert(adchannelconfig);
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("adchannelconfig:adchannelconfig:update")
    public R update(@RequestBody AdChannelConfigEntity adchannelconfig){
			adChannelConfigService.updateById(adchannelconfig);
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("adchannelconfig:adchannelconfig:delete")
    public R delete(@RequestBody Long[] ids){
    for (Long id : ids) {
			adChannelConfigService.deleteById(id);
	}
        return R.ok();
    }

}

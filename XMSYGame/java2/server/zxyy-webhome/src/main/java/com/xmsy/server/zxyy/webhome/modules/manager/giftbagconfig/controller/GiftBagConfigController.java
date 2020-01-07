package com.xmsy.server.zxyy.webhome.modules.manager.giftbagconfig.controller;

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
import com.xmsy.server.zxyy.webhome.modules.manager.giftbagconfig.entity.GiftBagConfigEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.giftbagconfig.service.GiftBagConfigService;



/**
 * 礼包配置表
 *
 * @author axiong
 * @email xxxxx
 * @date 2019-07-15 10:13:06
 */
@RestController
@RequestMapping("giftbagconfig/giftbagconfig")
public class GiftBagConfigController {
    @Autowired
    private GiftBagConfigService giftBagConfigService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("giftbagconfig:giftbagconfig:list")
    public R list(GiftBagConfigEntity giftbagconfig, PageParam pageParam){
        Page<GiftBagConfigEntity> result = new Page<GiftBagConfigEntity>(pageParam.getPage(), pageParam.getLimit());
		Wrapper<GiftBagConfigEntity> entityWrapper = new EntityWrapper<GiftBagConfigEntity>(giftbagconfig);
		entityWrapper.orderBy(pageParam.getSort(), pageParam.getDirection());
		giftbagconfig.selectPage(result, entityWrapper);
		return R.ok().put("page", new PageUtils(result.getRecords(), result.getTotal(), result.getSize(),
				result.getCurrent(), result.getPages()));
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("giftbagconfig:giftbagconfig:info")
    public R info(@PathVariable("id") Long id){
			GiftBagConfigEntity giftBagConfig = giftBagConfigService.selectById(id);
        return R.ok().put("giftbagconfig", giftBagConfig);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("giftbagconfig:giftbagconfig:save")
    public R save(@RequestBody GiftBagConfigEntity giftbagconfig){
			giftBagConfigService.insert(giftbagconfig);
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("giftbagconfig:giftbagconfig:update")
    public R update(@RequestBody GiftBagConfigEntity giftbagconfig){
			giftBagConfigService.updateById(giftbagconfig);
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("giftbagconfig:giftbagconfig:delete")
    public R delete(@RequestBody Long[] ids){
    for (Long id : ids) {
			giftBagConfigService.deleteById(id);
	}
        return R.ok();
    }

}

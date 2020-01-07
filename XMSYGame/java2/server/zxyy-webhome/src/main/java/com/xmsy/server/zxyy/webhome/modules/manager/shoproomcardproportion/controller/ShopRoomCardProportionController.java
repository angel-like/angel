package com.xmsy.server.zxyy.webhome.modules.manager.shoproomcardproportion.controller;

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
import com.xmsy.server.zxyy.webhome.modules.manager.shoproomcardproportion.entity.ShopRoomCardProportionEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.shoproomcardproportion.service.ShopRoomCardProportionService;



/**
 * 金币兑换房卡比例表
 *
 * @author axiong
 * @email xxxxx
 * @date 2019-07-05 17:00:34
 */
@RestController
@RequestMapping("shoproomcardproportion/shoproomcardproportion")
public class ShopRoomCardProportionController {
    @Autowired
    private ShopRoomCardProportionService shopRoomCardProportionService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("shoproomcardproportion:shoproomcardproportion:list")
    public R list(ShopRoomCardProportionEntity shoproomcardproportion, PageParam pageParam){
        Page<ShopRoomCardProportionEntity> result = new Page<ShopRoomCardProportionEntity>(pageParam.getPage(), pageParam.getLimit());
		Wrapper<ShopRoomCardProportionEntity> entityWrapper = new EntityWrapper<ShopRoomCardProportionEntity>(shoproomcardproportion);
		entityWrapper.orderBy(pageParam.getSort(), pageParam.getDirection());
		shoproomcardproportion.selectPage(result, entityWrapper);
		return R.ok().put("page", new PageUtils(result.getRecords(), result.getTotal(), result.getSize(),
				result.getCurrent(), result.getPages()));
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("shoproomcardproportion:shoproomcardproportion:info")
    public R info(@PathVariable("id") Long id){
			ShopRoomCardProportionEntity shopRoomCardProportion = shopRoomCardProportionService.selectById(id);
        return R.ok().put("shoproomcardproportion", shopRoomCardProportion);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("shoproomcardproportion:shoproomcardproportion:save")
    public R save(@RequestBody ShopRoomCardProportionEntity shoproomcardproportion){
			shopRoomCardProportionService.insert(shoproomcardproportion);
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("shoproomcardproportion:shoproomcardproportion:update")
    public R update(@RequestBody ShopRoomCardProportionEntity shoproomcardproportion){
			shopRoomCardProportionService.updateById(shoproomcardproportion);
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("shoproomcardproportion:shoproomcardproportion:delete")
    public R delete(@RequestBody Long[] ids){
    for (Long id : ids) {
			shopRoomCardProportionService.deleteById(id);
	}
        return R.ok();
    }

}

package com.xmsy.server.zxyy.manager.modules.manager.shoproomcardproportion.controller;

import java.util.List;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.xmsy.common.define.page.PageParam;
import com.xmsy.server.zxyy.manager.common.utils.PageUtils;
import com.xmsy.server.zxyy.manager.common.utils.R;
import com.xmsy.server.zxyy.manager.constant.SysConstant;
import com.xmsy.server.zxyy.manager.modules.manager.shoproomcardproportion.entity.ShopRoomCardProportionEntity;
import com.xmsy.server.zxyy.manager.modules.manager.shoproomcardproportion.service.ShopRoomCardProportionService;
import com.xmsy.server.zxyy.manager.modules.manager.sysprop.entity.SysPropEntity;
import com.xmsy.server.zxyy.manager.modules.manager.sysprop.service.SysPropService;



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
    
    @Autowired
    private SysPropService sysPropService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("shoproomcardproportion:shoproomcardproportion:list")
    public R list(ShopRoomCardProportionEntity shopRoomCardProportionEntity, PageParam pageParam){
        Page<ShopRoomCardProportionEntity> page = new Page<ShopRoomCardProportionEntity>(pageParam.getPage(), pageParam.getLimit());
        List<ShopRoomCardProportionEntity> list = shopRoomCardProportionService.findShopRoomCardProportion(page, shopRoomCardProportionEntity);
        page.setRecords(list);
        List<ShopRoomCardProportionEntity> lists=page.getRecords();
		if(!CollectionUtils.isEmpty(lists)) {
			for(ShopRoomCardProportionEntity entity:lists) {
				if(entity!=null) {
					if(entity.getTargetProp()!=null&&entity.getTargetProp()!=0) {
						SysPropEntity sysPropEntity=sysPropService.selectOne(new EntityWrapper<SysPropEntity>(null).eq("id", entity.getTargetProp()));
						if(sysPropEntity!=null) {
							entity.setTargetPropName(sysPropEntity.getName());
						}
					}
				}
			}
		}	
		return R.ok().put("page", new PageUtils(page.getRecords(), page.getTotal(), page.getSize(),
				page.getCurrent(), page.getPages()));
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
    
    /**
     * 启用
     */
    @RequestMapping("/openEnable/{id}")
    @RequiresPermissions("shoproomcardproportion:shoproomcardproportion:openEnable")
    public R openEnable(@PathVariable("id") Long id){
    	ShopRoomCardProportionEntity shoproomcardproportion = new ShopRoomCardProportionEntity();
    	shoproomcardproportion.setId(id);
    	shoproomcardproportion.setStatus(SysConstant.ENABLE_TRUE);
    	shopRoomCardProportionService.updateById(shoproomcardproportion);
        return R.ok();
    }
    /**
     * 禁用
     */
    @RequestMapping("/closeEnable/{id}")
    @RequiresPermissions("shoproomcardproportion:shoproomcardproportion:closeEnable")
    public R closeEnable(@PathVariable("id") Long id){
    	ShopRoomCardProportionEntity shoproomcardproportion = new ShopRoomCardProportionEntity();
    	shoproomcardproportion.setId(id);
    	shoproomcardproportion.setStatus(SysConstant.ENABLE_FALSE);
    	shopRoomCardProportionService.updateById(shoproomcardproportion);
        return R.ok();
    }

}

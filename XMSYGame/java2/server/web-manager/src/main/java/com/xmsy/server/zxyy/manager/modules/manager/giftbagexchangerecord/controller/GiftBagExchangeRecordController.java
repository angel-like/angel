package com.xmsy.server.zxyy.manager.modules.manager.giftbagexchangerecord.controller;

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
import com.xmsy.server.zxyy.manager.modules.manager.giftbagexchangerecord.entity.GiftBagExchangeRecordEntity;
import com.xmsy.server.zxyy.manager.modules.manager.giftbagexchangerecord.service.GiftBagExchangeRecordService;



/**
 * 礼包兑换记录表
 *
 * @author axiong
 * @email xxxxx
 * @date 2019-07-18 15:14:51
 */
@RestController
@RequestMapping("giftbagexchangerecord/giftbagexchangerecord")
public class GiftBagExchangeRecordController {
    @Autowired
    private GiftBagExchangeRecordService giftBagExchangeRecordService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("giftbagexchangerecord:giftbagexchangerecord:list")
    public R list(GiftBagExchangeRecordEntity giftbagexchangerecord, PageParam pageParam){
        Page<GiftBagExchangeRecordEntity> result = new Page<GiftBagExchangeRecordEntity>(pageParam.getPage(), pageParam.getLimit());
		Wrapper<GiftBagExchangeRecordEntity> entityWrapper = new EntityWrapper<GiftBagExchangeRecordEntity>(giftbagexchangerecord);
		entityWrapper.orderBy("create_time", false);
		entityWrapper.orderBy(pageParam.getSort(), pageParam.getDirection());
		if(giftbagexchangerecord.getSTime()!=null) {
			entityWrapper.gt("exchange_time", giftbagexchangerecord.getSTime());
			if(giftbagexchangerecord.getETime()!=null) {
				entityWrapper.le("exchange_time", giftbagexchangerecord.getETime());
			}
		}
		giftbagexchangerecord.selectPage(result, entityWrapper);
		return R.ok().put("page", new PageUtils(result.getRecords(), result.getTotal(), result.getSize(),
				result.getCurrent(), result.getPages()));
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("giftbagexchangerecord:giftbagexchangerecord:info")
    public R info(@PathVariable("id") Long id){
			GiftBagExchangeRecordEntity giftBagExchangeRecord = giftBagExchangeRecordService.selectById(id);
        return R.ok().put("giftbagexchangerecord", giftBagExchangeRecord);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("giftbagexchangerecord:giftbagexchangerecord:save")
    public R save(@RequestBody GiftBagExchangeRecordEntity giftbagexchangerecord){
			giftBagExchangeRecordService.insert(giftbagexchangerecord);
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("giftbagexchangerecord:giftbagexchangerecord:update")
    public R update(@RequestBody GiftBagExchangeRecordEntity giftbagexchangerecord){
			giftBagExchangeRecordService.updateById(giftbagexchangerecord);
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("giftbagexchangerecord:giftbagexchangerecord:delete")
    public R delete(@RequestBody Long[] ids){
    for (Long id : ids) {
			giftBagExchangeRecordService.deleteById(id);
	}
        return R.ok();
    }

}

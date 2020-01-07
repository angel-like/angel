package com.xmsy.server.zxyy.webhome.modules.manager.signrewardcontinuouseveryday.controller;

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
import com.xmsy.server.zxyy.webhome.modules.manager.signrewardcontinuouseveryday.entity.SignRewardContinuousEveryDayEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.signrewardcontinuouseveryday.service.SignRewardContinuousEveryDayService;



/**
 * 每天签到奖励
 *
 * @author aleng
 * @email xxxxx
 * @date 2019-05-13 15:50:02
 */
@RestController
@RequestMapping("signrewardcontinuouseveryday/signrewardcontinuouseveryday")
public class SignRewardContinuousEveryDayController {
    @Autowired
    private SignRewardContinuousEveryDayService signRewardContinuousEveryDayService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("signrewardcontinuouseveryday:signrewardcontinuouseveryday:list")
    public R list(SignRewardContinuousEveryDayEntity signrewardcontinuouseveryday, PageParam pageParam){
        Page<SignRewardContinuousEveryDayEntity> result = new Page<SignRewardContinuousEveryDayEntity>(pageParam.getPage(), pageParam.getLimit());
		Wrapper<SignRewardContinuousEveryDayEntity> entityWrapper = new EntityWrapper<SignRewardContinuousEveryDayEntity>(signrewardcontinuouseveryday);
		entityWrapper.orderBy(pageParam.getSort(), pageParam.getDirection());
		signrewardcontinuouseveryday.selectPage(result, entityWrapper);
		return R.ok().put("page", new PageUtils(result.getRecords(), result.getTotal(), result.getSize(),
				result.getCurrent(), result.getPages()));
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("signrewardcontinuouseveryday:signrewardcontinuouseveryday:info")
    public R info(@PathVariable("id") Long id){
			SignRewardContinuousEveryDayEntity signRewardContinuousEveryDay = signRewardContinuousEveryDayService.selectById(id);
        return R.ok().put("signrewardcontinuouseveryday", signRewardContinuousEveryDay);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("signrewardcontinuouseveryday:signrewardcontinuouseveryday:save")
    public R save(@RequestBody SignRewardContinuousEveryDayEntity signrewardcontinuouseveryday){
			signRewardContinuousEveryDayService.insert(signrewardcontinuouseveryday);
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("signrewardcontinuouseveryday:signrewardcontinuouseveryday:update")
    public R update(@RequestBody SignRewardContinuousEveryDayEntity signrewardcontinuouseveryday){
			signRewardContinuousEveryDayService.updateById(signrewardcontinuouseveryday);
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("signrewardcontinuouseveryday:signrewardcontinuouseveryday:delete")
    public R delete(@RequestBody Long[] ids){
    for (Long id : ids) {
			signRewardContinuousEveryDayService.deleteById(id);
	}
        return R.ok();
    }

}

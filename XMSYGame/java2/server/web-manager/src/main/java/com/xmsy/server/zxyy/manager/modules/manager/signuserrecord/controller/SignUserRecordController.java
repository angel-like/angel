package com.xmsy.server.zxyy.manager.modules.manager.signuserrecord.controller;

import java.util.List;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.toolkit.CollectionUtils;
import com.xmsy.common.define.page.PageParam;
import com.xmsy.server.zxyy.manager.cache.LocalContentCache;
import com.xmsy.server.zxyy.manager.common.utils.PageUtils;
import com.xmsy.server.zxyy.manager.common.utils.R;
import com.xmsy.server.zxyy.manager.modules.manager.signrewardcontinuouseveryday.entity.SignRewardContinuousEveryDayEntity;
import com.xmsy.server.zxyy.manager.modules.manager.signrewardcontinuouseveryday.service.SignRewardContinuousEveryDayService;
import com.xmsy.server.zxyy.manager.modules.manager.signuserrecord.entity.SignUserRecordEntity;
import com.xmsy.server.zxyy.manager.modules.manager.signuserrecord.service.SignUserRecordService;



/**
 * 用户签到记录表
 *
 * @author aleng
 * @email xxxxx
 * @date 2019-05-13 15:50:02
 */
@RestController
@RequestMapping("signuserrecord/signuserrecord")
public class SignUserRecordController {
    @Autowired
    private SignUserRecordService signUserRecordService;
    @Autowired
    private SignRewardContinuousEveryDayService signRewardContinuousEveryDayService;
    @Autowired
    private LocalContentCache localContentCache;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("signuserrecord:signuserrecord:list")
    public R list(SignUserRecordEntity signuserrecord, PageParam pageParam){
        Page<SignUserRecordEntity> result = new Page<SignUserRecordEntity>(pageParam.getPage(), pageParam.getLimit());
		Wrapper<SignUserRecordEntity> entityWrapper = new EntityWrapper<SignUserRecordEntity>(signuserrecord);
		entityWrapper.orderBy("create_time", false);
		entityWrapper.orderBy(pageParam.getSort(), pageParam.getDirection());
		signuserrecord.selectPage(result, entityWrapper);
		List<SignUserRecordEntity> list = result.getRecords();
		if (!CollectionUtils.isEmpty(list)) {
			for (SignUserRecordEntity entity : list) {
				if (entity != null) {
					if (entity.getDayId()!= null && entity.getDayId() != 0) {
						SignRewardContinuousEveryDayEntity signRewardContinuousEveryDayEntity = signRewardContinuousEveryDayService.selectOne(
								new EntityWrapper<SignRewardContinuousEveryDayEntity>(null).
								eq("id", entity.getDayId()));
                        if(signRewardContinuousEveryDayEntity!=null) {
                            Long vipId = signRewardContinuousEveryDayEntity.getVipId();
							String vipNameAndDayName = (localContentCache.getVipName(vipId)==null?"VIP0":localContentCache.getVipName(vipId))+" "+signRewardContinuousEveryDayEntity.getDayName();
							entity.setVipNameAndDayName(vipNameAndDayName);
						}
					}
				}
			}
		}
		return R.ok().put("page", new PageUtils(result.getRecords(), result.getTotal(), result.getSize(),
				result.getCurrent(), result.getPages()));
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("signuserrecord:signuserrecord:info")
    public R info(@PathVariable("id") Long id){
			SignUserRecordEntity signUserRecord = signUserRecordService.selectById(id);
        return R.ok().put("signuserrecord", signUserRecord);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("signuserrecord:signuserrecord:save")
    public R save(@RequestBody SignUserRecordEntity signuserrecord){
			signUserRecordService.insert(signuserrecord);
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("signuserrecord:signuserrecord:update")
    public R update(@RequestBody SignUserRecordEntity signuserrecord){
			signUserRecordService.updateById(signuserrecord);
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("signuserrecord:signuserrecord:delete")
    public R delete(@RequestBody Long[] ids){
    for (Long id : ids) {
			signUserRecordService.deleteById(id);
	}
        return R.ok();
    }

}

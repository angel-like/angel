package com.xmsy.server.zxyy.webhome.modules.manager.luckyuserrecord.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.xmsy.common.define.page.PageParam;

import com.xmsy.server.zxyy.webhome.common.utils.PageUtils;
import com.xmsy.server.zxyy.webhome.common.utils.R;
import com.xmsy.server.zxyy.webhome.modules.manager.luckyuserrecord.entity.LuckyUserRecordEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.luckyuserrecord.service.LuckyUserRecordService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * 用户抽奖记录表
 *
 * @author ayang
 * @email xxxxx
 * @date 2019-11-22 17:55:00
 */
@RestController
@RequestMapping("luckyuserrecord/luckyuserrecord")
public class LuckyUserRecordController {
    @Autowired
    private LuckyUserRecordService luckyUserRecordService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("luckyuserrecord:luckyuserrecord:list")
    public R list(LuckyUserRecordEntity luckyuserrecord, PageParam pageParam){
        Page<LuckyUserRecordEntity> result = new Page<LuckyUserRecordEntity>(pageParam.getPage(), pageParam.getLimit());
		Wrapper<LuckyUserRecordEntity> entityWrapper = new EntityWrapper<LuckyUserRecordEntity>(luckyuserrecord);
		entityWrapper.orderBy(pageParam.getSort(), pageParam.getDirection());
		luckyuserrecord.selectPage(result, entityWrapper);
		return R.ok().put("page", new PageUtils(result.getRecords(), result.getTotal(), result.getSize(),
				result.getCurrent(), result.getPages()));
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("luckyuserrecord:luckyuserrecord:info")
    public R info(@PathVariable("id") Long id){
			LuckyUserRecordEntity luckyUserRecord = luckyUserRecordService.selectById(id);
        return R.ok().put("luckyuserrecord", luckyUserRecord);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("luckyuserrecord:luckyuserrecord:save")
    public R save(@RequestBody LuckyUserRecordEntity luckyuserrecord){
			luckyUserRecordService.insert(luckyuserrecord);
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("luckyuserrecord:luckyuserrecord:update")
    public R update(@RequestBody LuckyUserRecordEntity luckyuserrecord){
			luckyUserRecordService.updateById(luckyuserrecord);
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("luckyuserrecord:luckyuserrecord:delete")
    public R delete(@RequestBody Long[] ids){
    for (Long id : ids) {
			luckyUserRecordService.deleteById(id);
	}
        return R.ok();
    }

}

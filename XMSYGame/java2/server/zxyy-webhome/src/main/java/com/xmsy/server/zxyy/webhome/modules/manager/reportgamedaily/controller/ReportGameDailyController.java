package com.xmsy.server.zxyy.webhome.modules.manager.reportgamedaily.controller;

import com.xmsy.common.define.page.PageParam;

import java.util.Arrays;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;

import com.xmsy.server.zxyy.webhome.modules.manager.reportgamedaily.entity.ReportGameDailyEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.reportgamedaily.service.ReportGameDailyService;
import com.xmsy.server.zxyy.webhome.common.utils.PageUtils;
import com.xmsy.server.zxyy.webhome.common.utils.R;



/**
 * 每日游戏投入产出报表
 *
 * @author xiaoling
 * @email xxxxx
 * @date 2019-05-11 09:26:16
 */
@RestController
@RequestMapping("reportgamedaily/reportgamedaily")
public class ReportGameDailyController {
    @Autowired
    private ReportGameDailyService reportGameDailyService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("reportgamedaily:reportgamedaily:list")
    public R list(ReportGameDailyEntity reportgamedaily, PageParam pageParam){
        Page<ReportGameDailyEntity> result = new Page<ReportGameDailyEntity>(pageParam.getPage(), pageParam.getLimit());
		Wrapper<ReportGameDailyEntity> entityWrapper = new EntityWrapper<ReportGameDailyEntity>(reportgamedaily);
		if(!StringUtils.isBlank(pageParam.getSort())) {
			if(pageParam.getDirection()) {
				entityWrapper.orderAsc(Arrays.asList(pageParam.getSort().split(",")));
			}else {
				entityWrapper.orderDesc(Arrays.asList(pageParam.getSort().split(",")));
			}
			
			
		}
		reportgamedaily.selectPage(result, entityWrapper);
		return R.ok().put("page", new PageUtils(result.getRecords(), result.getTotal(), result.getSize(),
				result.getCurrent(), result.getPages()));
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("reportgamedaily:reportgamedaily:info")
    public R info(@PathVariable("id") Long id){
			ReportGameDailyEntity reportGameDaily = reportGameDailyService.selectById(id);
        return R.ok().put("reportgamedaily", reportGameDaily);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("reportgamedaily:reportgamedaily:save")
    public R save(@RequestBody ReportGameDailyEntity reportgamedaily){
			reportGameDailyService.insert(reportgamedaily);
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("reportgamedaily:reportgamedaily:update")
    public R update(@RequestBody ReportGameDailyEntity reportgamedaily){
			reportGameDailyService.updateById(reportgamedaily);
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("reportgamedaily:reportgamedaily:delete")
    public R delete(@RequestBody Long[] ids){
    for (Long id : ids) {
			reportGameDailyService.deleteById(id);
	}
        return R.ok();
    }

}

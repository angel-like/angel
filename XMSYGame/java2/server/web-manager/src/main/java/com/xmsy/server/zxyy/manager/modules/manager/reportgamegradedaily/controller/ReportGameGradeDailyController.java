package com.xmsy.server.zxyy.manager.modules.manager.reportgamegradedaily.controller;

import com.xmsy.common.define.page.PageParam;
import com.xmsy.server.zxyy.manager.common.utils.PageUtils;
import com.xmsy.server.zxyy.manager.common.utils.R;
import com.xmsy.server.zxyy.manager.modules.manager.reportgamegradedaily.entity.ReportGameGradeDailyEntity;
import com.xmsy.server.zxyy.manager.modules.manager.reportgamegradedaily.service.ReportGameGradeDailyService;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.druid.util.StringUtils;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;



/**
 * 每日游戏投入产出报表
 *
 * @author xiaoling
 * @email xxxxx
 * @date 2019-05-11 09:26:16
 */
@RestController
@RequestMapping("reportgamegradedaily/reportgamegradedaily")
public class ReportGameGradeDailyController {
    @Autowired
    private ReportGameGradeDailyService reportGameGradeDailyService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("reportgamegradedaily:reportgamegradedaily:list")
    public R list(ReportGameGradeDailyEntity reportgamedaily, PageParam pageParam){
        Page<ReportGameGradeDailyEntity> result = new Page<ReportGameGradeDailyEntity>(pageParam.getPage(), pageParam.getLimit());
		Wrapper<ReportGameGradeDailyEntity> entityWrapper = new EntityWrapper<ReportGameGradeDailyEntity>(reportgamedaily)
				.ge(!StringUtils.isEmpty(reportgamedaily.getStartTime()), "create_time",
						reportgamedaily.getStartTime())
						.le(!StringUtils.isEmpty(reportgamedaily.getEndTime()), "create_time",
								reportgamedaily.getEndTime());
		entityWrapper.orderBy(pageParam.getSort(), pageParam.getDirection());
		reportgamedaily.selectPage(result, entityWrapper);
		return R.ok().put("page", new PageUtils(result.getRecords(), result.getTotal(), result.getSize(),
				result.getCurrent(), result.getPages()));
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("reportgamegradedaily:reportgamegradedaily:info")
    public R info(@PathVariable("id") Long id){
    	ReportGameGradeDailyEntity reportGameGradeDaily = reportGameGradeDailyService.selectById(id);
        return R.ok().put("reportGameGradeDaily", reportGameGradeDaily);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("reportgamegradedaily:reportgamegradedaily:save")
    public R save(@RequestBody ReportGameGradeDailyEntity reportGameGradeDaily){
    	reportGameGradeDailyService.insert(reportGameGradeDaily);
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("reportgamegradedaily:reportgamegradedaily:update")
    public R update(@RequestBody ReportGameGradeDailyEntity reportGameGradeDaily){
    	reportGameGradeDailyService.updateById(reportGameGradeDaily);
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("reportgamegradedaily:reportgamegradedaily:delete")
    public R delete(@RequestBody Long[] ids){
    for (Long id : ids) {
    	reportGameGradeDailyService.deleteById(id);
	}
        return R.ok();
    }

}

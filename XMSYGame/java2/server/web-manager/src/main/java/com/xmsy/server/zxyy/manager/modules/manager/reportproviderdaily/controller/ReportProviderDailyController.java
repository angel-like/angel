package com.xmsy.server.zxyy.manager.modules.manager.reportproviderdaily.controller;


import com.alibaba.druid.util.StringUtils;
import com.xmsy.common.define.page.PageParam;
import com.xmsy.server.zxyy.manager.common.utils.MathUtil;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;

import com.xmsy.server.zxyy.manager.modules.manager.reportproviderdaily.entity.ReportProviderDailyEntity;
import com.xmsy.server.zxyy.manager.modules.manager.reportproviderdaily.service.ReportProviderDailyService;
import com.xmsy.server.zxyy.manager.common.utils.PageUtils;
import com.xmsy.server.zxyy.manager.common.utils.R;

import java.math.BigDecimal;
import java.util.Arrays;


/**
 * 每日平台投入产出报表
 *
 * @author ayang
 * @email xxxxx
 * @date 2020-01-03 11:16:44
 */
@RestController
@RequestMapping("reportproviderdaily/reportproviderdaily")
public class ReportProviderDailyController {
    @Autowired
    private ReportProviderDailyService reportProviderDailyService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("reportproviderdaily:reportproviderdaily:list")
    public R list(ReportProviderDailyEntity reportproviderdaily, PageParam pageParam) {
        Page<ReportProviderDailyEntity> result = new Page<ReportProviderDailyEntity>(pageParam.getPage(), pageParam.getLimit());
        Wrapper<ReportProviderDailyEntity> entityWrapper = new EntityWrapper<ReportProviderDailyEntity>(reportproviderdaily)
                .ge(!StringUtils.isEmpty(reportproviderdaily.getStartTime()), "create_time",
                        reportproviderdaily.getStartTime())
                .le(!StringUtils.isEmpty(reportproviderdaily.getEndTime()), "create_time",
                        reportproviderdaily.getEndTime());
        if (!org.apache.commons.lang3.StringUtils.isBlank(pageParam.getSort())) {
            if (pageParam.getDirection()) {
                entityWrapper.orderAsc(Arrays.asList(pageParam.getSort().split(",")));
            } else {
                entityWrapper.orderDesc(Arrays.asList(pageParam.getSort().split(",")));
            }


        }
        reportproviderdaily.selectPage(result, entityWrapper);
        Integer participateNum = 0;
        BigDecimal investmentTotal = BigDecimal.ZERO;
        BigDecimal outputTotal = BigDecimal.ZERO;
        BigDecimal winTotal = BigDecimal.ZERO;
        for (ReportProviderDailyEntity data : result.getRecords()) {
            participateNum = participateNum + data.getParticipateNum();
            investmentTotal = investmentTotal.add(MathUtil.getBigDecimal(data.getInvestmentTotal()));
            outputTotal = outputTotal.add(MathUtil.getBigDecimal(data.getOutputTotal()));
            winTotal = winTotal.add(MathUtil.getBigDecimal(data.getWinTotal()));

        }
        return R.ok().put("page", new PageUtils(result.getRecords(), result.getTotal(), result.getSize(),
                result.getCurrent(), result.getPages()))
                .put("participateNum", participateNum)
                .put("investmentTotal", investmentTotal)
                .put("outputTotal", outputTotal)
                .put("winTotal", winTotal);

    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("reportproviderdaily:reportproviderdaily:info")
    public R info(@PathVariable("id") Long id) {
        ReportProviderDailyEntity reportProviderDaily = reportProviderDailyService.selectById(id);
        return R.ok().put("reportproviderdaily", reportProviderDaily);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("reportproviderdaily:reportproviderdaily:save")
    public R save(@RequestBody ReportProviderDailyEntity reportproviderdaily) {
        reportProviderDailyService.insert(reportproviderdaily);
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("reportproviderdaily:reportproviderdaily:update")
    public R update(@RequestBody ReportProviderDailyEntity reportproviderdaily) {
        reportProviderDailyService.updateById(reportproviderdaily);
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("reportproviderdaily:reportproviderdaily:delete")
    public R delete(@RequestBody Long[] ids) {
        for (Long id : ids) {
            reportProviderDailyService.deleteById(id);
        }
        return R.ok();
    }

}

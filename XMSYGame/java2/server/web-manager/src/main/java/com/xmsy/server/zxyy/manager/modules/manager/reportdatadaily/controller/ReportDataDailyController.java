package com.xmsy.server.zxyy.manager.modules.manager.reportdatadaily.controller;

import com.xmsy.common.define.page.PageParam;
import com.xmsy.server.zxyy.manager.common.utils.PageUtils;
import com.xmsy.server.zxyy.manager.common.utils.R;
import com.xmsy.server.zxyy.manager.modules.manager.giftbagexchangerecord.entity.GiftBagExchangeRecordParam;
import com.xmsy.server.zxyy.manager.modules.manager.giftbagexchangerecord.service.GiftBagExchangeRecordService;
import com.xmsy.server.zxyy.manager.modules.manager.reportdatadaily.entity.ReportDataDailyEntity;
import com.xmsy.server.zxyy.manager.modules.manager.reportdatadaily.service.ReportDataDailyService;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

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



/**
 * 每日平台数据报表
 *
 * @author xiaoling
 * @email xxxxx
 * @date 2019-05-11 09:26:16
 */
@RestController
@RequestMapping("reportdatadaily/reportdatadaily")
public class ReportDataDailyController {
    @Autowired
    private ReportDataDailyService reportDataDailyService;
    
    @Autowired
    private GiftBagExchangeRecordService giftBagExchangeRecordService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("reportdatadaily:reportdatadaily:list")
    public R list(ReportDataDailyEntity reportdatadaily, PageParam pageParam){
        Page<ReportDataDailyEntity> result = new Page<ReportDataDailyEntity>(pageParam.getPage(), pageParam.getLimit());
		Wrapper<ReportDataDailyEntity> entityWrapper = new EntityWrapper<ReportDataDailyEntity>(reportdatadaily);
		entityWrapper.ge(!StringUtils.isBlank(reportdatadaily.getStartTime()),"count_day", reportdatadaily.getStartTime());
		entityWrapper.le(!StringUtils.isBlank(reportdatadaily.getEndTime()),"count_day", reportdatadaily.getEndTime());
		entityWrapper.orderBy(pageParam.getSort(), pageParam.getDirection());
		reportdatadaily.selectPage(result, entityWrapper);
		//查询兑换总额
		List<GiftBagExchangeRecordParam> totalExchangeAmount = giftBagExchangeRecordService.getTotalExchangeAmount(reportdatadaily.getStartTime(),reportdatadaily.getEndTime());
		for(ReportDataDailyEntity data:result.getRecords()) {
			data.setTotalExchangeAmount(new BigDecimal(0));
			data.setExchangeNum(0);
			for(GiftBagExchangeRecordParam gift:totalExchangeAmount) {
				if(data.getCountDay().compareTo(gift.getExchangeTime()) == 0) {
					data.setTotalExchangeAmount(gift.getTotalExchangeAmount());
					data.setExchangeNum(gift.getExchangeNum());
					break;
				}
			}
    	}
		return R.ok().put("page", new PageUtils(result.getRecords(), result.getTotal(), result.getSize(),
				result.getCurrent(), result.getPages()));
    }
    
    //算出时间差（日）
  	public static long getDatePoor(Date endDate, Date nowDate) {
  		 
  	    long nd = 1000 * 24 * 60 * 60;
  	    // 获得两个时间的毫秒时间差异
  	    long diff = endDate.getTime() - nowDate.getTime();
  	    // 计算差多少天
  	    long day = diff / nd;
  	   
  	    return day+1;
  	}


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("reportdatadaily:reportdatadaily:info")
    public R info(@PathVariable("id") Long id){
			ReportDataDailyEntity reportDataDaily = reportDataDailyService.selectById(id);
        return R.ok().put("reportdatadaily", reportDataDaily);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("reportdatadaily:reportdatadaily:save")
    public R save(@RequestBody ReportDataDailyEntity reportdatadaily){
			reportDataDailyService.insert(reportdatadaily);
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("reportdatadaily:reportdatadaily:update")
    public R update(@RequestBody ReportDataDailyEntity reportdatadaily){
			reportDataDailyService.updateById(reportdatadaily);
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("reportdatadaily:reportdatadaily:delete")
    public R delete(@RequestBody Long[] ids){
    for (Long id : ids) {
			reportDataDailyService.deleteById(id);
	}
        return R.ok();
    }

}

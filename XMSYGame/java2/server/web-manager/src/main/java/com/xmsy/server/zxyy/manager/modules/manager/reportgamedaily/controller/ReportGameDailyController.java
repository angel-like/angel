package com.xmsy.server.zxyy.manager.modules.manager.reportgamedaily.controller;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.xmsy.server.zxyy.manager.modules.manager.gamerecord.controller.GameInfoComponent;
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
import com.xmsy.common.define.page.PageParam;
import com.xmsy.server.zxyy.manager.common.utils.MathUtil;
import com.xmsy.server.zxyy.manager.common.utils.PageUtils;
import com.xmsy.server.zxyy.manager.common.utils.R;
import com.xmsy.server.zxyy.manager.modules.manager.reportgamedaily.entity.ReportGameDailyEntity;
import com.xmsy.server.zxyy.manager.modules.manager.reportgamedaily.service.ReportGameDailyService;
import com.xmsy.server.zxyy.manager.utils.DateUtils;



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
    @Autowired
    private GameInfoComponent gameInfo;
    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("reportgamedaily:reportgamedaily:list")
    public R list(ReportGameDailyEntity reportgamedaily, PageParam pageParam){
        Page<ReportGameDailyEntity> result = new Page<ReportGameDailyEntity>(pageParam.getPage(), pageParam.getLimit());
		Wrapper<ReportGameDailyEntity> entityWrapper = new EntityWrapper<ReportGameDailyEntity>(reportgamedaily);
		entityWrapper.ge(!StringUtils.isBlank(reportgamedaily.getStartTime()),"count_day", reportgamedaily.getStartTime());
		entityWrapper.le(!StringUtils.isBlank(reportgamedaily.getEndTime()),"count_day", reportgamedaily.getEndTime());
		if(!StringUtils.isBlank(pageParam.getSort())) {
			if(pageParam.getDirection()) {
				entityWrapper.orderAsc(Arrays.asList(pageParam.getSort().split(",")));
			}else {
				entityWrapper.orderDesc(Arrays.asList(pageParam.getSort().split(",")));
			}


		}
		reportgamedaily.selectPage(result, entityWrapper);
		Integer participateNum = 0;
		BigDecimal investmentTotal = BigDecimal.ZERO;
		BigDecimal outputTotal = BigDecimal.ZERO;
		BigDecimal winTotal = BigDecimal.ZERO;
		for(ReportGameDailyEntity data:result.getRecords()) {
			participateNum = participateNum + data.getParticipateNum();
			investmentTotal = investmentTotal.add(MathUtil.getBigDecimal(data.getInvestmentTotal()));
			outputTotal = outputTotal.add(MathUtil.getBigDecimal(data.getOutputTotal()));
			winTotal = winTotal.add(MathUtil.getBigDecimal(data.getWinTotal()));
            data.setGameName(gameInfo.getGameName(data.getGameId()));
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


  //=============================================  首页 :统计每日游戏盈利    ===========================================
    @RequestMapping("/sumPlayerProfit")
    @RequiresPermissions("reportgamedaily:reportgamedaily:sumPlayerProfit")
    public R getPlayerProfit(){
    	List<List<Map<String, Object>>> list = reportGameDailyService.sumPlayerProfit(DateUtils.format(DateUtils.getToday()), null);
    	return R.ok().put("list", list);
    }


}

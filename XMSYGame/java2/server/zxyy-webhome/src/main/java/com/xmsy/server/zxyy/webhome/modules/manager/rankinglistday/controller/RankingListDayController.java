package com.xmsy.server.zxyy.webhome.modules.manager.rankinglistday.controller;

import java.util.Date;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.xmsy.server.zxyy.webhome.common.annotation.SysLog;
import com.xmsy.server.zxyy.webhome.common.utils.DateUtils;
import com.xmsy.server.zxyy.webhome.common.utils.PageUtils;
import com.xmsy.server.zxyy.webhome.common.utils.R;
import com.xmsy.server.zxyy.webhome.constant.SysConstant;
import com.xmsy.server.zxyy.webhome.modules.manager.rankinglist.entity.RankingListEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.rankinglist.service.RankingListService;
import com.xmsy.server.zxyy.webhome.modules.manager.rankinglistday.entity.RankingListDayEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.rankinglistday.service.RankingListDayService;
import com.xmsy.server.zxyy.webhome.cache.LocalContentCache;


/**
 * 日排行榜
 *
 * @author lpp
 * @email xxxxx
 * @date 2019-01-21 09:46:00
 */
@RestController
@RequestMapping("rankinglistday/rankinglistday")
public class RankingListDayController {
    @Autowired
    private RankingListDayService rankingListDayService;
    @Autowired
    private RankingListService rankingListService;
    
    @Autowired
	private LocalContentCache localContentCache;
    
    Date yesterday = DateUtils.getStartOfYesterday();

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("rankinglistday:rankinglistday:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = rankingListDayService.queryPage(params);
        return R.ok().put("page", page);
    }
    
    /**
     * 榜单信息
     */
    @RequestMapping("/getRankingList")
    @RequiresPermissions("rankinglistday:rankinglistday:list")
    public R getRankingList(){
    	RankingListEntity ranklist=new RankingListEntity();
    	ranklist.setEnable(true);
        return R.ok().put("ranklist", 
        		rankingListService.
        		selectList(new EntityWrapper<RankingListEntity>(ranklist)));
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("rankinglistday:rankinglistday:info")
    public R info(@PathVariable("id") Long id){
			RankingListDayEntity rankingListDay = rankingListDayService.selectById(id);
        return R.ok().put("rankinglistday", rankingListDay);
    }

    /**
     * 保存
     */
    @SysLog("新增日排行榜")
    @RequestMapping("/save")
    @RequiresPermissions("rankinglistday:rankinglistday:save")
    public R save(@RequestBody RankingListDayEntity rankingListDay){
		rankingListDay.setType(1);
		rankingListDayService.save(rankingListDay);
		localContentCache.remove(SysConstant.FORTUNEKEY + DateUtils.format(yesterday, "yyyy-MM-dd"));
		localContentCache.remove(SysConstant.PIEAWARKEY + DateUtils.format(yesterday, "yyyy-MM-dd"));
        return R.ok();
    }

    /**
     * 修改
     */
    @SysLog("修改日排行榜")
    @RequestMapping("/update")
    @RequiresPermissions("rankinglistday:rankinglistday:update")
    public R update(@RequestBody RankingListDayEntity rankingListDay){
			rankingListDayService.save(rankingListDay);
			localContentCache.remove(SysConstant.FORTUNEKEY + DateUtils.format(yesterday, "yyyy-MM-dd"));
			localContentCache.remove(SysConstant.PIEAWARKEY + DateUtils.format(yesterday, "yyyy-MM-dd"));
        return R.ok();
    }

    /**
     * 删除
     */
    @SysLog("删除日排行榜")
    @RequestMapping("/delete")
    @RequiresPermissions("rankinglistday:rankinglistday:delete")
    public R delete(@RequestBody Long[] ids){
		rankingListDayService.batchDelete(ids);
		localContentCache.remove(SysConstant.FORTUNEKEY + DateUtils.format(yesterday, "yyyy-MM-dd"));
		localContentCache.remove(SysConstant.PIEAWARKEY + DateUtils.format(yesterday, "yyyy-MM-dd"));
        return R.ok();
    }

}

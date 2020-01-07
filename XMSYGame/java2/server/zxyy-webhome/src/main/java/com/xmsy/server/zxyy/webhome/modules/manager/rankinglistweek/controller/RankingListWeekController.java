package com.xmsy.server.zxyy.webhome.modules.manager.rankinglistweek.controller;

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
import com.xmsy.server.zxyy.webhome.modules.manager.rankinglist.entity.RankingListEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.rankinglist.service.RankingListService;
import com.xmsy.server.zxyy.webhome.modules.manager.rankinglistweek.entity.RankingListWeekEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.rankinglistweek.service.RankingListWeekService;



/**
 * 周排行榜
 *
 * @author lpp
 * @email xxxxx
 * @date 2019-01-21 09:46:00
 */
@RestController
@RequestMapping("rankinglistweek/rankinglistweek")
public class RankingListWeekController {
    @Autowired
    private RankingListWeekService rankingListWeekService;

    @Autowired
    private RankingListService rankingListService;
    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("rankinglistweek:rankinglistweek:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = rankingListWeekService.queryPage(params);
        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("rankinglistweek:rankinglistweek:info")
    public R info(@PathVariable("id") Long id){
			RankingListWeekEntity rankingListWeek = rankingListWeekService.selectById(id);
			rankingListWeek.setFirstOfWeek(DateUtils.getFirstDateForWeekDays(rankingListWeek.getWeekOfYear()));
        return R.ok().put("rankinglistweek", rankingListWeek);
    }
    
    /**
     * 榜单信息
     */
    @RequestMapping("/getRankingList")
    @RequiresPermissions("rankinglistweek:rankinglistweek:info")
    public R getRankingList(){
    	RankingListEntity ranklist=new RankingListEntity();
    	ranklist.setEnable(true);
        return R.ok().put("ranklist", 
        		rankingListService.
        		selectList(new EntityWrapper<RankingListEntity>(ranklist)));
    }

    /**
     * 保存
     */
    @SysLog("新增周排行榜")
    @RequestMapping("/save")
    @RequiresPermissions("rankinglistweek:rankinglistweek:save")
    public R save(@RequestBody RankingListWeekEntity rankingListWeek){
		rankingListWeekService.save(rankingListWeek);
        return R.ok();
    }

    /**
     * 修改
     */
    @SysLog("修改周排行榜")
    @RequestMapping("/update")
    @RequiresPermissions("rankinglistweek:rankinglistweek:update")
    public R update(@RequestBody RankingListWeekEntity rankingListWeek){
		rankingListWeekService.save(rankingListWeek);
        return R.ok();
    }

    /**
     * 删除
     */
    @SysLog("删除周排行榜")
    @RequestMapping("/delete")
    @RequiresPermissions("rankinglistweek:rankinglistweek:delete")
    public R delete(@RequestBody Long[] ids){
		rankingListWeekService.batchDelete(ids);
        return R.ok();
    }

}

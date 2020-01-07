package com.xmsy.server.zxyy.webhome.modules.manager.rankinglist.controller;

import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.xmsy.server.zxyy.webhome.common.annotation.SysLog;
import com.xmsy.server.zxyy.webhome.common.utils.PageUtils;
import com.xmsy.server.zxyy.webhome.common.utils.R;
import com.xmsy.server.zxyy.webhome.modules.manager.rankinglist.entity.RankingListEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.rankinglist.service.RankingListService;



/**
 * 排行榜
 *
 * @author lpp
 * @email xxxxx
 * @date 2019-01-21 09:46:00
 */
@RestController
@RequestMapping("rankinglist/rankinglist")
public class RankingListController {
    @Autowired
    private RankingListService rankingListService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("rankinglist:rankinglist:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = rankingListService.queryPage(params);
        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("rankinglist:rankinglist:info")
    public R info(@PathVariable("id") Long id){
			RankingListEntity rankingList = rankingListService.selectById(id);
        return R.ok().put("rankinglist", rankingList);
    }

    /**
     * 保存
     */
    @SysLog("新增排行榜榜单")
    @RequestMapping("/save")
    @RequiresPermissions("rankinglist:rankinglist:save")
    public R save(@RequestBody RankingListEntity rankingList){
			rankingListService.insert(rankingList);
        return R.ok();
    }

    /**
     * 修改
     */
    @SysLog("修改排行榜榜单")
    @RequestMapping("/update")
    @RequiresPermissions("rankinglist:rankinglist:update")
    public R update(@RequestBody RankingListEntity rankingList){
			rankingListService.updateById(rankingList);
        return R.ok();
    }

    /**
     * 删除
     */
    @SysLog("删除排行榜榜单")
    @RequestMapping("/delete")
    @RequiresPermissions("rankinglist:rankinglist:delete")
    public R delete(@RequestBody Long[] ids){
    for (Long id : ids) {
			rankingListService.deleteById(id);
	}
        return R.ok();
    }

}

package com.xmsy.server.zxyy.webhome.modules.manager.pooldispatchtask.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.xmsy.server.zxyy.webhome.cache.LocalContentCache;
import com.xmsy.server.zxyy.webhome.common.annotation.SysLog;
import com.xmsy.server.zxyy.webhome.common.utils.PageUtils;
import com.xmsy.server.zxyy.webhome.common.utils.R;
import com.xmsy.server.zxyy.webhome.modules.manager.pooldispatchtask.entity.PoolDispatchTaskEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.pooldispatchtask.service.PoolDispatchTaskService;
import com.xmsy.server.zxyy.webhome.modules.manager.poolgame.entity.PoolGameEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.poolgame.service.PoolGameService;
import com.xmsy.server.zxyy.webhome.modules.manager.rankinglist.entity.RankingListEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.rankinglist.service.RankingListService;



/**
 * 
 *
 * @author lpp
 * @email xxxxx
 * @date 2019-01-21 09:46:00
 */
@RestController
@RequestMapping("pooldispatchtask/pooldispatchtask")
public class PoolDispatchTaskController {
    @Autowired
    private PoolDispatchTaskService poolDispatchTaskService;
    @Autowired
    private PoolGameService poolGameService;
    
    @Autowired
    private RankingListService rankingListService;
    
    @Autowired
   	private LocalContentCache localContentCache;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("pooldispatchtask:pooldispatchtask:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = poolDispatchTaskService.queryPage(params);
        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("pooldispatchtask:pooldispatchtask:info")
    public R info(@PathVariable("id") Long id){
			PoolDispatchTaskEntity poolDispatchTask = poolDispatchTaskService.selectById(id);
        return R.ok().put("pooldispatchtask", poolDispatchTask);
    }
    
    /**
     * 榜单信息
     */
    @RequestMapping("/getRankingList")
    @RequiresPermissions("pooldispatchtask:pooldispatchtask:info")
    public R getRankingList(){
    	RankingListEntity ranklist=new RankingListEntity();
    	ranklist.setEnable(true);
        return R.ok().put("ranklist", 
        		rankingListService.
        		selectList(new EntityWrapper<RankingListEntity>(ranklist)));
    }
    
    /**
     * 奖池信息
     */
    @RequestMapping("/getPool")
    @RequiresPermissions("pooldispatchtask:pooldispatchtask:info")
    public R getPoolList(){
    	PoolGameEntity poolgame = new PoolGameEntity();
    	poolgame.setEnable(true);
    	List<Map<String, Object>> result = new ArrayList<>();
    	List<PoolGameEntity> dataList = poolGameService.selectList(new EntityWrapper<PoolGameEntity>(poolgame));
        if(dataList!=null && !dataList.isEmpty()) {
        	Map<String, Object> amap = null;
        	Map<Long, String> gameMap = localContentCache.getGameMap();//SysConstant.gameMap
        	for(PoolGameEntity data : dataList) {
        		amap = new HashMap<>();
        		amap.put("id", data.getId());
        		amap.put("name", gameMap.get(data.getGameId())+"奖池");
        		result.add(amap);
        	}
        }
    	return R.ok().put("poolList", result);
//    	return R.ok().put("poolList", poolGameService.findPoolList());
    }
    
    /**
     * 奖池金额信息
     */
    @RequestMapping("/pool/{id}")
    @RequiresPermissions("pooldispatchtask:pooldispatchtask:info")
    public R pool(@PathVariable("id") Long id){
        return R.ok().put("pool", poolGameService.selectById(id).getPool());
    }

    /**
     * 保存
     */
    @SysLog("新增奖池奖金派送任务")
    @RequestMapping("/save")
    @RequiresPermissions("pooldispatchtask:pooldispatchtask:save")
    public R save(@RequestBody PoolDispatchTaskEntity poolDispatchTask){
			poolDispatchTaskService.insert(poolDispatchTask);
        return R.ok();
    }

    /**
     * 修改
     */
    @SysLog("修改奖池奖金派送任务")
    @RequestMapping("/update")
    @RequiresPermissions("pooldispatchtask:pooldispatchtask:update")
    public R update(@RequestBody PoolDispatchTaskEntity poolDispatchTask){
			poolDispatchTaskService.updateById(poolDispatchTask);
        return R.ok();
    }

    /**
     * 删除
     */
    @SysLog("删除奖池奖金派送任务")
    @RequestMapping("/delete")
    @RequiresPermissions("pooldispatchtask:pooldispatchtask:delete")
    public R delete(@RequestBody Long[] ids){
	    for (Long id : ids) {
				poolDispatchTaskService.deleteById(id);
		}
        return R.ok();
    }
    /**
     * 派发奖金
     */
    @SysLog(" 派发奖金")
    @RequestMapping("/dispatch/{id}")
    @RequiresPermissions("pooldispatchtask:pooldispatchtask:dispatch")
    public R dispatch(@PathVariable("id") Long id){
    	poolDispatchTaskService.dispatch(id);
    	return R.ok();
    }

}

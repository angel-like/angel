package com.xmsy.server.zxyy.manager.modules.game.controller;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.xmsy.server.zxyy.manager.modules.game.service.GameConfigService;



/**
 * 游戏概率
 *
 * @author xiaoling
 * @email xxxxx
 * @date 2019-09-25 14:58:45
 */
@RestController
@RequestMapping("gameconfig/gameconfig")
public class GameConfigController {
    @Autowired
    private GameConfigService gameConfigService;

    /**
     * 列表
     */
    @RequestMapping("/gamelist")
    @RequiresPermissions("gameconfig:gameconfig:list")
    public JSONObject gamelist(@RequestBody JSONObject gameconfig){
		return gameConfigService.getGameConfigList(gameconfig); 
    }
    
    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("gameconfig:gameconfig:list")
    public JSONObject list(@RequestBody JSONObject gameconfig){
		return gameConfigService.getList(gameconfig); 
    }



    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("gameconfig:gameconfig:info")
    public JSONObject info(@PathVariable("id") Long id){
        return gameConfigService.getGameConfigInfoList(id);
    }
    
    /**
     * 下拉
     */
    @RequestMapping("/select/{interval}")
    public JSONObject selectDictionary(@PathVariable("interval") boolean interval){
        return gameConfigService.selectDictionary(interval);
    }
    
    /**
     * 通过游戏id获取区间游戏胜率信息
     */
    @RequestMapping("/selectIntervalByGameId/{gameId}")
    public JSONObject selectIntervalByGameId(@PathVariable("gameId") Long gameId){
        return gameConfigService.selectIntervalByGameId(gameId);
    }
    
    /**
     * 批量新增修改时：获取当前选中游戏厅的所有信息，并放入数据字典里
     */
    @RequestMapping("/selectDataPerfectByGameId/{gameId}")
    public JSONObject selectDataPerfectByGameId(@PathVariable("gameId") Long gameId){
        return gameConfigService.selectDataPerfectByGameId(gameId);
    }
    
    /**
	 * 保存
	 */
    @RequestMapping("/save")
    @RequiresPermissions("gameconfig:gameconfig:save")
    public JSONObject saveConfig(@RequestBody JSONObject json) {
    	return  gameConfigService.saveGameConfig(json); 
    }
    
    /**
	 * 保存游戏区间
	 */
    @RequestMapping("/saveOrUpdateIntervalGameRate")
    @RequiresPermissions("gameconfig:gameconfig:update")
    public JSONObject updateIntervalGameRateByGameId(@RequestBody JSONObject json) {
    	return  gameConfigService.updateIntervalGameRateByGameId(json); 
    }
    
    /**
	 * 批量新增修改时：对传过来的数据进行更新或者插入
	 */
    @RequestMapping("/saveOrUpdatePerfect")
    @RequiresPermissions("gameconfig:gameconfig:update")
    public JSONObject saveOrUpdatePerfect(@RequestBody JSONObject json) {
    	return  gameConfigService.saveOrUpdatePerfect(json); 
    }
    
    
    /**
	 * 修改
	 */
    @RequestMapping("/update")
    @RequiresPermissions("gameconfig:gameconfig:update")
    public JSONObject update(@RequestBody JSONObject json) {
    	return  gameConfigService.updateGameConfig(json); 
    }
    
    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("gameconfig:gameconfig:delete")
    public JSONObject delete(@RequestBody Long[] ids ) {
    	return  gameConfigService.deleteGameConfig(ids); 
    }
    

    
  
}

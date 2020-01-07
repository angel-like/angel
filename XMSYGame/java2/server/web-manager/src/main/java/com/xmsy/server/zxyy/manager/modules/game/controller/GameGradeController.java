package com.xmsy.server.zxyy.manager.modules.game.controller;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.xmsy.server.zxyy.manager.modules.game.service.GameGradeService;
import com.alibaba.fastjson.JSONObject;

/**
 * 游戏场次等级
 *
 * @author xiaoling
 * @email xxxxx
 * @date 2019-09-30 14:58:45
 */
@RestController
@RequestMapping("gamegrade/gamegrade")
public class GameGradeController {
	
	@Autowired
    private GameGradeService gameGradeService;
	 /**
     * 游戏场次等级列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("gamegrade:gamegrade:list")
    public JSONObject list(@RequestBody JSONObject gamegrade){
		return  gameGradeService.getGameGradeList(gamegrade); 
    }
    
    /**
     * 游戏场次等级列表
     */
    @RequestMapping("/GradeList")
    @RequiresPermissions("gamegrade:gamegrade:list")
    public JSONObject getGradeList(){
		return  gameGradeService.getGradeList(); 
    }
    
    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("gamegrade:gamegrade:info")
    public JSONObject info(@PathVariable("id") Long id){
        return gameGradeService.getGameGradeInfoList(id);
    }
    
    /**
	 * 保存
	 */
    @RequestMapping("/save")
    @RequiresPermissions("gamegrade:gamegrade:save")
    public JSONObject saveConfig(@RequestBody JSONObject json) {
    	return  gameGradeService.saveGameGrade(json); 
    }
    
    /**
	 * 修改
	 */
    @RequestMapping("/update")
    @RequiresPermissions("gamegrade:gamegrade:update")
    public JSONObject update(@RequestBody JSONObject json) {
    	return  gameGradeService.updateGameGrade(json); 
    }
    
    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("gamegrade:gamegrade:delete")
    public JSONObject delete(@RequestBody Long[] ids ) {
    	return  gameGradeService.deleteGameGrade(ids); 
    }
       
    
  
}




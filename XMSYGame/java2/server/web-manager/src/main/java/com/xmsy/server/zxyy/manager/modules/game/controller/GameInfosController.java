package com.xmsy.server.zxyy.manager.modules.game.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.xmsy.server.zxyy.manager.modules.game.service.GameInfosService;
import com.xmsy.server.zxyy.manager.modules.manager.apppopulargames.service.AppPopularGamesService;
import com.xmsy.server.zxyy.manager.modules.manager.hierarchygamerole.service.HierarchyGameRoleService;

/**
 * 游戏信息
 *
 * @author xiaoling
 * @email xxxxx
 * @date 2019-09-30 14:58:45
 */
@RestController
@RequestMapping("info/info")
public class GameInfosController {
	@Autowired
	private GameInfosService gameInfoService;
	
	@Autowired
	private HierarchyGameRoleService hierarchyGameRoleService;
	
	@Autowired
	private AppPopularGamesService appPopularGamesService;
	
	/**
     * 游戏大厅列表--新版页面
     */
    @RequestMapping("/listNew")
    @RequiresPermissions("info:info:list")
    public JSONObject listNew(@RequestBody JSONObject info){
		return gameInfoService.getGameInfoListNew(info); 
    }
    /**
	 * 保存--新版页面
	 */
    @RequestMapping("/saveNew")
    @RequiresPermissions("info:info:save")
    public JSONObject saveConfigNew(@RequestBody JSONObject json) {
    	return  gameInfoService.saveGameInfoNew(json); 
    }
    /**
     * 信息--新版页面
     */
    @RequestMapping("/infoNew/{gameId}")
    @RequiresPermissions("info:info:info")
    public JSONObject infoNew(@PathVariable("gameId") Long gameId){
        return gameInfoService.getGameInfoInfoListNew(gameId);
    }
    /**
	 * 修改--新版页面
	 */
    @RequestMapping("/updateNew")
    @RequiresPermissions("info:info:update")
    public JSONObject updateNew(@RequestBody JSONObject gameInfoListJSONObject) {
    	return  gameInfoService.updateGameInfoNew(gameInfoListJSONObject); 
    }
    /**
	 * 修改或保存  游戏配置信息--新版页面
	 * 
	 * @throws CloneNotSupportedException
	 */
	@RequestMapping("/updateNewGameConfig")
	public JSONObject updateNewGameConfig(@RequestBody JSONObject sysDictionaryListListJSONObject) throws CloneNotSupportedException {
		return  gameInfoService.updateNewGameConfig(sysDictionaryListListJSONObject); 
	}
	
	/**
	 * 修改游戏状态（是否完成）
	 * 
	 * @throws CloneNotSupportedException
	 */
	@RequestMapping("/updateEnable")
	public JSONObject updateEnable(@RequestParam("id") Long id, @RequestParam("finished") Boolean finished){
		 if(finished==false) {
		    	hierarchyGameRoleService.deleteByGameId(id);
		    	appPopularGamesService.deleteByGameId(id);
		    }
		return  gameInfoService.updateEnable(id,finished); 
	}
	
	/**
	 * 修改游戏状态（是否维护）
	 * 
	 * @throws CloneNotSupportedException
	 */
	@RequestMapping("/updateMaintenance")
	public JSONObject updateMaintenance(@RequestParam("id") Long id, @RequestParam("maintenance") Boolean maintenance){
		return  gameInfoService.updateMaintenance(id,maintenance); 
	}

	/**
	 * 导出sql	--新版页面
	 * 
	 * @throws IOException
	 */
	@RequestMapping("/exportSqlNew")
	public void exportSqlNew(@RequestBody Long[] ids, HttpServletResponse response) throws IOException {
		String data = gameInfoService.generatorCodeNew(ids);
		response.setContentType("application/octet-stream; charset=gbk");
		response.setHeader("Content-Disposition", "attachment; filename=游戏信息.sql");
		try {
			response.getOutputStream().write(data.getBytes());
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	//============================上面新版本页面  下面原有的不动=========================
	 /**
     * 游戏大厅列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("info:info:list")
    public JSONObject list(@RequestBody JSONObject info){
		return gameInfoService.getGameInfoList(info); 
    }
    
    /**
     * 游戏大厅列表
     */
    @RequestMapping("/GameList")
    @RequiresPermissions("info:info:list")
    public JSONObject getGameTypeList(){
		return gameInfoService.getGameTypeList(); 
    }
    
    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("info:info:info")
    public JSONObject info(@PathVariable("id") Long id){
        return gameInfoService.getGameInfoInfoList(id);
    }
    
    /**
	 * 保存
	 */
    @RequestMapping("/save")
    @RequiresPermissions("info:info:save")
    public JSONObject saveConfig(@RequestBody JSONObject json) {
    	return  gameInfoService.saveGameInfo(json); 
    }
    
    /**
	 * 修改
	 */
    @RequestMapping("/update")
    @RequiresPermissions("info:info:update")
    public JSONObject update(@RequestBody JSONObject json) {
    	return  gameInfoService.updateGameInfo(json); 
    }
    
    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("info:info:delete")
    public JSONObject delete(@RequestBody Long[] ids ) {
    	return  gameInfoService.deleteGameInfo(ids); 
    }
       
    /**
	 * 导出sql
	 * @throws IOException 
	 */
	@RequestMapping("/exportSql")
	public void exportSql(@RequestBody Long[] ids, HttpServletResponse response) throws IOException {
		String data = gameInfoService.generatorCode(ids);
		response.setContentType("application/octet-stream; charset=gbk");
		response.setHeader("Content-Disposition", "attachment; filename=游戏信息.sql");
 		try {
 			response.getOutputStream().write(data.getBytes());
 		} catch (IOException e) {
 			e.printStackTrace();
 		}

	}
}

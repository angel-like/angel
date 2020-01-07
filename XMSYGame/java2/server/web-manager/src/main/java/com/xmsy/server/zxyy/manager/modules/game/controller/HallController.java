package com.xmsy.server.zxyy.manager.modules.game.controller;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.xmsy.server.zxyy.manager.modules.game.service.HallService;

/**
 * 游戏大厅
 *
 * @author xiaoling
 * @email xxxxx
 * @date 2019-09-30 14:58:45
 */
@RestController
@RequestMapping("hall/hall")
public class HallController {
	@Autowired
    private HallService hallService;
	 /**
     * 游戏大厅列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("hall:hall:list")
    public JSONObject list(@RequestBody JSONObject hall){
		return hallService.getRobotHallList(hall); 
    }
    
    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("hall:hall:info")
    public JSONObject info(@PathVariable("id") Long id){
        return hallService.getHallInfoList(id);
    }
    
    /**
	 * 保存
	 */
    @RequestMapping("/save")
    @RequiresPermissions("hall:hall:save")
    public JSONObject saveConfig(@RequestBody JSONObject json) {
    	return  hallService.saveHall(json); 
    }
    
    /**
	 * 修改
	 */
    @RequestMapping("/update")
    @RequiresPermissions("hall:hall:update")
    public JSONObject update(@RequestBody JSONObject json) {
    	return  hallService.updateHall(json); 
    }
    
    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("hall:hall:delete")
    public JSONObject delete(@RequestBody Long[] ids ) {
    	return  hallService.deleteHall(ids); 
    }
       
    
    
}

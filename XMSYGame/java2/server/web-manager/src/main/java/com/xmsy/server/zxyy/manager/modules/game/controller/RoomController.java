package com.xmsy.server.zxyy.manager.modules.game.controller;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.xmsy.server.zxyy.manager.modules.game.service.RoomService;

/**
 * 房间
 *
 * @author xiaoling
 * @email xxxxx
 * @date 2019-10-02 10:37:36
 */
@RestController
@RequestMapping("room/room")
public class RoomController {
	
	@Autowired
    private RoomService roomService;
	/**
     * 列表  
     */
    @RequestMapping("/list")
    @RequiresPermissions("room:room:list")
    public JSONObject list(@RequestBody JSONObject room){
    	return roomService.getList(room);
    }
    
    /**
     * 列表  
     */
    @RequestMapping("/RoomList")
    @RequiresPermissions("room:room:list")
    public JSONObject getRoomList(){
    	return roomService.getRoomList();
    }
    
    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("room:room:info")
    public JSONObject info(@PathVariable("id") Long id){
        return roomService.getRoomInfoList(id);
    }
    
    /**
	 * 保存
	 */
    @RequestMapping("/save")
    @RequiresPermissions("room:room:save")
    public JSONObject saveConfig(@RequestBody JSONObject json) {
    	return  roomService.saveRoom(json); 
    }
    
    /**
	 * 修改
	 */
    @RequestMapping("/update")
    @RequiresPermissions("room:room:update")
    public JSONObject update(@RequestBody JSONObject json) {
    	return  roomService.updateRoom(json); 
    }
    
    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("room:room:delete")
    public JSONObject delete(@RequestBody Long[] ids ) {
    	return  roomService.deleteRoom(ids); 
    }
       
}

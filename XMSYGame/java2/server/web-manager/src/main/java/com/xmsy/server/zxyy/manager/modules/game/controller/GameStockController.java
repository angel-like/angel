package com.xmsy.server.zxyy.manager.modules.game.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xmsy.server.zxyy.manager.cache.LocalContentCache;
import com.xmsy.server.zxyy.manager.common.utils.Constant;
import com.xmsy.server.zxyy.manager.common.utils.MathUtil;
import com.xmsy.server.zxyy.manager.common.utils.R;
import com.xmsy.server.zxyy.manager.modules.game.service.GameStockService;
import com.xmsy.server.zxyy.manager.modules.manager.gamerecord.controller.GameInfoComponent;
import com.xmsy.server.zxyy.manager.modules.manager.gamestockoperationrecord.entity.GameStockOperationRecordEntity;
import com.xmsy.server.zxyy.manager.modules.manager.sys.controller.AbstractController;

/**
 * 游戏信息
 *
 * @author adu
 * @email xxxxx
 * @date 2019-10-29 14:58:45
 */
@RestController
@RequestMapping("gamestock/gamestock")
public class GameStockController extends AbstractController{
	@Autowired
	private GameStockService gameStockService;
	@Autowired
    private GameInfoComponent gameInfo;
	
	@Autowired
    private LocalContentCache localContentCache;
	 /**
     * 游戏大厅列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("gamestock:gamestock:list")
    public JSONObject list(@RequestBody JSONObject info){
		return gameStockService.getGameStockList(info); 
    }
    
    /**
     * 列表  
     */
    @RequestMapping("/RoomList")
    public Object getRoomList(){
    	Map<Long, String> roomMap = gameInfo.getRoomMap();
    	Map<String, Object> map=null;
    	List<Map<String, Object>> list= new ArrayList<Map<String,Object>>();
    	for(Long rid : roomMap.keySet()) {
    		map=new HashMap<String, Object>();
    		map.put("id", rid);
    		map.put("name", roomMap.get(rid));
    		list.add(map);
    	}
    	
    	return R.ok().put("roomList", list);
    }
    
    
    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("gamestock:gamestock:info")
    public JSONObject info(@PathVariable("id") Long id){
        return gameStockService.getGameStockInfoList(id);
    }
    
    /**
	 * 保存
	 */
    @RequestMapping("/save")
    @RequiresPermissions("gamestock:gamestock:save")
    public JSONObject saveConfig(@RequestBody JSONObject json) {
    	return  gameStockService.saveGameStock(json); 
    }
    
    /**
	 * 修改
	 */
    @RequestMapping("/update")
    @RequiresPermissions("gamestock:gamestock:update")
    public JSONObject update(@RequestBody JSONObject json) {
    	//获取修改前的数据
    	JSONObject entity =  gameStockService.getGameStockInfoList(Long.valueOf(json.get("id").toString()));
    	String data = entity.getString("gamestock");
    	JSONObject jsonObjects= JSON.parseObject(data);
    	GameStockOperationRecordEntity entityOld = new GameStockOperationRecordEntity();
    	Map<Long, String> roomMap = localContentCache.getRoomMap();
    	//将修改前的数据赋值给操作记录表
    	entityOld.setStockId(Long.valueOf(jsonObjects.get("id").toString()));
        Boolean enable1 = Boolean.valueOf(jsonObjects.get("enable").toString());
        entityOld.setEnable(enable1);
        String roomId1 = jsonObjects.get("roomId").toString();
        entityOld.setRoomId(roomId1);
        String roomIdArr1[] = roomId1.split(",");
 		String roomName1 = "";
 		for(String rid : roomIdArr1) {
 			roomName1+=","+roomMap.get(Long.parseLong(rid));
 		}
 		roomName1 = roomName1.replaceFirst(",", "");
        entityOld.setStock(new BigDecimal(jsonObjects.get("stock").toString()));
        BigDecimal taxRate1 = new BigDecimal(jsonObjects.get("taxRate").toString());
    	BigDecimal stockLimit1 = new BigDecimal(jsonObjects.get("stockLimit").toString());
        entityOld.setStockLimit(stockLimit1);
        entityOld.setTaxRate(taxRate1);
        entityOld.setTax(new BigDecimal(jsonObjects.get("tax").toString()));
    	entityOld.setSysUserId(getUser().getUserId());
    	entityOld.setSysUserName(getUser().getUsername());
    	entityOld.setCreateTime(new Date());
    	JSONObject entityNew = gameStockService.updateGameStock(json);
    	JSONObject entity2 =  gameStockService.getGameStockInfoList(Long.valueOf(json.get("id").toString()));
    	String data2 = entity2.getString("gamestock");
    	JSONObject jsonObjects2= JSON.parseObject(data2);
    	String roomId2 = jsonObjects2.get("roomId").toString();
    	Boolean enable2 = Boolean.valueOf(jsonObjects2.get("enable").toString());
    	BigDecimal taxRate2 = new BigDecimal(jsonObjects2.get("taxRate").toString());
    	BigDecimal stockLimit2 = new BigDecimal(jsonObjects2.get("stockLimit").toString());
    	String enableRemark1 = "";
    	String enableRemark2 = "";
    	String remark = "";
    	String roomIdArr2[] = roomId2.split(",");
 		String roomName2 = "";
 		for(String rid : roomIdArr2) {
 			roomName2+=","+roomMap.get(Long.parseLong(rid));
 		}
 		roomName2 = roomName2.replaceFirst(",", "");
 		if(!roomId1.equals(roomId2)) {
    		remark += "风控对象由：【"+roomName1+"】改为：【"+roomName2+"】; ";
    	}
    	if(enable1!=enable2) {
    		if(enable2) {
        		enableRemark2="是";
        	}else {
        		enableRemark2="否";
        	}
    		if(enable1) {
        		enableRemark1="是";
        	}else {
        		enableRemark1="否";
        	}
    		remark += "启动状态由：【"+enableRemark1+"】改为：【"+enableRemark2+"】; ";
    	}
    	if(taxRate1.compareTo(taxRate2)!=0) {
    		taxRate1 = MathUtil.getBigDecimal(taxRate1).multiply(new BigDecimal(100));
    		taxRate2 = MathUtil.getBigDecimal(taxRate2).multiply(new BigDecimal(100));
    		taxRate1 = taxRate1.setScale(2, BigDecimal.ROUND_HALF_UP);
    		taxRate2 = taxRate2.setScale(2, BigDecimal.ROUND_HALF_UP);
    		remark += "利润收益比例由：【"+taxRate1+"%】改为：【"+taxRate2+"%】; ";
    	}
    	if(stockLimit1.compareTo(stockLimit2)!=0) {
    		stockLimit1 = MathUtil.getBigDecimal(stockLimit1).
    				divide(MathUtil.getBigDecimal(Constant.DB_COIN_RATE),2,BigDecimal.ROUND_HALF_UP);
    		stockLimit2 = MathUtil.getBigDecimal(stockLimit2).
    				divide(MathUtil.getBigDecimal(Constant.DB_COIN_RATE),2,BigDecimal.ROUND_HALF_UP);
    		remark += "风控阀门调节值由：【"+stockLimit1+"】改为：【"+stockLimit2+"】; ";
    	}
    	entityOld.setOperationContent(remark);
    	if(entityOld.getOperationContent()!=""&&entityOld.getOperationContent()!=null) {
    		entityOld.insert();
    	}
    	
    	return entityNew; 
    }
    
	/**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("gamestock:gamestock:delete")
    public JSONObject delete(@RequestBody Long[] ids ) {
    	return  gameStockService.deleteGameStock(ids); 
    }
    
    /**
	 * 获取操作记录表的id和风控对象
	 */
	@RequestMapping("/getInfo")
	 public JSONObject getInfo() {
		return  gameStockService.getInfo();
	}
	
       
}

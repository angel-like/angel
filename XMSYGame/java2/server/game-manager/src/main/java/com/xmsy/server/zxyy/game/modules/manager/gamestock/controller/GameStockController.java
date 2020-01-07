package com.xmsy.server.zxyy.game.modules.manager.gamestock.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.xmsy.common.define.page.PageParam;
import com.xmsy.common.define.page.PageUtils;
import com.xmsy.server.zxyy.game.cache.LocalContentCache;
import com.xmsy.server.zxyy.game.common.utils.R;
import com.xmsy.server.zxyy.game.modules.manager.gamestock.entity.GameStockEntity;
import com.xmsy.server.zxyy.game.modules.manager.gamestock.service.GameStockService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;


/**
 * 游戏库存
 *
 * @author adu
 * @email xxxxx
 * @date 2019-10-28 17:03:37
 */
@RestController
@RequestMapping("gamestock/gamestock")
public class GameStockController {
    @Autowired
    private GameStockService gameStockService;
    @Autowired
    private LocalContentCache localContentCache;

    /**
     * 列表
     */
    @RequestMapping("/list")
//    @RequiresPermissions("gamestock:gamestock:list")
    public R list(GameStockEntity gamestock, PageParam pageParam){
        Page<GameStockEntity> result = new Page<GameStockEntity>(pageParam.getPage(), pageParam.getLimit());
		Wrapper<GameStockEntity> entityWrapper = new EntityWrapper<GameStockEntity>(gamestock);
		entityWrapper.orderBy(pageParam.getSort(), pageParam.getDirection());
		gamestock.selectPage(result, entityWrapper);
		if(result.getTotal()>0) {
			Map<Long, String> roomMap = localContentCache.getRoomMap();
			for(GameStockEntity data : result.getRecords()) {
				if(data.getRoomId() == null || "".equals(data.getRoomId())) {
					continue;
				}
				String roomIdArr[] = data.getRoomId().split(",");
				String roomName = "";
				for(String rid : roomIdArr) {
					roomName+=","+roomMap.get(Long.parseLong(rid));
				}
				data.setRoomName(roomName.replaceFirst(",", ""));
			}
		}

		return R.ok().put("page", new PageUtils(result.getRecords(), result.getTotal(), result.getSize(),
				result.getCurrent(), result.getPages()));
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
//    @RequiresPermissions("gamestock:gamestock:info")
    public R info(@PathVariable("id") Long id){
			GameStockEntity gameStock = gameStockService.selectById(id);
        return R.ok().put("gamestock", gameStock);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
//    @RequiresPermissions("gamestock:gamestock:save")
    public R save(@RequestBody GameStockEntity gamestock){
			gameStockService.insert(gamestock);
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
//    @RequiresPermissions("gamestock:gamestock:update")
    public R update(@RequestBody GameStockEntity gamestock){
			gameStockService.updateById(gamestock);
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
//    @RequiresPermissions("gamestock:gamestock:delete")
    public R delete(@RequestBody Long[] ids){
    for (Long id : ids) {
			gameStockService.deleteById(id);
	}
        return R.ok();
    }
    
    /**
     * 获取操作记录表的id和风控对象
     */
    @RequestMapping("/getInfo")
    public R getInfo(){
		List<GameStockEntity> list = gameStockService.getInfo();
		Map<Long, String> roomMap = localContentCache.getRoomMap();
		for(GameStockEntity entity:list) {
			String roomId = entity.getRoomId();
			 String roomIdArr[] = roomId.split(",");
		 		String roomName = "";
		 		for(String rid : roomIdArr) {
		 			roomName +="," + roomMap.get(Long.parseLong(rid));
		 		}
		 		entity.setRoomName(roomName.replaceFirst(",", ""));
		}
        return R.ok().put("getInfo", list);
    }

}

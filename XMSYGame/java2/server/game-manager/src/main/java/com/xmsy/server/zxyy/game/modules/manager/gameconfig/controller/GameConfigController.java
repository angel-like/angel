package com.xmsy.server.zxyy.game.modules.manager.gameconfig.controller;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.toolkit.StringUtils;
import com.xmsy.common.define.page.PageParam;
import com.xmsy.server.zxyy.game.common.utils.Constant;
import com.xmsy.server.zxyy.game.common.utils.PageUtils;
import com.xmsy.server.zxyy.game.common.utils.R;
import com.xmsy.server.zxyy.game.constant.SysConstant;
import com.xmsy.server.zxyy.game.modules.manager.game.entity.GameInfoEntity;
import com.xmsy.server.zxyy.game.modules.manager.game.service.GameInfoService;
import com.xmsy.server.zxyy.game.modules.manager.gameconfig.entity.GameConfigDetailParam;
import com.xmsy.server.zxyy.game.modules.manager.gameconfig.entity.GameConfigEntity;
import com.xmsy.server.zxyy.game.modules.manager.gameconfig.entity.IntervalGameRateParam;
import com.xmsy.server.zxyy.game.modules.manager.gameconfig.service.GameConfigService;
import com.xmsy.server.zxyy.game.modules.manager.sysdictionary.entity.SysDictionaryEntity;
import com.xmsy.server.zxyy.game.modules.manager.sysdictionary.service.SysDictionaryService;
import com.xmsy.server.zxyy.game.push.GamePush;
import com.xmsy.server.zxyy.game.utils.EntityValidateUtil;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 *游戏概率
 * @author xiaoyang
 * @email xxxxx
 * @date 2019-01-26 11:40:23
 */
@Slf4j
@RestController
@RequestMapping("gameconfig/gameconfig")
public class GameConfigController {
	@Autowired
	private GameConfigService gameConfigService;
	@Autowired
	private GameInfoService gameInfoService;
	@Autowired
	private SysDictionaryService sysDictionaryService;
	@Autowired
	private GamePush gamePush;

	/**
	 * 列表
	 */
	@RequestMapping("/gamelist")
//	@RequiresPermissions("gameconfig:gameconfig:list")
	public R gamelist(GameConfigEntity gameconfig, PageParam pageParam) {
		Page<GameConfigEntity> result = new Page<GameConfigEntity>(pageParam.getPage(), pageParam.getLimit());
		Wrapper<GameConfigEntity> entityWrapper = new EntityWrapper<GameConfigEntity>(gameconfig);
		entityWrapper.orderBy(pageParam.getSort(), pageParam.getDirection());
		entityWrapper.setSqlSelect("DISTINCT game_id");
		gameconfig.selectPage(result, entityWrapper);
		return R.ok().put("page", new PageUtils(result.getRecords(), result.getTotal(), result.getSize(),
				result.getCurrent(), result.getPages()));
	}
	
	/**
	 * 列表
	 */
	@RequestMapping("/list")
//	@RequiresPermissions("gameconfig:gameconfig:list")
	public R list(GameConfigEntity gameconfig, PageParam pageParam) {
		Page<GameConfigEntity> result = new Page<GameConfigEntity>(pageParam.getPage(), pageParam.getLimit());
		Wrapper<GameConfigEntity> entityWrapper = new EntityWrapper<GameConfigEntity>(gameconfig);
		entityWrapper.orderBy("game_id,name,start_val", true);
		if(gameconfig.getInterval()) {
			entityWrapper.eq("name", SysConstant.INTERVALGAMERATE);
		}else {
			entityWrapper.ne("name", SysConstant.INTERVALGAMERATE);
		}
		gameconfig.selectPage(result, entityWrapper);
		return R.ok().put("page", new PageUtils(result.getRecords(), result.getTotal(), result.getSize(),
				result.getCurrent(), result.getPages()));
	}

	/**
	 * 信息
	 */
	@RequestMapping("/info/{id}")
//	@RequiresPermissions("gameconfig:gameconfig:info")
	public R info(@PathVariable("id") Long id) {
		GameConfigEntity gameConfig = gameConfigService.selectById(id);
		return R.ok().put("gameconfig", gameConfig);
	}

	/**
	 * 保存
	 */
	@RequestMapping("/save")
//	@RequiresPermissions("gameconfig:gameconfig:save")
	public R save(@RequestBody @Valid GameConfigEntity gameconfig) {
		String errorEesult = EntityValidateUtil.validateModel(gameconfig);
		if (StringUtils.isNotEmpty(errorEesult)) {
			log.error("参数校验失败：{}", errorEesult);
			return R.error(errorEesult);
		}
		gameConfigService.insert(gameconfig);
		GameInfoEntity tempGame = gameInfoService.selectById(gameconfig.getGameId());
		if(tempGame!=null) {
			tempGame.setGameConfig(gameConfigService.getGameConfig(tempGame.getId()));
			gamePush.pushModifyGameattr(tempGame);
		}
		return R.ok();
	}

	

	

	/**
	 * 修改
	 */
	@RequestMapping("/update")
//	@RequiresPermissions("gameconfig:gameconfig:update")
	public R update(@RequestBody GameConfigEntity gameconfig) {
		String errorEesult = EntityValidateUtil.validateModel(gameconfig);
		if (StringUtils.isNotEmpty(errorEesult)) {
			log.error("参数校验失败：{}", errorEesult);
			return R.error(errorEesult);
		}
		GameConfigEntity oldEntity = gameConfigService.selectById(gameconfig.getId());
		gameConfigService.updateById(gameconfig);
		if(!oldEntity.getName().equals(gameconfig.getName())
				|| oldEntity.getVal().compareTo(gameconfig.getVal())!=0) {
			GameInfoEntity tempGame = gameInfoService.selectById(gameconfig.getGameId());
			if(Constant.GameConfigName.INITIALSTOCK.getValue().equals(gameconfig.getName())) {
				//初始库存要有修改，当前库存必须重置为初始库存
				GameConfigEntity gameconfig2 = new GameConfigEntity();
				gameconfig2.setGameId(gameconfig.getGameId());
				gameconfig2.setName(Constant.GameConfigName.CURRENTSTOCK.getValue());
				gameconfig2.setVal(gameconfig.getVal());
				gameConfigService.updateGameConfigVal(gameconfig2);
			}
			if(tempGame!=null) {//去通知游戏服
				tempGame.setGameConfig(gameConfigService.getGameConfig(tempGame.getId()));
				gamePush.pushModifyGameattr(tempGame);
			}
		}
		return R.ok();
	}

	/**
	 * 删除
	 */
	@RequestMapping("/delete")
//	@RequiresPermissions("gameconfig:gameconfig:delete")
	public R delete(@RequestBody Long[] ids) {
		for (Long id : ids) {
			GameConfigEntity oldEntity = gameConfigService.selectById(id);
//			gameConfigService.deleteById(id);
			gameConfigService.physicsDeleteById(id);
			GameInfoEntity tempGame = gameInfoService.selectById(oldEntity.getGameId());
			if(tempGame!=null) {
				tempGame.setGameConfig(gameConfigService.getGameConfig(tempGame.getId()));
				gamePush.pushModifyGameattr(tempGame);
			}
		}
		return R.ok();
	}
	
	/**
	 * 通过上级code获取下拉
	 */
	@RequestMapping("/select/{interval}")
	public R selectDictionary(@PathVariable("interval") boolean interval) {
		SysDictionaryEntity disctionary = new SysDictionaryEntity();
		disctionary.setParentCode(SysConstant.GAMECONFIG);
		disctionary.setEnable(true);
		Wrapper<SysDictionaryEntity> entityWrapper = new EntityWrapper<SysDictionaryEntity>(disctionary);
		if(interval) {
			entityWrapper.eq("code", SysConstant.INTERVALGAMERATE);
		}else {
			entityWrapper.ne("code", SysConstant.INTERVALGAMERATE);
		}
		
		List<SysDictionaryEntity> list = sysDictionaryService.selectList(entityWrapper);
		return R.ok().put("data", list);
	}

	//==================================批量新增修改操作==================================
	
	/**
	 * 批量新增修改时：获取当前选中游戏厅的所有信息（过时不用）
	 */
	@RequestMapping("/selectDataByGameId/{gameId}")
	public R selectDataByGameId(@PathVariable("gameId") Long gameId) {
		GameConfigEntity gameConfig = new GameConfigEntity();
		gameConfig.setGameId(gameId);
		List<GameConfigEntity> gameConfigList = gameConfigService.selectList(new EntityWrapper<GameConfigEntity>(gameConfig));
		Map<String, Object> config =  new HashMap<>();
		config.put("gameId", gameId);
		if(gameConfigList !=null && !gameConfigList.isEmpty()) {
			for(GameConfigEntity gameConfigEntity:gameConfigList) {//遍历当前游戏配置信息
				config.put(gameConfigEntity.getName(), gameConfigEntity.getVal());
			}
		}
		return R.ok().put("map", config);
	}
	/**
	 * 批量新增修改时：对传过来的数据进行更新或者插入（过时不用）
	 */
	@RequestMapping("/saveOrUpdateAll")
//	@RequiresPermissions("gameconfig:gameconfig:update")
	public R updateDataByGameId(@RequestBody GameConfigDetailParam GameConfigDetail) {
		GameConfigEntity gameConfig = new GameConfigEntity();
		gameConfig.setGameId(GameConfigDetail.getGameId());
		List<GameConfigEntity> gameConfigList = gameConfigService.selectList(new EntityWrapper<GameConfigEntity>(gameConfig));
		//去判断并修改或者插入字段
		gameConfigService.updateAllGameConfig(GameConfigDetail,gameConfigList);
		//去通知游戏服
		GameInfoEntity tempGame = gameInfoService.selectById(GameConfigDetail.getGameId());
		if(tempGame!=null) {
			tempGame.setGameConfig(gameConfigService.getGameConfig(tempGame.getId()));
			gamePush.pushModifyGameattr(tempGame);
		}
		return R.ok();
	}
	
	/**
	 * 通过游戏id获取区间游戏胜率信息
	 */
	@RequestMapping("/selectIntervalByGameId/{gameId}")
	public R selectIntervalByGameId(@PathVariable("gameId") Long gameId) {
		GameConfigEntity gameConfig = new GameConfigEntity();
		gameConfig.setGameId(gameId);
		gameConfig.setName(Constant.GameConfigName.INTERVALGAMERATE.getValue());
		List<GameConfigEntity> gameConfigList = gameConfigService.selectList(new EntityWrapper<GameConfigEntity>(gameConfig).orderBy("start_val"));
		List<Map<String, Object>> list=new ArrayList<>();
		Map<String, Object> map;
		if(gameConfigList !=null && !gameConfigList.isEmpty()) {
			for(GameConfigEntity gameConfigEntity:gameConfigList) {//遍历当前游戏配置信息
				if(Constant.GameConfigName.INTERVALGAMERATE.getValue().equals(gameConfigEntity.getName())) {
					map =  new HashMap<>();
					map.put(gameConfigEntity.getName(), gameConfigEntity.getVal());
					map.put("startVal", gameConfigEntity.getStartVal());
					map.put("endVal", gameConfigEntity.getEndVal());
					map.put("id", gameConfigEntity.getId());
					list.add(map);
				}
			}
		}
		return R.ok().put("list", list).put("gameId", gameId);
	}
	/**
	 * 保存游戏区间
	 * @param intervalGameRate
	 * @return
	 */
	@RequestMapping("/saveOrUpdateIntervalGameRate")
//	@RequiresPermissions("gameconfig:gameconfig:update")
	public R updateIntervalGameRateByGameId(@RequestBody IntervalGameRateParam intervalGameRate) {
		//去保存游戏区间
		gameConfigService.updateIntervalGameRate(intervalGameRate);
		// 去通知游戏服
		GameInfoEntity tempGame = gameInfoService.selectById(intervalGameRate.getGameId());
		if (tempGame != null) {
			tempGame.setGameConfig(gameConfigService.getGameConfig(tempGame.getId()));
			gamePush.pushModifyGameattr(tempGame);
		}
		return R.ok();
	}
	
	//=======================================更好的的方法======================
	/**
	 * 去数据字典查询所有字段再页面上遍历
	 */
	@RequestMapping("/selectSysDictionary")
	public R selectSysDictionary(){
		// 1.去数据字典查询所有字段 列表
		List<SysDictionaryEntity> sysDictionaryList = sysDictionaryService.selectList(
				new EntityWrapper<SysDictionaryEntity>(new SysDictionaryEntity().setParentCode("gameconfig")));
		return R.ok().put("list", sysDictionaryList);
	}
	
	/**
	 * 批量新增修改时：获取当前选中游戏厅的所有信息，并放入数据字典里
	 */
	@RequestMapping("/selectDataPerfectByGameId/{gameId}")
	public R selectDataPerfectByGameId(@PathVariable("gameId") Long gameId) {
		//1.查询当前游戏ID所有数据
		GameConfigEntity gameConfig = new GameConfigEntity();
		gameConfig.setGameId(gameId);
		List<GameConfigEntity> gameConfigList = gameConfigService.selectList(new EntityWrapper<GameConfigEntity>(gameConfig));
		// 2.去数据字典查询所有字段 列表(公共public、百人hundred、匹配match、房卡roomCard、风控risk五类)
		/*List<SysDictionaryEntity> sysDictionaryList = sysDictionaryService.selectList(
				new EntityWrapper<SysDictionaryEntity>(new SysDictionaryEntity().setParentCode("gameconfig")));*/

		//3.获取游戏属于哪个场次，查找对应场次所对应的数据。匹配场(roomId=1) 房卡场(roomId=2)  百人场(roomId=3) 拉霸场(roomId=4)
		GameInfoEntity gameInfo = gameInfoService.selectById(gameId);//gameConfig 里的 gameId对应  gameInfo里的id
		List<SysDictionaryEntity> publicList = sysDictionaryService.selectList(//公共的数据--共有的
				new EntityWrapper<SysDictionaryEntity>(new SysDictionaryEntity().setParentCode("public").setEnable(true)));
		if(gameInfo.getRoomId()==1) {//代表匹配场
			List<SysDictionaryEntity> matchList = sysDictionaryService.selectList(//匹配场数据
					new EntityWrapper<SysDictionaryEntity>(new SysDictionaryEntity().setParentCode("match").setEnable(true)));
			publicList.addAll(matchList);
		}else if(gameInfo.getRoomId()==2) {//代表房卡场
			List<SysDictionaryEntity> roomCardList = sysDictionaryService.selectList(//匹配场数据
					new EntityWrapper<SysDictionaryEntity>(new SysDictionaryEntity().setParentCode("roomCard").setEnable(true)));
			publicList.addAll(roomCardList);
		}else if(gameInfo.getRoomId()==3) {//代表百人场
			List<SysDictionaryEntity> hundredList = sysDictionaryService.selectList(//匹配场数据
					new EntityWrapper<SysDictionaryEntity>(new SysDictionaryEntity().setParentCode("hundred").setEnable(true)));
			publicList.addAll(hundredList);
		}else if(gameInfo.getRoomId()==4) {//代表拉霸场--跟百人场一样
			List<SysDictionaryEntity> hundredList = sysDictionaryService.selectList(//匹配场数据
					new EntityWrapper<SysDictionaryEntity>(new SysDictionaryEntity().setParentCode("hundred").setEnable(true)));
			publicList.addAll(hundredList);
		}

		//4..把字段对应值赋值给数据字典里面
		for(SysDictionaryEntity SysDictionary:publicList) {
			SysDictionary.setGameConigGameId(gameId);//字段的gameId  这样子游戏ID肯定有  就算下面数据都没有游戏id也有
			for(GameConfigEntity GameConfig:gameConfigList) {
				if(GameConfig.getName().equals(SysDictionary.getCode())) {
					SysDictionary.setGameConigId(GameConfig.getId());//字段id
					SysDictionary.setVal(GameConfig.getVal());//字段值
				}
			}
		}

		return R.ok().put("list", publicList).put("gameId", gameId).put("roomId", gameInfo.getRoomId());
	}
	
	/**
	 * 批量新增修改时：对传过来的数据进行更新或者插入
	 */
	@RequestMapping("/saveOrUpdatePerfect")
//	@RequiresPermissions("gameconfig:gameconfig:update")
	public R saveOrUpdatePerfect(@RequestBody Object sysDictionaryListObject) {
		//1.把数据提取成数据字典集合
		JSONObject sysDictionaryListJson=(JSONObject)JSON.toJSON(sysDictionaryListObject);
		Map<String,Object> jsonMap=sysDictionaryListJson.getInnerMap();
		@SuppressWarnings("unchecked")
		List<JSONObject> jsonObjectList=(List<JSONObject>)jsonMap.get("sysDictionaryListObject");
		System.out.println(jsonObjectList);
		//2.去增加或者插入  
		Long gameId = gameConfigService.updateAllGameConfigPerfect(jsonObjectList);
		//3.去通知游戏服
		GameInfoEntity tempGame = gameInfoService.selectById(gameId);
		if (tempGame != null) {
			tempGame.setGameConfig(gameConfigService.getGameConfig(tempGame.getId()));
			gamePush.pushModifyGameattr(tempGame);
		}
		return R.ok();
	}
}

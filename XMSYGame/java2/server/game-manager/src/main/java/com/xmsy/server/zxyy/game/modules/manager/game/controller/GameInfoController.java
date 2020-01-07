package com.xmsy.server.zxyy.game.modules.manager.game.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.google.common.collect.Lists;
import com.xmsy.common.define.page.PageParam;
import com.xmsy.server.zxyy.game.common.utils.PageUtils;
import com.xmsy.server.zxyy.game.common.utils.R;
import com.xmsy.server.zxyy.game.listener.ReloadRedis;
import com.xmsy.server.zxyy.game.modules.manager.game.entity.GameInfoEntity;
import com.xmsy.server.zxyy.game.modules.manager.game.entity.param.GameParam;
import com.xmsy.server.zxyy.game.modules.manager.game.entity.result.GameResult;
import com.xmsy.server.zxyy.game.modules.manager.game.service.GameInfoService;
import com.xmsy.server.zxyy.game.modules.manager.game.service.SqlGeneratorService;
import com.xmsy.server.zxyy.game.modules.manager.gameconfig.entity.GameConfigEntity;
import com.xmsy.server.zxyy.game.modules.manager.gameconfig.service.GameConfigService;
import com.xmsy.server.zxyy.game.modules.manager.hall.entity.HallEntity;
import com.xmsy.server.zxyy.game.modules.manager.hall.service.HallService;
import com.xmsy.server.zxyy.game.modules.manager.sysdictionary.entity.SysDictionaryEntity;
import com.xmsy.server.zxyy.game.modules.manager.sysdictionary.service.SysDictionaryService;
import com.xmsy.server.zxyy.game.push.GamePush;
import com.xmsy.server.zxyy.game.utils.EntityValidateUtil;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 *
 * @author aleng
 * @email xxxxx
 * @date 2019-01-31 14:51:26
 */
@Slf4j
@RestController
@RequestMapping("info/info")
public class GameInfoController {

	@Autowired
	private GameInfoService gameInfoService;
	@Autowired
	private GameConfigService gameConfigService;
	@Autowired
	private HallService hallService;
	@Autowired
	private GamePush gamePush;
	@Autowired
	private SqlGeneratorService sqlGeneratorService;
	@Autowired
	private SysDictionaryService sysDictionaryService;
	@Resource
	private ReloadRedis reloadRedis;

	/**
	 * 列表--新版页面
	 */
	@RequestMapping("/listNew")
	public R listNew(GameParam gameParam, PageParam pageParam) {
		Page<GameResult> result = gameInfoService.findGameInfosNew(gameParam, pageParam);
		return R.ok().put("page", new PageUtils(result.getRecords(), result.getTotal(), result.getSize(),
				result.getCurrent(), result.getPages()));
	}
	/**
	 * 保存--新版页面
	 */
	@RequestMapping("/saveNew")
	public R saveNew(@RequestBody GameInfoEntity info) {
		String infoentity = EntityValidateUtil.validateModel(info);
		if (StringUtils.isNotEmpty(infoentity)) {
			log.error("参数校验失败：{}", infoentity);
			return R.error(infoentity);
		}
		info.setLimitHigh(99999999900L);// 默认限高10亿
		//匹配场(roomId=1) 房卡场(roomId=2)  百人场(roomId=3) 拉霸场(roomId=4)
		//荣耀厅(gradeId=1),王牌厅(gradeId=2),战神厅(gradeId=3),金币厅(gradeId=4),积分厅(gradeId=5),体验厅(gradeId=6)
		if(info.getRoomId()==2) {//房卡房间，只有一个积分厅 +金币厅
			info.setGradeId(5l);
			gameInfoService.insert(info);
			pushGameInfo(info);
			info.setGradeId(4l);
			gameInfoService.insert(info);
			pushGameInfo(info);
			reloadRedis.saveToRedis();
			return R.ok();
		}
		info.setGradeId(1l);//荣耀厅(gradeId=1)
		gameInfoService.insert(info);
		pushGameInfo(info);
		info.setGradeId(2l);//王牌厅(gradeId=2)
		gameInfoService.insert(info);
		pushGameInfo(info);
		info.setGradeId(3l);//战神厅(gradeId=3)
		gameInfoService.insert(info);
		pushGameInfo(info);
		info.setGradeId(6l);//体验厅(gradeId=6)
		gameInfoService.insert(info);
		pushGameInfo(info);
		reloadRedis.saveToRedis();
		return R.ok();
	}
	/**
	 * 信息--新版页面
	 */
	@RequestMapping("/infoNew/{gameId}")
	public R infoNew(@PathVariable("gameId") Long gameId) {
		//1.查询游戏信息gameInfo
		List<GameInfoEntity> gameInfoList = gameInfoService
				.selectList(new EntityWrapper<>(new GameInfoEntity().setGameId(gameId)));
		List<List<SysDictionaryEntity>> sysDictionaryListList=new ArrayList<>();
		//2.遍历游戏信息 gameInfoList列表   
		for(GameInfoEntity gameInfo:gameInfoList) {
			if(gameInfo.getRoomId()==2) {
				continue;
			}
			//2.1通过gameInfo的id 查询对应的 gameConfig游戏配置
			GameConfigEntity gameConfig = new GameConfigEntity();
			gameConfig.setGameId(gameInfo.getId());
			List<GameConfigEntity> gameConfigList = gameConfigService.selectList(new EntityWrapper<GameConfigEntity>(gameConfig));
			//2.2获取游戏属于哪个场次，查找对应场次所对应的数据。
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
						new EntityWrapper<SysDictionaryEntity>(new SysDictionaryEntity().setParentCode("tiger").setEnable(true)));
				publicList.addAll(hundredList);
			}
			//2.3把字段对应值赋值给数据字典里面
			for(int i=publicList.size()-1;i>=0;i--) {
				SysDictionaryEntity SysDictionary=publicList.get(i);
				if((gameInfo.getRoomId()==1 && gameInfo.getGameId()!=8) || gameInfo.getRoomId()==4) {
					if("chipMax".equals(SysDictionary.getCode())||"chipMin".equals(SysDictionary.getCode())) {
						publicList.remove(i);
					}
				}else if(gameInfo.getRoomId()==3 && gameInfo.getGameId()!=4) {//只有百人牛牛VIP限红
					if("vipLimitRed".equals(SysDictionary.getCode())) {
						publicList.remove(i);
					}
				}
				if(gameInfo.getRoomId()==1 && gameInfo.getGameId()!=16) {//只有德州扑克有
					if("roomMax".equals(SysDictionary.getCode())||"roomMin".equals(SysDictionary.getCode())) {
						publicList.remove(i);
					}
				}
				if((gameInfo.getRoomId()==1 && gameInfo.getGameId()!=8 && gameInfo.getGameId()!=23) || gameInfo.getRoomId()==4) {
					if("limitRedMax".equals(SysDictionary.getCode())||"tenChips".equals(SysDictionary.getCode())) {
						publicList.remove(i);
					}
				}
				
				SysDictionary.setGameConigGameId(gameInfo.getId());//字段的游戏配置的gameId   也就是游戏信息的id  这样子游戏ID肯定有  就算下面数据都没有游戏id也有
				for(GameConfigEntity GameConfig:gameConfigList) {
					if(GameConfig.getName().equals(SysDictionary.getCode())) {
						SysDictionary.setGameConigId(GameConfig.getId());//字段id
						SysDictionary.setVal(GameConfig.getVal());//字段值
					}
				}
			}
			sysDictionaryListList.add(publicList);
			
		}
		return R.ok().put("gameInfoList", gameInfoList).put("sysDictionaryListList", sysDictionaryListList);
	}
	/**
	 * 修改--新版页面
	 * 
	 * @throws CloneNotSupportedException
	 */
	@RequestMapping("/updateNew")
	public R updateNew(@RequestBody JSONObject gameInfoListJSONObject) throws CloneNotSupportedException {
		//1.转化为JSONArray数组
		JSONArray gameInforJSONArray = JSON
				.parseArray(gameInfoListJSONObject.get("gameInfoListJSONObject").toString());
		//2.遍历JSONArray数组
		for(Object gameInfoObject:gameInforJSONArray) {
			//3.把遍历出来的 对象转为   实体类
			GameInfoEntity gameInfo = JSON.toJavaObject(JSON.parseObject(gameInfoObject.toString()), GameInfoEntity.class);
			//4.更新数据库
			if(gameInfoService.updateById(gameInfo)) {//如果数据库更新成功
				//5.通知游服
				gameInfo.setGameConfig(gameConfigService.getGameConfig(gameInfo.getId()));
				gamePush.pushModifyGameattr(gameInfo); //  -----提交的时候要放开   测试时不通知游服
			}
		}
		reloadRedis.saveToRedis();
		return R.ok();
	}
	/**
	 * 修改或保存  游戏配置信息--新版页面
	 * 
	 * @throws CloneNotSupportedException
	 */
	@RequestMapping("/updateNewGameConfig")
	public R updateNewGameConfig(@RequestBody JSONObject sysDictionaryListListJSONObject) throws CloneNotSupportedException {
		//1.转化为JSONArray数组
		JSONArray sysDictionaryListListJSONArray = JSON
				.parseArray(sysDictionaryListListJSONObject.get("sysDictionaryListListJSONObject").toString());
		//2.遍历JSONArray数组
		for(Object sysDictionaryListObject:sysDictionaryListListJSONArray) {
			//1.获取单个的JSONArray数组
			JSONArray sysDictionaryListJSONArray = JSON.parseArray(sysDictionaryListObject.toString());
			gameConfigService.updateAllGameConfigPerfectNew(sysDictionaryListJSONArray);
		}
		
		return R.ok();
	}
	/**
	 * 导出sql  --新版页面
	 * @throws IOException 
	 */
	@RequestMapping("/exportSqlNew")
	public String exportSqlNew(@RequestBody Long[] ids, HttpServletResponse response) throws IOException {
		List<Long> listIds=new ArrayList<>();
		for(Long id:ids) {
			List<GameInfoEntity> gameInfoList = gameInfoService
					.selectList(new EntityWrapper<>(new GameInfoEntity().setGameId(id)));
			for(GameInfoEntity gameInfo:gameInfoList) {
				listIds.add(gameInfo.getId());
			}
		}
		Long[] array = listIds.toArray(new Long[listIds.size()]);
		String data = sqlGeneratorService.generatorCode(array);
		/*response.setContentType("application/octet-stream; charset=gbk");
		response.setHeader("Content-Disposition", "attachment; filename=游戏信息.sql");
 		try {
 			response.getOutputStream().write(data.getBytes());
 		} catch (IOException e) {
 			e.printStackTrace();
 		}*/
		return data;
	}
	
	 /**
     * 修改状态
     */
    @RequestMapping("/updateEnable")
    public R updateEnable(@RequestParam("id") Long id, @RequestParam("finished") Boolean finished) {
    	gameInfoService.updateStatus(id,finished);
    	reloadRedis.saveToRedis();
        return R.ok();
    }
    
    /**
     * 修改状态
     */
    @RequestMapping("/updateMaintenance")
    public R updateMaintenance(@RequestParam("id") Long id, @RequestParam("maintenance") Boolean maintenance) {
    	gameInfoService.updateMaintenance(id,maintenance);
    	reloadRedis.saveToRedis();
        return R.ok();
    }
	//==========================================上面新版==================================
	
	
	/**
	 * 列表
	 */
	@RequestMapping("/list")
//	@RequiresPermissions("info:info:list")
	public R list(GameParam gameParam, PageParam pageParam) {
		Page<GameResult> result = gameInfoService.findGameInfos(gameParam, pageParam);
		return R.ok().put("page", new PageUtils(result.getRecords(), result.getTotal(), result.getSize(),
				result.getCurrent(), result.getPages()));
	}
	
	
	
	/**
	 * 信息
	 */
	@RequestMapping("/info/{id}")
//	@RequiresPermissions("info:info:info")
	public R info(@PathVariable("id") Long id) {
		GameInfoEntity info = gameInfoService.selectById(id);
		return R.ok().put("info", info);
	}

	/**
	 * 查询游戏名字和游戏场次
	 */
	@RequestMapping("/GameList")
//	@RequiresPermissions("info:info:list")
	public R getGameTypeList() {
		GameInfoEntity entity = new GameInfoEntity();
		entity.setEnable(true);
		return R.ok().put("gameList", gameInfoService.selectGameInfoList().values());
	}

	/**
	 * 保存
	 */
	@RequestMapping("/save")
//	@RequiresPermissions("info:info:save")
	public R save(@RequestBody GameInfoEntity info) {
		String infoentity = EntityValidateUtil.validateModel(info);
		if (StringUtils.isNotEmpty(infoentity)) {
			log.error("参数校验失败：{}", infoentity);
			return R.error(infoentity);
		}
		gameInfoService.insert(info);
		pushGameInfo(info);
		return R.ok();
	}

	/**
	 * 修改
	 * 
	 * @throws CloneNotSupportedException
	 */
	@RequestMapping("/update")
//	@RequiresPermissions("info:info:update")
	public R update(@RequestBody GameInfoEntity info) throws CloneNotSupportedException {
		String infoentity = EntityValidateUtil.validateModel(info);
		if (StringUtils.isNotEmpty(infoentity)) {
			log.error("参数校验失败：{}", infoentity);
			return R.error(infoentity);
		}
		GameInfoEntity result = gameInfoService.selectById(info.getId());
		if (null == result) {
			return R.error();
		}
		GameInfoEntity tempGame = info.clone();
		info.setGameId(null);
		info.setGameName(null);
		gameInfoService.updateById(info);
		if (result.getGradeId() != info.getGradeId()) {
			pushGameInfo(tempGame);
		} else {
			tempGame.setGameConfig(gameConfigService.getGameConfig(tempGame.getId()));
			gamePush.pushModifyGameattr(tempGame);
		}
		return R.ok();
	}

	/**
	 * 删除
	 */
	@RequestMapping("/delete")
//	@RequiresPermissions("info:info:delete")
	public R delete(@RequestBody Long id) {
		gameInfoService.deleteGameInfo(id);
		return R.ok();
	}

	// 推送添加游戏的消息
	private void pushGameInfo(GameInfoEntity info) {
		try {
			Long roomId = info.getRoomId();
			if (null == roomId) {
				return;
			}
			List<HallEntity> hallResults = hallService.selectList(new EntityWrapper<HallEntity>(new HallEntity()));
			for (HallEntity hall : hallResults) {
				String roomIds = hall.getRoomIds();
				if (StringUtils.isEmpty(roomIds)) {
					continue;
				}
				List<String> roomIdList = Lists.newArrayList(roomIds.split(","));
				if (roomIdList.contains(roomId.toString())) {
					gamePush.pushAddGame(info, hall.getId());
				}
			}
		} catch (Exception e) {
			log.error("[GameInfoController] gamePush.pushAddGame exception", e);
		}
	}
	/**
	 * 导出sql
	 * @throws IOException 
	 */
	@RequestMapping("/exportSql")
	public String exportSql(@RequestBody Long[] ids, HttpServletResponse response) throws IOException {
		String data = sqlGeneratorService.generatorCode(ids);
		
		/*response.setContentType("application/octet-stream; charset=gbk");
		response.setHeader("Content-Disposition", "attachment; filename=游戏信息.sql");
 		try {
 			response.getOutputStream().write(data.getBytes());
 		} catch (IOException e) {
 			e.printStackTrace();
 		}*/
		return data;
	}

}

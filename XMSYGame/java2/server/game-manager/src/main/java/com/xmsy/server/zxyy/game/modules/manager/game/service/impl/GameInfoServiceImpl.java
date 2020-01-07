package com.xmsy.server.zxyy.game.modules.manager.game.service.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.xmsy.common.define.constant.ResultDef;
import com.xmsy.common.define.page.PageParam;
import com.xmsy.server.zxyy.game.cache.EhCacheName;
import com.xmsy.server.zxyy.game.cache.LocalContentCache;
import com.xmsy.server.zxyy.game.common.utils.SpringUtil;
import com.xmsy.server.zxyy.game.constant.HallUrlConstant;
import com.xmsy.server.zxyy.game.modules.manager.game.dao.GameInfoDao;
import com.xmsy.server.zxyy.game.modules.manager.game.entity.GameInfoEntity;
import com.xmsy.server.zxyy.game.modules.manager.game.entity.param.GameParam;
import com.xmsy.server.zxyy.game.modules.manager.game.entity.result.GameResult;
import com.xmsy.server.zxyy.game.modules.manager.game.service.GameInfoService;
import com.xmsy.server.zxyy.game.modules.manager.gameconfig.service.GameConfigService;
import com.xmsy.server.zxyy.game.modules.manager.gamegrade.service.GameGradeService;
import com.xmsy.server.zxyy.game.modules.manager.room.entity.RoomEntity;
import com.xmsy.server.zxyy.game.modules.manager.room.service.RoomService;
import com.xmsy.server.zxyy.game.modules.web.game.param.GameGradeResult;
import com.xmsy.server.zxyy.game.modules.web.game.param.GameInfoResult;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.http.HttpRequest;
import lombok.extern.slf4j.Slf4j;

/**
 * 游戏信息
 *
 * @author Administrator
 *
 */
@Slf4j
@Transactional
@Service("gameInfoService")
public class GameInfoServiceImpl extends ServiceImpl<GameInfoDao, GameInfoEntity> implements GameInfoService {

	@Resource
	private GameGradeService gameGradeService;

	@Resource
	private GameConfigService gameConfigService;

	@Resource
	private LocalContentCache localContentCache;

	@Override
	@Async//异步调用
	public void deployRobotConfig() {

		JSONObject returnObj = new JSONObject();
		try {
			String url = HallUrlConstant.getROBOTMANAGER_GAMELIST() ;
			// String url = "http://10.0.0.144:9000/gamelist";
			String result = HttpRequest.post(url).execute().body();
			returnObj = JSON.parseObject(result);
		} catch (Exception e) {
			log.error("获取游戏机器人失败 ", 000000, e);
		}
//		List<JSONObject> list = new ArrayList<JSONObject>();
		if (null != returnObj.getInteger("code") && ResultDef.SUCCESS == returnObj.getInteger("code")) {
			if (!CollectionUtil.isEmpty(returnObj.getJSONArray("gamelist"))) {
				for (int i = 0; i < returnObj.getJSONArray("gamelist").size(); i++) {
					// 如果获取机器人配置的路径不为空
					/*  log.debug("[robotGameConfig] 游戏对象 {}", returnObj.getJSONArray("gamelist").getJSONObject(i));*/
					if (null != returnObj.getJSONArray("gamelist").getJSONObject(i) && !StringUtils.isEmpty(
							returnObj.getJSONArray("gamelist").getJSONObject(i).getString("putRobotConfigUrl"))) {
						//获取机器人对应的游戏id和房间id  start
						String gameId = returnObj.getJSONArray("gamelist").getJSONObject(i).getString("gameId");
						String gradeId = returnObj.getJSONArray("gamelist").getJSONObject(i).getString("gradeId");
						String robotCount = returnObj.getJSONArray("gamelist").getJSONObject(i).getString("robotCount");
						String name = returnObj.getJSONArray("gamelist").getJSONObject(i).getString("name");
						List<Map<String, Object>> lis = new ArrayList<>();
						Map<String, Object> map = new HashMap<>();
						map.put("name", "num");
						map.put("alias", "机器人的数量");
						map.put("type", "input");
						map.put("value", Integer.parseInt(robotCount));
						lis.add(map);
						GameParam gameParam = new GameParam();
						gameParam.setGameId(Long.valueOf(gameId));
						gameParam.setGradeId(Long.valueOf(gradeId));
						GameResult gr = getGameInfos(gameParam);
						//计算
						Long roomId = gr.getRoomId();
						Long limitHigh = gr.getLimitHigh();//限高
						Long restrict = gr.getRestrict();//准入
						Random r = new Random();
						int a = r.nextInt((15 - 10) + 1) + 10;

						if (roomId == 3) {
							restrict = gr.getMaxLines();//百人场准许下注金币值
							restrict=restrict==null|| restrict==0?100000:restrict;
							//百人场
							// 初始金币为准许下注金币值的10~15倍与限高取最小值
							Long lo = (restrict * a) <= limitHigh ? (restrict * a) : limitHigh;
							//金额小于多少金币替换机器人为准许下注金币值

							//金额大于多少金币替换机器人为准许下注金币值*30与限高取最小值
							Long lon = (restrict * 30) <= limitHigh ? (restrict * 30) : limitHigh;
							HashMap<String, Object> map1 = new HashMap<>();

							map1.put("name", "initcoin");
							map1.put("alias", "初始金额");
							map1.put("type", "input");
							map1.put("value", lo);
							HashMap<String, Object> map2 = new HashMap<>();

							map2.put("name", "shiftRobotCoinMin");
							map2.put("alias", "金额少于*替换机器人");
							map2.put("type", "input");
							map2.put("value", restrict);
							HashMap<String, Object> map3 = new HashMap<>();

							map3.put("name", "shiftRobotCoinMax");
							map3.put("alias", "金额大于*替换机器人");
							map3.put("type", "input");
							map3.put("value", lon);
							lis.add(map1);
							lis.add(map2);
							lis.add(map3);

						}else if (roomId == 1) {
							//匹配场
							// 最小初始金币  准入金币*1
							//最大初始金币  准入金币*10与限高取最小值
							restrict=restrict==null|| restrict==0?50000:restrict;
							Long lon = (restrict * 10) <= limitHigh ? (restrict * 10) : limitHigh;
							HashMap<String, Object> map1 = new HashMap<>();

							map1.put("name", "initCoinMin");
							map1.put("alias", "最小初始金额");
							map1.put("type", "input");
							map1.put("value", restrict * 1);
							HashMap<String, Object> map2 = new HashMap<>();

							map2.put("name", "initCoinMax");
							map2.put("alias", "最大初始金额");
							map2.put("type", "input");
							map2.put("value", lon);
							lis.add(map1);
							lis.add(map2);
						}
						log.info("game {} Restrict={} MaxLines = {} LimitHigh = {} ",name,gr.getRestrict(),gr.getMaxLines(),gr.getLimitHigh());
						log.info("pushlist {}",lis);
						JSONArray array = JSONArray.parseArray(JSON.toJSONString(lis));
						String pushUrl = "";
						try {
							pushUrl=returnObj.getJSONArray("gamelist").getJSONObject(i).getString("putRobotConfigUrl");
							JSONObject jsonObject = saveConfig(pushUrl, array);
							if(jsonObject!=null) {
								Long code = Long.valueOf(jsonObject.getString("code"));
								if(code!=200){
									log.error("【"+name+"】"+jsonObject.getString("msg"));
								}
							}
						} catch (Exception e) {
							log.error("【{}】推送机器人携带金币信息失败！ pushUrl: {} error:{}", name,pushUrl, e.getMessage());
						}

						//end
						// 那么就通过路径获取机器人配置
                     /*   JSONObject robotConfig = robotGameConfig(
                                returnObj.getJSONArray("gamelist").getJSONObject(i).getString("getRobotConfigUrl"));
                        log.debug("[robotGameConfig] 通过路径获取的配置 {}", robotConfig);
                        if (null != robotConfig.getInteger("code")
                                && ResultDef.SUCCESS == robotConfig.getInteger("code")) {
                            //将机器人提交路径保存到实体中
                            robotConfig.put("putRobotConfigUrl",
                                    returnObj.getJSONArray("gamelist").getJSONObject(i).getString("putRobotConfigUrl"));
                            robotConfig.put("name", returnObj.getJSONArray("gamelist").getJSONObject(i).getString("name"));
                            log.debug("[robotGameConfig] 要展示的对象 {}", robotConfig);
                            list.add(robotConfig);
                        }*/

					}
				}
			}

		}
	}
	/**
	 * 提交配置信息
	 */
	public static JSONObject saveConfig(String url, JSONArray arry) {
		/** token */
		String TOKEN = "eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiJ0b2tlbiIsImlhdCI6MTU1Mjg5ODIyMn0.uSfTj7U9XcEAHddfjv742Ti44ZthUhRTWk4_ke1s9R8";
		String gameResult = HttpRequest.put(url).header("token", TOKEN).body(arry.toString()).execute().body();
		JSONObject	returnObj = JSON.parseObject(gameResult);
		return returnObj;
	}
	@Override
	@CacheEvict(value = { EhCacheName.GAME_INFO_CACHE, EhCacheName.ROOM_CACHE,
			EhCacheName.HALL_CACHE }, allEntries = true)
	public boolean insert(GameInfoEntity entity) {
		//mqClient.gamePush();
		boolean flag = super.insert(entity);
//		reloadRedis.saveToRedis();
		return flag;
	}

	@Override
	@CacheEvict(value = { EhCacheName.GAME_INFO_CACHE, EhCacheName.ROOM_CACHE,
			EhCacheName.HALL_CACHE }, allEntries = true)
	public boolean deleteById(Serializable id) {
		//mqClient.gamePush();
		boolean flag = super.deleteById(id);
//		reloadRedis.saveToRedis();
		return flag;
	}

	@Override
	@CacheEvict(value = { EhCacheName.GAME_INFO_CACHE, EhCacheName.ROOM_CACHE,
			EhCacheName.HALL_CACHE }, allEntries = true)
	public boolean updateById(GameInfoEntity entity) {
		//mqClient.gamePush();
		boolean flag = super.updateById(entity);
//		reloadRedis.saveToRedis();
		return flag;
	}

	@Override
	@Cacheable(value = EhCacheName.GAME_INFO_CACHE, key = "#id")
	public GameInfoEntity selectById(Serializable id) {
		return super.selectById(id);
	}

//	@Cacheable(cacheNames = EhCacheName.GAME_INFO_CACHE, key = "'gameResult'")
	public Map<Long, GameResult> selectGameInfoList() {
		List<GameResult> gameResults = this.baseMapper.findGameInfos(new GameParam());
		Map<Long, GameResult> result = Maps.newConcurrentMap();
		for (GameResult gameResult : gameResults) {
			result.put(gameResult.getId(), gameResult);
		}
		return result;
	}

	@Override
	@Cacheable(cacheNames = EhCacheName.GAME_INFO_CACHE, key = "'gameInfoEmtity'")
	public Map<Long, GameInfoEntity> GameInfoList() {
		List<GameInfoEntity> gameColl = super.selectList(new EntityWrapper<GameInfoEntity>(new GameInfoEntity()));
		Map<Long, GameInfoEntity> result = Maps.newConcurrentMap();
		for (GameInfoEntity game : gameColl) {
			result.put(game.getId(), game);
		}
		return result;
	}

	/**
	 * 根据游戏id精确的获取对应场次的游戏
	 *
	 * @param ids
	 * @return
	 */
	public Map<Long, GameInfoResult> selectGameByIds(List<String> ids) {
		List<GameInfoEntity> gameInfos = super.selectList(new EntityWrapper<GameInfoEntity>(new GameInfoEntity()));
		Map<Long, GameInfoResult> resultMap = Maps.newConcurrentMap();
		GameInfoResult gameInfoResult = null;
		GameGradeResult gameGradeResult = null;
		for (GameInfoEntity gameInfo : gameInfos) {
			if (null == gameInfo) {
				continue;
			}
			if (!CollectionUtils.isEmpty(ids) && !ids.contains(gameInfo.getId().toString())) {
				continue;
			}
			gameInfoResult = new GameInfoResult();
			gameInfoResult.setId(gameInfo.getGameId());
			gameInfoResult.setName(gameInfo.getGameName());
			gameInfoResult.setRoomId(gameInfo.getRoomId());
			gameInfoResult.setProvider(gameInfo.getProvider());
			gameInfoResult.setMaintenance(gameInfo.getMaintenance());
			gameGradeResult = new GameGradeResult();
			gameGradeResult.setBscore(gameInfo.getBscore());
			gameGradeResult.setDisplay(gameInfo.getDisplay());
			gameGradeResult.setEnable(gameInfo.getEnable());
			gameGradeResult.setGradeId(gameInfo.getGradeId());
			gameGradeResult.setGradeName(gameGradeService.selectById(gameInfo.getGradeId()).getName());
			gameGradeResult.setMaintenance(gameInfo.getMaintenance());
			gameGradeResult.setMaxLines(gameInfo.getMaxLines());
			gameGradeResult.setMaxTimes(gameInfo.getMaxTimes());
			gameGradeResult.setRestrict(gameInfo.getRestrict());
			gameGradeResult.setLimitHigh(gameInfo.getLimitHigh());
			gameGradeResult.setOnlineMin(gameInfo.getOnlineMin());
			gameGradeResult.setOnlineMax(gameInfo.getOnlineMax());
//			gameGradeResult.setProvider(gameInfo.getProvider());
			gameGradeResult.setRate(gameInfo.getRate());
			gameGradeResult.setSence(gameInfo.getSence());
			GameInfoResult gameInfoResultTemp = resultMap.get(gameInfo.getGameId());
			if (null != gameInfoResultTemp) {
				gameInfoResultTemp.getGrades().add(gameGradeResult);
			} else {
				gameInfoResult.getGrades().add(gameGradeResult);
				resultMap.put(gameInfo.getGameId(), gameInfoResult);
			}
		}
		return resultMap;
	}

	@Cacheable(cacheNames = EhCacheName.GAME_INFO_CACHE, key = "'gameInfoResult'")
	public Map<Long, GameInfoResult> selectAll() {
		List<GameInfoEntity> gameInfos = super.selectList(new EntityWrapper<GameInfoEntity>(new GameInfoEntity()));
		Map<Long, GameInfoResult> resultMap = Maps.newConcurrentMap();
		GameInfoResult gameInfoResult = null;
		GameGradeResult gameGradeResult = null;
		for (GameInfoEntity gameInfo : gameInfos) {
			if (null == gameInfo) {
				continue;
			}
			gameInfoResult = new GameInfoResult();
			gameInfoResult.setId(gameInfo.getGameId());
			gameInfoResult.setName(gameInfo.getGameName());
			gameGradeResult = new GameGradeResult();
			gameGradeResult.setBscore(gameInfo.getBscore());
			gameGradeResult.setDisplay(gameInfo.getDisplay());
			gameGradeResult.setEnable(gameInfo.getEnable());
			gameGradeResult.setGradeId(gameInfo.getGradeId());
			gameGradeResult.setGradeName(gameGradeService.selectById(gameInfo.getGradeId()).getName());
			gameGradeResult.setMaintenance(gameInfo.getMaintenance());
			gameGradeResult.setMaxLines(gameInfo.getMaxLines());
			gameGradeResult.setMaxTimes(gameInfo.getMaxTimes());
			gameGradeResult.setRestrict(gameInfo.getRestrict());
			gameGradeResult.setLimitHigh(gameInfo.getLimitHigh());
			gameGradeResult.setRate(gameInfo.getRate());
			gameGradeResult.setSence(gameInfo.getSence());
			GameInfoResult gameInfoResultTemp = resultMap.get(gameInfo.getGameId());
			if (null != gameInfoResultTemp) {
				gameInfoResultTemp.getGrades().add(gameGradeResult);
			} else {
				gameInfoResult.getGrades().add(gameGradeResult);
				resultMap.put(gameInfo.getGameId(), gameInfoResult);
			}
		}
		return resultMap;
	}

	@Override
	public Page<GameResult> findGameInfos(GameParam gameParam, PageParam pageParam) {
		log.info("[findGameInfos] gameParam {} pageParam {}", gameParam, pageParam);
		Page<GameResult> page = new Page<>(pageParam.getPage(), pageParam.getLimit());// 当前页，总条数 构造 page 对象
		return page.setRecords(this.baseMapper.findGameInfos(page, gameParam));
	}
	/**
	 * 获取游戏列表--新版页面
	 */
	@Override
	public Page<GameResult> findGameInfosNew(GameParam gameParam, PageParam pageParam) {
		log.info("[findGameInfos] gameParam {} pageParam {}", gameParam, pageParam);
		Page<GameResult> page = new Page<>(pageParam.getPage(), pageParam.getLimit());// 当前页，总条数 构造 page 对象
		return page.setRecords(this.baseMapper.findGameInfosNew(page, gameParam));
	}
	@Override
	public void deleteGameInfo(Long id) {
		// 删除游戏
		baseMapper.deleteById(id);
		String idStr = String.valueOf(id);
		RoomService roomService = SpringUtil.getApplicationContext().getBean(RoomService.class);
		// 删除房间关联的游戏
		Collection<RoomEntity> rooms = roomService.getRoomMap().values();
		List<String> roomList = null;
		for (RoomEntity room : rooms) {
			roomList = Lists.newArrayList(room.getGameIds().split(","));
			if (roomList.contains(idStr)) {
				roomList.remove(idStr);
				room.setGameIds(Joiner.on(",").join(roomList));
				roomService.updateById(room);
			}
		}
	}

	@Override
	// @Cacheable(cacheNames = EhCacheName.GAME_INFO_CACHE, key =
	// "T(String).valueOf(#gameParam.gameId).concat('-').concat(T(String).valueOf(#gameParam.gradeId)).concat('-').concat(T(String).valueOf(#gameParam.roomId))")
	public List<GameResult> findGameInfos(GameParam gameParam) {
		List<GameResult> gameList = this.baseMapper.findGameInfos(gameParam);
		if (gameList != null && !gameList.isEmpty()) {
			for (GameResult game : gameList) {
//				Map<String, Object> config =  gameConfigService.getGameConfig(game.getId());
//				Map<String, Object> gameGrade = localContentCache.getGameRoomGradeInfo(game.getGameId(), game.getGradeId());
//				if(gameGrade==null) {
//					 gameGrade = localContentCache.getRoomGradeInfo(game.getRoomId(), game.getGradeId());
//				}
//				if(gameGrade!=null) {
//					if(game.getBscore()==null) {
//						game.setBscore(Long.parseLong(gameGrade.get("bscore").toString()));
//					}
//					if(game.getOnlineMin()==null) {
//						game.setOnlineMin(Long.parseLong(gameGrade.get("onlineMin").toString()));
//					}
//					if(game.getOnlineMax()==null) {
//						game.setOnlineMax(Long.parseLong(gameGrade.get("onlineMax").toString()));
//					}
//					if(game.getRestrict()==null) {
//						game.setRestrict(Long.parseLong(gameGrade.get("restrict").toString()));
//					}
//					if(game.getMaxTimes()==null) {
//						game.setMaxTimes(Long.parseLong(gameGrade.get("maxTimes").toString()));
//					}
//					if(game.getMaxLines()==null) {
//						game.setMaxLines(Long.parseLong(gameGrade.get("maxLines").toString()));
//					}
//					if(game.getLimitHigh()==null) {
//						game.setLimitHigh(Long.parseLong(gameGrade.get("limitHigh").toString()));
//					}
//					if(game.getLimitLower()==null) {
//						game.setLimitLower(Long.parseLong(gameGrade.get("limitLower").toString()));
//					}
//					if(game.getRate()==null) {
//						game.setRate(new BigDecimal(gameGrade.get("rate").toString()));
//					}
//					@SuppressWarnings("unchecked")
//					Map<String, Object> configs=  (Map<String, Object>)gameGrade.get("gameConfig");
//					if(configs!=null) {
//						for(String key : configs.keySet()){
//							if((config.get(key)==null ||"".equals(config.get(key).toString()) )  && configs.get(key)!=null) {
//								config.put(key, configs.get(key));
//							}
//						}
//					}
//				}
//				game.setGameConfig(config);
				game.setGameConfig(gameConfigService.getGameConfig(game.getId()));
			}
		}
		return gameList;
	}

	@Override
	public List<GameInfoEntity> queryconfig(Long gradeId) {
		return this.baseMapper.queryconfig(gradeId);
	}
	@Override
	public GameResult getGameInfos(GameParam gameParam){
		return this.baseMapper.getGameInfos(gameParam);
	}
	@Override
	public void updateStatus(Long id, Boolean finished) {
		this.baseMapper.updateStatus(id,finished);
	}

	@Override
	public void updateMaintenance(Long id, Boolean maintenance) {
		this.baseMapper.updateMaintenance(id,maintenance);
	}
	
	@Override
	public List<GameResult> findGameInfosNotConfig() {
		return this.baseMapper.findGameInfos(new GameParam());
	};
}

package com.xmsy.server.zxyy.robot.modules.manager.robot.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xmsy.common.define.constant.ResultDef;
import com.xmsy.server.zxyy.robot.cache.LocalContentCache;
import com.xmsy.server.zxyy.robot.common.exception.RRException;
import com.xmsy.server.zxyy.robot.common.utils.ErrorCode;
import com.xmsy.server.zxyy.robot.common.utils.MathUtil;
import com.xmsy.server.zxyy.robot.constant.HallUrlConstant;
import com.xmsy.server.zxyy.robot.constant.SysConstant;
import com.xmsy.server.zxyy.robot.modules.manager.game.service.GameInterfaceService;
import com.xmsy.server.zxyy.robot.modules.manager.gamerecord.entity.GameRecordEntity;
import com.xmsy.server.zxyy.robot.modules.manager.gamerecord.service.GameRecordService;
import com.xmsy.server.zxyy.robot.modules.manager.robot.dao.RobotDao;
import com.xmsy.server.zxyy.robot.modules.manager.robot.entity.RobotEntity;
import com.xmsy.server.zxyy.robot.modules.manager.robot.service.RobotService;
import com.xmsy.server.zxyy.robot.modules.robotinterface.distribution.param.HallResultParam;
import com.xmsy.server.zxyy.robot.modules.robotinterface.distribution.param.RobotResultParam;
import com.xmsy.server.zxyy.robot.queue.ConcurrentLinked;
import com.xmsy.server.zxyy.robot.rabbitmq.param.RobotParam;
import com.xmsy.server.zxyy.robot.utils.JwtUtil;
import com.xmsy.server.zxyy.robot.utils.UniqueCodeUtil;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.http.HttpRequest;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Transactional
@Service("robotService")
public class RobotServiceImpl extends ServiceImpl<RobotDao, RobotEntity> implements RobotService {
	@Autowired
	private GameRecordService gameRecordService;
	@Autowired
	private LocalContentCache cache;
	
//	private int requestNum = 1;

	// 分配机器人
	@Override
	public RobotResultParam distributionRobot(Long gameId, Long gradeId, Long hallId, Long coin) {
//		log.info("[distributionRobot=====>分配机器人] gameId {}, gradeId {}, coin {}, hallId {}", gameId, gradeId, coin,
//				hallId);
//		log.info("请求分配机器人第 {}次",requestNum);
//		requestNum++;
		String token = null;
		// 获取大厅ip
		RobotResultParam result = setHallIp(hallId);
		// 从空闲队列中获取机器人
		RobotEntity entity = ConcurrentLinked.getIdle(ConcurrentLinked.getKey(gameId, gradeId));
//		log.info("[distributionRobot=====>分配机器人] 从空闲队列中获取机器人 {}", entity);
		// 如果队列不为空
		if (null != entity) {
			// 生成一个新的token
			token = JwtUtil.createJWT(entity.getId());
			// 修改机器人状态和信息
			entity.setToken(token);
			entity.setCoin(coin);
			entity.setUpdateTime(new Date());
			entity.setStatus(SysConstant.ROBOT_STATUS_TRUE);
//			ConcurrentLinked.putModify(entity);//将机器人放入待修改队列
			synUpdateRobot(entity);//同步更新
//			log.info("[distributionRobot=====>分配机器人] 待修改机器人 {}", entity);
		} else {// 如果为空就从数据库中新增机器人
			entity = new RobotEntity();
			entity.setCoin(coin);
			entity.setEnable(SysConstant.ROBOT_ENABLE_TRUE);
			entity.setStatus(SysConstant.ROBOT_STATUS_TRUE);
			entity.setGameId(gameId);
			entity.setGradeId(gradeId);
			entity.setName(UniqueCodeUtil.create());
			entity.setSex(MathUtil.getRandomSex());
			entity.setUpdateTime(new Date());
			entity.setPortrait(MathUtil.getRandomPortrait(MathUtil.getRandomSex()));// 用户头像
			baseMapper.insertGetId(entity);
			token = JwtUtil.createJWT(entity.getId());
			entity.setProfitCoin(0L);
		}
		// 将机器人保存到缓存
		cache.put(SysConstant.ROBOT + entity.getId(), entity);
//		cache.put(SysConstant.ROBOT+"time"+ entity.getId(), System.currentTimeMillis());
		// 将机器人加入待更新状态,更新时间为当前时间
		result.setToken(token);
//		log.info("[distributionRobot===>分配机器人] result {}", result);
		return result;

	}

	// 归还机器人
	@Override
	@Transactional
	public void recyclingRobot(RobotParam entity) {
//		log.info("[recyclingRobot===>归还机器人====>开始] RobotParam {}", entity);
		Long robotId = Long.valueOf(JwtUtil.getUserId(entity.getToken()));
		RobotEntity robot = (RobotEntity) cache.get(SysConstant.ROBOT + robotId);
//		log.info("[recyclingRobot===>归还机器人] robot {}, id {}", robot, robotId);
		if (null != robot) {// 如果缓存获取不到就从数据库获取
//			log.info("[recyclingRobot===>归还机器人begin] 机器人状态{},", robot.getStatus());
			if (robot.getStatus()) {
				Long robotCoin = robot.getCoin();
				Long variableCoin = 0L;
				variableCoin = entity.getCoin() - robotCoin;// 变化了的金币数量
				robot.setProfitCoin(robot.getProfitCoin() + variableCoin);
				robot.setUpdateTime(new Date());
				robot.setEnable(SysConstant.ROBOT_ENABLE_TRUE);
				robot.setStatus(SysConstant.ROBOT_STATUS_FALSE);
				// cache.put(SysConstant.ROBOT + robotId, robot);// 更新缓存
				cache.remove(SysConstant.ROBOT + robotId);// 删除机器人缓存
//				Object obj = cache.get(SysConstant.ROBOT+"time"+ robotId);
//				if(obj!=null) {
//					cache.remove(SysConstant.ROBOT+"time"+ robotId);// 删除机器人缓存
//					long s=System.currentTimeMillis()-Long.parseLong(obj.toString());
//					log.info("[归还机器人] robotId {} time {}",robotId,s);
//				}
				synUpdateRobot(robot);//同步更新
//				ConcurrentLinked.putModify(robot);// 将归还的机器人放入待修改队列
				ConcurrentLinked.putIdle(robot);// 将机器人放入空闲队列中
//				log.info("[recyclingRobot===>归还机器人] end {}");
			}

		}
//		log.info("[recyclingRobot===>归还机器人====>结束] robot {}", robot);
	}

	// =====================================获取大厅ip===============================
	public RobotResultParam setHallIp(Long hallId) {
		RobotResultParam result = new RobotResultParam();
		// 如果缓存中存在，就从缓存中获取
		if (null == cache.get(SysConstant.HALL_ID + hallId)) {
			boolean isOk = false;
			String HallIp = null;
			Integer Port = null;
			try {
				if ("localhost".equals(HallUrlConstant.getHALL_URL())) {
					HallIp = "192.168.0.181";
					Port = 8500;
					return result;
				}
				String url = HallUrlConstant.getHALL_URL() + hallId;
				String body = HttpRequest.get(url).timeout(3000).execute().body();
				JSONObject robj = JSON.parseObject(body);
				if (robj.getInteger("Result") == 0) {
					String hallIp = robj.getString("HallIp");
					String hallIpArr[] = hallIp.split(":");
					if (hallIpArr.length == 1) {
						HallIp = hallIpArr[0];
						Port = null;
						isOk = true;
					} else if (hallIpArr.length == 2) {
						HallIp = hallIpArr[0];
						Port = Integer.parseInt(hallIpArr[1]);
						isOk = true;
					}
				}
			} catch (Exception e) {
				// log.error("获取大厅ip失败 error {}", e);
				String hallIp = SysConstant.hallAddressMap.get(hallId.intValue());
				if (hallIp != null) {
					String hallIpArr[] = hallIp.split(":");
					if (hallIpArr.length == 1) {
						HallIp = hallIpArr[0];
						Port = null;
						isOk = true;
					} else if (hallIpArr.length == 2) {
						HallIp = hallIpArr[0];
						Port = Integer.parseInt(hallIpArr[1]);
						isOk = true;
					}
				}
			}
			if (!isOk) {
				throw new RRException(ErrorCode.RobotCode.USER_LOGIN_GET_HALL_IP_ERRO.getErrMsg(),
						ErrorCode.RobotCode.USER_LOGIN_GET_HALL_IP_ERRO.getCode());
			}
			result.setHallIp(HallIp);
			result.setPort(Port);
			HallResultParam hallEntity = new HallResultParam();
			hallEntity.setHallIp(HallIp);
			hallEntity.setPort(Port);
			cache.put(SysConstant.HALL_ID + hallId, hallEntity);
		} else {
			HallResultParam hallEntity = (HallResultParam) cache.get(SysConstant.HALL_ID + hallId);
			result.setHallIp(hallEntity.getHallIp());
			result.setPort(hallEntity.getPort());
		}

		return result;

	}
	/**
	 * 同步更新
	 * @param robot
	 */
	private void synUpdateRobot(RobotEntity robot) {
		if(!this.updateRobot(robot)) {
			log.error("[修改机器人] 失败 robotId {}", robot.getId());
			log.error("[修改机器人] 失败 robot {}", robot);
		}
	}

	// 定时回收未归还机器人
	@Override
	public void taskRecyclingRobot() {
		// 获取游戏列表
		JSONObject returnObj = GameInterfaceService.robotGameList(null);
//		log.info("[taskRecyclingRobot] returnObj {}", returnObj);
		if (returnObj.getInteger("code") != null && returnObj.getInteger("code") == ResultDef.SUCCESS) {
			JSONArray arry = returnObj.getJSONArray("gamelist");
//			log.info("[taskRecyclingRobot] 有机器人的游戏列表 {}", arry);
			for (Object gameObj : arry) {
				if (null != gameObj) {
					JSONObject gameJson = (JSONObject) gameObj;
					Long gameId = gameJson.getLong("gameId");
					Long gradeId = gameJson.getLong("gradeId");
					String verificationUrl = gameJson.getString("checkRobotUrl");// 获取验证用户的路径
					if (null != gameId && null != gradeId && !StringUtils.isEmpty(verificationUrl)) {
						// 获取到在线时常超过3小时的机器人列表
						List<JSONObject> list = robotList(gameId, gradeId, verificationUrl);
						if (!CollectionUtil.isEmpty(list)) {
							// 开始计算机器人的盈亏
							getRobotGameRecord(list);
						}

					}
				}

			}
		}

	}

	@SuppressWarnings("unchecked")
	public List<JSONObject> robotList(Long gameId, Long gradeId, String verificationUrl) {
//		log.info("[robotList====>获取超时机器人列表] gameId {},gradeId {},gradeId {}", gameId, gradeId, verificationUrl);
		// 获取到该游戏所有超时机器人
		List<RobotEntity> list = baseMapper.taskRecyclingRobot(gameId, gradeId);
//		log.info("[robotList====>获取超时机器人列表] 验证前机器人数量 {}", list.size());
		if (!CollectionUtil.isEmpty(list)) {
			// 验证机器人，将不可下线的机器人在列表中移除
			String gameResult = HttpRequest.put(verificationUrl).header("token", SysConstant.TOKEN)
					.body(JSON.toJSONString(list)).execute().body();
			JSONObject returnObj = JSON.parseObject(gameResult);
			if (returnObj.getInteger("code") != null && returnObj.getInteger("code") == ResultDef.SUCCESS) {
				List<JSONObject> returnList = (List<JSONObject>) returnObj.get("data");
//				log.info("[robotList====>获取超时机器人列表] 验证后机器人数量 {}", returnList.size());
				return returnList;
			} else {
				throw new RRException(ErrorCode.RobotCode.ROBOT_CHECK_ERRO.getErrMsg(),
						ErrorCode.RobotCode.ROBOT_CHECK_ERRO.getCode());
			}
		}
		return null;

	}

	// 计算所有要下线机器人游戏记录的输赢，并修改机器人状态-----该接口是验证机器人是否存在游戏中
	public void getRobotGameRecord(List<JSONObject> list) {
//		log.info("[getRobotGameRecord====>定时结算机器人开始] 需要回收的机器人数量 {}", list.size());
		if (!CollectionUtils.isEmpty(list)) {
			for (JSONObject jsobj : list) {
//				log.info("[getRobotGameRecord====>循环结算] 开始结算机器人 {} ", jsobj);
				// 查询用户在上次进入游戏之后的所有游戏记录
				if (null == jsobj) {
					log.error("机器人为空");
					continue;
				}
				GameRecordEntity entity = new GameRecordEntity();
				entity.setUserId(jsobj.getLong("id"));
				entity.setUserAccount(jsobj.getString("name"));
				entity.setGameId(jsobj.getLong("gameId"));
				entity.setGradeId(jsobj.getLong("gradeId"));
//				log.info("[getRobotGameRecord====>循环结算] 游戏记录开始时间 {} ,游戏记录结束时间 {}", jsobj.get("updateTime"),
//						DateUtils.formatTime(new Date()));
				List<GameRecordEntity> records = gameRecordService.selectList(
						new EntityWrapper<GameRecordEntity>(entity).ge("create_time", jsobj.get("updateTime")));
//				log.info("[getRobotGameRecord====>循环结算] 游戏总条数 {} ", records.size());
				Long PrizeCoin = 0L;
				if (!CollectionUtil.isEmpty(records)) {
					for (GameRecordEntity record : records) {
						PrizeCoin -= record.getPrizeCoins();// 取代理盈利的反数就是用户的盈利额
					}
				}
//				log.info("[getRobotGameRecord====>循环结算] 游戏总盈利 {} ", PrizeCoin);
				// 从缓存获取机器人，如果缓存中不存在机器人那么就不在游戏中
				RobotEntity robot = (RobotEntity) cache.get(SysConstant.ROBOT + jsobj.getLong("id"));
				if (null != robot) {
//					log.info("[getRobotGameRecord====>循环结算] 开始回收机器人 {} ", robot);
					// 将可回收机器人放入机器人回收接口
					RobotParam param = new RobotParam();
					param.setCoin(robot.getCoin() + PrizeCoin);
					param.setToken(JwtUtil.createJWT(robot.getId()));
					recyclingRobot(param);
//					log.info("[getRobotGameRecord====>循环结算] 回收机器人完毕 {} ");
				}
			}
		}
//		log.info("[getRobotGameRecord====>定时结算机器人完成]");

	}

	@Override
	public Long countProfitCoin(RobotEntity entity) {
		Long num = baseMapper.countProfitCoin(entity);
		return num;
	}

	// 手动回收机器人
	@Override
	public void taskRobot(String[] ids, Long gameId, Long gradeId) {
//		log.info("[taskRobot=====>手动回收机器人开始] ids {}, gameId {}, gradeId {}", ids, gameId, gradeId);
		JSONArray list = new JSONArray();
		for (String id : ids) {
			RobotEntity robot = (RobotEntity) cache.get(SysConstant.ROBOT + id);
//			log.info("[taskRobot=====>手动回收机器人开始] robot缓存 {}", robot);
			if (null != robot) {
				list.add(robot.getToken());
			}
		}
//		log.info("[taskRobot==>手动回收机器人===>游戏中的机器人TOKEN] 可回收的机器人个数 {}", list.size());
		if (!CollectionUtils.isEmpty(list)) {
			// 获取游戏大厅接口，获取到游戏列表
			JSONObject obj = new JSONObject();
			obj.put("gameId", gameId);
			obj.put("gradeId", gradeId);
			JSONObject returnObj = GameInterfaceService.robotGameList(obj);
			if (null != returnObj.getInteger("code") && ResultDef.SUCCESS == returnObj.getInteger("code")) {
				if (!CollectionUtil.isEmpty(returnObj.getJSONArray("gamelist"))) {
					// 获取到回收机器人接口
					if (null != returnObj.getJSONArray("gamelist").getJSONObject(0) && !StringUtils.isEmpty(
							returnObj.getJSONArray("gamelist").getJSONObject(0).getString("offlineRobotUrl"))) {
						// 那么就通过路径获取机器人配置
//						log.info("[taskRobot==>手动回收机器人====>机器人回收路径] offlineRobotUrl{}",
//								returnObj.getJSONArray("gamelist").getJSONObject(0).getString("offlineRobotUrl"));
						// 该接口是返回不存在于游戏中的问题机器人
						JSONObject robotObj = GameInterfaceService.takeRobot(
								returnObj.getJSONArray("gamelist").getJSONObject(0).getString("offlineRobotUrl"),
								list.toString());
						if (null != robotObj.getInteger("code") && ResultDef.SUCCESS == robotObj.getInteger("code")) {
							// 开始计算机器人盈利情况
							updateRobotForToken(robotObj.getJSONArray("data"));
						} else {
							log.info("[taskRobot==>手动回收机器人====>机器人回收路径] offlineRobotUrl{}",
									returnObj.getJSONArray("gamelist").getJSONObject(0).getString("offlineRobotUrl"));
							// 如果请求失败
							throw new RRException(robotObj.getString("msg"), robotObj.getInteger("code"));
						}
					}
				}

			} else {
				// 如果请求失败
				throw new RRException(returnObj.getString("msg"), returnObj.getInteger("code"));
			}
		}
//		log.info("[taskRobot=====>手动回收机器人完成]===========手动回收机器人完成=========");
	}

	// 修改机器人盈利情况
	private void updateRobotForToken(JSONArray arry) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//		log.info("[updateRobotForToken] 要修改的机器人数量 {}, arry {}", arry.size(), arry);
		if (!CollectionUtil.isEmpty(arry)) {
			for (Object token : arry) {
//				log.info("[updateRobotForToken===>准备结算异常机器人] token{}", token);
				if (null == token || StringUtils.isEmpty(token.toString())) {
					log.error("机器人token为null");
					continue;
				}
				Long id = Long.valueOf(JwtUtil.getUserId(token.toString()));
				// 从缓存获取机器人，如果缓存中不存在机器人那么就不在游戏中
				RobotEntity robot = (RobotEntity) cache.get(SysConstant.ROBOT + id);
				log.info("[updateRobotForToken===>准备结算异常机器人] id {}", id);
				if (null != robot) {
//					log.info("[updateRobotForToken===>开始结算异常机器人] robot {}", robot);
					GameRecordEntity entity = new GameRecordEntity();
					entity.setUserId(robot.getId());
					entity.setUserAccount(robot.getName());
					entity.setGameId(robot.getGameId());
					entity.setGradeId(robot.getGradeId());
					String time = null;
					if (null != robot.getUpdateTime()) {
						time = format.format(robot.getUpdateTime());
					} else {
						time = format.format(robot.getCreateTime());
					}
					List<GameRecordEntity> records = gameRecordService
							.selectList(new EntityWrapper<GameRecordEntity>(entity).ge("create_time", time));
					Long prizeCoin = 0L;
					if (!CollectionUtil.isEmpty(records)) {
						for (GameRecordEntity record : records) {
							prizeCoin -= record.getPrizeCoins();// 取代理盈利的反数就是用户的盈利额
						}
					}
					// 将可回收机器人放入机器人回收接口
					RobotParam param = new RobotParam();
					param.setCoin(robot.getCoin() + prizeCoin);
					param.setToken(JwtUtil.createJWT(robot.getId()));
					recyclingRobot(param);
//					log.info("[updateRobotForToken===>异常机器人结算成功] robot {}", robot);
				}

			}
		}
//		log.info("[updateRobotForToken===>异常机器人结算结束]=======================异常机器人结算结束=========================");
	}

	@Override
	public boolean updateRobot(RobotEntity robot) {
		return baseMapper.updateRobot(robot);
	}
}

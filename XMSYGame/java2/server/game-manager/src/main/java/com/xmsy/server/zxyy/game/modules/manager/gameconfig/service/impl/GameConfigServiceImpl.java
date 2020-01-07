package com.xmsy.server.zxyy.game.modules.manager.gameconfig.service.impl;



import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xmsy.server.zxyy.game.common.utils.Constant;
import com.xmsy.server.zxyy.game.common.utils.MathUtil;
import com.xmsy.server.zxyy.game.modules.manager.gameconfig.dao.GameConfigDao;
import com.xmsy.server.zxyy.game.modules.manager.gameconfig.entity.GameConfigDetailParam;
import com.xmsy.server.zxyy.game.modules.manager.gameconfig.entity.GameConfigEntity;
import com.xmsy.server.zxyy.game.modules.manager.gameconfig.entity.IntervalGameRateParam;
import com.xmsy.server.zxyy.game.modules.manager.gameconfig.service.GameConfigService;
import com.xmsy.server.zxyy.game.modules.web.gameconfig.param.GameCurrentStockResult;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service("gameConfigService")
public class GameConfigServiceImpl extends ServiceImpl<GameConfigDao, GameConfigEntity> implements GameConfigService {

	@Override
	public List<Map<String, Object>> getGameConfigList(long gameId) {
		GameConfigEntity gameConfig = new GameConfigEntity();
		gameConfig.setGameId(gameId);
		List<Map<String, Object>> configList = new ArrayList<>();
		List<GameConfigEntity> gameConfigList = this.selectList(new EntityWrapper<GameConfigEntity>(gameConfig));
		if(gameConfigList !=null && !gameConfigList.isEmpty()) {
			Map<String, Object> config = null;
			for(GameConfigEntity gameConfigEntity:gameConfigList) {
				config = new HashMap<>();
				if(Constant.GameConfigName.GAMERATE.getValue().equals(gameConfigEntity.getName())
					||Constant.GameConfigName.MINIRATE.getValue().equals(gameConfigEntity.getName())) {
					config.put(gameConfigEntity.getName(), gameConfigEntity.getVal());
				}else {
					config.put(gameConfigEntity.getName(), gameConfigEntity.getVal());//这里做了修改gameConfigEntity.getVal().longValue()
				}
				configList.add(config);
			}
		}
		return configList;
	}
	@Override
	public Map<String, Object> getGameConfig(long gameId) {
		GameConfigEntity gameConfig = new GameConfigEntity();
		gameConfig.setGameId(gameId);
		Map<String, Object> config =  new HashMap<>();
		Map<String, Object> hierarchyConfig = null;
		Map<String, Object> intervalConfig =  null;
		List<Map<String, Object>> hierarchyRateList =new ArrayList<>();
		List<Map<String, Object>> intervalRateList =new ArrayList<>();
		List<GameConfigEntity> gameConfigList = this.selectList(new EntityWrapper<GameConfigEntity>(gameConfig).orderBy("name,start_val", true));
		if(gameConfigList !=null && !gameConfigList.isEmpty()) {
			for(GameConfigEntity gameConfigEntity:gameConfigList) {
				if(StringUtils.isBlank(gameConfigEntity.getVal())) {
					continue;
				}
				if(Constant.GameConfigName.MINIRATE.getValue().equals(gameConfigEntity.getName())) {//小游戏胜率BigDecimal
					config.put(gameConfigEntity.getName(), MathUtil.getBigDecimal(gameConfigEntity.getVal()));
				}else if(Constant.GameConfigName.HIERARCHYGAMERATE.getValue().equals(gameConfigEntity.getName())) {//层级游戏胜率BigDecimal
					hierarchyConfig = new HashMap<>();
					hierarchyConfig.put("hierarchyId", gameConfigEntity.getStartVal());
					hierarchyConfig.put("rate",  MathUtil.getBigDecimal(gameConfigEntity.getVal()));
					hierarchyRateList.add(hierarchyConfig);
				}else if(Constant.GameConfigName.INTERVALGAMERATE.getValue().equals(gameConfigEntity.getName())) {//区间游戏胜率BigDecimal
					intervalConfig = new HashMap<>();
					intervalConfig.put("intervalStart", gameConfigEntity.getStartVal());
					intervalConfig.put("intervalEnd", gameConfigEntity.getEndVal());
					intervalConfig.put("rate", MathUtil.getBigDecimal(gameConfigEntity.getVal()));
					intervalRateList.add(intervalConfig);
				}else if(Constant.GameConfigName.PROFITVALUE.getValue().equals(gameConfigEntity.getName())) {//盈利值BigDecimal
					config.put(gameConfigEntity.getName(), MathUtil.getBigDecimal(gameConfigEntity.getVal()));
				}else if(Constant.GameConfigName.TENCHIPS.getValue().equals(gameConfigEntity.getName())) {//10个筹码值  数组
					config.put(gameConfigEntity.getName(),StringToLong(gameConfigEntity.getVal().split(",")));
				}else if(Constant.GameConfigName.DIFFERENTBETAREA.getValue().equals(gameConfigEntity.getName())) {//不同下注区域  数组
					config.put(gameConfigEntity.getName(),StringToLong(gameConfigEntity.getVal().split(",")));
				}else {
					config.put(gameConfigEntity.getName(),MathUtil.getBigDecimal(gameConfigEntity.getVal()).longValue());//其他转为Long
				}
			}
			if(!hierarchyRateList.isEmpty()) {
				config.put("hierarchyGameRate", hierarchyRateList);
			}
			if(!intervalRateList.isEmpty()) {
				config.put("intervalGameRate", intervalRateList);
			}
		}
		return config;
	}
	
	// string数组转Long数组
	public Long[] StringToLong(String[] arrs) {
		Long[] longArr = new Long[arrs.length];
		for (int i = 0; i < arrs.length; i++) {
			longArr[i] = MathUtil.getBigDecimal(arrs[i]).longValue();
		}
		return longArr;
	}
	
	
	@Override
	public void updateCurrentStock(GameCurrentStockResult gameCurrentStock) {
		GameConfigEntity gameConfigEntity = new GameConfigEntity();
		gameConfigEntity.setGameId(gameCurrentStock.getGameId());
		gameConfigEntity.setVal(gameCurrentStock.getCurrentStock().toString());//这里做了修改gameCurrentStock.getCurrentStock()
		gameConfigEntity.setName(Constant.GameConfigName.CURRENTSTOCK.getValue());
		if(this.baseMapper.updateGameConfigValForIncrement(gameConfigEntity)<=0) {
			log.info("当前库存 更新失败 {}",gameCurrentStock);
		}
		
		GameConfigEntity queryGameConfigEntity  = new GameConfigEntity();
		queryGameConfigEntity.setGameId(gameCurrentStock.getGameId());
		queryGameConfigEntity.setName(Constant.GameConfigName.CURRENTSTOCK.getValue());
		GameConfigEntity newGameConfig = this.selectOne(new EntityWrapper<GameConfigEntity>(queryGameConfigEntity));
		
		if(newGameConfig==null) {
			gameCurrentStock.setCurrentStock(BigDecimal.ZERO);
		}else {
			gameCurrentStock.setCurrentStock(MathUtil.getBigDecimal(newGameConfig.getVal()));
		}
		
	}

	@Override
	public void physicsDeleteById(Long id) {
		this.baseMapper.physicsDeleteById(id);
		
	}

	@Override
	public void updateGameConfigVal(GameConfigEntity entity) {
		this.baseMapper.updateGameConfigVal(entity);
	}
	
	
	@Override
	public void updateCumulativeStock(GameCurrentStockResult gameCurrentStock) {
		// 通过   游戏id  和 累计库存      查找累计库存的值
		GameConfigEntity gameConfig = this.baseMapper.selectOne(new GameConfigEntity()
				.setGameId(gameCurrentStock.getGameId()).setName(Constant.GameConfigName.CUMULATIVESTOCK.getValue()));
		if (gameConfig == null) {
			// 插入一条累计库存的数据
			GameConfigEntity insertGameConfig = new GameConfigEntity();
			insertGameConfig.setGameId(gameCurrentStock.getGameId())//游戏id
			.setName(Constant.GameConfigName.CUMULATIVESTOCK.getValue())//累计库存名称
			.setVal(gameCurrentStock.getCurrentStock().abs().toString());//累计库存值
			if(this.baseMapper.insert(insertGameConfig)<=0) {
				log.info("累计库存 新增失败 {}",gameCurrentStock);
			}
			
		} else {// 取出累计库存，并把当前库存的 |绝对值| 增加到 累计库存 然后修改
			BigDecimal total = new BigDecimal(gameConfig.getVal()).add(MathUtil.getBigDecimal(gameCurrentStock.getCurrentStock()).abs());
			gameConfig.setVal(total.toString());
			if(this.baseMapper.updateById(gameConfig)<=0) {
				log.info("累计库存 更新失败 {}",gameCurrentStock);
			}
		}
		
	}
	/**
	 * 批量更新或插入当前游戏id的所有数据（过时不用）
	 * @param gameConfigList
	 */
	@Override
	@Transactional
	public void updateAllGameConfig(GameConfigDetailParam GameConfigDetail,List<GameConfigEntity> gameConfigList) {
		GameConfigEntity entity = new GameConfigEntity();
		entity.setGameId(GameConfigDetail.getGameId());//游戏id
		Boolean flag1=true,flag2=true,flag3=true,flag4=true,flag5=true,flag6=true,flag7=true,flag8=true,flag9=true,flag10=true,
				 flag11=true,flag12=true,flag13=true,flag14=true;
		for (GameConfigEntity gameConfigEntity : gameConfigList) {// 遍历对比一个个判断
			if (Constant.GameConfigName.INITIALSTOCK.getValue().equals(gameConfigEntity.getName())) {// 初始库存
				gameConfigEntity.setVal(GameConfigDetail.getInitialStock());
				flag1=false;
				this.baseMapper.updateGameConfigVal(gameConfigEntity);
				continue;
			}else if (Constant.GameConfigName.CURRENTSTOCK.getValue().equals(gameConfigEntity.getName())) {// 当前库存
				flag2=false;
				gameConfigEntity.setVal(GameConfigDetail.getCurrentStock());
				this.baseMapper.updateGameConfigVal(gameConfigEntity);
				continue;
			}else if (Constant.GameConfigName.GOALSTOCK.getValue().equals(gameConfigEntity.getName())) {// 目标库存
				gameConfigEntity.setVal(GameConfigDetail.getGoalStock());
				flag3=false;
				this.baseMapper.updateGameConfigVal(gameConfigEntity);
				continue;
			}else if (Constant.GameConfigName.MAXROBOT.getValue().equals(gameConfigEntity.getName())) {// 最多机器人数量
				gameConfigEntity.setVal(GameConfigDetail.getMaxRobot());
				flag4=false;
				this.baseMapper.updateGameConfigVal(gameConfigEntity);
				continue;
			}else if (Constant.GameConfigName.MINIRATE.getValue().equals(gameConfigEntity.getName())) {// 小游戏胜率BigDecimal
				gameConfigEntity.setVal(GameConfigDetail.getMiniRate());
				flag5=false;
				this.baseMapper.updateGameConfigVal(gameConfigEntity);
				continue;
			} else if (Constant.GameConfigName.ROBOTWAIT.getValue().equals(gameConfigEntity.getName())) {// 机器人等待时间
				gameConfigEntity.setVal(GameConfigDetail.getRobotWait());
				flag6=false;
				this.baseMapper.updateGameConfigVal(gameConfigEntity);
				continue;
			} else if (Constant.GameConfigName.CUMULATIVESTOCK.getValue().equals(gameConfigEntity.getName())) {// 累计库存
				gameConfigEntity.setVal(GameConfigDetail.getCumulativeStock());
				flag7=false;
				this.baseMapper.updateGameConfigVal(gameConfigEntity);
				continue;
			} else if (Constant.GameConfigName.PROFITVALUE.getValue().equals(gameConfigEntity.getName())) {// 盈利值BigDecimal
				gameConfigEntity.setVal(GameConfigDetail.getProfitValue());
				flag8=false;
				this.baseMapper.updateGameConfigVal(gameConfigEntity);
				continue;
			} else if (Constant.GameConfigName.LIMITREDMAX.getValue().equals(gameConfigEntity.getName())) {// 限红最大值
				gameConfigEntity.setVal(GameConfigDetail.getLimitRedMax());
				flag9=false;
				this.baseMapper.updateGameConfigVal(gameConfigEntity);
				continue;
			} else if (Constant.GameConfigName.VIPLIMITRED.getValue().equals(gameConfigEntity.getName())) {// vip限红
				gameConfigEntity.setVal(GameConfigDetail.getVipLimitRed());
				flag10=false;
				this.baseMapper.updateGameConfigVal(gameConfigEntity);
				continue;
			} else if (Constant.GameConfigName.CHIPMIN.getValue().equals(gameConfigEntity.getName())) {// 筹码最小值
				gameConfigEntity.setVal(GameConfigDetail.getChipMin());
				flag11=false;
				this.baseMapper.updateGameConfigVal(gameConfigEntity);
				continue;
			} else if (Constant.GameConfigName.CHIPMAX.getValue().equals(gameConfigEntity.getName())) {// 筹码最大值
				gameConfigEntity.setVal(GameConfigDetail.getChipMax());
				flag12=false;
				this.baseMapper.updateGameConfigVal(gameConfigEntity);
				continue;
			} else if (Constant.GameConfigName.TENCHIPS.getValue().equals(gameConfigEntity.getName())) {// 10个筹码值 数组
				gameConfigEntity.setVal(GameConfigDetail.getTenChips());
				flag13=false;
				this.baseMapper.updateGameConfigVal(gameConfigEntity);
				continue;
			}  else if (Constant.GameConfigName.DIFFERENTBETAREA.getValue().equals(gameConfigEntity.getName())) {// 不同下注区域 数组																									
				gameConfigEntity.setVal(GameConfigDetail.getDifferentBetArea());
				flag14=false;
				this.baseMapper.updateGameConfigVal(gameConfigEntity);
				continue;
			}
		}
		GameConfigEntity insertGameConfig = new GameConfigEntity();
		insertGameConfig.setGameId(GameConfigDetail.getGameId());
		if(flag1) {	
			insertGameConfig.setName(Constant.GameConfigName.INITIALSTOCK.getValue());
			insertGameConfig.setVal(GameConfigDetail.getInitialStock());
			insertGameConfig.setDescription("初始库存");
			this.baseMapper.insert(insertGameConfig);
		}
		if(flag2) {
			insertGameConfig.setName(Constant.GameConfigName.CURRENTSTOCK.getValue());
			insertGameConfig.setVal(GameConfigDetail.getCumulativeStock());
			insertGameConfig.setDescription("当前库存");
			this.baseMapper.insert(insertGameConfig);
		}
		if(flag3) {
			insertGameConfig.setName(Constant.GameConfigName.GOALSTOCK.getValue());
			insertGameConfig.setVal(GameConfigDetail.getGoalStock());
			insertGameConfig.setDescription("目标库存");
			this.baseMapper.insert(insertGameConfig);
		}
		if(flag4) {
			insertGameConfig.setName(Constant.GameConfigName.MAXROBOT.getValue());
			insertGameConfig.setVal(GameConfigDetail.getMaxRobot());
			insertGameConfig.setDescription("最多机器人数量");
			this.baseMapper.insert(insertGameConfig);
		}
		if(flag5) {
			insertGameConfig.setName(Constant.GameConfigName.MINIRATE.getValue());
			insertGameConfig.setVal(GameConfigDetail.getMiniRate());
			insertGameConfig.setDescription("小游戏胜率");
			this.baseMapper.insert(insertGameConfig);
		}
		if(flag6) {
			insertGameConfig.setName(Constant.GameConfigName.ROBOTWAIT.getValue());
			insertGameConfig.setVal(GameConfigDetail.getRobotWait());
			insertGameConfig.setDescription("机器人等待时间");
			this.baseMapper.insert(insertGameConfig);
		}
		if(flag7) {
			insertGameConfig.setName(Constant.GameConfigName.CUMULATIVESTOCK.getValue());
			insertGameConfig.setVal(GameConfigDetail.getCumulativeStock());
			insertGameConfig.setDescription("累计库存");
			this.baseMapper.insert(insertGameConfig);
		}
		
		if(flag8) {
			insertGameConfig.setName(Constant.GameConfigName.PROFITVALUE.getValue());
			insertGameConfig.setVal(GameConfigDetail.getProfitValue());
			insertGameConfig.setDescription("盈利值");
			this.baseMapper.insert(insertGameConfig);
		}
		if(flag9) {
			insertGameConfig.setName(Constant.GameConfigName.LIMITREDMAX.getValue());
			insertGameConfig.setVal(GameConfigDetail.getLimitRedMax());
			insertGameConfig.setDescription("限红最大值");
			this.baseMapper.insert(insertGameConfig);
		}
		if(flag10) {
			insertGameConfig.setName(Constant.GameConfigName.VIPLIMITRED.getValue());
			insertGameConfig.setVal(GameConfigDetail.getVipLimitRed());
			insertGameConfig.setDescription("vip限红");
			this.baseMapper.insert(insertGameConfig);
		}
		if(flag11) {
			insertGameConfig.setName(Constant.GameConfigName.CHIPMIN.getValue());
			insertGameConfig.setVal(GameConfigDetail.getChipMin());
			insertGameConfig.setDescription("筹码最小值");
			this.baseMapper.insert(insertGameConfig);
		}
		if(flag12) {
			insertGameConfig.setName(Constant.GameConfigName.CHIPMAX.getValue());
			insertGameConfig.setVal(GameConfigDetail.getChipMax());
			insertGameConfig.setDescription("筹码最大值");
			this.baseMapper.insert(insertGameConfig);
		}
		if(flag13) {
			insertGameConfig.setName(Constant.GameConfigName.TENCHIPS.getValue());
			insertGameConfig.setVal(GameConfigDetail.getTenChips());
			insertGameConfig.setDescription("十个筹码值");
			this.baseMapper.insert(insertGameConfig);
		}
		if(flag14) {
			insertGameConfig.setName(Constant.GameConfigName.DIFFERENTBETAREA.getValue());
			insertGameConfig.setVal(GameConfigDetail.getDifferentBetArea());
			insertGameConfig.setDescription("不同下注区域");
			this.baseMapper.insert(insertGameConfig);
		}
	}
	
	/**
	 * 保存游戏区间
	 */
	@Override
	@Transactional
	public void updateIntervalGameRate(IntervalGameRateParam intervalGameRateParam) {
		Long[] id = intervalGameRateParam.getId();
		String[] starvalArray = intervalGameRateParam.getStartVal().split(",");
		String[] endValArray  = intervalGameRateParam.getEndVal().split(",");
		String[] intervalGameRateArray  = intervalGameRateParam.getIntervalGameRate().split(",");
		
		for(int i=0;i<id.length;i++) {
			this.baseMapper.physicsDeleteById(id[i]);//物理删除
		}
		for(int i=0;i<intervalGameRateArray.length;i++) {//
			GameConfigEntity entity = new GameConfigEntity(); 
			if(i<id.length) {
				entity.setId(id[i]);
			}
			entity.setGameId(intervalGameRateParam.getGameId());
			entity.setStartVal(MathUtil.getBigDecimal(starvalArray[i]).longValue());
			entity.setEndVal(MathUtil.getBigDecimal(endValArray[i]).longValue());
			entity.setVal(intervalGameRateArray[i]);
			entity.setName(Constant.GameConfigName.INTERVALGAMERATE.getValue());
			entity.setDescription("库存区间游戏概率");
			entity.setCreateTime(new Date());
			entity.setVersion(0);
			entity.setUpdateTime(new Date());
			this.baseMapper.saveGameConfig(entity);
		}
	}
	/**
	 * 批量更新或插入当前游戏id的所有数据最终版
	 * @param sysDictionaryList
	 */
	@Override
	@Transactional
	public Long updateAllGameConfigPerfect(List<JSONObject> jsonObjectList) {
		long gameId=0;//获取游戏id  通知游戏服使用
		//遍历数据字典集合
		GameConfigEntity gameConfig;
		for(JSONObject jsonObject:jsonObjectList) {
			Map<String, Object> map = jsonObject.getInnerMap();
			//对值做校验
			dataVerification(map);
			//如果数据字典里的gameConigId存在   就更新数据
			if(null!=map.get("gameConigId")) {
				gameId = MathUtil.getBigDecimal(map.get("gameConigGameId")).longValue();//获取游戏id  通知游戏服使用
				gameConfig=new GameConfigEntity();
				gameConfig.setId(MathUtil.getBigDecimal(map.get("gameConigId")).longValue());//id
				gameConfig.setGameId(MathUtil.getBigDecimal(map.get("gameConigGameId")).longValue());//游戏id
				gameConfig.setName(map.get("code").toString());//属性名称
				gameConfig.setVal(map.get("val").toString());//属性的  值
				gameConfig.setDescription(map.get("name").toString());//属性描述
				this.baseMapper.updateById(gameConfig);
			}else {
				gameConfig=new GameConfigEntity();
				gameConfig.setGameId(MathUtil.getBigDecimal(map.get("gameConigGameId")).longValue());//游戏id
				gameConfig.setName(map.get("code").toString());//属性名称
				if(null!=map.get("val")) {
					gameConfig.setVal(map.get("val").toString());//属性的  值
				}
				gameConfig.setDescription(map.get("name").toString());//属性描述
				gameId = MathUtil.getBigDecimal(map.get("gameConigGameId")).longValue();//获取游戏id  通知游戏服使用
				this.baseMapper.insert(gameConfig);
			}
		}
		return gameId;
		
	}
	//数据校验
	public void dataVerification(Map<String, Object> map) {
		//库存要*100进数据库
		if(null!=map.get("val")) {
			if (Constant.GameConfigName.INITIALSTOCK.getValue().equals(map.get("code"))
					|| Constant.GameConfigName.GOALSTOCK.getValue().equals(map.get("code"))
					|| Constant.GameConfigName.CURRENTSTOCK.getValue().equals(map.get("code"))
					|| Constant.GameConfigName.CUMULATIVESTOCK.getValue().equals(map.get("code"))) {
				
					map.put("val", MathUtil.getBigDecimal(map.get("val")).multiply(new BigDecimal(100)).toString()) ;
			}else if(Constant.GameConfigName.MINIRATE.getValue().equals(map.get("code"))) {//小游戏胜率要除以100
					map.put("val", MathUtil.getBigDecimal(map.get("val")).divide(new BigDecimal(100)).toString()) ;
			}else if(Constant.GameConfigName.DIFFERENTBETAREA.getValue().equals(map.get("code"))
					||Constant.GameConfigName.TENCHIPS.getValue().equals(map.get("code"))) {//10个筹码值跟不同下注区域不变
			}else {//其他的做数字校验
				map.put("val", MathUtil.getBigDecimal(map.get("val").toString())) ;
			}
		}
		if(Constant.GameConfigName.MINIRATE.getValue().equals(map.get("code"))&&null==map.get("val")) {//小游戏胜率要默认0.5放进去
			map.put("val",0.5);
		}
	}

	//================================新版页面==========================================
	/**
	 * 游戏信息表  对应的游戏配置的信息  -游戏id
	 * 批量更新或插入当前游戏id的所有数据最终版
	 * @param sysDictionaryList
	 */
	@Override
	@Transactional
	public Long updateAllGameConfigPerfectNew(JSONArray sysDictionaryListJSONArray) {
		long gameId=0;//获取游戏id  通知游戏服使用
		//遍历数据字典集合
		GameConfigEntity gameConfig;
		for(Object Object:sysDictionaryListJSONArray) {
			JSONObject jsonObject=JSON.parseObject(Object.toString());
			Map<String, Object> map = jsonObject.getInnerMap();
			//对值做校验
			dataVerificationNew(map);
			//如果数据字典里的gameConigId存在   就更新数据
			if(null!=map.get("gameConigId")) {
				gameId = MathUtil.getBigDecimal(map.get("gameConigGameId")).longValue();//获取游戏id  通知游戏服使用
				gameConfig=new GameConfigEntity();
				gameConfig.setId(MathUtil.getBigDecimal(map.get("gameConigId")).longValue());//id
				gameConfig.setGameId(MathUtil.getBigDecimal(map.get("gameConigGameId")).longValue());//游戏id
				gameConfig.setName(map.get("code").toString());//属性名称
				gameConfig.setVal(map.get("val").toString());//属性的  值
				gameConfig.setDescription(map.get("name").toString());//属性描述
				this.baseMapper.updateById(gameConfig);
			}else {
				gameConfig=new GameConfigEntity();
				gameConfig.setGameId(MathUtil.getBigDecimal(map.get("gameConigGameId")).longValue());//游戏id
				gameConfig.setName(map.get("code").toString());//属性名称
				if(null!=map.get("val")) {
					gameConfig.setVal(map.get("val").toString());//属性的  值
				}
				gameConfig.setDescription(map.get("name").toString());//属性描述
				gameId = MathUtil.getBigDecimal(map.get("gameConigGameId")).longValue();//获取游戏id  通知游戏服使用
				this.baseMapper.insert(gameConfig);
			}
		}
		return gameId;
	}
	
	// 数据校验  --新版页面
	public void dataVerificationNew(Map<String, Object> map) {
		// 库存要*100进数据库	+限红最大值 筹码最小值  筹码最大值   vip限红
		if (null != map.get("val")) {
			if (Constant.GameConfigName.INITIALSTOCK.getValue().equals(map.get("code"))
					|| Constant.GameConfigName.GOALSTOCK.getValue().equals(map.get("code"))
					|| Constant.GameConfigName.CURRENTSTOCK.getValue().equals(map.get("code"))
					|| Constant.GameConfigName.CUMULATIVESTOCK.getValue().equals(map.get("code"))
					|| Constant.GameConfigName.LIMITREDMAX.getValue().equals(map.get("code"))
					|| Constant.GameConfigName.CHIPMIN.getValue().equals(map.get("code"))
					|| Constant.GameConfigName.CHIPMAX.getValue().equals(map.get("code"))
					|| Constant.GameConfigName.VIPLIMITRED.getValue().equals(map.get("code"))) {
				map.put("val", MathUtil.getBigDecimal(map.get("val")).multiply(new BigDecimal(100)).toString());
			} else if (Constant.GameConfigName.MINIRATE.getValue().equals(map.get("code"))) {// 小游戏胜率要除以100
				map.put("val", MathUtil.getBigDecimal(map.get("val")).divide(new BigDecimal(100)).toString());
			} else if (Constant.GameConfigName.DIFFERENTBETAREA.getValue().equals(map.get("code"))
					|| Constant.GameConfigName.TENCHIPS.getValue().equals(map.get("code"))) {// 10个筹码值跟不同下注区域不变
			} else {// 其他的做数字校验
				map.put("val", MathUtil.getBigDecimal(map.get("val").toString()));
			}
		}
		if (Constant.GameConfigName.MINIRATE.getValue().equals(map.get("code")) && null == map.get("val")) {// 小游戏胜率要默认0.5放进去
			map.put("val", 0.5);
		}
	}
}

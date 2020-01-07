package com.xmsy.server.zxyy.manager.modules.manager.reportgamedaily.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xmsy.server.zxyy.manager.cache.LocalContentCache;
import com.xmsy.server.zxyy.manager.common.utils.MathUtil;
import com.xmsy.server.zxyy.manager.constant.SysConstant;
import com.xmsy.server.zxyy.manager.modules.manager.gamerecord.controller.GameInfoComponent;
import com.xmsy.server.zxyy.manager.modules.manager.reportgamedaily.dao.ReportGameDailyDao;
import com.xmsy.server.zxyy.manager.modules.manager.reportgamedaily.entity.ReportGameDailyEntity;
import com.xmsy.server.zxyy.manager.modules.manager.reportgamedaily.service.ReportGameDailyService;


@Service("reportGameDailyService")
public class ReportGameDailyServiceImpl extends ServiceImpl<ReportGameDailyDao, ReportGameDailyEntity> implements ReportGameDailyService {
	@Autowired
	private LocalContentCache localContentCache;
	
	@Autowired
    private GameInfoComponent gameInfo;
	/**
	 * 获取指定日期 天数的 游戏盈利：   玩家人数（playerNum）  游戏名称（gameName）   游戏盈利（profit）
	 * @return
	 */
	@Override
	public List<List<Map<String, Object>>> sumPlayerProfit(String startDate, String endDate) {
		List<Map<String, Object>> list = this.baseMapper.sumPlayerProfit(startDate, endDate);
		
		List<List<Map<String, Object>>> listTotal=new ArrayList<>();//存放所有的
		List<Map<String, Object>> listRoomMate=new ArrayList<>();//匹配场
		List<Map<String, Object>> listRoomHundred=new ArrayList<>();//百人场
		List<Map<String, Object>> listRoomLaBa=new ArrayList<>();//拉霸场
		List<Map<String, Object>> listRoomFaka=new ArrayList<>();//房卡场
		
		//从缓存中获取  游戏ID对应的  房间id 数据
		//{1=1, 2=1, 3=1, 4=3, 5=3, 6=3, 7=1, 8=1, 9=3, 201=4, 10=3, 202=3, 11=1, 12=3, 13=2, 14=2, 15=3, 16=1, 17=3, 18=2, 19=2}
		Map<Long, String> mapGameRoom = localContentCache.getGameRoomMap();
		if(mapGameRoom==null){
			return listTotal;
		}
	    Map<String,Object> playerInfoMap = new HashMap<>();
	    List<Map<String, Object>> playerInfoList =  new ArrayList<>();
		Integer playerNumListTotal = 0;
    	Integer playerNumListRoomMate = 0;
    	Integer playerNumListRoomHundred = 0;
    	Integer playerNumListRoomLaBa = 0;
    	Integer playerNumListRoomFaka = 0;
    	BigDecimal profitListTotal = new BigDecimal(0);
    	BigDecimal profitListRoomMate = new BigDecimal(0);
    	BigDecimal profitListRoomHundred = new BigDecimal(0);
    	BigDecimal profitListRoomLaBa = new BigDecimal(0);
    	BigDecimal profitListRoomFaka = new BigDecimal(0);
		Set<Long> keySets = mapGameRoom.keySet();
		for(Long key:keySets) {//遍历mapGameRoom,把其房间id与游戏id的对应 跟list里的map关联起来
			for(Map<String, Object> map:list) {
				if(map.get("gameId") != null) {
					map.put("gameName", gameInfo.getGameName((Long) map.get("gameId")));
				}
				if(map.get(SysConstant.GAMEID).equals(key)) {//如果map里的gameId=mapGameRoom里的gameId，获取其房间id
					if(mapGameRoom.get(key).equals(SysConstant.ROOMMATEID)) {//匹配场
						listRoomMate.add(map);
						playerNumListRoomMate += MathUtil.getBigDecimal(map.get("playerNum")).intValue();
						profitListRoomMate = profitListRoomMate.add(MathUtil.getBigDecimal(map.get("profit")));
					}
					if(mapGameRoom.get(key).equals(SysConstant.ROOMHUNDREDId)) {//百人场
						listRoomHundred.add(map);
						playerNumListRoomHundred += MathUtil.getBigDecimal(map.get("playerNum")).intValue();
						profitListRoomHundred = profitListRoomHundred.add(MathUtil.getBigDecimal(map.get("profit")));
					}
					if(mapGameRoom.get(key).equals(SysConstant.LABAID)) {//拉霸场
						listRoomLaBa.add(map);
						playerNumListRoomLaBa += MathUtil.getBigDecimal(map.get("playerNum")).intValue();
						profitListRoomLaBa = profitListRoomLaBa.add(MathUtil.getBigDecimal(map.get("profit")));
					}
					if(mapGameRoom.get(key).equals(SysConstant.ROOMCARDID)) {//房卡场
						listRoomFaka.add(map);
						playerNumListRoomFaka += MathUtil.getBigDecimal(map.get("playerNum")).intValue();
						profitListRoomFaka = profitListRoomFaka.add(MathUtil.getBigDecimal(map.get("profit")));
					}
					playerNumListTotal += MathUtil.getBigDecimal(map.get("playerNum")).intValue();
					profitListTotal = profitListTotal.add(MathUtil.getBigDecimal(map.get("profit")));
					break;
				}
			}
		}
		listTotal.add(listRoomMate);
		listTotal.add(listRoomHundred);
		listTotal.add(listRoomLaBa);
		listTotal.add(listRoomFaka);
		playerInfoMap.put("playerNumListTotal", playerNumListTotal);
		playerInfoMap.put("profitListTotal", profitListTotal);
		playerInfoMap.put("playerNumListRoomMate", playerNumListRoomMate);
		playerInfoMap.put("profitListRoomMate", profitListRoomMate);
		playerInfoMap.put("playerNumListRoomHundred", playerNumListRoomHundred);
		playerInfoMap.put("profitListRoomHundred", profitListRoomHundred);
		playerInfoMap.put("playerNumListRoomLaBa", playerNumListRoomLaBa);
		playerInfoMap.put("profitListRoomLaBa", profitListRoomLaBa);
		playerInfoMap.put("playerNumListRoomFaka", playerNumListRoomFaka);
		playerInfoMap.put("profitListRoomFaka", profitListRoomFaka);
		playerInfoList.add(playerInfoMap);
		listTotal.add(playerInfoList);
		return listTotal;
	}


}

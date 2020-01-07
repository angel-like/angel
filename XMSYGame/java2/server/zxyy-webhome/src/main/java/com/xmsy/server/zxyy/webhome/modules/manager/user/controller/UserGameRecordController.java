package com.xmsy.server.zxyy.webhome.modules.manager.user.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.xmsy.common.define.page.PageParam;
import com.xmsy.server.zxyy.webhome.cache.LocalContentCache;
import com.xmsy.server.zxyy.webhome.common.exception.RRException;
import com.xmsy.server.zxyy.webhome.common.utils.Constant;
import com.xmsy.server.zxyy.webhome.common.utils.ErrorCode;
import com.xmsy.server.zxyy.webhome.common.utils.MathUtil;
import com.xmsy.server.zxyy.webhome.common.utils.PageUtils;
import com.xmsy.server.zxyy.webhome.common.utils.R;
import com.xmsy.server.zxyy.webhome.common.utils.SpringContextUtils;
import com.xmsy.server.zxyy.webhome.constant.GameEnum;
import com.xmsy.server.zxyy.webhome.constant.SysConstant;
import com.xmsy.server.zxyy.webhome.modules.app.gamerecord.IAppGameRecordService;
import com.xmsy.server.zxyy.webhome.modules.manager.gamerecord.entity.GameRecordEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.gamerecord.entity.GameRecordFindCardType;
import com.xmsy.server.zxyy.webhome.modules.manager.gamerecord.service.GameRecordService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("user/gemerecord")
public class UserGameRecordController {
	 @Autowired
	 private GameRecordService gameRecordService;
//	 @Autowired
//	 private UserService userService;
	 @Autowired
	 private LocalContentCache localContentCache;

	 /**
     * 
     */
    @RequestMapping("/list")
    @RequiresPermissions("user:gemerecord:list")
    public R list(@RequestParam(required = false, value = "startTime") String startTime,
			@RequestParam(required = false, value = "endTime") String endTime,
			GameRecordEntity gameRecordEntity,
			PageParam pageParam) {
    	gameRecordEntity.setRobot(false);
		Page<GameRecordEntity> result = gameRecordService.getGameRecords(gameRecordEntity, pageParam, startTime,
				endTime);
		List<GameRecordEntity> gameRecordList = result.getRecords();
		BigDecimal totalPrizeCoins=BigDecimal.ZERO;
		BigDecimal subTotalPrizeCoins=BigDecimal.ZERO;
		if(gameRecordList!=null && !gameRecordList.isEmpty()) {
			for(GameRecordEntity gameRecord :gameRecordList) {
				subTotalPrizeCoins=subTotalPrizeCoins.add(MathUtil.getBigDecimal(gameRecord.getPrizeCoins()));
			}
//			//取出userid去查询用户的信息,再把用户余额和金币余额取出
//			Set<Long> idList = new HashSet<>();
//			for(GameRecordEntity gameRecord :gameRecordList) {
//				idList.add(gameRecord.getUserId());
//			}
//			List<UserEntity> userList=userService.findUserListByIdList(idList);
//			Map<Long, UserEntity> userMap=new HashMap<>();
//			if(userList!=null && !userList.isEmpty()) {
//				for(UserEntity user : userList) {
//					userMap.put(user.getId(), user);
//				}
//			}
//			//设置对应userid用户余额和金币余额取出
//			for(GameRecordEntity gameRecord :gameRecordList) {
//				UserEntity user=userMap.get(gameRecord.getUserId());
//				if(user!=null) {
//					gameRecord.setMoney(user. ());
//					gameRecord.setCoin(user.getCoin());
//				}
//			}
		}
		totalPrizeCoins = gameRecordService.sumPrizeCoinsByParam(gameRecordEntity, startTime, endTime);
		totalPrizeCoins=totalPrizeCoins==null?BigDecimal.ZERO:totalPrizeCoins;
		return R.ok().put("totalPrizeCoins", totalPrizeCoins).put("subTotalPrizeCoins", subTotalPrizeCoins).
				put("page", new PageUtils(gameRecordList, result.getTotal(), result.getSize(),
				result.getCurrent(), result.getPages()));
	}
  
    @RequestMapping("/showRoundList")
    @RequiresPermissions("user:gemerecord:list")
    public R showRound(GameRecordEntity gameRecordEntity) {
    	List<GameRecordEntity> gameRecordList = gameRecordService.selectList(new EntityWrapper<GameRecordEntity>(gameRecordEntity));
    	if(gameRecordEntity.getRoomId()==SysConstant.ROOM_ID_1) {
    		String name = GameEnum.getGamemap().get(gameRecordEntity.getGameId());
    		if (StringUtils.isEmpty(name)) {
    			log.error("[gameRecordDetails] name is null");
    			throw new RRException(ErrorCode.GameErrorCode.GAMEID_IS_ERRO.getErrMsg(),
    					ErrorCode.GameErrorCode.GAMEID_IS_ERRO.getCode());
    		}
    		IAppGameRecordService appGameRecordService = (IAppGameRecordService) SpringContextUtils.getBean(name);
    		if (null == appGameRecordService) {
    			log.error("[gameRecordDetails] appGameRecordService is null");
    			throw new RRException(ErrorCode.GameErrorCode.GAMEID_IS_ERRO.getErrMsg(),
    					ErrorCode.GameErrorCode.GAMEID_IS_ERRO.getCode());
    		}
    		List<GameRecordFindCardType> dataList = null;
    		try {
    			dataList = appGameRecordService.findCardType(gameRecordEntity.getGameRoundNo(), gameRecordEntity.getRound());
			} catch (Exception e) {
				e.printStackTrace();
			}
    		
    		if(dataList != null && !dataList.isEmpty()) {
    			Map<Long, String> userCartMap = new HashMap<>();
    			for (GameRecordFindCardType cartType:dataList) {
    				userCartMap.put(cartType.getUserId(), cartType.getCardsStr());
				}
    			for (GameRecordEntity gameRecord:gameRecordList) {
    				gameRecord.setCardsStr(userCartMap.get(gameRecord.getUserId()));
				}
    		}
    	}else if(gameRecordEntity.getRoomId()==SysConstant.ROOM_ID_3) {
    		String name = GameEnum.getGamemap().get(gameRecordEntity.getGameId());
    		if (StringUtils.isEmpty(name)) {
    			log.error("[gameRecordDetails] name is null");
    			throw new RRException(ErrorCode.GameErrorCode.GAMEID_IS_ERRO.getErrMsg(),
    					ErrorCode.GameErrorCode.GAMEID_IS_ERRO.getCode());
    		}
    		IAppGameRecordService appGameRecordService = (IAppGameRecordService) SpringContextUtils.getBean(name);
    		if (null == appGameRecordService) {
    			log.error("[gameRecordDetails] appGameRecordService is null");
    			throw new RRException(ErrorCode.GameErrorCode.GAMEID_IS_ERRO.getErrMsg(),
    					ErrorCode.GameErrorCode.GAMEID_IS_ERRO.getCode());
    		}
    		List<GameRecordFindCardType> dataList = null;
    		try {
    			dataList = appGameRecordService.findCardType(gameRecordEntity.getGameRoundNo(), gameRecordEntity.getRound());
			} catch (Exception e) {
				e.printStackTrace();
			}
    		
    		if(dataList != null && !dataList.isEmpty()) {
    			Map<Long, String> userBetAreaStrMap = new HashMap<>();
    			Map<Long, String> userIdleCardStrMap = new HashMap<>();
    			Map<Long, String> userBankerCardStrMap = new HashMap<>();
    			Map<Long, String> userOpenCardStrMap = new HashMap<>();
    			Map<Long, String> userDragonCardStrMap = new HashMap<>();
    			Map<Long, String> userTigerCardStrMap = new HashMap<>();
    			for (GameRecordFindCardType cartType:dataList) {
    				userBetAreaStrMap.put(cartType.getUserId(), cartType.getBetAreaStr());
    				userIdleCardStrMap.put(cartType.getUserId(), cartType.getIdleCardStr());
    				userBankerCardStrMap.put(cartType.getUserId(), cartType.getBankerCardStr());
    				userOpenCardStrMap.put(cartType.getUserId(), cartType.getOpenCardStr());
    				userDragonCardStrMap.put(cartType.getUserId(), cartType.getDragonCardStr());
    				userTigerCardStrMap.put(cartType.getUserId(), cartType.getTigerCardStr());
    				
				}
    			for (GameRecordEntity gameRecord:gameRecordList) {
    				gameRecord.setBetAreaStr(userBetAreaStrMap.get(gameRecord.getUserId()));
    				gameRecord.setIdleCardStr(userIdleCardStrMap.get(gameRecord.getUserId()));
    				gameRecord.setBankerCardStr(userBankerCardStrMap.get(gameRecord.getUserId()));
    				gameRecord.setOpenCardStr(userOpenCardStrMap.get(gameRecord.getUserId()));
    				gameRecord.setDragonCardStr(userDragonCardStrMap.get(gameRecord.getUserId()));
    				gameRecord.setTigerCardStr(userTigerCardStrMap.get(gameRecord.getUserId()));
    				String sourceStr = gameRecord.getBetAreaStr();
    		        String[] sourceStrArray = sourceStr.split(",");
    		        String str="";
    		        for (int i = 0; i < sourceStrArray.length; i++) {
    		            String[] sumStrArray = sourceStrArray[i].split(":");
    		            str += sumStrArray[0]+":"+MathUtil.getBigDecimal(sumStrArray[1])
    		            .divide(new BigDecimal(Constant.CLIENT_COIN_RATE), 2,BigDecimal.ROUND_HALF_UP);
    		           
    		        }
    		        gameRecord.setBetAreaStr(str);
				}
    			 
    			
    		}
    	}
    	return R.ok().put("gameRecordList", gameRecordList);
    }
    
    @RequestMapping("/selectList")
    @RequiresPermissions("user:gemerecord:list")
    public R selectList() {
    	List<Map<String, Object>>  gameList=new ArrayList<>();
    	List<Map<String, Object>>  gradeList=new ArrayList<>();
    	Map<String, Object> data=null;
    	Map<Long, String> gameMap = localContentCache.getGameMap();//SysConstant.gameMap
    	Map<Long, String> gradeMap = localContentCache.getGradeMap();//SysConstant.gradeMap
    	for(Long gameId:gameMap.keySet()) {
    		data = new HashMap<>();
    		data.put("id", gameId);
    		data.put("name", gameMap.get(gameId));
    		gameList.add(data);
    	}
    	for(Long gradeId:gradeMap.keySet()) {
    		data = new HashMap<>();
    		data.put("id", gradeId);
    		data.put("name", gradeMap.get(gradeId));
    		gradeList.add(data);
    	}
    	return R.ok().put("gameList", gameList).put("gradeList", gradeList);
    }
}

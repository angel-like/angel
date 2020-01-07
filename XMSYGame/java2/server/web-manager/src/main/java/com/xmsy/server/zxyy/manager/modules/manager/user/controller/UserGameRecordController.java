package com.xmsy.server.zxyy.manager.modules.manager.user.controller;

import com.baomidou.mybatisplus.plugins.Page;
import com.xmsy.common.define.constant.kaiyuan.*;
import com.xmsy.common.define.page.PageParam;
import com.xmsy.server.zxyy.manager.common.exception.RRException;
import com.xmsy.server.zxyy.manager.common.utils.*;
import com.xmsy.server.zxyy.manager.constant.GameEnum;
import com.xmsy.server.zxyy.manager.constant.SysConstant;
import com.xmsy.server.zxyy.manager.modules.app.gamerecord.IAppGameRecordService;
import com.xmsy.server.zxyy.manager.modules.manager.gamerecord.controller.GameInfoComponent;
import com.xmsy.server.zxyy.manager.modules.manager.gamerecord.entity.GameRecordEntity;
import com.xmsy.server.zxyy.manager.modules.manager.gamerecord.entity.GameRecordFindCardType;
import com.xmsy.server.zxyy.manager.modules.manager.gamerecord.service.GameRecordService;
import com.xmsy.server.zxyy.manager.modules.manager.user.entity.UserEntity;
import com.xmsy.server.zxyy.manager.modules.manager.user.service.UserService;
import com.xmsy.server.zxyy.manager.utils.ReadCardValuesForMj;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("user/gemerecord")
public class UserGameRecordController {
	 @Autowired
	 private GameRecordService gameRecordService;
	 @Autowired
	 private UserService userService;
	 @Autowired
     private GameInfoComponent gameInfo;

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
		UserEntity u = null;
    	if (StringUtils.isNotBlank(gameRecordEntity.getUserAccount())){
			List<UserEntity> ul = userService.findUserListByAccount(new String[]{gameRecordEntity.getUserAccount()});
			if (ul!=null && ul.size() >0){
				u = ul.get(0);
				gameRecordEntity.setUserId(ul.get(0).getId());
				gameRecordEntity.setUserAccount("");
			}
		}
		Page<GameRecordEntity> result = gameRecordService.getGameRecords(gameRecordEntity, pageParam, startTime,
				endTime);
		List<GameRecordEntity> gameRecordList = result.getRecords();
		BigDecimal totalPrizeCoins=BigDecimal.ZERO;
		BigDecimal subTotalPrizeCoins=BigDecimal.ZERO;
		BigDecimal totalValidBet=BigDecimal.ZERO;
		if(gameRecordList!=null && !gameRecordList.isEmpty()) {
			for(GameRecordEntity gameRecord :gameRecordList) {

				gameRecord.setRoomName(gameInfo.getRoomName(gameRecord.getRoomId()));
				gameRecord.setGameName(gameInfo.getGameName(gameRecord.getGameId()));
				gameRecord.setGradeName(gameInfo.getGradeName(gameRecord.getGradeId()));
				if (u != null ){
					gameRecord.setUserAccount(u.getAccount());
				}
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
		totalValidBet=gameRecordService.sumValidBet(gameRecordEntity,startTime,
				endTime);
		totalPrizeCoins = gameRecordService.sumPrizeCoinsByParam(gameRecordEntity, startTime, endTime);
		totalPrizeCoins=totalPrizeCoins==null?BigDecimal.ZERO:totalPrizeCoins;
		return R.ok().put("totalValidBet", totalValidBet).put("totalPrizeCoins", totalPrizeCoins).put("subTotalPrizeCoins", subTotalPrizeCoins).
				put("page", new PageUtils(gameRecordList, result.getTotal(), result.getSize(),
				result.getCurrent(), result.getPages()));
	}

    @RequestMapping("/showRoundList")
    @RequiresPermissions("user:gemerecord:list")
    public R showRound(GameRecordEntity gameRecordEntity) {
//		Page<GameRecordEntity> result = gameRecordService.getGameRecords(gameRecordEntity, null, null,
//				null);
//		List<GameRecordEntity> gameRecordList = result.getRecords();
//    	List<GameRecordEntity> gameRecordList = gameRecordService.selectList(new EntityWrapper<GameRecordEntity>(gameRecordEntity));
		List<GameRecordEntity> gameRecordList = gameRecordService.getRecords(gameRecordEntity);
		Integer round = 0;
		if(gameRecordEntity.getRound()!=null){
			round =gameRecordEntity.getRound();
		}
		if(gameRecordEntity.getRoomId().equals(SysConstant.ROOM_ID_1)) {
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

    			dataList = appGameRecordService.findCardType(gameRecordEntity.getGameRoundNo(), round);
			} catch (Exception e) {
				e.printStackTrace();
			}

    		if(dataList != null && !dataList.isEmpty()) {
    			Map<Long, String> userCartMap = new HashMap<>();
    			Map<Long, String> playerCardStrMap = new HashMap<>();
    			Map<Long, String> settlementCardStrMap = new HashMap<>();
    			Map<Long, String> idleCardStrMap = new HashMap<>();
    			Map<Long, String> bankerCardStrMap = new HashMap<>();
    			Map<Long,Long>  chairMap = new HashMap<>();
				Map<Long, String> cardMap = new HashMap<>();
				Map<Long, String> playCardsStrMap = new HashMap<>();
				Map<Long, String> publicCardsStrMap = new HashMap<>();
    			for (GameRecordFindCardType cartType:dataList) {
    				userCartMap.put(cartType.getUserId(), cartType.getCardsStr());
    				idleCardStrMap.put(cartType.getUserId(), cartType.getIdleCardStr());
    				bankerCardStrMap.put(cartType.getUserId(), cartType.getBankerCardStr());
    				playerCardStrMap.put(cartType.getUserId(), cartType.getPlayerCardStr());
    				settlementCardStrMap.put(cartType.getUserId(), cartType.getSettlementCardStr());
    				chairMap.put(cartType.getUserId(),cartType.getChairId());
					cardMap.put(cartType.getUserId(),cartType.getCardString());
					playCardsStrMap.put(cartType.getUserId(), cartType.getPlayCardsStr());
					publicCardsStrMap.put(cartType.getUserId(),cartType.getPublicCardsStr());
				}
    			for (GameRecordEntity gameRecord:gameRecordList) {
					String cardValue = userCartMap.get(gameRecord.getUserId());
					String s1 = cardMap.get(gameRecord.getUserId());
					String str ="";
					if("".equals(cardValue)||cardValue==null) {
						if (gameRecord.getGameId().equals(740L)) {
							if ("".equals(cardValue) || cardValue == null) {
//							String cards =chairMap.get(gameRecord.getUserId())+";"+s1;
//							log.info(cards);
								String[] arr = s1.split(",");
								String cardValues1 = ReadCardValuesForMj.readCard(arr[0]);
								String cardValues2 = ReadCardValuesForMj.readCard(arr[1]);
								if (chairMap.get(gameRecord.getUserId()).equals(1L)) {
									cardValues1 = "本方:" + cardValues1;
									cardValues2 = "对方:" + cardValues2;
								} else {
									cardValues2 = "本方:" + cardValues2;
									cardValues1 = "对方:" + cardValues1;
								}
								str = cardValues1 + "," + cardValues2;

//							log.info(ReadCardValuesForMj.readCard(cards));
								log.info(gameRecord.toString());
							}
						}
						if (gameRecord.getGameId().equals(620L)) {

							str = ReadCardValuesForDzpk.readCard(s1);

							log.info(cardValue);
							log.info(gameRecord.toString());

						}
						if (gameRecord.getGameId().equals(650L)) {
							str = ReadCardValuesForXlch.readString(s1);
							log.info(cardValue);
							log.info(gameRecord.toString());
						}
						if (gameRecord.getGameId().equals(720L)) {
							str = ReadCardValuesForMjErBa.readCard(s1);
							log.info(cardValue);
							log.info(gameRecord.toString());
						}
						if (gameRecord.getGameId().equals(1690L)) {
							str = ReadCardValuesForXueZhanToubao.readCard(s1);
							log.info(cardValue);
							log.info(gameRecord.toString());
						}
						if (gameRecord.getGameId().equals(1610L)) {
							str = ReadCardValuesForXingYunDuoBao.readCard(s1);
							log.info(cardValue);
							log.info(gameRecord.toString());
						}
						if (gameRecord.getGameId().equals(600L)) {
							str = ReadCardValuesForEsyd.readCard(s1);
							log.info(cardValue);
							log.info(gameRecord.toString());
						}
						if (gameRecord.getGameId().equals(1370L)) {
							str = ReadCardValuesForGssh.readCard(s1);
							log.info(cardValue);
							log.info(gameRecord.toString());
						}
						if (gameRecord.getGameId().equals(830L)) {
							str = ReadCardValuesForQzhnn.readCard(s1);
							log.info(cardValue);
							log.info(gameRecord.toString());
						}
						if (gameRecord.getGameId().equals(610L)) {
							str = ReadCardValuesForDdz.readCard(s1);
							log.info(cardValue);
							log.info(gameRecord.toString());
						}
						if (gameRecord.getGameId().equals(730L)) {
							str = ReadCardValuesForQzpj.readCard(s1);
							log.info(cardValue);
							log.info(gameRecord.toString());
						}
						if (gameRecord.getGameId().equals(1810L)) {
							str = ReadCardValuesForDtnn.readCard(s1);
							log.info(cardValue);
							log.info(gameRecord.toString());
						}
						if (gameRecord.getGameId().equals(860L)) {
							str = ReadCardValuesForDzpk.readCard(s1);
							log.info(cardValue);
							log.info(gameRecord.toString());
						}
						if (gameRecord.getGameId().equals(220L)) {
							str = ReadCardValuesForZjh.readCard(s1);
							log.info(cardValue);
							log.info(gameRecord.toString());

						}
						if (gameRecord.getGameId().equals(630L)) {
							str = ReadCardValuesForShiSanShui.readCard(s1);
							log.info(cardValue);
							log.info(gameRecord.toString());
						}
						if (gameRecord.getGameId().equals(1860L)) {
							str = ReadCardValuesForDuChangPuKe.readCard(s1);
							log.info(cardValue);
							log.info(gameRecord.toString());
						}
						if (gameRecord.getGameId().equals(1960L)) {
							str = ReadCardValuesForBenChiBaoMa.readCard(s1);
							log.info(cardValue);
							log.info(gameRecord.toString());
						}
						if (gameRecord.getGameId().equals(1970L)) {
							str = ReadCardValuesForWuXingHongHui.readCard(s1);
							log.info(cardValue);
							log.info(gameRecord.toString());
						}
						if (gameRecord.getGameId().equals(1940L)) {
							str = ReadCardValuesForJinShaYinSha.readCard(s1);
							log.info(cardValue);
							log.info(gameRecord.toString());
						}
						if (gameRecord.getGameId().equals(230L)) {
							str = ReadCardValuesForJiSuZhaJinHua.readCard(s1);
							log.info(cardValue);
							log.info(gameRecord.toString());
						}
						if (gameRecord.getGameId().equals(1950L)) {
							str = ReadCardValuesForWrzjh.readCard(s1);
							log.info(cardValue);
							log.info(gameRecord.toString());
						}
						if (gameRecord.getGameId().equals(910L)) {
							str = ReadCardValuesForBaiJiaLe.readCard(s1);
							log.info(cardValue);
							log.info(gameRecord.toString());
						}
						if (gameRecord.getGameId().equals(1980L)) {
							str = ReadCardValuesForBaiRenTouBao.readCard(s1);
							log.info(cardValue);
							log.info(gameRecord.toString());
						}
						if (gameRecord.getGameId().equals(1850L)) {
							str = ReadCardValuesForYaBaoQiangZhuangNiuNiu.readCard(s1);
							log.info(cardValue);
							log.info(gameRecord.toString());
						}
						if (gameRecord.getGameId().equals(1990L)) {
							str = ReadCardValuesForZjn.readCard(s1);
							log.info(cardValue);
							log.info(gameRecord.toString());
						}
						if (gameRecord.getGameId().equals(900L)) {
							str = ReadCardValuesForYaZhuangLongHu.readCard(s1);
							log.info(cardValue);
							log.info(gameRecord.toString());
						}
						if (gameRecord.getGameId().equals(930L)) {
							str = ReadCardValuesForBaiRenNiuNiu.readCard(s1);
							log.info(cardValue);
							log.info(gameRecord.toString());
						}
						if (gameRecord.getGameId().equals(870L)) {
							str = ReadCardValuesForTbnn.readCard(s1);
							log.info(cardValue);
							log.info(gameRecord.toString());
						}
						gameRecord.setCardsStr(str);


					}
					else {
    					gameRecord.setCardsStr(userCartMap.get(gameRecord.getUserId()));
    				}

    				gameRecord.setIdleCardStr(idleCardStrMap.get(gameRecord.getUserId()));
    				gameRecord.setBankerCardStr(bankerCardStrMap.get(gameRecord.getUserId()));
    				gameRecord.setPlayerCardStr(playerCardStrMap.get(gameRecord.getUserId()));
    				gameRecord.setSettlementCardStr(settlementCardStrMap.get(gameRecord.getUserId()));
    				gameRecord.setPlayCardsStr(playCardsStrMap.get(gameRecord.getUserId()));
    				gameRecord.setPublicCardsStr(publicCardsStrMap.get(gameRecord.getUserId()));
    				gameRecord.setRoomName(gameInfo.getRoomName(gameRecord.getRoomId()));
    				gameRecord.setGameName(gameInfo.getGameName(gameRecord.getGameId()));
    				gameRecord.setGradeName(gameInfo.getGradeName(gameRecord.getGradeId()));

				}
    		}
    	}else if(gameRecordEntity.getRoomId().equals(SysConstant.ROOM_ID_3)) {
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
    			dataList = appGameRecordService.findCardType(gameRecordEntity.getGameRoundNo(), round);
			} catch (Exception e) {
				e.printStackTrace();
			}

    		if(dataList != null && !dataList.isEmpty()) {
    			Map<Long, String> userCartMap = new HashMap<>();
    			Map<Long, String> userBetAreaStrMap = new HashMap<>();
    			Map<Long, String> userIdleCardStrMap = new HashMap<>();
    			Map<Long, String> userBankerCardStrMap = new HashMap<>();
    			Map<Long, String> userOpenCardStrMap = new HashMap<>();
    			Map<Long, String> userDragonCardStrMap = new HashMap<>();
    			Map<Long, String> userTigerCardStrMap = new HashMap<>();
    			Map<Long, String> userRedCardStrMap = new HashMap<>();
    			Map<Long, String> userBlackCardStrMap = new HashMap<>();
    			Map<Long, String> userLotteryCardStrMap = new HashMap<>();
				Map<Long, String> cardMap = new HashMap<>();
    			for (GameRecordFindCardType cartType:dataList) {
    				userCartMap.put(cartType.getUserId(), cartType.getCardsStr());
    				userBetAreaStrMap.put(cartType.getUserId(), cartType.getBetAreaStr());
    				userIdleCardStrMap.put(cartType.getUserId(), cartType.getIdleCardStr());
    				userBankerCardStrMap.put(cartType.getUserId(), cartType.getBankerCardStr());
    				userOpenCardStrMap.put(cartType.getUserId(), cartType.getOpenCardStr());
    				userDragonCardStrMap.put(cartType.getUserId(), cartType.getDragonCardStr());
    				userTigerCardStrMap.put(cartType.getUserId(), cartType.getTigerCardStr());
    				userRedCardStrMap.put(cartType.getUserId(), cartType.getRedCardStr());
    				userBlackCardStrMap.put(cartType.getUserId(), cartType.getBlackCardStr());
    				userLotteryCardStrMap.put(cartType.getUserId(), cartType.getLotteryCardStr());
					cardMap.put(cartType.getUserId(),cartType.getCardString());
				}
    			for (GameRecordEntity gameRecord:gameRecordList) {
    				gameRecord.setBetAreaStr(userBetAreaStrMap.get(gameRecord.getUserId()));
    				gameRecord.setIdleCardStr(userIdleCardStrMap.get(gameRecord.getUserId()));
    				gameRecord.setBankerCardStr(userBankerCardStrMap.get(gameRecord.getUserId()));
    				gameRecord.setOpenCardStr(userOpenCardStrMap.get(gameRecord.getUserId()));
    				gameRecord.setDragonCardStr(userDragonCardStrMap.get(gameRecord.getUserId()));
    				gameRecord.setTigerCardStr(userTigerCardStrMap.get(gameRecord.getUserId()));
    				gameRecord.setRedCardStr(userRedCardStrMap.get(gameRecord.getUserId()));
    				gameRecord.setBlackCardStr(userBlackCardStrMap.get(gameRecord.getUserId()));
    				gameRecord.setLotteryCardStr(userLotteryCardStrMap.get(gameRecord.getUserId()));
    				gameRecord.setCardsStr(userCartMap.get(gameRecord.getUserId()));
    				gameRecord.setRoomName(gameInfo.getRoomName(gameRecord.getRoomId()));
    				gameRecord.setGameName(gameInfo.getGameName(gameRecord.getGameId()));
    				gameRecord.setGradeName(gameInfo.getGradeName(gameRecord.getGradeId()));
					String cardValue = userCartMap.get(gameRecord.getUserId());
					String s1 = cardMap.get(gameRecord.getUserId());
					String str="";
					String st="";
    				if(gameRecord.getBetAreaStr()!=null) {
    					String[] sourceStrArray = gameRecord.getBetAreaStr().split(",");
        		        for (int i = 0; i < sourceStrArray.length; i++) {
        		            String[] sumStrArray = sourceStrArray[i].split(":");
        		            str += sumStrArray[0]+":"+MathUtil.getBigDecimal(sumStrArray[1])
        		            .divide(new BigDecimal(Constant.CLIENT_COIN_RATE), 2,BigDecimal.ROUND_HALF_UP);
        		        }
						log.info(str);
    				}
					if(gameRecord.getGameId().equals(920L)){
						if("".equals(cardValue)||cardValue==null){
							st =  ReadCardValuesForSlwh.readCard(s1);
							log.info(gameRecord.toString());
						}else {
							st=cardValue;
						}
						gameRecord.setLotteryCardStr(st);
					}
					
				}


    		}
    	}else if(gameRecordEntity.getRoomId().equals(SysConstant.ROOM_ID_2)) {
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
    			dataList = appGameRecordService.findCardType(gameRecordEntity.getGameRoundNo(), round);
			} catch (Exception e) {
				e.printStackTrace();
			}

    		if(dataList != null && !dataList.isEmpty()) {
    			Map<Long, String> userCartMap = new HashMap<>();
    			Map<Long, String> settlementCardMap = new HashMap<>();
    			for (GameRecordFindCardType cartType:dataList) {
    				userCartMap.put(cartType.getUserId(), cartType.getCardsStr());
    				settlementCardMap.put(cartType.getUserId(),cartType.getSettlementCard());
				}
    			for (GameRecordEntity gameRecord:gameRecordList) {
    					String sourceStr = settlementCardMap.get(gameRecord.getUserId());
    					String[] sourceStrArray = sourceStr.split(";");
    			        String str="";
    			        for (int i = 0; i < sourceStrArray.length; i++) {
    			            String[] sumStrArray = sourceStrArray[i].split("\\@");
    					    str += sumStrArray[0]+MathUtil.getBigDecimal(sumStrArray[1]).
    								divide(MathUtil.getBigDecimal(Constant.DB_COIN_RATE),2,BigDecimal.ROUND_HALF_UP)+sumStrArray[2];
    			        }

    				gameRecord.setSettlementCard(str);
    				gameRecord.setCardsStr(userCartMap.get(gameRecord.getUserId()));
    				gameRecord.setRoomName(gameInfo.getRoomName(gameRecord.getRoomId()));
    				gameRecord.setGameName(gameInfo.getGameName(gameRecord.getGameId()));
    				gameRecord.setGradeName(gameInfo.getGradeName(gameRecord.getGradeId()));
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
    	Map<Long, String> gameMap = gameInfo.getGameMap();
    	Map<Long, String> gradeMap = gameInfo.getGradeMap();
    	//SysConstant.gradeMap
    	Map<String, Object> data=null;
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

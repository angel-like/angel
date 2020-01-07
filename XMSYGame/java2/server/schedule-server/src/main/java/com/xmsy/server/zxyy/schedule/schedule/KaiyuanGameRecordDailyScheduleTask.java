package com.xmsy.server.zxyy.schedule.schedule;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xmsy.server.zxyy.schedule.server.GameRecordService;
import com.xmsy.server.zxyy.schedule.server.KaiyuanGameService;
import com.xmsy.server.zxyy.schedule.server.UserService;
import com.xmsy.server.zxyy.schedule.utils.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Date;


/**
 * 开元注单 定时任务
 *  每两分钟执行一次
 * @author adu
 *
 */
//@Slf4j
@Component
public class KaiyuanGameRecordDailyScheduleTask {
	@Autowired
	private KaiyuanGameService kaiyuanGameService;

	@Autowired
	GameRecordService gameRecordService;

//	@Autowired
//	UserBetRecordService userBetRecordService;
	@Autowired
	private UserService userService;
	/**
	 * 开元游戏记录
	 */
	@Scheduled(fixedRate = 120000)

	public void GameRecord() {

		//拉取开元游戏并 写入数据库
		//第一步kaiyuanGameService.getRecord();
		try{
			JSONObject returnJson =  kaiyuanGameService.getRecord();
			if (returnJson.getIntValue("code") == 0){
				//查询成功

				int count = returnJson.getIntValue("count");
				JSONObject listjson = returnJson.getJSONObject("list");

				JSONArray GameID = listjson.getJSONArray("GameID");
				JSONArray Accounts = listjson.getJSONArray("Accounts");
				JSONArray ServerID = listjson.getJSONArray("ServerID");
				JSONArray KindID = listjson.getJSONArray("KindID");
				JSONArray TableID = listjson.getJSONArray("TableID");
				JSONArray ChairID = listjson.getJSONArray("ChairID");
				JSONArray UserCount = listjson.getJSONArray("UserCount");
				JSONArray CardValue = listjson.getJSONArray("CardValue");
				JSONArray CellScore = listjson.getJSONArray("CellScore");
				JSONArray AllBet = listjson.getJSONArray("AllBet");
				JSONArray Profit = listjson.getJSONArray("Profit");
				JSONArray Revenue = listjson.getJSONArray("Revenue");
				JSONArray GameStartTime = listjson.getJSONArray("GameStartTime");
				JSONArray GameEndTime = listjson.getJSONArray("GameEndTime");
				JSONArray ChannelID = listjson.getJSONArray("ChannelID");
				JSONArray LineCode = listjson.getJSONArray("LineCode");

				for (int i =0 ;i<count;i++) {

					String str[] = Accounts.getString(i).split("_");
					JSONObject      	user = userService.getUserById(Long.parseLong(str[1]));
					if (str[2].equals(String.valueOf(HallUrlConstant.getPROVIDER_CODE()))){

						String gameId = GameID.getString(i);
						  Long userId =Long.parseLong(str[1]);
						 Long    serverId =ServerID.getLong(i);
						Long kindId =KindID.getLong(i);
						Long   tableId =TableID.getLong(i);
						Long chairId =ChairID.getLong(i);
						 Long    userCount  = UserCount.getLong(i);
//						String card = CardValue.getString(i);
						String cardValue =CardValue.getString(i);
						String roomName ="匹配房间";
//						if (KindID.getLong(i).equals(740L)) {//二人麻将
//							String[] arr = card.split(",");
//							String cardValues1 = ReadCardValuesForMj.readCard(arr[0]);
//							String cardValues2 = ReadCardValuesForMj.readCard(arr[1]);
//							if (ChairID.getLong(i).equals(1L)) {
//								cardValues1 = "本方:" + cardValues1;
//								cardValues2 = "对方:" + cardValues2;
//							} else {
//								cardValues2 = "本方:" + cardValues2;
//								cardValues1 = "对方:" + cardValues1;
//							}
//							cardValue =cardValues1 + "," + cardValues2;
//						}
//						if (KindID.getLong(i).equals(620L)) {//德州扑克
//							cardValue =ReadCardValuesForDzpk.readCard(card);
//						}
						if (KindID.getLong(i).equals(920L)) {//森林舞会
//							cardValue =ReadCardValuesForSlwh.readCard(card);
							roomName ="百人房间";
						}
						Long cellScore =new BigDecimal(CellScore.getFloat(i)).multiply(new BigDecimal(100)).setScale(0, BigDecimal.ROUND_HALF_UP).longValue();
						Long allBet=new BigDecimal(AllBet.getFloat(i)).multiply(new BigDecimal(100)).setScale(0, BigDecimal.ROUND_HALF_UP).longValue();
						Long profit=new BigDecimal(Profit.getFloat(i)).multiply(new BigDecimal(100)).setScale(0, BigDecimal.ROUND_HALF_UP).longValue();
						Long revenue=new BigDecimal(Revenue.getFloat(i)).multiply(new BigDecimal(100)).setScale(0, BigDecimal.ROUND_HALF_UP).longValue();
						Date gameStartTime=	GameStartTime.getDate(i);
						Date gameEndTime= GameEndTime.getDate(i);
						Long channelId =ChannelID.getLong(i);
						String lineCode =LineCode.getString(i);
//						String gameName =GameEnum.getGameName(KindID.getLong(i));
//						String  gradeName =GameRoomEnum.getRoomName(ServerID.getLong(i));
						String accounts =null;
						//其他字段
						if (!kaiyuanGameService.isExistGameRecord(gameId)) {

							if (user != null&&user.size()>0){
								accounts =user.getString("account");
//								gameRecordKaiyuanEntity.setAccounts(user.getAccount());
							}
							kaiyuanGameService.save(gameId,userId,serverId,kindId,tableId,chairId,userCount,cardValue,
									cellScore,allBet,profit,revenue,gameStartTime,gameEndTime,channelId,lineCode,accounts,roomName);
						}
						//并入游戏注单
						 Long    GameId =KindID.getLong(i);
						  Long roomId =1L;
						Long    GradeId =ServerID.getLong(i);
						String gameRoundNo =GameID.getString(i);
						BigDecimal  waterProfit =new BigDecimal(Revenue.getFloat(i)).multiply(new BigDecimal(100)).setScale(0, BigDecimal.ROUND_HALF_UP);

						if (KindID.getLong(i).equals(920L)) {//森林舞会
							roomId=3L;

						}
						Long betCoins =new BigDecimal(AllBet.getFloat(i)).multiply(new BigDecimal(100)).setScale(0, BigDecimal.ROUND_HALF_UP).longValue();
				        Long validBet =new BigDecimal(CellScore.getFloat(i)).multiply(new BigDecimal(100)).setScale(0, BigDecimal.ROUND_HALF_UP).longValue();
						Long  prizeCoins =new BigDecimal(Profit.getFloat(i)).multiply(new BigDecimal(100)).setScale(0, BigDecimal.ROUND_HALF_UP).longValue();
						Long profitCoins =prizeCoins * -1;
						Integer round =0;
						String userAccount =null;
						JSONArray jsonArray = gameRecordService.queryByGameRoundNo(gameId, 0);
						if (jsonArray != null && jsonArray.size() > 0) {
							continue;
						} else {

							boolean isGmUser =false;

							if (user!=null&&user.size() >0){
								 userAccount = user.getString("account");
								String userType = user.getString("userType");
								if(userType.equals("TEST")){
									isGmUser = true;
								}
							}
							gameRecordService.save(userId,userAccount,GameId,GradeId,roomId,roomName,gameRoundNo,waterProfit,betCoins,validBet,prizeCoins,profitCoins,round,isGmUser);

							userService.updateUserEveryDateBet(userId,userAccount,validBet, DateUtils.format(new Date()),isGmUser);
						}
					}
				}

			}
		}catch (Exception e){
			e.printStackTrace();
		}
	}
}

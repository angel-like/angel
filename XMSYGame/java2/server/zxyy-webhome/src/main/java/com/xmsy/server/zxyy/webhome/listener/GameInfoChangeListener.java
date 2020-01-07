package com.xmsy.server.zxyy.webhome.listener;

import java.util.HashMap;

import org.springframework.amqp.core.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.rabbitmq.client.Channel;
import com.xmsy.common.bean.message.BaseMessage;
import com.xmsy.common.define.constant.ResultDef;
import com.xmsy.server.zxyy.webhome.constant.SysConstant;
import com.xmsy.server.zxyy.webhome.modules.gamemanage.gameinfo.server.GameInfoInterface;
import com.xmsy.server.zxyy.webhome.utils.EventRetryUtil;

import lombok.extern.slf4j.Slf4j;

/**
 * .监听游戏服游戏信息更新
 * 
 * @author Administrator
 *
 */
@Slf4j
@Component
public class GameInfoChangeListener {

	@Autowired
	private EventRetryUtil eventRetryUtil;
	/**
	 * 监听游戏服游戏信息更新
	 *
	 * @param baseMessage
	 *            接收到的消息
	 * @param message
	 * @param channel
	 */
//	@RabbitListener(queues = SysConstant.GAME_QUEUE)
	public void receiveMessage(BaseMessage baseMessage,Message message, Channel channel) {
		Long deliveryTag = message.getMessageProperties().getDeliveryTag();
		try {
			if (updateGameinfo()) {
				// 手动签收
				log.info("gameInfoChangeListener1  监听游戏服游戏信息更新事件，接收到消息:{} deliveryTag {}", baseMessage, deliveryTag);
				channel.basicAck(deliveryTag, false);
				SysConstant.MESSAGE.remove(baseMessage.getMessageId());
			} else {
				eventRetryUtil.retry(deliveryTag, baseMessage, channel, "GameInfoChangeListener");
			}
		} catch (Exception e) {
			eventRetryUtil.retry(deliveryTag, baseMessage, channel, "GameInfoChangeListener");
		}
	}

	private boolean updateGameinfo() {
		JSONObject returnObj = null;
		boolean gameInfoIsOk = false;
	   try {
		    returnObj = GameInfoInterface.gameInfo();
		} catch (Exception e) {
			log.error("游戏信息获取失败 error {}",e);
		}
	   log.info("returnObj 游戏信息返回结果 {}",returnObj);
	   if(returnObj !=null && returnObj.getInteger("code")==200
			   && returnObj.get("gameinfo") !=null) {
		   JSONArray gamaInfoList = returnObj.getJSONArray("gameinfo");
		   if(gamaInfoList!=null && !gamaInfoList.isEmpty()) {
			   for(int i=0;i<gamaInfoList.size();i++) {
				   JSONObject obj=gamaInfoList.getJSONObject(i);
				   SysConstant.gameMap.put(obj.getLongValue("gameId"), obj.getString("gameName"));
				   SysConstant.gradeMap.put(obj.getLongValue("gradeId"), obj.getString("gradeName"));
				   SysConstant.roomMap.put(obj.getLongValue("roomId"), obj.getString("roomName"));
			   }
		   }else {
			   SysConstant.gameMap=new HashMap<>();
			   SysConstant.gradeMap=new HashMap<>();
			   SysConstant.roomMap=new HashMap<>();
		   }
		   gameInfoIsOk=true;
		   log.info("gameMap 游戏信息map {}",SysConstant.gameMap);
		   log.info("gradeMap 游戏场次map {}",SysConstant.gradeMap);
		   log.info("roomMap 房间map {}",SysConstant.roomMap);
	   }
	   
	   boolean hallIsOk = false;
	   JSONObject hallReturnObj = null;
	   try {
		   hallReturnObj = GameInfoInterface.hallList();
		} catch (Exception e) {
			log.error("大厅列表获取失败 error {}",e);
		}
	   
	   log.info("hallReturnObj 大厅列表返回结果 {}", hallReturnObj);
	   if (hallReturnObj !=null && hallReturnObj.getInteger("code") != null && hallReturnObj.getInteger("code") == ResultDef.SUCCESS) {
			if (hallReturnObj.getJSONObject("data").getJSONArray("hall") != null) {
				JSONArray hallArry = hallReturnObj.getJSONObject("data").getJSONArray("hall");
				if (hallArry.size()>0) {
					for(int i=0;i<hallArry.size();i++) {
						JSONObject hallobj = hallArry.getJSONObject(i);
						SysConstant.hallMap.put(hallobj.getInteger("id"), hallobj.toJSONString());
					}
				}else {
					SysConstant.hallMap=new HashMap<>();
				}
				hallIsOk=true;
			}
		}
		
		return gameInfoIsOk && hallIsOk;
	}
}

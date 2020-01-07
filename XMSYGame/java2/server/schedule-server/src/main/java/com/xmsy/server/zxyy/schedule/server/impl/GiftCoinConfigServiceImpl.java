package com.xmsy.server.zxyy.schedule.server.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xmsy.server.zxyy.schedule.event.PushService;
import com.xmsy.server.zxyy.schedule.event.param.UserInfoMessage;
import com.xmsy.server.zxyy.schedule.jdbc.WebHomeJdbcUtil;
import com.xmsy.server.zxyy.schedule.server.GiftCoinConfigService;
import com.xmsy.server.zxyy.schedule.server.UserService;
import com.xmsy.server.zxyy.schedule.utils.Constant;
import com.xmsy.server.zxyy.schedule.utils.DateUtils;
import com.xmsy.server.zxyy.schedule.utils.MathUtil;
import com.xmsy.server.zxyy.schedule.utils.OrderNoUtil;

import lombok.extern.slf4j.Slf4j;
@Slf4j
@Service("giftCoinConfigService")
public class GiftCoinConfigServiceImpl implements GiftCoinConfigService {
	@Autowired
	private WebHomeJdbcUtil jdbcUtil;
	@Autowired
	private UserService userService;
	
	@Autowired
	private PushService pushService;
	
	public static final int CONTENT_TYPE = 1; // 用户邮件类型
	public static final int TARGET_OBJECT = 1;// 指定用户
	
	@Override
	public JSONArray findGiftCoinConfigList(int type) {
		StringBuilder sqlSB=new StringBuilder();
		sqlSB.append(" SELECT type,num,max_num maxNum,coin,send_type sendType,vip_id vipId,");
		sqlSB.append(" cycle,enable,template_id templateId");
		sqlSB.append(" FROM gift_coin_config");
		sqlSB.append(" where type  = ? and enable =1 and  delete_status=0 ");
		sqlSB.append(" order by num;");
		JSONArray data=null;
		try {
			data = jdbcUtil.selectByParamReturnJsonArray(sqlSB.toString(), type);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("findGiftCoinConfigList error {}",e);
		}
		return data;
	}
	@Override
	public void saveUserGiftCoin(JSONObject userGame, JSONObject giftCoinConfig) {
		
		Long userId = userGame.getLong("userId");
		Integer gameNum = userGame.getInteger("num");//游戏次数
		Integer fnum = userGame.getInteger("fnum");//已经领取的次数
		int num = giftCoinConfig.getIntValue("num");//游戏要求次数
		int maxNum = giftCoinConfig.getIntValue("maxNum");
		Long vipId = giftCoinConfig.getLong("vipId");
		vipId=vipId==null?0:vipId;
		int shouldNum=0;//应该获得次数
		if(num>0) {
			shouldNum=gameNum/num;
		}
		if(fnum>= maxNum || shouldNum<=fnum) {
			return;
		}
		Long coin = giftCoinConfig.getLong("coin");
		Integer sendType = giftCoinConfig.getInteger("sendType");
		if(userId == null || coin ==null || coin<=0) {
			log.info("会员不存在或者赠送金币为0 userid:{} coin:{}",userId,coin);
			return;
		}
		JSONObject user = userService.getUserById(userGame.getLong("userId"));
		if(user == null || user.isEmpty()  ) {
			log.info("会员不存在 userid:{}",userId);
			return;
		}
		if(user.getBoolean("forbiddenEnable")) {
			log.info("会员被加入黑名单 userid:{}",userId);
			return;
		}
		if(vipId.compareTo(user.getLong("vipId"))>0) {
			log.info("会员VIP等级不符合 userid:{}",userId);
			return;
		}
		boolean isOk = true;
		if(sendType==Constant.SENDTYPE_0) {
			String sqlArray[] =new String[2];
			sqlArray[0]=getAddUserCoinSql();
			Object param0[]=new Object[2];
			param0[0]=coin;
			param0[1]=userId;
			
			sqlArray[1]=getInsertUserTransactionRecordSql();
			Object param1[] =new Object[10];
			param1[0]=user.get("id");
			param1[1]=user.get("account");
			param1[2]=Constant.TransactionType.REBATE.getValue();
			param1[3]=OrderNoUtil.getOrderNo();
			param1[4]=MathUtil.getBigDecimal(coin).divide(MathUtil.getBigDecimal(Constant.COIN_RATE),2,BigDecimal.ROUND_HALF_UP);
			param1[5]=0;
			param1[6]=user.get("money");
			param1[7]=MathUtil.getBigDecimal(user.get("coin")).add(MathUtil.getBigDecimal(coin)).longValue();
			param1[8]=user.get("commission");
			param1[9]=Constant.TransactionDetailType.USERMATCHAWARD.getValue();
			
			isOk = jdbcUtil.transactionExecuteUpdate(sqlArray, param0,param1);
		}
		if(isOk) {
			boolean receive = sendType==Constant.SENDTYPE_0;
			Long userGiftId = insertGiftCoinRecordSql(userId, user.getString("account"), Constant.ACTIVITYTYPE_1, 
					giftCoinConfig.getIntValue("type"), coin.intValue(), Constant.COIN_PROP_ID, sendType, receive);
			sentToUserForMessage(Constant.ACTIVITYTYPE_1, userGiftId,receive, giftCoinConfig.getLong("templateId"),
					userId, user.getString("account"), receive?coin:0l);
		}
	}
	
	private void sentToUserForMessage(int activityType,Long activityId,boolean receive,Long messageModelId,
			Long userId ,String account,Long coin) {
		if(sentMessage(activityId,receive, activityType, messageModelId, userId, account)) {
			UserInfoMessage userInfoMessage = new UserInfoMessage(userId, coin, 1);
			pushService.pushUserInfoForClient(userInfoMessage);
		}
	}
	
	
	/*
	 * 为用户增加金币Sql
	 */
	private String getAddUserCoinSql() {
		StringBuilder sql = new StringBuilder();
		sql.append(" update `user` set  coin=coin+?,");
		sql.append(" version=version+1,update_time=now()");
		sql.append(" where  id=? ");
		return sql.toString();
	}
	
	/**
	 * 增加用户资金交易记录sql
	 * @return
	 */
	private String getInsertUserTransactionRecordSql() {
		StringBuilder insertTransactionSql = new StringBuilder();
		insertTransactionSql.append(" INSERT INTO user_transaction_record (");
		insertTransactionSql.append(" `user_id`, `user_account`, `type`, `order_no`,");
		insertTransactionSql.append(" `amount`,  `fristrecharge`, `money`, `coin`,");
		insertTransactionSql.append(" `commission`, detail_type,create_time,update_time)");
		insertTransactionSql.append(" VALUES (?,?,?,?,?,?,?,?,?,?,now(),now())");

		return insertTransactionSql.toString();
	}
	
	/**
	 * 增加用户金币奖励记录sql
	 * @return
	 */
	private Long insertGiftCoinRecordSql(Long userId,String userAccount,int type ,int detailType,int num,long propId,
			int sendType,boolean receive) {
		StringBuilder insertTransactionSql = new StringBuilder();
		insertTransactionSql.append(" INSERT INTO user_gift_record (");
		insertTransactionSql.append(" `user_id`, `user_account`, `type`,`detail_type`, `num`,");
		insertTransactionSql.append(" `prop_id`,  `send_type`,receive, ");
		insertTransactionSql.append(" create_time,update_time)");
		insertTransactionSql.append(" VALUES (?,?,?,?,?,?,?,?,now(),now())");

		return jdbcUtil.insertReturnId(insertTransactionSql.toString(), userId,userAccount,
				type , detailType, num, propId,
				 sendType, receive);
	}
	/**
	 * 增加用户金币奖励记录sql
	 * @return
	 */
	private Boolean sentMessage(Long activityId,boolean receive,int activityType,Long messageModelId,Long userId ,String account) {
		
		JSONObject messageModel = getMessageModel(messageModelId);
	
		if(messageModel==null || messageModel.isEmpty()) {
			log.info("获取模板消息失败 messageModelId:{}",messageModelId);
			return false;
		}
		Integer effectTime = messageModel.getInteger("effectTime");
		boolean readonly = messageModel.getBoolean("readonly");
		
		Boolean isOk =false;
//		StringBuilder insertSql = new StringBuilder();
//	insertSql.append(" INSERT INTO sys_message_management (");
//		insertSql.append(" title, content,content_type,target_object,failure_time,effect_time,");
//		insertSql.append(" user_account,readonly,create_time,update_time)");
//	insertSql.append(" VALUES (?,?,?,?,?,?,?,?,now(),now())");
		Date EFFECT_TIME = new Date(); // 邮件生效时间
		Date FAIL_TIME =effectTime==0?null:DateUtils.addDateDays(EFFECT_TIME, effectTime); // 邮件失效时间
//	Long messageId = jdbcUtil.insertReturnId(insertSql.toString(), messageModel.get("title"),
//				messageModel.get("content"),messageModel.get("contentType"),TARGET_OBJECT,
//			FAIL_TIME,EFFECT_TIME,account,messageModel.getInteger("readonly")
//			);
//		if(messageId!=null && messageId>0) {
			StringBuilder insertMessageUserSql = new StringBuilder();
			insertMessageUserSql.append(" INSERT INTO sys_message_user (");
			insertMessageUserSql.append(" message_id, user_id,user_account,status,delete_message,");
			insertMessageUserSql.append(" activity_type,activity_id,receive,create_time,update_time,");
		insertMessageUserSql.append("title, content,content_type,failure_time,effect_time,readonly)");
			insertMessageUserSql.append(" VALUES (?,?,?,?,?,?,?,?,now(),now(),?,?,?,?,?,?)");
			Long id = jdbcUtil.insertReturnId(insertMessageUserSql.toString(), 0,userId,account,0,0,
					activityType,activityId,receive,messageModel.get("title"),messageModel.get("content"),messageModel.get("contentType"),FAIL_TIME,EFFECT_TIME,readonly);
			isOk=id>0;
			JSONArray propList =  getMessageModelProp(messageModelId);
			if(propList!=null && !propList.isEmpty()) {
				StringBuilder insertMessagePropSql = new StringBuilder();
				insertMessagePropSql.append(" INSERT INTO sys_message_prop (");
				insertMessagePropSql.append(" message_id,prop_id,prop_num,create_time,update_time)");
				insertMessagePropSql.append(" VALUES (?,?,?,now(),now())");
				List<Object[]> paramsList=new ArrayList<>();
				Object[] param=null;
				for(int index = 0;index <propList.size();index++) {
					JSONObject prop = propList.getJSONObject(index);
					param=new Object[3];
					param[0]=id;
					
					param[1]=prop.get("propId");
					param[2]=prop.get("propNum");
					paramsList.add(param);
				}
				jdbcUtil.insertBatch(insertMessagePropSql.toString(), paramsList);
			}
			
			
//		}
//		else {
//			log.info("保存邮件消息失败 ");
//		}
		return isOk;
	}
	
	private JSONObject getMessageModel(Long id) {
		StringBuilder sqlSB=new StringBuilder();
		sqlSB.append(" SELECT name,title,content,content_type contentType,readonly,");
		sqlSB.append(" effect_time effectTime");
		sqlSB.append(" FROM sys_message_model");
		sqlSB.append(" where id  = ? and enable =1 and  delete_status=0 ");
		JSONObject data=null;
		try {
			data = jdbcUtil.selectByParamReturnJsonObject(sqlSB.toString(), id);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("getMessageModel error {}",e);
		}
		return data;
	}
	private JSONArray getMessageModelProp(Long id) {
		StringBuilder sqlSB=new StringBuilder();
		sqlSB.append(" SELECT message_id messageId, prop_id propId,prop_num  propNum");
		sqlSB.append(" FROM sys_message_model_prop");
		sqlSB.append(" where message_id  = ? and  delete_status=0 ");
		sqlSB.append(" order by id;");
		JSONArray data=null;
		try {
			data = jdbcUtil.selectByParamReturnJsonArray(sqlSB.toString(), id);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("getMessageModelProp error {}",e);
		}
		return data;
	}
	@Override
	public void saveUserReliftCoin(JSONObject user, JSONObject giftCoinConfig) {
		Long userId = user.getLong("id");
		Long coin = giftCoinConfig.getLong("coin");
		Integer sendType = giftCoinConfig.getInteger("sendType");
		if(user == null || user.isEmpty()  || userId == null || coin ==null || coin<=0) {
			log.info("会员不存在或者赠送金币为0 userid:{} coin:{}",userId,coin);
			return;
		}
		boolean isOk = true;
		if(sendType==Constant.SENDTYPE_0) {
			String sqlArray[] =new String[2];
			sqlArray[0]=getAddUserCoinSql();
			Object param0[]=new Object[2];
			param0[0]=coin;
			param0[1]=userId;
			
			sqlArray[1]=getInsertUserTransactionRecordSql();
			Object param1[] =new Object[10];
			param1[0]=user.get("id");
			param1[1]=user.get("account");
			param1[2]=Constant.TransactionType.REBATE.getValue();
			param1[3]=OrderNoUtil.getOrderNo();
			param1[4]=MathUtil.getBigDecimal(coin).divide(MathUtil.getBigDecimal(Constant.COIN_RATE),2,BigDecimal.ROUND_HALF_UP);
			param1[5]=0;
			param1[6]=user.get("money");
			param1[7]=MathUtil.getBigDecimal(user.get("coin")).add(MathUtil.getBigDecimal(coin)).longValue();
			param1[8]=user.get("commission");
			param1[9]=Constant.TransactionDetailType.USERRELIEF.getValue();
			
			isOk = jdbcUtil.transactionExecuteUpdate(sqlArray, param0,param1);
		}
		if(isOk) {
			boolean receive = sendType==Constant.SENDTYPE_0;
			Long userGiftId = insertGiftCoinRecordSql(userId, user.getString("account"), Constant.ACTIVITYTYPE_1, 
					giftCoinConfig.getIntValue("type"), coin.intValue(), Constant.COIN_PROP_ID, sendType, receive);
			sentToUserForMessage(Constant.ACTIVITYTYPE_1, userGiftId,receive, giftCoinConfig.getLong("templateId"),
					userId, user.getString("account"), receive?coin:0l);
		}
		
	}

}

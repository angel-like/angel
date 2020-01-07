package com.xmsy.server.zxyy.schedule.server.impl;

import java.math.BigDecimal;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.xmsy.server.zxyy.schedule.jdbc.WebHomeJdbcUtil;
import com.xmsy.server.zxyy.schedule.server.OrderCashExamineService;
import com.xmsy.server.zxyy.schedule.utils.OrderNoUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service("orderCashExamineService")
public class OrderCashExamineServiceImpl implements OrderCashExamineService {
	@Autowired
	private WebHomeJdbcUtil jdbcUtil;
	@Override
	public JSONObject getUnsettledCashExamineByUserId(Long userId) {
		StringBuilder sqlSB=new StringBuilder();
		sqlSB.append(" SELECT o.id,o.user_need_bet,o.deduction_valid_bet,	o.deduction_need_bet,o.user_money,");
		sqlSB.append(" o.user_valid_bet,o.deduction_administrative,o.update_time,o.create_time,o.version");
		sqlSB.append(" FROM order_cash_examine o");
		sqlSB.append(" where o.delete_status=false ");
		sqlSB.append(" and o.user_id=? ");
		sqlSB.append(" and o.status=false");
		JSONObject data=null;
		try {
			data = jdbcUtil.selectByParamReturnJsonObject(sqlSB.toString(), userId);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("statisticsWealthByDay error {}",e);
		}
		return data;
	}
	@Override
	public void bindUserinfoGiftCashExamine(JSONObject userData, BigDecimal amount, BigDecimal multiple) {
		JSONObject preOrderCashExamineEntity = getUnsettledCashExamineByUserId(userData.getLong("id"));
		BigDecimal userNeedBet = amount.multiply(multiple);
		if(preOrderCashExamineEntity!=null && !preOrderCashExamineEntity.isEmpty()) {
			updateOrderCashExamineUserNeedBet(preOrderCashExamineEntity.getLong("id"), userNeedBet);
		}else {
			insertOrderCashExamine(userData.getLong("id"), userData.getString("account"),
					amount, multiple, userData.getBigDecimal("money"), userNeedBet);
		}
	}
	@Override
	public void updateOrderCashExamineUserNeedBet(Long id, BigDecimal userNeedBet) {
		StringBuilder sqlSB=new StringBuilder();
		sqlSB.append(" update order_cash_examine");
		sqlSB.append(" set user_need_bet=user_need_bet+?");
		sqlSB.append(" where id=? ");
		jdbcUtil.update(sqlSB.toString(), userNeedBet,id);
	}
	@Override
	public void insertOrderCashExamine(Long userId, String userAccount, BigDecimal amount, BigDecimal multiple,
			BigDecimal userMoney, BigDecimal userNeedBet) {
		StringBuilder sqlSB=new StringBuilder();
		sqlSB.append(" insert into order_cash_examine(user_id,user_account,order_no,order_time,user_money,recharge_amount,");
		sqlSB.append(" user_need_bet,hierarchy_normal_multiple,hierarchy_normal_bet,create_time,update_time)");
		sqlSB.append(" value(?,?,?,?,?,?,?,?,?,?,?)");
		Date now=new Date();
		jdbcUtil.update(sqlSB.toString(), userId,userAccount, OrderNoUtil.getOrderNo(),now
				, userMoney.add(amount),amount, userNeedBet,multiple, userNeedBet,now,now);
	}

}

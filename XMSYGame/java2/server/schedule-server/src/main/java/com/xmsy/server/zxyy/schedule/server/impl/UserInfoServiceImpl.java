package com.xmsy.server.zxyy.schedule.server.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xmsy.server.zxyy.schedule.jdbc.WebHomeJdbcUtil;
import com.xmsy.server.zxyy.schedule.server.UserInfoService;

//@Slf4j
@Service("userInfoService")
public class UserInfoServiceImpl implements UserInfoService {
    @Autowired
    private WebHomeJdbcUtil webHomeJdbcUtil;
    @Override
    public void updateIntegral(String startTime, String endTime) throws Exception {
        StringBuilder sql =new StringBuilder();
        sql.append(" UPDATE user_info a");
        sql.append(" SET a.integral = 0");
        sql.append(" where a.integral > 0");
        webHomeJdbcUtil.update(sql.toString());
        StringBuilder sqlSB = new StringBuilder();
        sqlSB.append(" UPDATE user_info a");
        sqlSB.append(" INNER JOIN (");
        sqlSB.append(" SELECT user_id ,bet_coins ");
        sqlSB.append("  FROM user_bet_record ");
        sqlSB.append(" where create_time >= ?");
        sqlSB.append(" and create_time <= ? )");
        sqlSB.append(" c ON a.user_id = c.user_id");
        sqlSB.append(" SET a.integral = c.bet_coins/100 ");
        webHomeJdbcUtil.update(sqlSB.toString(), startTime, endTime);
    }



}

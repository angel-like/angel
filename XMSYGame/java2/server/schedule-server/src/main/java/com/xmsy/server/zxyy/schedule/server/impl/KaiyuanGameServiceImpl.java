package com.xmsy.server.zxyy.schedule.server.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.xmsy.server.zxyy.schedule.event.KaiyuanHttpRequestInterface;
import com.xmsy.server.zxyy.schedule.jdbc.WebHomeJdbcUtil;
import com.xmsy.server.zxyy.schedule.server.KaiyuanGameService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Transactional
@Service("kaiyuanGameService")
public class KaiyuanGameServiceImpl implements KaiyuanGameService {
    @Autowired
    private WebHomeJdbcUtil jdbcUtil;

    @Override
    public boolean isExistGameRecord(String gameId) {
        StringBuffer sb =new StringBuffer();
        sb.append(" select count(*) num from game_record_kaiyuan");
        sb.append(" where game_id  = ?");
        JSONObject data = null;
        try {
            data = jdbcUtil.selectByParamReturnJsonObject(sb.toString(), gameId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(data!=null && data.get("num")!=null && data.getLong("num")>0) {
            return true;
        }
        return false;

    }

    @Override
    public JSONObject getRecord() throws Exception {
        Long timestamp =System.currentTimeMillis();
        StringBuffer sb = new StringBuffer();
        sb.append("s=6").append("&");
        sb.append("startTime="+(timestamp-4*60*1000)).append("&");
        sb.append("endTime="+timestamp);
        JSONObject returnJson = KaiyuanHttpRequestInterface.sendToKaiyuan(sb.toString()).getJSONObject("d");
        return  returnJson;

    }

    @Override
    public void save(String gameId, Long userId, Long serverId, Long kindId, Long  tableId, Long chairId, Long userCount, String cardValue,
                     Long cellScore, Long allBet, Long profit, Long revenue, Date gameStartTime, Date gameEndTime, Long channelId, String lineCode, String accounts,String roomName) {
        StringBuffer sql=new StringBuffer();
        sql.append(" INSERT INTO game_record_kaiyuan(");
        sql.append(" game_id,accounts,server_id,kind_id,");
        sql.append(" table_id,chair_id,user_count,card_str,");
        sql.append(" cell_score,all_bet,profit,revenue,game_start_time,game_end_time,");
        sql.append(" channel_id,line_code,user_id,room_name,create_time,update_time)");
        sql.append("VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,now(),now())");
        try {

            jdbcUtil.insert(sql.toString(),gameId,accounts,serverId,kindId,tableId,chairId,userCount,cardValue,
                    cellScore,allBet,profit,revenue,gameStartTime,gameEndTime,channelId,lineCode,userId,roomName);
        } catch (Exception e) {
            log.error("saveBalanceRateYesterday error ",e);
        }
    }





}
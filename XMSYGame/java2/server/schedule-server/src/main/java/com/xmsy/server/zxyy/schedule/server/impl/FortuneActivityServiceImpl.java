package com.xmsy.server.zxyy.schedule.server.impl;

import com.xmsy.server.zxyy.schedule.jdbc.WebHomeJdbcUtil;
import com.xmsy.server.zxyy.schedule.server.FortuneActivityService;
import com.xmsy.server.zxyy.schedule.utils.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

@Slf4j
@Service("fortuneActivityService")
public class FortuneActivityServiceImpl implements FortuneActivityService {
    @Autowired
    private WebHomeJdbcUtil jdbcUtil;

    @Override
    public void checkEndTime() {

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.set(Calendar.HOUR,
                calendar.get(Calendar.HOUR) + 1);// 让日期加1
        calendar.set(Calendar.MINUTE,
                calendar.get(Calendar.MINUTE) + 1);// 让日期加1
        String format = new SimpleDateFormat("yyyy-MM-dd HH:mm:00").format(calendar.getTime());
        Calendar calendar1 = Calendar.getInstance();
        calendar1.setTime(new Date());
        calendar1.set(Calendar.HOUR,
                calendar1.get(Calendar.HOUR) + 1);// 让日期加1

        String format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:00").format(calendar1.getTime());
        Date date = DateUtils.stringToDate(format, DateUtils.DATE_TIME_PATTERN);
        Date date2 = DateUtils.stringToDate(DateUtils.format(new Date(), "yyyy-MM-dd HH:mm:00"), DateUtils.DATE_TIME_PATTERN);
        StringBuilder sqlSB = new StringBuilder();
        sqlSB.append(" SELECT id,name,detail,start_time,end_time,maxnum_reward,minnum_reward");
        sqlSB.append(" FROM fortune_activi_config");
        sqlSB.append(" where end_time  >= ? and end_time  <= ?  and  delete_status=0 ");
        sqlSB.append(" order by start_time limit 1;");
        Map<String, Object> data = null;

        try {
            log.info("【format1】 data {}", format1);
            log.info("【format】 data {}", format);
            data = jdbcUtil.selectByParamReturnJsonObject(sqlSB.toString(), format1, format);
            log.info("【天降财神活动】 data {}", data);
            if (data.size() > 0) {
                String end_time = (String) data.get("end_time");
                Date endTime = DateUtils.stringToDate(end_time, DateUtils.DATE_TIME_PATTERN);
			/*calendar.setTime(endTime);
			calendar.set(Calendar.HOUR,
					calendar.get(Calendar.HOUR) - 1);// 让日期加1
			String format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(calendar.getTime());
			Date date1 = DateUtils.stringToDate(format1, DateUtils.DATE_TIME_PATTERN);*/
                insertNotice(new Date(), endTime);
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("findGiftCoinConfigList error {}", e);
        }
    }


    /**
     * 增加用户资金交易记录sql
     *
     * @return
     */
    private Long insertNotice(Date effectTime, Date failureTime) {
        String title = "天降财神活动公告";
        String content = "天降财神活动即将结束，未使用的红包将被清空，请及时使用！";
        StringBuilder insertTransactionSql = new StringBuilder();
        insertTransactionSql.append(" INSERT INTO sys_notice_management (");
        insertTransactionSql.append(" `title`, `content`, `effect_time`,`failure_time`, `notice_type`,");
        /*		insertTransactionSql.append(" `prop_id`,  `send_type`,receive, ");*/
        insertTransactionSql.append(" create_time,update_time)");
        insertTransactionSql.append(" VALUES (?,?,?,?,1,now(),now())");

        return jdbcUtil.insertReturnId(insertTransactionSql.toString(), title, content, effectTime, failureTime);
    }

}

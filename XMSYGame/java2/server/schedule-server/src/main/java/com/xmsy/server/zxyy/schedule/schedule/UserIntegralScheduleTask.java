package com.xmsy.server.zxyy.schedule.schedule;

import com.xmsy.server.zxyy.schedule.server.UserInfoService;
import com.xmsy.server.zxyy.schedule.utils.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

@Slf4j
@Component
public class UserIntegralScheduleTask {
    @Autowired
    private UserInfoService userInfoService;

    /**
     * 每天凌晨4点更新用户积分，
     */
    @Scheduled(cron = "0 0 0 * * ? ")
    // @Scheduled(fixedRate = 60000 * 5)
    public void UserIntegralByDay() {
        // 昨日(日期格式)
        Date yesterday = DateUtils.getStartOfYesterday();
        // 昨日凌晨（时间格式）
        String startOfYesterday = DateUtils.formatTime(DateUtils.getStartOfYesterday());
        // 昨日最后一秒（时间格式）
        String endOfYesterday = DateUtils.formatTime(DateUtils.getEndOfYesterday());
        DateUtils.formatTime(DateUtils.getEndOfYesterday());
        log.info("【GameAnalysisDailyScheduleTask】 更新游戏积分: 开始时间 :{} - 结束时间: {} yesterday：{}", startOfYesterday,
                endOfYesterday, yesterday);
        try {
       //   JSONArray dataList = gameRecordService.getGameAnalysisByDay(startOfYesterday, endOfYesterday, false);
//            JSONArray robotDataList = gameRecordService.getGameAnalysisByDay(startOfYesterday, endOfYesterday, true);
//            gameRecordService.batchInsertGameAnalysisDaily(dataList, yesterday, false);
//            gameRecordService.batchInsertGameAnalysisDaily(robotDataList, yesterday, true);
            userInfoService.updateIntegral(startOfYesterday,endOfYesterday);
        } catch (Exception e) {
            log.error("GameAnalysisDailyByDay error :{}", e);
            e.printStackTrace();
        }

    }
}

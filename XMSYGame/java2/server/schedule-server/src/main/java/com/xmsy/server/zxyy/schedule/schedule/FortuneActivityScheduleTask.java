package com.xmsy.server.zxyy.schedule.schedule;

import com.xmsy.server.zxyy.schedule.event.PushService;
import com.xmsy.server.zxyy.schedule.event.param.UserInfoMessage;

import com.xmsy.server.zxyy.schedule.server.FortuneActivityService;
import com.xmsy.server.zxyy.schedule.utils.Constant;

import com.xmsy.server.zxyy.schedule.utils.MathUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;


/**
 * 天降财神活动结束提醒 定时任务
 *
 * @author adu
 */
@Slf4j
@Component
public class FortuneActivityScheduleTask {


   /* @Autowired
    private FortuneActivityService fortuneActivityService;

    *//**
     * 查询活动结束时间
     *//*
//	@Scheduled(cron = "0 0 0/1 * * ? ")
    @Scheduled(cron = "0 0/1 * * * ? ")
    public void fortuneActivity() {
        try {

            fortuneActivityService.checkEndTime();


        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/


}

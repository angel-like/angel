package com.itheima.springboot.service;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
/**
 * 定时任务
 * @author Administrator
 *
 */
@Service
public class ScheduleService {
	
	/**
	 * second(秒)，minute(分)，hour(时)，day of month(日)，month（月），day of week((周几)
	 *   * * * * * MON-FRI 周一到周五每一分钟每一秒都执行一次 
	 *   0 * * * * MON-FRI 周一到周五每一分钟零秒都执行一次
	 */
	@Scheduled(cron="0 * * * * MON-SAT")
	public void hello4() {//经过的时候执行一次  ，定时的时候也会-执行项目开启状态就行
		System.out.println("hello定时任务");//每分钟执行一次  只要项目开启状态就行，不用进入这个方法
	}
}

package com.xmsy.server.zxyy.manager.schedule;

import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.xmsy.server.zxyy.manager.constant.ThirdPartyDef;
import com.xmsy.server.zxyy.manager.modules.manager.pushdispatchdetail.entity.PushDispatchDetailEntity;
import com.xmsy.server.zxyy.manager.modules.manager.pushdispatchdetail.service.PushDispatchDetailService;

/**
 * .推送调度任务
 * 
 * @author aleng
 *
 */
@Component
public class PushDispatchSchedule {

	@Autowired
	private PushDispatchDetailService pushDispatchDetailService;

	@Scheduled(cron = "0/30 * * * * *")
	public void pushDispatchTask() {
		// 定时推送
		List<PushDispatchDetailEntity> timinglist = pushDispatchDetailService.selectList(
				new EntityWrapper<PushDispatchDetailEntity>(new PushDispatchDetailEntity().setType(ThirdPartyDef.TIMING)
						.setStatus(ThirdPartyDef.UN_EXECUTE)).lt("execute_time", new Date()));
		if (CollectionUtils.isEmpty(timinglist)) {
			return;
		}
		for (PushDispatchDetailEntity entity : timinglist) {
			pushDispatchDetailService.push(entity);
		}
	}
}

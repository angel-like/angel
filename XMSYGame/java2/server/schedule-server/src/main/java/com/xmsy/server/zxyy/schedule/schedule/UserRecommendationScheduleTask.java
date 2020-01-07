package com.xmsy.server.zxyy.schedule.schedule;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.xmsy.server.zxyy.schedule.entity.RecommendationGradeEntity;
import com.xmsy.server.zxyy.schedule.entity.UserBetEntity;
import com.xmsy.server.zxyy.schedule.entity.UserSumBetEntity;
import com.xmsy.server.zxyy.schedule.server.UserRecommendationService;

import lombok.extern.slf4j.Slf4j;

/**
 * 代理返佣
 * 
 * @author xiaoliu
 *
 */
@Slf4j
@Component
public class UserRecommendationScheduleTask {

	@Autowired
	private UserRecommendationService userRecommendationService;

	
	/**
	 * 查询每天统计用户下注表，将会员返水更新到会员金币中
	 */
	@Scheduled(cron = "0 0 4 * * ?")
//	 @Scheduled(fixedDelay = 1000 * 600)
	public void UserReturnWaterByDay() {
		log.info("UserReturnWaterByDay 每天统计用户下注表开始");
		List<UserBetEntity> userList = userRecommendationService.selectYestadayUserListForValidBet();
		if (CollectionUtils.isEmpty(userList)) {
			log.info("UserReturnWaterByDay 无需要返佣用户");
			return;
		}
		List<RecommendationGradeEntity> gradeList = userRecommendationService.getRecommendationGradeList();
		if (CollectionUtils.isEmpty(gradeList)) {
			log.info("UserReturnWaterByDay 代理等级未设定");
			return;
		}
		for(UserBetEntity userBetEntity:userList) {
			userRecommendationService.updatUserCommissionRebate(userBetEntity, gradeList);
		}
		
	}
	
	/**
	 * 查询每天统计用户下注表，将会员返水更新到会员金币中
	 */
//	@Scheduled(cron = "0 0 4 * * ?")
	// @Scheduled(fixedDelay = 1000 * 60)
	public void UserReturnWaterByDayOld() {
		log.info("UserReturnWaterByDay 每天统计用户下注表开始");
		List<UserSumBetEntity> teamList = userRecommendationService.selectYestadayTeamEnableUserList();

		if (CollectionUtils.isEmpty(teamList)) {
			log.info("UserReturnWaterByDay 无需要返佣用户");
			return;
		}
		List<UserBetEntity> userList = userRecommendationService.selectYestadayUserList();
		if (CollectionUtils.isEmpty(userList)) {
			log.info("UserReturnWaterByDay 获取下级盈利情况失败");
			return;
		}
		userRecommendationService.updatUserCommissionRebate(userList, teamList);
	}

}

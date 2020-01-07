package com.xmsy.server.zxyy.schedule.server;

import java.util.List;

import com.xmsy.server.zxyy.schedule.entity.RecommendationGradeEntity;
import com.xmsy.server.zxyy.schedule.entity.UserBetEntity;
import com.xmsy.server.zxyy.schedule.entity.UserSumBetEntity;

public interface UserRecommendationService {
	
	
	List<UserBetEntity> selectYestadayUserListForValidBet();

	// 获取是团队的用户昨日直属下级总有效投注
	List<UserSumBetEntity> selectYestadayTeamEnableUserList();

	// 获取用户昨日下级总有效投注
	List<UserBetEntity> selectYestadayUserList();
	
	List<RecommendationGradeEntity> getRecommendationGradeList();

	void updatUserCommissionRebate(List<UserBetEntity> userList, List<UserSumBetEntity> teamList);
	void updatUserCommissionRebate(UserBetEntity userBetEntity,List<RecommendationGradeEntity> gradeList);

}

package com.xmsy.server.zxyy.webhome.modules.app.bind;

import java.math.BigDecimal;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.xmsy.server.zxyy.webhome.modules.manager.userrecommendation.entity.UserRecommendationEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.userrecommendation.service.UserRecommendationService;
import com.xmsy.server.zxyy.webhome.modules.manager.userrecommendationrecord.service.UserRecommendationRecordService;
import com.xmsy.server.zxyy.webhome.modules.manager.userrecommendationtree.service.UserRecommendationTreeService;
import com.xmsy.server.zxyy.webhome.modules.manager.user.entity.UserEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.user.service.UserService;
import com.xmsy.server.zxyy.webhome.modules.app.bind.param.RecommendationCodeParam;
import com.xmsy.server.zxyy.webhome.modules.manager.userrecommendationrecord.entity.UserRecommendationRecordEntity;
import com.xmsy.server.zxyy.webhome.common.exception.RRException;
import com.xmsy.server.zxyy.webhome.common.utils.ErrorCode;
import com.xmsy.server.zxyy.webhome.common.utils.R;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/V1.0/App")
public class BindRecommender {
	@Autowired
	private UserRecommendationService userRecommendationService;
	@Autowired
	private UserRecommendationRecordService userRecommendationRecordService;
	@Autowired
	private UserRecommendationTreeService userRecommendationTreeService;
	@Autowired
	private UserService userService;
	
	/**
	 * 绑定推荐人
	 * @param registerMessage
	 * @return
	 */
	@PostMapping("/bindRecommender")
	@Transactional
	public R bindRecommender(@RequestHeader("token")  String token, @RequestBody @Valid RecommendationCodeParam recommendationCode) {
		//被推荐人账号
		UserEntity user = userService.getUser(token);
		// 验证填写的邀请码是否存在,存在就获取上级ID及账号
		Long superiorsId = null;// 上级ID
		String superiorsAccount = null;// 上级账号
		//通过推荐码获取 用户推荐码表实体
		UserRecommendationEntity userRecommendationEntity = userRecommendationService
				.selectOne(new EntityWrapper<UserRecommendationEntity>(new UserRecommendationEntity()
						.setRecommendationCode(recommendationCode.getRecommendationCode())));
		if (null != userRecommendationEntity) {
			UserEntity superiorsEntity = userService.selectById(userRecommendationEntity.getUserId());
			if(user.getId().equals(superiorsEntity.getId())) {//判断不能对自身做绑定
				throw new RRException(ErrorCode.InvitationCodeErrorCode.RECOMMENDATION_USER_THEMSELVES_ERROR.getErrMsg(),
						ErrorCode.InvitationCodeErrorCode.RECOMMENDATION_USER_THEMSELVES_ERROR.getCode());
			}
			//log.info("[userRegisterOperation1] 上级代理状态是否正常 {}", superiorsEntity.getAgentEnable());
			if (!superiorsEntity.getAgentEnable()) {// 如果上级是正常代理，那么就有上级ID
				throw new RRException(ErrorCode.InvitationCodeErrorCode.RECOMMENDATION_AGENT_CLOSE_ERROR.getErrMsg(),
						ErrorCode.InvitationCodeErrorCode.RECOMMENDATION_AGENT_CLOSE_ERROR.getCode());
			}
			superiorsId = userRecommendationEntity.getUserId();
			superiorsAccount = userRecommendationEntity.getUserAccount();
		}else {
			throw new RRException(ErrorCode.InvitationCodeErrorCode.RECOMMENDATION_AGENT_CLOSE_ERROR.getErrMsg(),
					ErrorCode.InvitationCodeErrorCode.RECOMMENDATION_AGENT_CLOSE_ERROR.getCode());
		}

		if (superiorsId != null) {
			// 新增邀请记录并返回id
			UserRecommendationRecordEntity userRecommendationRecord = new UserRecommendationRecordEntity();
			userRecommendationRecord.setUserId(user.getId());//被推荐人id
			userRecommendationRecord.setUserAccount(user.getAccount());//被推荐人账号
			userRecommendationRecord.setPromoterId(superiorsId);//上级id
			userRecommendationRecord.setPromoterAccount(superiorsAccount);//上级账号
			userRecommendationRecord.setRecommendationCode(recommendationCode.getRecommendationCode());//推荐码
			userRecommendationRecord.setPromotingProfit(BigDecimal.ZERO);//推广盈利			
			boolean isOk=userRecommendationRecordService.insert(userRecommendationRecord);
			log.debug("save userRecommendationRecord {}",isOk);
			// 修改推荐人的推荐详情
			userRecommendationService.updateByUserId(superiorsId);
			// 修改用户上级id
			userService.updateSuperiorsId(user.getId(), superiorsId);	
			//新增代理关系树
			userRecommendationTreeService.insertBatch(user.getId(), superiorsId);	
		}
		return R.ok();
	}
}

package com.xmsy.server.zxyy.manager.modules.manager.userrecommendation.service.impl;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xmsy.server.zxyy.manager.common.exception.RRException;
import com.xmsy.server.zxyy.manager.common.utils.ErrorCode;
import com.xmsy.server.zxyy.manager.common.utils.PageUtils;
import com.xmsy.server.zxyy.manager.common.utils.Query;
import com.xmsy.server.zxyy.manager.constant.SysConstant;
import com.xmsy.server.zxyy.manager.modules.app.user.param.AppRecommenderResultParam;
import com.xmsy.server.zxyy.manager.modules.app.user.param.UserRecommenderResultParam;
import com.xmsy.server.zxyy.manager.modules.manager.domainmanagement.service.DomainManagementService;
import com.xmsy.server.zxyy.manager.modules.manager.sysdictionary.service.SysDictionaryService;
/*import com.xmsy.server.zxyy.manager.modules.manager.user.entity.UserEntity;
import com.xmsy.server.zxyy.manager.modules.manager.user.service.UserService;*/
import com.xmsy.server.zxyy.manager.modules.manager.userrebatecommissionrecord.entity.UserRebateCommissionSumEntity;
import com.xmsy.server.zxyy.manager.modules.manager.userrebatecommissionrecord.service.UserRebateCommissionRecordService;
import com.xmsy.server.zxyy.manager.modules.manager.userrecommendation.dao.UserRecommendationDao;
import com.xmsy.server.zxyy.manager.modules.manager.userrecommendation.entity.UserRecommendationEntity;
import com.xmsy.server.zxyy.manager.modules.manager.userrecommendation.service.UserRecommendationService;
import com.xmsy.server.zxyy.manager.utils.DateUtils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Transactional
@Service("recommendationCodeService")
public class UserRecommendationServiceImpl extends ServiceImpl<UserRecommendationDao, UserRecommendationEntity>
		implements UserRecommendationService {
	@Autowired
	private SysDictionaryService sysDictionaryService;
	@Autowired
	private DomainManagementService domainManagementService;
	@Autowired
	private UserRebateCommissionRecordService userRebateCommissionRecordService;
	/*@Autowired
	private UserService userService;*/
	@Override
	public PageUtils queryPage(Map<String, Object> params) {
		UserRecommendationEntity entity = new UserRecommendationEntity();
		if (params != null) {
			if (params.get("userAccount") != null) {
				entity.setUserAccount(params.get("userAccount").toString());
			}
			if (params.get("recommendationCode") != null) {
				entity.setRecommendationCode(params.get("recommendationCode").toString());
			}
		}
		Wrapper<UserRecommendationEntity> wrapper = new EntityWrapper<UserRecommendationEntity>(entity);
		String sort = params.get("sort") != null ? params.get("sort").toString() : "";
		if (!StringUtils.isEmpty(sort)) {
			String direction = params.get("direction") != null ? params.get("direction").toString() : "";
			if ("desc".equalsIgnoreCase(direction)) {
				wrapper.orderDesc(Arrays.asList(sort.split(",")));
			} else {
				wrapper.orderAsc(Arrays.asList(sort.split(",")));
			}
		}
		Page<UserRecommendationEntity> page = this.selectPage(new Query<UserRecommendationEntity>(params).getPage(),
				wrapper);
		//获取是否开启代理的状态
		/*	List<UserRecommendationEntity> list = page.getRecords();
		for(UserRecommendationEntity userRecommendation:list) {
			UserEntity userEntity = new UserEntity();
			userEntity.setId(userRecommendation.getUserId());
			UserEntity user = userService.selectOne(new EntityWrapper<>(userEntity));
			userRecommendation.setAgentEnable(user.getAgentEnable());
		}*/
		return new PageUtils(page);
	}

	/**
	 * 新增用户推广码
	 */
	@Override
	public void insertRecommendationCode(Long userId, String account, String recommendationCode,
			Long agentHierarchyId) {
		UserRecommendationEntity recommendationCodeEntity = new UserRecommendationEntity();
		recommendationCodeEntity.setRecommendationCode(recommendationCode);
		recommendationCodeEntity.setUserId(userId);
		recommendationCodeEntity.setUserAccount(account);
		recommendationCodeEntity.setAgentHierarchyId(agentHierarchyId);
		log.info("[insertRecommendationCode] recommendationCodeEntity {}", recommendationCodeEntity);
		baseMapper.insert(recommendationCodeEntity);
	}

	/**
	 * 根据Id，修改邀请总人数
	 */
	@Override
	public void updateSuperiors(Long id) {
		baseMapper.updateSuperiors(id);
	}

	@Override
	public List<Map<String, Object>> findNotRecommendationCodeUser() {
		return this.baseMapper.findNotRecommendationCodeUser();
	}

	@Override
	public void save(UserRecommendationEntity entity) {
		if (!this.checkCode(entity.getRecommendationCode(), entity.getId())) {
			throw new RRException(ErrorCode.InvitationCodeErrorCode.INVITATION_CODE_ALREADY_EXISTS_ERRO.getErrMsg(),
					ErrorCode.InvitationCodeErrorCode.INVITATION_CODE_ALREADY_EXISTS_ERRO.getCode());
		}
		if (entity.getId() != null && entity.getId() > 0) {
			baseMapper.updateById(entity);
		} else {
			baseMapper.insert(entity);
		}
	}

	@Override
	public void delete(Long[] ids) {

	}

	@Override
	public Integer deletePhysicalById(Long id) {
		return this.baseMapper.deletePhysicalById(id);
	}

	@Override
	public boolean checkCode(String code, Long id) {
		return this.baseMapper.checkCode(code, id) == 0;
	}

	@Override
	public UserRecommendationEntity selectRecommendationForUserId(Long userId) {
		UserRecommendationEntity recommendationEntity = new UserRecommendationEntity();
		recommendationEntity.setUserId(userId);
		UserRecommendationEntity entity = baseMapper.selectOne(recommendationEntity);
		// TODO Auto-generated method stub
		return entity;
	}

	@Override
	public void updateSuperiorsAmount(UserRecommendationEntity entity) {
		baseMapper.updateSuperiorsAmount(entity);

	}

	/**
	 * 通过用户id获取用户邀请码及代理信息
	 * 
	 * @param userId
	 * @return
	 */
	@Override
	public UserRecommenderResultParam selectUserRecommendation(Long userId) {
		UserRecommenderResultParam entity = new UserRecommenderResultParam();
		entity = baseMapper.selectUserRecommendation(userId);
		String domain = domainManagementService.getRecommendationDomain();
		entity.setQrCode(domain + sysDictionaryService.getName(SysConstant.urlCode, SysConstant.RECOMMENDATIONURL)
				+ "?code=" + entity.getRecommendationCode());
		return entity;
	}

	@Override
	public UserRecommenderResultParam userRecommendationDetails(Long userId) {
		// TODO Auto-generated method stub
		return baseMapper.selectUserRecommendation(userId);
	}

	// 获取用户邀请信息
	@Override
	public AppRecommenderResultParam selectUserRecommendationInfo(Long userId) {
		// TODO Auto-generated method stub
		AppRecommenderResultParam entity = baseMapper.selectUserRecommendationInfo(userId);
		String domain = domainManagementService.getRecommendationDomain();
		entity.setQrCode(domain + sysDictionaryService.getName(SysConstant.urlCode, SysConstant.RECOMMENDATIONURL)
				+ "?code=" + entity.getRecommendationCode());
		// 获取用户上周获取佣金额
		UserRebateCommissionSumEntity sumEntity = userRebateCommissionRecordService.selectSumCommission(userId,
				DateUtils.format(DateUtils.getYesterday()), DateUtils.format(DateUtils.getWeekStart(-1)),
				DateUtils.format(DateUtils.getWeekStart(0)));
		entity.setLastWeekCommission(sumEntity.getIntervalCommission());
		entity.setYesterdayCommission(sumEntity.getDateCommission());
		entity.setHistoryCommission(sumEntity.getTotalCommission());
		return entity;
	}

	@Override
	public Integer deleteUserRecommendationById(Long userId) {
		return baseMapper.deleteUserRecommendationById(userId);
	}

}

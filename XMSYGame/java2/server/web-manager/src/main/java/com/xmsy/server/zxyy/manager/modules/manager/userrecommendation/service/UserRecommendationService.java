package com.xmsy.server.zxyy.manager.modules.manager.userrecommendation.service;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.service.IService;
import com.xmsy.server.zxyy.manager.common.utils.PageUtils;
import com.xmsy.server.zxyy.manager.modules.app.user.param.AppRecommenderResultParam;
import com.xmsy.server.zxyy.manager.modules.app.user.param.UserRecommenderResultParam;
import com.xmsy.server.zxyy.manager.modules.manager.userrecommendation.entity.UserRecommendationEntity;

/**
 * 用户推荐码表
 *
 * @author aleng
 * @email xxxxx
 * @date 2018-12-26 19:15:50
 */
public interface UserRecommendationService extends IService<UserRecommendationEntity> {

	PageUtils queryPage(Map<String, Object> params);

	// 为用户新增推广码
	void insertRecommendationCode(Long userId, String account, String recommendationCode, Long agentHierarchyId);

	// 根据Id修改邀请总人数(+1)
	void updateSuperiors(Long id);

	// 修改总奖金
	void updateSuperiorsAmount(UserRecommendationEntity entity);

	/**
	 * 查找没有邀请码的会员
	 * 
	 * @return
	 */
	List<Map<String, Object>> findNotRecommendationCodeUser();

	/**
	 * 保存
	 * 
	 * @param entity
	 */
	void save(UserRecommendationEntity entity);

	void delete(Long[] ids);

	/**
	 * 物理删除
	 * 
	 * @param id
	 * @return
	 */
	Integer deletePhysicalById(Long id);

	/**
	 * 检查邀请码是否唯一
	 * 
	 * @param code
	 * @param id
	 * @return
	 */
	boolean checkCode(String code, Long id);

	/**
	 * 根据用户ID获取用户推荐码
	 * 
	 * @param userId
	 * @return
	 */
	UserRecommendationEntity selectRecommendationForUserId(Long userId);

	/**
	 * 通过用户id获取用户邀请码及代理信息
	 * 
	 * @param userId
	 * @return
	 */
	UserRecommenderResultParam selectUserRecommendation(Long userId);

	UserRecommenderResultParam userRecommendationDetails(Long userId);

	AppRecommenderResultParam selectUserRecommendationInfo(Long userId);
	
	/**
	 * 逻辑删除用户邀请码
	 * @param userId
	 * @return
	 */
	Integer deleteUserRecommendationById(Long userId);

}

package com.xmsy.server.zxyy.manager.modules.manager.user.dao;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.xmsy.server.zxyy.manager.modules.manager.agent.param.Subordinate;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.xmsy.server.zxyy.manager.modules.manager.agent.param.Agent;
import com.xmsy.server.zxyy.manager.modules.manager.agent.param.TeamAgent;
import com.xmsy.server.zxyy.manager.modules.manager.rankinglistday.entity.RankingListDayEntity;
import com.xmsy.server.zxyy.manager.modules.manager.user.entity.UserEntity;
import com.xmsy.server.zxyy.manager.modules.manager.user.param.UserParam;
import com.xmsy.server.zxyy.manager.modules.manager.user.param.UserParamFour;
import com.xmsy.server.zxyy.manager.modules.manager.user.param.UserParamThree;
import com.xmsy.server.zxyy.manager.modules.manager.user.param.UserParamTwo;
import com.xmsy.server.zxyy.manager.modules.manager.userlogin.entity.UserLoginEntity;
import com.xmsy.server.zxyy.manager.modules.web.user.result.UserPasswordResult;

/**
 * 用户信息表
 *
 * @author aleng
 * @email xxxxx
 * @date 2018-12-26 11:18:25
 */
@Mapper
public interface UserDao extends BaseMapper<UserEntity> {
	/**
	 * 新增用户并返回用户ID
	 *
	 * @param userEntity
	 */
	void insertGetId(UserEntity userEntity);

	/**
	 * 单个用户查询
	 * @param user
	 * @return
	 */
	UserParamThree findSingleUserPage(@Param("user") UserParamFour user);

	/**
	 * 查询用户集合 根据账号列表
	 *
	 * @param accountList
	 *            账号列表
	 * @return
	 */
	List<UserEntity> findUserListByAccount(@Param("accountList") String[] accountList);

	/**
	 * 查询用户集合 根据userId列表
	 *
	 * @param idList
	 * @return
	 */
	List<UserEntity> findUserListByIdList(@Param("idList") Collection<Long> idList);

	/**
	 * 查询用户集合 根据层级列表
	 *
	 * @param hierarchyIDs
	 *            层级列表
	 * @return
	 */
	List<UserEntity> findUserListByHierarchy(@Param("hierarchyIDs") String[] hierarchyIDs);

	/**
	 * 会员管理页面分页查询
	 *
	 * @param param
	 * @return
	 */
	List<Map<String, Object>> findUserPage(@Param("user") UserParam user, Pagination page);


	/**
	 * 根据账号查询
	 * @param account
	 * @return
	 */
	 UserParamTwo queryByAccount(@Param("account") String account);

	/**
	 * 会员管理页面分页查询(根据最近充值)
	 *
	 * @param param
	 * @return
	 */
	List<Map<String, Object>> findUserPageForRecharge(@Param("user") UserParam user, Pagination page);

	/**
	 * 会员管理页面分页查询(根据充值总额)
	 *
	 * @param param
	 * @return
	 */
	List<Map<String, Object>> findUserPageForRechargeNumOrder(@Param("user") UserParam user, Pagination page);

	/**
	 * 会员管理页面分页查询(根据最近取款)
	 *
	 * @param param
	 * @return
	 */
	List<Map<String, Object>> findUserPageForTakeMoney(@Param("user") UserParam user, Pagination page);

	/**
	 * 会员管理页面分页查询(根据取款总额)
	 *
	 * @param param
	 * @return
	 */
	List<Map<String, Object>> findUserPageForTakeMoneyOrderNum(@Param("user") UserParam user, Pagination page);

	/**
	 * 会员管理页面分页查询
	 *
	 * @param param
	 * @return
	 */
	List<Map<String, Object>> findRiskUserPage(@Param("user") UserParam user, Pagination page);

	/**
	 * 会员管理页面
	 *
	 * @param param
	 * @return
	 */
	List<Map<String, Object>> findUserList(@Param("user") UserParam user);
	/**
	 * 会员管理导出csv
	 * @param user
	 * @return
	 */
	List<Map<String, Object>> findUserListForCsv(@Param("user") UserParam user);

	/**
	 * 会员管理页面计算有多少条
	 *
	 * @param param
	 * @return
	 */
	Integer queryTotal(Map<String, Object> param);

	/**
	 * 删除会员的银行卡信息
	 *
	 * @param userId
	 * @return
	 */
	Integer deleteBankInfo(@Param("userId") Long userId);

	/**
	 * 修改备注
	 *
	 * @param userEntity
	 * @return
	 */
	Integer updateUserBaseInfo(UserEntity userEntity);

	/**
	 * 根据ID修改用户余额，金币，佣金
	 *
	 * @param userEntity
	 * @return
	 */
	Integer updateUserWalletByUserId(UserEntity userEntity);

	/**
	 * 代理列表
	 *
	 * @param user
	 *            用户信息
	 * @return
	 */
	List<Agent> getAgentList(Pagination page, @Param("user") UserEntity user);

	/**
	 * 代理列表
	 *
	 * @param 代理下级列表
	 * @return
	 */
	List<Subordinate> getAgentSubordinateList(Pagination page, @Param("account") String account,
											  @Param("userId") Long userId);

	/**
	 * 试玩账号列表 包含删除
	 *
	 * @return
	 */
	List<UserEntity> getTrialUserList();

	Integer countUserByAccount(@Param("account") String account);

	/**
	 * 修改删除状态为0
	 *
	 * @param id
	 * @return
	 */
	Integer updateUserDeleteStatus(@Param("id") long id);

	/**
	 * .用户密码信息
	 *
	 * @param id
	 * @return
	 */
	UserPasswordResult getUserPasswordInfo(@Param("id") long id);

	/**
	 * 统计会员数量，有效会员数量(有充值)====非试玩账号
	 *
	 * @param
	 * @return
	 */
	Map<String, Object> getStatisticsNumber();

	/**
	 * 通过时间获取新增会员数,有效会员数
	 *
	 * @return
	 */
	Map<String, Object> sumUserForDate(@Param("startDate") String startDate, @Param("endDate") String endDate);

	/**
	 * 新增会员数近7日
	 *
	 * @param
	 * @return
	 */
	List<Map<String, Object>> getInsertUserNumber();

	/**
	 * 按照日期范围统计余额
	 *
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	List<RankingListDayEntity> statisticsWealthByDay();

	/**
	 * 修改用户是否首冲状态
	 */
	void updateUserFirstRecharge(Long id);

	/**
	 *
	 * 逻辑删除用户信息
	 *
	 * @param id
	 * @return
	 */
	Integer deleteUserById(Long id);
	/**
	 * 获取用户风控记录
	 * @return
	 */
	List<Map<String,Object>> statisticsRiskNum();
	/**
	 * 获取用户风控人数总计
	 * @return
	 */
	Long statisticsRiskTotalNum();

	/**
	 * 团队代理账号列表
	 *
	 */
	List<TeamAgent> getAgentTeamList(Page<TeamAgent> page, @Param("user") UserEntity user);
	/**
	 * 通过UserEntity,以及代理商id
	 *     查询当前代理商下的所有会员信息
	 * @param user
	 * @return
	 */
	List<UserEntity> findProxyUser(Page<UserEntity> page,@Param("user") UserEntity user);

	/**
	 * 单个会员登录记录
	 * @param user
	 * @return
	 */
	List<UserLoginEntity> logonLog(@Param("user") UserParamFour user);

	/**
	 * 将remark的值设为空
	 */
	void setReamrkNull(@Param("id") Long userId);

	/**
	 * 将phone的值设为空
	 */
	void setPhoneNull(@Param("id") Long userId);

	/**
	 * 通过id查询用户名
	 * @param id
	 * @return
	 */
	String selectAccountById(@Param("id") Long id);

	/**
	 * 修改会员层级通过account
	 */
	void batchUpdateHierarchy(@Param("hierarchyId") Long hierarchyId ,@Param("id") Long id);

	/**
	 * 修改会员类型
	 * @param userType
	 * @param id
	 */
	void batchMarkTestUser(@Param("id") Long id);
	/**
	 * Gm账号id列表
	 * @return
	 */
	 List<Long> getGmUser();

	 Integer checkGmUser (@Param("userId") Long userId);
	/**
	 * 统计
	 *
	 * @param id
	 */
	Map<String, Object> count(@Param("id") Long id);
	/**
	 * 根据用户ID查询直属上级
	 */
	String getSuperiorsName(@Param("userId") Long userId);
}

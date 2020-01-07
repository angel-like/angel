package com.xmsy.server.zxyy.manager.modules.manager.user.service;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.xmsy.common.define.page.PageParam;
import com.xmsy.server.zxyy.manager.common.utils.Constant.TransactionDetailType;
import com.xmsy.server.zxyy.manager.common.utils.PageUtils;
import com.xmsy.server.zxyy.manager.modules.app.login.param.LoginParams;
import com.xmsy.server.zxyy.manager.modules.app.login.param.OAuth2Params;
import com.xmsy.server.zxyy.manager.modules.app.login.param.PhoneRegisterParams;
import com.xmsy.server.zxyy.manager.modules.app.login.param.RegisterParams;
import com.xmsy.server.zxyy.manager.modules.app.login.result.UserDetail;
import com.xmsy.server.zxyy.manager.modules.app.user.param.EnterGameParam;
import com.xmsy.server.zxyy.manager.modules.app.user.param.RecommenderParam;
import com.xmsy.server.zxyy.manager.modules.app.user.param.UpdateCoinParam;
import com.xmsy.server.zxyy.manager.modules.manager.agent.param.Agent;
import com.xmsy.server.zxyy.manager.modules.manager.agent.param.Subordinate;
import com.xmsy.server.zxyy.manager.modules.manager.agent.param.TeamAgent;
import com.xmsy.server.zxyy.manager.modules.manager.orderrecharge.entity.OrderRechargeEntity;
import com.xmsy.server.zxyy.manager.modules.manager.user.entity.UserEntity;
import com.xmsy.server.zxyy.manager.modules.manager.user.param.UserParam;
import com.xmsy.server.zxyy.manager.modules.manager.user.param.UserParamFour;
import com.xmsy.server.zxyy.manager.modules.manager.user.param.UserParamThree;
import com.xmsy.server.zxyy.manager.modules.manager.user.param.UserParamTwo;
import com.xmsy.server.zxyy.manager.modules.manager.userinfo.entity.UserInfoEntity;
import com.xmsy.server.zxyy.manager.modules.manager.userlogin.entity.UserLoginEntity;
import com.xmsy.server.zxyy.manager.modules.web.login.entity.LoginEntity;
import com.xmsy.server.zxyy.manager.modules.web.login.entity.PhoneRegisterEntity;
import com.xmsy.server.zxyy.manager.modules.web.login.entity.RegisterEntity;
import com.xmsy.server.zxyy.manager.modules.web.user.result.UserAccountResult;
import com.xmsy.server.zxyy.manager.modules.web.user.result.UserInfoResult;
import com.xmsy.server.zxyy.manager.modules.web.user.result.UserPasswordResult;

/**
 * 用户信息表
 *
 * @author aleng
 * @email xxxxx
 * @date 2018-12-26 11:18:25
 */
public interface UserService extends IService<UserEntity> {

	/**
	 * 统计信息
	 *
	 * @param user
	 * @return
	 */
	Map<String,Object> statistics(UserParam user);

	/**
	 * 树形列表
	 *
	 * @param user
	 * @return
	 */
	List<Map<String,Object>> findUserTreePage(UserParam user);
	PageUtils findUserPage(UserParam user, PageParam pageParam);

	/**
	 * 单个会员信息
	 * @param user
	 * @return
	 */
	UserParamThree findSingleUserPage(UserParamFour user);

	/**
	 * 单个会员登录记录
	 * @param user
	 * @return
	 */
	List<UserLoginEntity> logonLog(UserParamFour user);



	/**
	 * 踢人改变在玩游戏信息id和游戏服务id
	 */
	void tickUserChangeGame(UserEntity user);

	/**
	 * 会员风控列表
	 *
	 * @param user
	 * @param pageParam
	 * @return
	 */
	PageUtils findRiskUserPage(UserParam user, PageParam pageParam);

	List<Map<String, Object>> findUserList(UserParam user);

	List<Map<String, Object>> findUserListForCsv(UserParam user);

	/**
	 * 查询用户集合 根据账号列表
	 *
	 * @param idList
	 * @return
	 */
	List<UserEntity> findUserListByAccount(String[] accountList);

	/**
	 * 查询用户集合 根据userId列表
	 *
	 * @param idList
	 * @return
	 */
	List<UserEntity> findUserListByIdList(Collection<Long> idList);

	/**
	 * WEB注册
	 *
	 * @param registerEntity
	 * @return
	 */
	UserEntity register(RegisterEntity registerEntity);

	/**
	 * APP注册
	 *
	 * @param user
	 * @return
	 */
	UserDetail appRegister(RegisterParams registerParams);

	/**
	 * APP手机注册
	 *
	 * @param user
	 * @return
	 */
	UserDetail appPhoneRegister(PhoneRegisterParams registerParams);

	/**
	 * 第三方登录注册
	 *
	 * @param user
	 * @return
	 */
	UserDetail thirdPartyRegister(UserEntity user, JSONObject userResult, OAuth2Params oAuth2Params);

	/**
	 * 绑定推荐人
	 *
	 * @param token
	 * @param recommenderParam
	 */
	void bindRecommender(String token, RecommenderParam recommenderParam);

	/**
	 * 进入游戏
	 *
	 * @param enterGameParam
	 */
	void enterGame(EnterGameParam enterGameParam);

	/**
	 * 修改用户金币
	 *
	 * @param enterGameParam
	 */
	void updateUserCoin(UpdateCoinParam updateCoinParam);

	/**
	 * 登陆
	 *
	 * @param user,Ip
	 * @return
	 */
	String login(UserEntity userEntity, LoginEntity loginEntity, String Ip);

	/**
	 * app登陆
	 *
	 * @param user,Ip
	 * @return
	 */
	UserDetail appLogin(UserEntity userEntity, LoginParams loginParams, String Ip, boolean VerifyPassword);

	/**
	 * app第三方登陆
	 */
	UserDetail appThirdPartyLogin(UserEntity user, OAuth2Params loginParams, String ip);

	/**
	 * 根据Token退出登陆
	 *
	 * @param token
	 * @return
	 */
	void logout(String token);

	/**
	 * 新增用户并返回用户ID
	 *
	 * @param user
	 * @return
	 */
	UserEntity insertGetId(UserEntity userEntity);

	/**
	 * 根据token获取人员信息
	 *
	 * @param token
	 * @return
	 */
	UserEntity selectUserForToken(String token);

	/**
	 * 删除用户的银行卡信息
	 *
	 * @param userId
	 * @return
	 */
	Integer deleteBankInfo(Long userId);

	void updateUserBaseInfo(UserEntity userEntity);

	/**
	 * 获取代理列表
	 *
	 * @param userId
	 * @return
	 */
	Page<Agent> getAgentList(UserEntity user, PageParam pageParam);

	/**
	 * 获取代理下级列表
	 *
	 * @param userId
	 * @return
	 */
	Page<Subordinate> getAgentSubordinateList(Long userId, String account, PageParam pageParam);

	/**
	 * 根据用户ID修改用户金币，余额，佣金等
	 *
	 * @param user
	 */
	void updateUserWalletByUserId(UserEntity user);

	/**
	 * .用户密码信息
	 *
	 * @param id
	 * @return
	 */
	UserPasswordResult getUserPasswordInfo(long id);

	String trialAccount(String ip, String deviceType);

	UserDetail appTrialAccount(String ip, Long hallId, String deviceType);

	/**
	 * 获取用户余额
	 *
	 * @param userId
	 * @return
	 */
	UserAccountResult getUserBalance(String token);

	/**
	 * 佣金转金币
	 */
	void exchangeCommission(UserEntity user, BigDecimal commission, Long coin);

	/**
	 * 额度转换
	 */
	void changer(UserEntity user, BigDecimal money, Long coin);

	/**
	 * 归集
	 */
	void integrate(UserEntity user, BigDecimal money, Long coin, TransactionDetailType transactionDetailType);

	/**
	 * 用户充值的同时直接额度转换
	 */
	void rechargeAndChanger(UserEntity user, BigDecimal money, Long coin);

	/**
	 * 统计会员数量，有效会员数量(有充值)====非试玩账号
	 */
	Map<String, Object> getStatisticsNumber();

	/**
	 * 新增会员数近7日====非试玩账号
	 */
	List<Map<String, Object>> getInsertUserNumber();

	/**
	 * 通过时间获取正式会员数量，有效会员数
	 */
	Map<String, Object> sumUserForDate(String startDate, String endDate);

	/**
	 * 获取用户基本信息
	 */
	UserInfoResult getUserInformation(Long userId);

	/**
	 * 修改用户基本信息
	 *
	 * @throws Exception
	 */
	void updateUserInformation(UserInfoResult entity) throws Exception;

	/**
	 * 修改用户信息并返利
	 *
	 * @throws Exception
	 */
	void updateUserInfoAndRabate(UserInfoEntity userInfo, UserEntity user) throws Exception;

	/**
	 * 修改是否首冲状态
	 */
	void updateUserFirstRecharge(Long id);

	/**
	 * 拉黑
	 */
	void editForbidden(UserEntity user);

	/**
	 * 撤销充值余额
	 */
	void revokeMoney(OrderRechargeEntity orderRecharge) throws Exception;

	/**
	 * H5端获取用户信息
	 *
	 * @param userId
	 * @return
	 */
	UserDetail getUserInfoForH5(Long userId, Long hallId);

	/**
	 * web端登入获取大厅ip和端口
	 *
	 * @param userId
	 * @return
	 */
	UserDetail setHallIp(UserDetail result, Long hallId);

	void deleteUser(Long userId);

	void updateUserCoin(UserEntity user, Long coin, int type, int detailType);

	// web端手机注册
	UserEntity webPhoneRegister(PhoneRegisterEntity registerEntity);
	/**
	 * 获取用户风控记录
	 * @return
	 */
	List<Map<String, Object>> statisticsRiskNum();
	/**
	 * 获取用户风控人数总计
	 * @return
	 */
	Long statisticsRiskTotalNum();

	// 团队代理账号列表
	Page<TeamAgent> getAgentTeamList(UserEntity user, PageParam pageParam);

	/**
	 * 根据账号查询
	 * @param account
	 * @return
	 */
	UserParamTwo queryByAccount(String account);
	/**
	 * 通过UserEntity,以及代理商id
	 *     查询当前代理商下的所有会员信息
	 * @param user
	 * @return
	 */
	List<UserEntity> findProxyUser(Page<UserEntity> page,UserEntity user);

	/**
	 * 通过id查询用户名
	 * @param id
	 * @return
	 */
	String selectAccountById(Long id);

	/**
	 * 修改会员层级通过account
	 */
	void batchUpdateHierarchy(Long hierarchyId,Long id);

	/**
	 * 修改会员类型
	 * @param userType
	 * @param id
	 */
	void batchMarkTestUser(Long[] id);
	/**
	 * GM账号账号列表
	 * @return
	 */
	List<Long> getGmUser();
	/**
	 * 是否是GM账号
	 * @param userId
	 * @return
	 */
	boolean isGmUser(Long userId);

	/**
	 * 查询直属上级账号
	 * @return
	 */
	String getSuperiorsName(Long userId);
}

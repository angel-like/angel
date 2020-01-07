package com.xmsy.server.zxyy.webhome.modules.manager.user.service;

import org.apache.ibatis.annotations.Param;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.xmsy.common.define.page.PageParam;
import com.xmsy.server.zxyy.webhome.common.utils.Constant.TransactionDetailType;
import com.xmsy.server.zxyy.webhome.common.utils.PageUtils;
import com.xmsy.server.zxyy.webhome.modules.app.balance.param.SwitchParam;
import com.xmsy.server.zxyy.webhome.modules.app.login.param.*;
import com.xmsy.server.zxyy.webhome.modules.app.login.result.UserDetail;
import com.xmsy.server.zxyy.webhome.modules.app.user.param.EnterGameParam;
import com.xmsy.server.zxyy.webhome.modules.app.user.param.RecommenderParam;
import com.xmsy.server.zxyy.webhome.modules.app.user.param.UpdateCoinParam;
import com.xmsy.server.zxyy.webhome.modules.app.user.param.UpdateRoomCardParam;
import com.xmsy.server.zxyy.webhome.modules.manager.agent.param.Agent;
import com.xmsy.server.zxyy.webhome.modules.manager.orderrecharge.entity.OrderRechargeEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.user.entity.UserEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.user.param.UserParam;
import com.xmsy.server.zxyy.webhome.modules.manager.userinfo.entity.UserInfoEntity;
import com.xmsy.server.zxyy.webhome.modules.web.login.entity.LoginEntity;
import com.xmsy.server.zxyy.webhome.modules.web.login.entity.PhoneRegisterEntity;
import com.xmsy.server.zxyy.webhome.modules.web.login.entity.RegisterEntity;
import com.xmsy.server.zxyy.webhome.modules.web.user.result.UserAccountResult;
import com.xmsy.server.zxyy.webhome.modules.web.user.result.UserInfoResult;
import com.xmsy.server.zxyy.webhome.modules.web.user.result.UserPasswordResult;
import com.xmsy.server.zxyy.webhome.modules.webim.logon.entity.WebimPhoneRegisterEntity;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * 用户信息表
 *
 * @author aleng
 * @email xxxxx
 * @date 2018-12-26 11:18:25
 */
public interface UserService extends IService<UserEntity> {

	PageUtils findUserPage(UserParam user, PageParam pageParam);

	/**
	 * 会员风控列表
	 * 
	 * @param user
	 * @param pageParam
	 * @return
	 */
	PageUtils findRiskUserPage(UserParam user, PageParam pageParam);

	List<Map<String, Object>> findUserList(UserParam user);

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
	UserDetail upgrade(UpgradeParams upgradeParams);

	UserDetail touristLogin(UserEntity user,  TLoginParams loginParams,String Ip);
	UserDetail touristRegister(TLoginParams loginParams);

	/**
	 * APP手机注册
	 * 
	 * @param user
	 * @return
	 */
	UserDetail appPhoneRegister(PhoneRegisterParams registerParams);


	/**
	 * APP手机注册
	 *
	 * @param user
	 * @return
	 */
	UserDetail appPhoneRegister(PhoneRegisterParamsExt registerParams);
	
	/**
	 * 绑定手机号
	 * 
	 * @param user
	 * @return
	 */
	void bindingPhone(BindingPhone bindingPhone,Long id);

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
	 * 修改用户金币
	 * 
	 * @param enterGameParam
	 */
	void updateUserRoomCard(UpdateRoomCardParam updateParam);

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
	 * web第三方登陆
	 */
	String webThirdPartyLogin(UserEntity user, OAuth2Params loginParams, String ip);

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
	Page<UserEntity> getAgentSubordinateList(Long userId, String account, PageParam pageParam);

	/**
	 * 根据用户ID修改用户金币，余额，佣金等
	 * 
	 * @param user
	 */
	void updateUserWalletByUserId(UserEntity user);

    void freezeUserCoinByUserId(UserEntity user);
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
	void batchUpgradeUserAccount(Long userId,String account);


	void updateUserCoin(UserEntity user, Long coin, int type, int detailType);

	void freezeUserCoin(UserEntity user, int type, int detailType);

	/**
	 * 扣除会员里的金额
	 * 
	 * @param user
	 *            用户实体
	 * @param param
	 *            扣除金额参数 所在的实体类
	 */
	Integer upadateUserSubtractCoin(UserEntity user, SwitchParam param);

	/**
	 * 通过token获取当前会员实体 并判断会员实体是否为空
	 * 
	 * @param token
	 * @return
	 */
	UserEntity getUser(String token);

	/**
	 * 通过touristId获取当前会员实体 并判断会员实体是否为空
	 *
	 * @param token
	 * @return
	 */
	UserEntity getUserByTouristId(String touristId);

	UserEntity getUserByPhone(String phone);

	// web端手机注册
	UserEntity webPhoneRegister(PhoneRegisterEntity registerEntity);

	/**
	 * 33WEB注册
	 * 
	 * @param phoneRegisterEntity
	 * @return
	 */
	UserEntity webimPhoneRegister(WebimPhoneRegisterEntity phoneRegisterEntity);

	Long statisticsRiskNum();
	/**
	 * 通过用户id  修改用户上级id
	 * @param userId
	 * @param superiorsId
	 */
	Integer updateSuperiorsId(@Param("userId") Long userId,@Param("superiorsId") Long superiorsId);
	
	List<Long> getGmUser();
	/**
	 * 是否是GM账号
	 * @param userId
	 * @return
	 */
	boolean isGmUser(Long userId);


	UserDetail phoneLogin(UserEntity user,PhoneLoginParams phoneLoginParams, String ip);

	UserDetail phoneRegister(PhoneLoginParams phoneLoginParams);

	void unfreezeUserCoin(UserEntity entity);
}

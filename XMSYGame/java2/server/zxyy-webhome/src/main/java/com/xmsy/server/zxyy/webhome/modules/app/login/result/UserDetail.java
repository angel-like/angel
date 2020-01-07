package com.xmsy.server.zxyy.webhome.modules.app.login.result;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.xmsy.server.zxyy.webhome.cache.LocalContentCache;
import com.xmsy.server.zxyy.webhome.common.utils.SpringUtil;
import com.xmsy.server.zxyy.webhome.constant.SysConstant;
import com.xmsy.server.zxyy.webhome.modules.app.login.param.OnlineInfo;
import com.xmsy.server.zxyy.webhome.modules.manager.user.entity.UserEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.userinfo.entity.UserInfoEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.userrecommendationrecord.entity.UserRecommendationRecordEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.userrecommendationrecord.service.UserRecommendationRecordService;
import com.xmsy.server.zxyy.webhome.utils.Base64Util;
import com.xmsy.server.zxyy.webhome.utils.VerificationUitl;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class UserDetail implements Serializable {

	private static final long serialVersionUID = 1L;

	public UserDetail() {
		super();
	}

	public UserDetail(UserEntity user, UserInfoEntity userinfo, String officialUrl, String supportUrl,
			Boolean bindPassword) {
		super();
		if (null != user) {
			if(SysConstant.TOURIST.equals(user.getUserType())){
				this.tourist = true;

			}else {
				this.tourist =false;
			}
			if(StringUtils.isNotEmpty(user.getOpenId())){
				this.bind=true;
			}else {
				this.bind =false;
			}
			this.id = user.getId();
			this.hierarchyId = user.getHierarchyId();
			this.riskHierarchyId = user.getRiskHierarchyId();
			this.account = user.getAccount();
			this.userName = user.getUserName();
			this.portrait = user.getPortrait();
			this.sex = user.getSex();
			this.token = user.getToken();
			this.coin = user.getCoin();
			this.freezeCoin = user.getFreezeCoin();
			this.roomCard = user.getRoomCard();
			this.commission = user.getCommission();
			this.money = user.getMoney();
			this.unreadNum = user.getUnreadNum();
			this.gameInfoId = user.getGameInfoId();
			this.gameServerId = user.getGameServerId();
			this.forbiddenEnable = user.getForbiddenEnable();
			this.frozenEnable = user.getFrozenEnable();
			this.officialUrl = officialUrl;
			this.supportUrl = supportUrl;
			this.bindPassword = bindPassword;
			this.vipId=user.getVipId();
			this.headframeId=user.getHeadframeId();
			this.nickName=Base64Util.base64Decoder(user.getNickName());
			this.phone=VerificationUitl.phoneNoShow(user.getPhone());
			this.pointKillEnable=user.getPointKillEnable();//点杀状态
			this.pointKillRate=user.getPointKillRate();//点杀概率
		}
		if (null != userinfo) {
			this.bankCard = VerificationUitl.bankCartNoShow(userinfo.getBankCard());
			this.alipayAccount = userinfo.getAlipayAccount();
			this.integral =userinfo.getIntegral();
		}
	}

	public UserDetail(UserEntity user, UserInfoEntity userinfo, Boolean bindPassword) {
		super();
		if (null != user) {
			if(SysConstant.TOURIST.equals(user.getUserType())){
				this.tourist = true;

			}else {
				this.tourist =false;
			}
			if(StringUtils.isNotEmpty(user.getOpenId())){
				this.bind=true;
			}else {
				this.bind =false;
			}
			this.id = user.getId();
			this.hierarchyId = user.getHierarchyId();
			this.riskHierarchyId = user.getRiskHierarchyId();
			this.account = user.getAccount();
			this.userName = user.getUserName();
			this.portrait = user.getPortrait();
			this.sex = user.getSex();
			this.coin = user.getCoin();
			this.freezeCoin =user.getFreezeCoin();
			this.roomCard = user.getRoomCard();
			this.commission = user.getCommission();
			this.money = user.getMoney();
			this.unreadNum = user.getUnreadNum();
			this.gameInfoId = user.getGameInfoId();
			this.gameServerId = user.getGameServerId();
			this.forbiddenEnable = user.getForbiddenEnable();
			this.frozenEnable = user.getFrozenEnable();
			this.bindPassword = bindPassword;
			this.vipId=user.getVipId();
			this.headframeId=user.getHeadframeId();
			this.nickName=Base64Util.base64Decoder(user.getNickName());
			this.phone=VerificationUitl.phoneNoShow(user.getPhone());
			this.pointKillEnable=user.getPointKillEnable();//点杀状态
			this.pointKillRate=user.getPointKillRate();//点杀概率
		}
		if (null != userinfo) {
			this.bankCard = VerificationUitl.bankCartNoShow(userinfo.getBankCard());
			this.alipayAccount = userinfo.getAlipayAccount();
			this.integral =userinfo.getIntegral();
		}

	}

	/**
	 * 用户id
	 */
	private Long id;
	
		/**
	 * 层级id
	 */
	private Long hierarchyId;
	
	/**
	 * 风控层级id
	 */
	private Long riskHierarchyId;
	

	/**
	 * 账号名称
	 */
	private String account;

	/**
	 * 真实姓名
	 */
	private String userName;
	/**
	 * 头像
	 */
	private String portrait;
	/**
	 * 性别(0:女，1：男)
	 */

	private Boolean sex;

	/**
	 * token验证Id
	 */
	private String token;
	/**
	 * 用户金币
	 */
	private Long coin;


	/**
	 * 用户金币
	 */
	private Long freezeCoin;
	
	/**
	 * 房卡
	 */
	private Long roomCard;
	/**
	 * 佣金
	 */
	private BigDecimal commission;
	/**
	 * 金钱
	 */
	private BigDecimal money;
	/**
	 * 未读消息条数
	 */
	private Integer unreadNum;
	/**
	 * 未开启红包个数
	 */
	private Integer unEnvelopeNum;
	/**
	 * 游戏信息id
	 */
	private Long gameInfoId;
	/**
	 * 游戏服务id
	 */
	private Long gameServerId;
	/**
	 * 银行卡号
	 */
	private String bankCard;
	/**
	 * 阿里账号
	 */
	private String alipayAccount;
	/**
	 * 禁用（正常）
	 */
	private Boolean forbiddenEnable;
	/**
	 * 冻结(正常)
	 */
	private Boolean frozenEnable;
	/**
	 * 客服url
	 */
	private String supportUrl;
	/**
	 * 官网url
	 */
	private String officialUrl;
	/**
	 * 大厅ip
	 */
	private String hallIp;
	/**
	 * 大厅端口
	 */
	private Integer hallPort;

	/**
	 * 是否绑定取款密码
	 */
	private Boolean bindPassword;
	
	/**
	 * vip等级Id
	 */
	private Long vipId;
	
	/**
	 * 下一个vip等级Id
	 */
	private Long nextVipId;
	/**
	 * 下一个vip等级name
	 */
	private String nextVipName;
	/**
	 * 当前充值金额
	 */
	private Long currentRechargeAmount;
	/**
	 * 下一个vip等级需充值的金额
	 */
	private Long nextRechargeAmount;
	/**
	 *头像框Id
	 */
	private Long headframeId;
	/**
	 * vip等级名称
	 */
	@SuppressWarnings("unused")
	private String vipName;
	
	/**
	 *	绑定邀请码
	 */
	@SuppressWarnings("unused")
	private String invitationCode;
	/**
	 *	绑定手机号
	 */
	private String phone;
	/**
	 *	绑定第三方
	 */
	private String nickName;
	/**
	 *	地理位置
	 */
	private String address;
	private  boolean tourist;
	private  boolean bind;
	private   Long  integral;

	private OnlineInfo onlineInfo;
	/**
	 * 点杀状态
	 */
	private Boolean pointKillEnable;
	/**
	 * 点杀概率
	 */
	private BigDecimal pointKillRate;
	public String getVipName() {
		
		if(this.getVipId()==null || this.getVipId()==0) {
			return "vip0";
		}
		String userVip = SpringUtil.getApplicationContext().getBean(LocalContentCache.class).getVipName(this.getVipId());
		return userVip!=null?userVip:"";
		
	}
	
	public String getInvitationCode() {
		UserRecommendationRecordEntity userRecommendation = SpringUtil.getApplicationContext().
				getBean(UserRecommendationRecordService.class).
		selectOne(new EntityWrapper<UserRecommendationRecordEntity>
		(new UserRecommendationRecordEntity().setUserId(this.getId())));
		return userRecommendation==null || userRecommendation.getRecommendationCode()==null?"":userRecommendation.getRecommendationCode();
		
	}

}

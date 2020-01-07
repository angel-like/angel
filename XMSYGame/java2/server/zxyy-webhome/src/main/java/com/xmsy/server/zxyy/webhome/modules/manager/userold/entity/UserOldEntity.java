package com.xmsy.server.zxyy.webhome.modules.manager.userold.entity;

import com.baomidou.mybatisplus.annotations.TableName;
import com.xmsy.server.zxyy.webhome.base.BaseEntity;

import java.math.BigDecimal;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;


/**
 * 用户信息表
 * 
 * @author axiong
 * @email xxxxx
 * @date 2019-06-22 15:43:01
 */
@Data
@EqualsAndHashCode(callSuper=false)
@Accessors(chain=true)
@TableName("user_old")
public class UserOldEntity extends BaseEntity<UserOldEntity> {
	private static final long serialVersionUID = 1L;
							/**
	 * 账号名称
	 */
    private String account;
			/**
	 * 头像
	 */
    private String portrait;
			/**
	 * 头像框id
	 */
    private Long headframeId;
			/**
	 * 性别(0:女，1：男)
	 */
    private Boolean sex;
			/**
	 * 账号类型（试玩账号，普通会员，vip）
	 */
    private String userType;
			/**
	 * token验证Id
	 */
    private String token;
			/**
	 * 第三方平台
	 */
    private String unionType;
			/**
	 * 第三方交互ID
	 */
    private String openId;
			/**
	 * 第三方ID
	 */
    private String unionId;
			/**
	 * 上级ID
	 */
    private Long superiorsId;
			/**
	 * 禁用（正常）
	 */
    private Boolean forbiddenEnable;
			/**
	 * 停押（正常）
	 */
    private Boolean nobetEnable;
			/**
	 * 冻结(正常)
	 */
    private Boolean frozenEnable;
			/**
	 * 是否异常
	 */
    private Boolean abnormalEnable;
			/**
	 * 层级id
	 */
    private Long hierarchyId;
			/**
	 * 风控层级id
	 */
    private Long riskHierarchyId;
			/**
	 * VIP等级id
	 */
    private Long vipId;
			/**
	 * 用户金币
	 */
    private Long coin;
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
	 * 注册ip
	 */
    private String registerIp;
			/**
	 * 注册ip地址
	 */
    private String registerIpAddress;
			/**
	 * 注册机器码
	 */
    private String registerDeviceCode;
			/**
	 * 真实姓名
	 */
    private String userName;
			/**
	 * 备注
	 */
    private String remark;
			/**
	 * 状态（1：是代理。0：禁用代理）
	 */
    private Boolean agentEnable;
			/**
	 * 是否首冲过
	 */
    private Boolean firstRecharge;
			/**
	 * 在玩游戏信息id
	 */
    private Long gameInfoId;
			/**
	 * 游戏服务id
	 */
    private Long gameServerId;
			/**
	 * 0:检索风控  1：不检索风控
	 */
    private Boolean noScan;
			/**
	 * 手机号
	 */
    private String phone;
	}

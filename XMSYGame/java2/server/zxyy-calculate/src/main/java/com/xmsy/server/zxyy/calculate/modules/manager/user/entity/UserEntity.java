package com.xmsy.server.zxyy.calculate.modules.manager.user.entity;

import java.math.BigDecimal;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.xmsy.server.zxyy.calculate.base.BaseEntity;
import com.xmsy.server.zxyy.calculate.modules.manager.user.param.UserOperater;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 用户信息表
 * 
 * @author aleng
 * @email xxxxx
 * @date 2019-01-16 10:17:38
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("user")
public class UserEntity extends BaseEntity<UserEntity> {
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
	 * 头像框
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
	 * 点杀(正常)
	 */
	private Boolean pointKillEnable;
	/**
	 * 层级id
	 */
	private Long hierarchyId;
	/**
	 * 用户金币
	 */
	private Long coin;

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
	 * 代理状态（）
	 */
	private Boolean agentEnable;
	/**
	 * 是否充值过
	 */
	private Boolean firstRecharge;

	/**
	 * 操作内容
	 */
	@TableField(exist = false)
	private UserOperater userOperater;
	/**
	 * 未读消息条数
	 */
	@TableField(exist = false)
	private Integer unreadNum;
	/**
	 * 游戏信息id
	 */
	private Long gameInfoId;
	/**
	 * 游戏服务id
	 */
	private Long gameServerId;
	/**
	 * 电话
	 */
	private String phone;
	
	/**
	 * 是否扫描
	 */
	private Boolean noScan;
	/**
	 * 房卡
	 */
	private Long roomCard;
	/**
	 * VIP等级id
	 */
	private Long vipId;
}

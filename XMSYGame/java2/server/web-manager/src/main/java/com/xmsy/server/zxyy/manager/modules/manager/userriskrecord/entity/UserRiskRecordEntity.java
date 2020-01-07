package com.xmsy.server.zxyy.manager.modules.manager.userriskrecord.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.xmsy.server.zxyy.manager.base.BaseEntity;

import java.math.BigDecimal;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;


/**
 * 用户风控记录表
 * 
 * @author xiaoling
 * @email xxxxx
 * @date 2019-06-06 14:56:24
 */
@Data
@EqualsAndHashCode(callSuper=false)
@Accessors(chain=true)
@TableName("user_risk_record")
public class UserRiskRecordEntity extends BaseEntity<UserRiskRecordEntity> {
	private static final long serialVersionUID = 1L;
							/**
	 * 用户id
	 */
    private Long userId;
			/**
	 * 用户类型
	 */
    private String userAccount;
			/**
	 * 风控类型
	 */
    private String riskType;
			/**
	 * 金币值
	 */
    private Long coin;
			/**
	 * 层级id
	 */
    private Long hierarchyId;
			/**
	 * 风控值
	 */
    private BigDecimal riskVal;
			/**
	 * 充值钱
	 */
    private BigDecimal rechargeVal;
    
    /**
     * 层级名称
     */
    @TableField(exist = false)
    private String hierarchyName;
    
    /**
     * 风控类型名称
     */
    @TableField(exist = false)
    private String riskTypeName;
	}

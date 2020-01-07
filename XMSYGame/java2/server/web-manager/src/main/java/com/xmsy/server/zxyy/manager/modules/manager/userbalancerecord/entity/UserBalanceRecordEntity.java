package com.xmsy.server.zxyy.manager.modules.manager.userbalancerecord.entity;

import java.math.BigDecimal;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.xmsy.server.zxyy.manager.base.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;


/**
 * 用户账户金额存取记录表
 *
 * @author axiong
 * @email xxxxx
 * @date 2019-06-18 09:18:59
 */
@Data
@EqualsAndHashCode(callSuper=false)
@Accessors(chain=true)
@TableName("user_balance_record")
public class UserBalanceRecordEntity extends BaseEntity<UserBalanceRecordEntity> {
	private static final long serialVersionUID = 1L;
							/**
	 * 用户ID
	 */
    private Long userId;
	/**
	 * 用户账号
	 */
	private String userAccount;
			/**
	 * 交易金额
	 */
    private Long money;
			/**
	 * 取出金额
	 */
    private Long takeMoney;
			/**
	 * 是否生效
	 */
    private Boolean effect;
			/**
	 * 类型 0:存入1:取出
	 */
    private Integer type;
    
    /**
     *  理财产品id
     */
    private Long userBalanceProductId;
    
    /**
     *  会员姓名
     */
    @TableField(exist=false)
    private String userName;
    /**
     * 理财产品
     */
    @TableField(exist=false)
    private String productName;
    /**
     * 结算类型（0:循环结算，1本金结算）
     */
    @TableField(exist=false)
    private Integer settlementType;
    /**
     * 利率
     */
    @TableField(exist=false)
    private BigDecimal rate;
    
    /**
     * 开始时间
     */
    @TableField(exist=false)
    private String sTime;
    /**
     * 结束时间
     */
    @TableField(exist=false)
    private String eTime;
}

package com.xmsy.server.zxyy.manager.modules.manager.ordercashrecord.entity;

import com.baomidou.mybatisplus.annotations.TableName;


import java.math.BigDecimal;


import com.xmsy.server.zxyy.manager.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;


/**
 * 用户打码量记录表
 * 
 * @author ayang
 * @email xxxxx
 * @date 2019-12-25 16:30:46
 */
@Data
@EqualsAndHashCode(callSuper=false)
@Accessors(chain=true)
@TableName("order_cash_record")
public class OrderCashRecordEntity extends BaseEntity<OrderCashRecordEntity> {
	private static final long serialVersionUID = 1L;
							/**
	 * 操作类型
	 */
    private Integer type;
			/**
	 * 用户id
	 */
    private Long userId;
			/**
	 * 操作金额
	 */
    private BigDecimal amount;
			/**
	 * 操作人用户名
	 */
    private String sysUserAccount;
			/**
	 * 操作人id
	 */
    private Long sysUserId;
			/**
	 * 
	 */
    private String userAccount;
    private  String remark;
	}

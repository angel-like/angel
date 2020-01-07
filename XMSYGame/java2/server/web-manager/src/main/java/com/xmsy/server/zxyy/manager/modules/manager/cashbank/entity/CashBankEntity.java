package com.xmsy.server.zxyy.manager.modules.manager.cashbank.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.xmsy.server.zxyy.manager.base.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;


/**
 * 系统银行表
 * 
 * @author aleng
 * @email xxxxx
 * @date 2019-01-07 18:46:36
 */
@Data
@EqualsAndHashCode(callSuper=false)
@Accessors(chain=true)
@TableName("cash_bank")
public class CashBankEntity extends BaseEntity<CashBankEntity> {
	private static final long serialVersionUID = 1L;
							/**
	 * 银行名称
	 */
    private String name;
			/**
	 * 所属层级ID
	 */
    private String hierarchyId;
			/**
	 * 银行卡号
	 */
    private String bankAccount;
			/**
	 * 收款人姓名
	 */
    private String bankUser;
			/**
	 * 开户行
	 */
    private String bankAddress;
    	/**
	 * 是否展示
	 */
    private Boolean enable;
    
    /**
     * 排序字段
     */
    private Integer sorts;
    /**
	 * 所属层级name
	 */
    @TableField(exist = false)
    private String hierarchyName;
	}

package com.xmsy.server.zxyy.manager.modules.manager.userrebate.entity;

import java.math.BigDecimal;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.xmsy.server.zxyy.manager.base.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;


/**
 * 用户返利
 * 
 * @author aleng
 * @email xxxxx
 * @date 2019-02-16 18:45:34
 */
@Data
@EqualsAndHashCode(callSuper=false)
@Accessors(chain=true)
@TableName("user_rebate")
public class UserRebateEntity extends BaseEntity<UserRebateEntity> {
	private static final long serialVersionUID = 1L;
							/**
	 * 返利类型
	 */
    private Integer type;
			/**
	 * 返利金币
	 */
    private Long coin;
	/**
	 * 返水比例
	 */
    private BigDecimal waterRate;
    /**
    * 打码倍数
	 */
   private BigDecimal codeMultiple;
	/**
	 * 类型名称
	 */
    @TableField(exist=false)
    private String typeName;
	}

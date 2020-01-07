package com.xmsy.server.zxyy.webhome.modules.manager.userriskconfig.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.xmsy.server.zxyy.webhome.base.BaseEntity;

import java.math.BigDecimal;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;


/**
 * 用户风控配置表
 * 
 * @author xiaoling
 * @email xxxxx
 * @date 2019-05-05 11:28:56
 */
@Data
@EqualsAndHashCode(callSuper=false)
@Accessors(chain=true)
@TableName("user_risk_config")
public class UserRiskConfigEntity extends BaseEntity<UserRiskConfigEntity> {
	private static final long serialVersionUID = 1L;
							/**
	 * 层级id
	 */
    private Long hierarchyId;
			/**
	 * 风控类型
	 */
    private String riskType;
			/**
	 * 风控值
	 */
    private BigDecimal riskVal;
    
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

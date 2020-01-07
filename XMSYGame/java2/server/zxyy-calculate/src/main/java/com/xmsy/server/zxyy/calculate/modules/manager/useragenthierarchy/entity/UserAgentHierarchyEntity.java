package com.xmsy.server.zxyy.calculate.modules.manager.useragenthierarchy.entity;

import java.math.BigDecimal;

import com.baomidou.mybatisplus.annotations.TableName;
import com.xmsy.server.zxyy.calculate.base.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;


/**
 * 用户代理层级设置
 * 
 * @author aleng
 * @email xxxxx
 * @date 2019-01-24 19:54:08
 */
@Data
@EqualsAndHashCode(callSuper=false)
@Accessors(chain=true)
@TableName("user_agent_hierarchy")
public class UserAgentHierarchyEntity extends BaseEntity<UserAgentHierarchyEntity> {
	private static final long serialVersionUID = 1L;
							/**
	 * 层级名称
	 */
    private String name;
			/**
	 * 是否是默认层级1为默认层级
	 */
    private Boolean type;
		/**
	* 赠送金币
	*/
	private Long coin;
	/**
	* 赠送佣金
	*/
	private BigDecimal commission;
	/**
	* 返佣比例
	*/
	private BigDecimal proportion;
	}

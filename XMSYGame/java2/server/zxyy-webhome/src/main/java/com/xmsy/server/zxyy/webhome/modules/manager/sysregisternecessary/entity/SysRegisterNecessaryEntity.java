package com.xmsy.server.zxyy.webhome.modules.manager.sysregisternecessary.entity;

import com.baomidou.mybatisplus.annotations.TableName;
import com.xmsy.server.zxyy.webhome.base.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;


/**
 * 注册必填控制表
 * 
 * @author aleng
 * @email xxxxx
 * @date 2018-12-28 10:51:51
 */
@Data
@EqualsAndHashCode(callSuper=false)
@Accessors(chain=true)
@TableName("sys_register_necessary")
public class SysRegisterNecessaryEntity extends BaseEntity<SysRegisterNecessaryEntity> {
	private static final long serialVersionUID = 1L;
							/**
	 * 显示名称
	 */
    private String name;
			/**
	 * 是否展示
	 */
    private Boolean show;
			/**
	 * 是否必填
	 */
    private Boolean necessary;
			/**
	 * 提示语
	 */
    private String hints;
			/**
	 * 序号
	 */
    private String orderNumber;
			/**
	 * 备注
	 */
    private String remark;
    /**
	 * 验证规则
	 */
    private String validationRule;
    /**
	 * 验证错误提示
	 */
    private String validationDescribe;
	}

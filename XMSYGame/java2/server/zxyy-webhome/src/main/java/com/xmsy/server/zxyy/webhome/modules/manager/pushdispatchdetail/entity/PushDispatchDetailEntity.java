package com.xmsy.server.zxyy.webhome.modules.manager.pushdispatchdetail.entity;

import java.util.Date;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.baomidou.mybatisplus.annotations.TableName;
import com.xmsy.server.zxyy.webhome.base.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 
 * 
 * @author aleng
 * @email xxxxx
 * @date 2019-05-22 19:49:20
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("push_dispatch_detail")
public class PushDispatchDetailEntity extends BaseEntity<PushDispatchDetailEntity> {
	private static final long serialVersionUID = 1L;
	/**
	 * 标题
	 */
	private String title;
	/**
	 * 操作人
	 */
	private String operator;
	/**
	 * 接收者账号
	 */
	private String recipient;
	/**
	* 
	*/
	private Date executeTime;
	/**
	 * 状态 0：未执行 1：成功 2：失败
	 */
	private Integer status;
	/**
	 * 范围 1:广播 2: 个人
	 */
	@NotNull(message = "推送范围不能为空")
	private Integer scope;
	/**
	 * 类型 1:即时 2: 定时
	 */
	@NotNull(message = "推送类型不能为空（1:即时 2: 定时，3,循环定时）")
	private Integer type;
	/**
	 * 发送内容
	 */
	@NotBlank(message = "推送内容不能为空")
	private String content;

	/**
	 * 发送层级
	 */
	private String hierarchyIds;

	/**
	 * 原因
	 */
	private String failReason;
}

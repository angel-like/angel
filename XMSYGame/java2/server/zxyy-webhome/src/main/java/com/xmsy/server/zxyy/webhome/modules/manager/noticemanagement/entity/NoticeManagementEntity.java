package com.xmsy.server.zxyy.webhome.modules.manager.noticemanagement.entity;

import java.util.Date;

import com.baomidou.mybatisplus.annotations.TableName;
import com.xmsy.server.zxyy.webhome.base.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 消息管理
 * 
 * @author aleng
 * @email xxxxx
 * @date 2019-01-03 17:26:35
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("sys_notice_management")
public class NoticeManagementEntity extends BaseEntity<NoticeManagementEntity> {
	private static final long serialVersionUID = 1L;
	/**
	 * 标题
	 */
	private String title;
	/**
	 * 内容
	 */
	private String content;
	/**
	 * 状态（0：禁用 1：启用）
	 */
	private Boolean enable;
	/**
	 * 生效时间
	 */
	private Date effectTime;
	/**
	 * 失效时间
	 */
	private Date failureTime;
	/**
	 * 目标对象（1：指定用户id 2:指定用户层次 3：所有用户）
	 */
	private Integer targetObject;
	/**
	 * 层级id集合
	 */
	private String hierarchyIds;
	/**
	 * 大厅id
	 */
	private Long noticeType;

}

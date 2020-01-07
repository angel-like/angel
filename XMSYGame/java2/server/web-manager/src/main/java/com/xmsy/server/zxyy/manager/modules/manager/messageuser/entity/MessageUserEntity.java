package com.xmsy.server.zxyy.manager.modules.manager.messageuser.entity;

import com.baomidou.mybatisplus.annotations.TableName;
import com.xmsy.server.zxyy.manager.base.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Date;


/**
 * 消息管理
 * 
 * @author aleng
 * @email xxxxx
 * @date 2019-01-02 16:39:41
 */
@Data
@EqualsAndHashCode(callSuper=false)
@Accessors(chain=true)
@TableName("sys_message_user")
public class MessageUserEntity extends BaseEntity<MessageUserEntity> {
	private static final long serialVersionUID = 1L;
	/**
	 * 消息id
	 */
    private Long messageId;
	/**
	 * 用户id
	 */
    private Long userId;
	/**
	 * 用户账号
	 */
    private String userAccount;
	/**
	 * 状态（0：未读 1：已读）
	 */
    private Boolean status;
    /**
     *活动类型
     */
    private Integer activityType;
    /**
     * 活动id
     */
    private Long activityId;
    /**
     * 是否领取
     */
    private Boolean receive;
    /**
     * 站内信删除状态（0：未删 1：已删）
     */
    private Boolean deleteMessage;

	/**
	 * 生效时间
	 */
	private Date effectTime;
	/**
	 * 失效时间
	 */
	private Date failureTime;
	/*
	 * 标题
	 */
	private String title;
	/**
	 * 内容
	 */
	private String content;
	/**
	 * 类型(1：会员站内信 2:管理员站内信）
	 */
	private Integer contentType;

	}

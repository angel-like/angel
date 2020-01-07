package com.xmsy.server.zxyy.manager.modules.manager.messagemanagement.entity;

import java.util.Date;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.xmsy.server.zxyy.manager.base.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;


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
@TableName("sys_message_management")
public class MessageManagementEntity extends BaseEntity<MessageManagementEntity> {
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
	 * 类型(1：会员站内信 2:管理员站内信）
	 */
    private Integer contentType;
	/**
	 * 状态（0：禁用  1：启用）
	 */
    private Boolean enable;
    /**
     * 是否只读（0：否  1：是）
     */
    private Boolean readonly;
	/**
	 * 目标对象（1：指定用户id 2:指定用户层次 3：所有用户）
	 */
    private Integer targetObject;
	/**
	 * 生效时间
	 */
    private Date effectTime;
    /**
     * 失效时间
     */
    private Date failureTime;
    /**
     * 层级ids
     */
    private String hierarchyIds;
    /**
     * 用户账号集合
     */
    private String userAccount;
    /**
     * 是否立即发送
     */
    @TableField(exist=false)
    private Boolean rightNow;
	}

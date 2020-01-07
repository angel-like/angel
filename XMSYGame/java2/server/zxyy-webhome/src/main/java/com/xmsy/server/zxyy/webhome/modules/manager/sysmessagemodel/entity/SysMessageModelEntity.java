package com.xmsy.server.zxyy.webhome.modules.manager.sysmessagemodel.entity;

import com.baomidou.mybatisplus.annotations.TableName;
import com.xmsy.server.zxyy.webhome.base.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;


/**
 * 站内信
 * 
 * @author xiaoling
 * @email xxxxx
 * @date 2019-05-22 16:15:53
 */
@Data
@EqualsAndHashCode(callSuper=false)
@Accessors(chain=true)
@TableName("sys_message_model")
public class SysMessageModelEntity extends BaseEntity<SysMessageModelEntity> {
	private static final long serialVersionUID = 1L;
							/**
	 * 伪标题
	 */
    private String name;
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
	 * 有效期限（天），0是无限制
	 */
    private Integer effectTime;
	}

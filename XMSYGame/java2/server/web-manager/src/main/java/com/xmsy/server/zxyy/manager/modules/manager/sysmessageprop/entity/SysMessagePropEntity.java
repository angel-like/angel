package com.xmsy.server.zxyy.manager.modules.manager.sysmessageprop.entity;

import com.baomidou.mybatisplus.annotations.TableName;
import com.xmsy.server.zxyy.manager.base.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;


/**
 * 站内信-道具列表
 * 
 * @author adu
 * @email xxxxx
 * @date 2019-05-23 19:08:25
 */
@Data
@EqualsAndHashCode(callSuper=false)
@Accessors(chain=true)
@TableName("sys_message_prop")
public class SysMessagePropEntity extends BaseEntity<SysMessagePropEntity> {
	private static final long serialVersionUID = 1L;
							/**
	 * 模板邮件id
	 */
    private Long messageId;
			/**
	 * 道具id
	 */
    private Long propId;
			/**
	 * 道具数量
	 */
    private Integer propNum;
	}

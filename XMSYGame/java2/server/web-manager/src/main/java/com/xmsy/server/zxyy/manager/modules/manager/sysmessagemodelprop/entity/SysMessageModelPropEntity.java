package com.xmsy.server.zxyy.manager.modules.manager.sysmessagemodelprop.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.xmsy.server.zxyy.manager.base.BaseEntity;

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
@TableName("sys_message_model_prop")
public class SysMessageModelPropEntity extends BaseEntity<SysMessageModelPropEntity> {
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
    
    /**
     * 道具名称
     */
    @TableField(exist=false)
    private String propName;
	}

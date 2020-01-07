package com.xmsy.server.zxyy.manager.modules.manager.webhomefriendshiptemplate.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.xmsy.server.zxyy.manager.base.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;


/**
 * 友情链接内容
 * 
 * @author aleng
 * @email xxxxx
 * @date 2019-03-13 15:31:14
 */
@Data
@EqualsAndHashCode(callSuper=false)
@Accessors(chain=true)
@TableName("webhome_friendship_template")
public class WebhomeFriendshipTemplateEntity extends BaseEntity<WebhomeFriendshipTemplateEntity> {
	private static final long serialVersionUID = 1L;
							/**
	 * 友情链接ID
	 */
    private Long friendshipId;
			/**
	 * 内容
	 */
    private String content;
			/**
	 * 图标ID
	 */
    private Long icon;
    /**
	 * 图标路径
	 */
    @TableField(exist=false)
    private String iconUrl;
	}

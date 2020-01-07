package com.xmsy.server.zxyy.webhome.modules.manager.userheadframe.entity;

import com.baomidou.mybatisplus.annotations.TableName;
import com.xmsy.server.zxyy.webhome.base.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;


/**
 * 用户头像框关系表
 * 
 * @author axiong
 * @email xxxxx
 * @date 2019-06-19 10:17:16
 */
@Data
@EqualsAndHashCode(callSuper=false)
@Accessors(chain=true)
@TableName("user_headframe")
public class UserHeadframeEntity extends BaseEntity<UserHeadframeEntity> {
	private static final long serialVersionUID = 1L;
							/**
	 * 类型 
	 */
    private Long userId;
			/**
	 * 头像id
	 */
    private Long headframeId;
	}

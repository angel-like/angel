package com.xmsy.server.zxyy.manager.modules.manager.imgameintroduction.entity;

import com.baomidou.mybatisplus.annotations.TableName;
import com.xmsy.server.zxyy.manager.base.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 33推荐游戏介绍
 * 
 * @author xiaoliu
 * @email xxxxx
 * @date 2019-06-22 19:50:09
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("im_game_introduction")
public class ImGameIntroductionEntity extends BaseEntity<ImGameIntroductionEntity> {
	private static final long serialVersionUID = 1L;
	/**
	 * 标题
	 */
	private String title;
	/**
	 * 介绍
	 */
	private String content;
	/**
	 * 附件ID
	 */
	private Long enclosureId;
	/**
	 * 状态(启用，禁用)
	 */
	private Boolean enable;
	/**
	 * 排序号
	 */
	private Integer orderNo;
}

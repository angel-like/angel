package com.xmsy.server.zxyy.webhome.modules.manager.impromotionalgame.entity;

import com.baomidou.mybatisplus.annotations.TableName;
import com.xmsy.server.zxyy.webhome.base.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 33推荐游戏管理
 * 
 * @author xiaoliu
 * @email xxxxx
 * @date 2019-06-21 15:37:14
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("im_promotional_game")
public class ImPromotionalGameEntity extends BaseEntity<ImPromotionalGameEntity> {
	private static final long serialVersionUID = 1L;
	/**
	 * 标题
	 */
	private String title;
	/**
	 * 介绍
	 */
	private String introduction;
	/**
	 * 游关联ID
	 */
	private Long gameId;
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

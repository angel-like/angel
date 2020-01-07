package com.xmsy.server.zxyy.manager.modules.manager.imgameranking.entity;

import com.baomidou.mybatisplus.annotations.TableName;
import com.xmsy.server.zxyy.manager.base.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 33推荐热门游戏排行
 * 
 * @author xiaoliu
 * @email xxxxx
 * @date 2019-06-24 11:10:56
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("im_game_ranking")
public class ImGameRankingEntity extends BaseEntity<ImGameRankingEntity> {
	private static final long serialVersionUID = 1L;
	/**
	 * 标题
	 */
	private String title;
	/**
	 * 连胜场次
	 */
	private Integer num;
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

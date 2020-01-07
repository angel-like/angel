package com.xmsy.server.zxyy.webhome.modules.manager.webhomepopulargames.entity;

import com.baomidou.mybatisplus.annotations.TableName;
import com.xmsy.server.zxyy.webhome.base.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;


/**
 * 官网热门游戏
 * 
 * @author xiaoliu
 * @email xxxxx
 * @date 2019-01-29 16:07:37
 */
@Data
@EqualsAndHashCode(callSuper=false)
@Accessors(chain=true)
@TableName("webhome_popular_games")
public class WebhomePopularGamesEntity extends BaseEntity<WebhomePopularGamesEntity> {
	private static final long serialVersionUID = 1L;
							/**
	 * 名称
	 */
    private String name;
			/**
	 * 附件ID
	 */
    private Long enclosureId;
			/**
	 * 介绍
	 */
    private String introduction;
			/**
	 * 类型(menu，game）
	 */
    private String type;
			/**
	 * 类型Id
	 */
    private Long typeId;
			/**
	 * 路径
	 */
    private String url;
			/**
	 * 状态
	 */
    private Boolean enable;
			/**
	 * 排序号
	 */
    private Integer orderNum;
	}

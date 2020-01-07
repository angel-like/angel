package com.xmsy.server.zxyy.webhome.modules.manager.apppopulargames.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.xmsy.server.zxyy.webhome.base.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;


/**
 * APP热门游戏
 * 
 * @author aleng
 * @email xxxxx
 * @date 2019-05-04 11:37:38
 */
@Data
@EqualsAndHashCode(callSuper=false)
@Accessors(chain=true)
@TableName("app_popular_games")
public class AppPopularGamesEntity extends BaseEntity<AppPopularGamesEntity> {
	private static final long serialVersionUID = 1L;
							/**
	 * 游戏ID
	 */
    private Long gameId;

    /**
    *类型

     */


	private Long type;


    /**
	 * 游戏名称
	 */
    @TableField(exist=false)
    private String gameName;
	}




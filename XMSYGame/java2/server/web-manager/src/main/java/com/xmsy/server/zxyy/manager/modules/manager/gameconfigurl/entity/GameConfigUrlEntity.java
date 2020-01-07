package com.xmsy.server.zxyy.manager.modules.manager.gameconfigurl.entity;

import com.baomidou.mybatisplus.annotations.TableName;
import com.xmsy.server.zxyy.manager.base.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;


/**
 * 游戏路径表
 * 
 * @author aleng
 * @email xxxxx
 * @date 2019-03-12 14:08:17
 */
@Data
@EqualsAndHashCode(callSuper=false)
@Accessors(chain=true)
@TableName("game_config_url")
public class GameConfigUrlEntity extends BaseEntity<GameConfigUrlEntity> {
	private static final long serialVersionUID = 1L;
							/**
	 * 游戏路径
	 */
    private String url;
	}

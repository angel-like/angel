package com.xmsy.server.zxyy.webhome.modules.manager.rankinglist.entity;

import com.baomidou.mybatisplus.annotations.TableName;
import com.xmsy.server.zxyy.webhome.base.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;


/**
 * 排行榜
 * 
 * @author lpp
 * @email xxxxx
 * @date 2019-01-21 09:46:00
 */
@Data
@EqualsAndHashCode(callSuper=false)
@Accessors(chain=true)
@TableName("ranking_list")
public class RankingListEntity extends BaseEntity<RankingListEntity> {
	private static final long serialVersionUID = 1L;
	/**
	 * 排行榜名称
	 */
    private String rankListName;
	/**
	 * 状态（0：禁用  1：启用）
	 */
    private Boolean enable;
    /**
     * 参与抽奖最高名次
     */
    private Integer topNum;
	}

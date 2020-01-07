package com.xmsy.server.zxyy.manager.modules.manager.userrecommendationtree.entity;

import com.baomidou.mybatisplus.annotations.TableName;
import com.xmsy.server.zxyy.manager.base.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 用户推荐关系表
 * 
 * @author aleng
 * @email xxxxx
 * @date 2019-06-05 15:35:08
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("user_recommendation_tree")
public class UserRecommendationTreeEntity extends BaseEntity<UserRecommendationTreeEntity> {
	private static final long serialVersionUID = 1L;
	/**
	 * 被推荐人id
	 */
	private Long userId;
	/**
	 * 上级id
	 */
	private Long parantUserId;
	/**
	 * 等级差
	 */
	private Integer userParantDistance;
}

package com.xmsy.server.zxyy.manager.modules.manager.appmoneystrategy.entity;

import com.baomidou.mybatisplus.annotations.TableName;
import com.xmsy.server.zxyy.manager.base.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 赚钱攻略表
 * 
 * @author xiaoling
 * @email xxxxx
 * @date 2019-05-07 17:50:34
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("app_money_strategy")
public class AppMoneyStrategyEntity extends BaseEntity<AppMoneyStrategyEntity> {
	
	private static final long serialVersionUID = 1L;
	/**
	 * 名称
	 */
	private String name;
	/**
	 * 路径
	 */
	private String url;
	/**
	 * 图片id
	 */
	private Long enclosureId;
	/**
	 * 状态（1.不可点击，2：可跳转，3，跳游戏）
	 */
	private Long type;
	/**
	 * md5
	 */
	private String md5;
	/**
	 * 是否可用
	 */
	private Boolean availability;
}

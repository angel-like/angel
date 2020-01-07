package com.xmsy.server.zxyy.manager.modules.manager.appadcofig.entity;

import com.baomidou.mybatisplus.annotations.TableName;
import com.xmsy.server.zxyy.manager.base.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * app广告配置
 * 
 * @author aleng
 * @email xxxxx
 * @date 2019-03-04 19:20:35
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("app_ad_cofig")
public class AppAdCofigEntity extends BaseEntity<AppAdCofigEntity> {

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
	 * 跳转类型（1.不可点击，2：可跳转，3，跳游戏）
	 */
	private Long type;
	/**
	 * 大厅类型（1.主大厅，2.房卡大厅 ）
	 */
	private Integer hallType;
	/**
	 * md5
	 */
	private String md5;

}

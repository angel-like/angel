package com.xmsy.server.zxyy.webhome.modules.manager.appalertcofig.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.xmsy.server.zxyy.webhome.base.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;


/**
 * app弹框配置
 *
 * @author aleng
 * @email xxxxx
 * @date 2019-03-07 14:27:10
 */
@Data
@EqualsAndHashCode(callSuper=false)
@Accessors(chain=true)
@TableName("app_alert_cofig")
public class AppAlertCofigEntity extends BaseEntity<AppAlertCofigEntity> {
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
	 * 路径
	 */
	private String url;
	/**
	 * md5
	 */
	private String md5;
	/**
	 * 类型（1：不调整 2：指定页面地址  3 跳游戏 4：app内指定功能）
	 */
	private Integer type;
	/**
	 * 附件Url
	 */
	@TableField(exist=false)
	private String enclosureUrl;

}

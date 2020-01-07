package com.xmsy.server.zxyy.manager.modules.manager.cashqrcode.entity;

import com.baomidou.mybatisplus.annotations.TableName;
import com.xmsy.server.zxyy.manager.base.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 收款二维码
 * 
 * @author aleng
 * @email xxxxx
 * @date 2019-04-22 11:02:28
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("cash_qrcode")
public class CashQrcodeEntity extends BaseEntity<CashQrcodeEntity> {
	private static final long serialVersionUID = 1L;
	/**
	 * 昵称
	 */
	private String nickName;
	/**
	 * 账号
	 */
	private String account;
	/**
	 * 备注
	 */
	private String remark;
	/**
	 * 状态（0：禁用 1：启用）
	 */
	private Boolean enable;
	/**
	 * 图片url
	 */
	private String url;
	
	/**
	 * 图片md5值
	 */
	private String md5;
	/**
	 * 类型
	 */
	private Long type;
	/**
	 * 类型名称
	 */
	private String typeStr;
	/**
	 * 层级id
	 */
	private String hierarchyId;

	/**
	 * 层级名称
	 */
	private String hierarchyIdStr;
}

package com.xmsy.server.zxyy.webhome.modules.manager.proxytransferrecord.entity;

import java.math.BigDecimal;

import com.baomidou.mybatisplus.annotations.TableName;
import com.xmsy.server.zxyy.webhome.base.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;


/**
 * 划拨记录
 * 
 * @author axiong
 * @email xxxxx
 * @date 2019-08-07 15:51:01
 */
@Data
@EqualsAndHashCode(callSuper=false)
@Accessors(chain=true)
@TableName("proxy_transfer_record")
public class ProxyTransferRecordEntity extends BaseEntity<ProxyTransferRecordEntity> {
	private static final long serialVersionUID = 1L;
							/**
	 * 会员ID
	 */
    private Long userId;
			/**
	 * 会员账户
	 */
    private String userAccount;
			/**
	 * 会员名称
	 */
    private String userName;
			/**
	 * 业务ID 划拨编号
	 */
    private String orderNo;
			/**
	 * 划拨金币
	 */
    private BigDecimal transferCoin;
			/**
	 * 代理商ID
	 */
    private Long proxyId;
	}

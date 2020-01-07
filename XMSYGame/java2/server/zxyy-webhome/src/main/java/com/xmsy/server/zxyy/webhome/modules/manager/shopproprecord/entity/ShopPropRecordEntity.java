package com.xmsy.server.zxyy.webhome.modules.manager.shopproprecord.entity;

import com.baomidou.mybatisplus.annotations.TableName;
import com.xmsy.server.zxyy.webhome.base.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;


/**
 * 商城道具交易记录表
 * 
 * @author axiong
 * @email xxxxx
 * @date 2019-06-12 09:52:03
 */
@Data
@EqualsAndHashCode(callSuper=false)
@Accessors(chain=true)
@TableName("shop_prop_record")
public class ShopPropRecordEntity extends BaseEntity<ShopPropRecordEntity> {
	private static final long serialVersionUID = 1L;
							/**
	 * 道具名称id
	 */
    private Long sysPropId;
			/**
	 * 道具数量
	 */
    private Integer propNumber;
			/**
	 * 类型0：购买   1：使用
	 */
    private Integer type;
			/**
	 * 产品id
	 */
    private Long produceId;
			/**
	 * 会员id
	 */
    private Long userId;
			/**
	 * 会员账号
	 */
    private String userAccount;
	}

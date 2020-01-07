package com.xmsy.server.zxyy.webhome.modules.manager.webhomepromotionstype.entity;

import com.baomidou.mybatisplus.annotations.TableName;
import com.xmsy.server.zxyy.webhome.base.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;


/**
 * 官网优惠活动类型
 * 
 * @author aleng
 * @email xxxxx
 * @date 2019-01-28 16:22:11
 */
@Data
@EqualsAndHashCode(callSuper=false)
@Accessors(chain=true)
@TableName("webhome_promotions_type")
public class WebhomePromotionsTypeEntity extends BaseEntity<WebhomePromotionsTypeEntity> {
	private static final long serialVersionUID = 1L;
							/**
	 * 名字
	 */
    private String name;
	}

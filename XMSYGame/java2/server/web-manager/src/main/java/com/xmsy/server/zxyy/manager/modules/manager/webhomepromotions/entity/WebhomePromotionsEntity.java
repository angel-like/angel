package com.xmsy.server.zxyy.manager.modules.manager.webhomepromotions.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.xmsy.server.zxyy.manager.base.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;


/**
 * 官网优惠活动
 * 
 * @author aleng
 * @email xxxxx
 * @date 2019-01-28 16:22:11
 */
@Data
@EqualsAndHashCode(callSuper=false)
@Accessors(chain=true)
@TableName("webhome_promotions")
public class WebhomePromotionsEntity extends BaseEntity<WebhomePromotionsEntity> {
	private static final long serialVersionUID = 1L;
							/**
	 * 属性ID
	 */
    private Long typeId;
			/**
	 * 附件ID
	 */
    private Long enclosureId;
			/**
	 * 内容
	 */
    private String content;
			/**
	 * 备用字段
	 */
    private String remake;
		/**
	* 属性名称
	*/
    @TableField(exist=false)
	private String typeName;
	}

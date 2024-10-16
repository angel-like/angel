package com.xmsy.server.zxyy.webhome.modules.manager.shoproomcardproportion.entity;

import com.baomidou.mybatisplus.annotations.TableName;
import com.xmsy.server.zxyy.webhome.base.BaseEntity;

import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;


/**
 * 金币兑换房卡比例表
 * 
 * @author axiong
 * @email xxxxx
 * @date 2019-07-05 17:00:34
 */
@Data
@EqualsAndHashCode(callSuper=false)
@Accessors(chain=true)
@TableName("shop_room_card_proportion")
public class ShopRoomCardProportionEntity extends BaseEntity<ShopRoomCardProportionEntity> {
	private static final long serialVersionUID = 1L;
	
	/**
	 * 持有道具
	 */
	private Integer holdProp;
	/**
	* 目标道具
	*/
	private Integer targetProp;
	/**
	* 状态
	*/
	private Boolean status;
							/**
	 * 比例
	 */
    private BigDecimal proportion;
			/**
	 * 生效时间
	 */
    private Date effectDate;
	}

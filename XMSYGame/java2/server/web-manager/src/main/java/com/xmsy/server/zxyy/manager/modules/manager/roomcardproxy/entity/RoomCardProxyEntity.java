package com.xmsy.server.zxyy.manager.modules.manager.roomcardproxy.entity;

import com.baomidou.mybatisplus.annotations.TableName;
import com.xmsy.server.zxyy.manager.base.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;


/**
 * 房卡代理说明
 * 
 * @author axiong
 * @email xxxxx
 * @date 2019-07-02 10:58:28
 */
@Data
@EqualsAndHashCode(callSuper=false)
@Accessors(chain=true)
@TableName("room_card_proxy")
public class RoomCardProxyEntity extends BaseEntity<RoomCardProxyEntity> {
	private static final long serialVersionUID = 1L;
							/**
	 * 按钮名称
	 */
    private String name;
			/**
	 * 图片id
	 */
    private Long enclosureId;
			/**
	 * 文字
	 */
    private String content;
			/**
	 * 类型(0:图片 1:文字）
	 */
    private Integer type;
			/**
	 * 排序
	 */
    private Integer sort;
	}

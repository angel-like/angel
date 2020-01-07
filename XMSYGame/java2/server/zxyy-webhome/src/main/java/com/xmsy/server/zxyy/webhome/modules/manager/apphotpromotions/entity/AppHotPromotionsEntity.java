package com.xmsy.server.zxyy.webhome.modules.manager.apphotpromotions.entity;

import com.baomidou.mybatisplus.annotations.TableName;
import com.xmsy.server.zxyy.webhome.base.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;


/**
 * APP热门活动
 * 
 * @author aleng
 * @email xxxxx
 * @date 2019-05-04 16:01:21
 */
@Data
@EqualsAndHashCode(callSuper=false)
@Accessors(chain=true)
@TableName("app_hot_promotions")
public class AppHotPromotionsEntity extends BaseEntity<AppHotPromotionsEntity> {
	private static final long serialVersionUID = 1L;
							/**
	 * 属性
	 */
    private Long type;
			/**
	 * 名称
	 */
    private String name;
			/**
	 * 内容
	 */
    private String content;
    /**
	 * 排序号
	 */
	private Long orderNum;

	/**
	 *  分类
	 */
	private Long classify;
	}

package com.xmsy.server.zxyy.manager.modules.manager.immaterial.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.xmsy.server.zxyy.manager.base.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 33娱乐
 * 
 * @author aleng
 * @email xxxxx
 * @date 2019-06-17 15:27:58
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("im_material")
public class ImMaterialEntity extends BaseEntity<ImMaterialEntity> {
	private static final long serialVersionUID = 1L;

	/**
	 * 标题，
	 */
	private String title;
	/**
	 * 内容，
	 */
	private String content;
	/**
	 * 素材类型 0:富文本，1：图片
	 */
	private Integer type;
	/**
	 * 类别：1.新闻，2.特色，3.活动，4.维护，5.防盗,6.游戏截图
	 */
	private Integer category;
	/**
	 * 图片url，素材为图片非富文本的场景
	 */
	private String imageUrl;
	/**
	 * 跳转url，素材为图片的时候可选
	 */
	private String jumpUrl;
	/**
	 * 排序字段
	 */
	private Integer orderNum;
	/**
	 * 判断哪个页面发送过来的请求参数
	 */
	@TableField(exist=false)
	private String fourTypes;
}

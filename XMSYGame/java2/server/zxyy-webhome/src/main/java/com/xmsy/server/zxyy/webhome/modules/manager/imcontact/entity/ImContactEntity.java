package com.xmsy.server.zxyy.webhome.modules.manager.imcontact.entity;

import com.baomidou.mybatisplus.annotations.TableName;
import com.xmsy.server.zxyy.webhome.base.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;


/**
 * 33联系方式
 * 
 * @author axiong
 * @email xxxxx
 * @date 2019-07-01 14:20:14
 */
@Data
@EqualsAndHashCode(callSuper=false)
@Accessors(chain=true)
@TableName("im_contact")
public class ImContactEntity extends BaseEntity<ImContactEntity> {
	private static final long serialVersionUID = 1L;
							/**
	 * 附件ID
	 */
    private Long enclosureId;
			/**
	 * 名称
	 */
    private String name;
			/**
	 * 内容
	 */
    private String content;
			/**
	 * 类型(0:联系方式，1:网站信息）
	 */
    private Integer type;
			/**
	 * 是否展示
	 */
    private Boolean enable;
			/**
	 * 备注
	 */
    private String remake;
			/**
	 * 跳转路径
	 */
    private String url;
			/**
	 * 排序
	 */
    private Integer sort;
			/**
	 * 文本类型(0:文本，1:链接，2:图片）
	 */
    private Integer contentType;
	}

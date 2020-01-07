package com.xmsy.server.zxyy.manager.modules.manager.picturesize.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.xmsy.server.zxyy.manager.base.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;


/**
 * 图片尺寸配置
 * 
 * @author xiaoling
 * @email xxxxx
 * @date 2019-09-25 11:03:55
 */
@Data
@EqualsAndHashCode(callSuper=false)
@Accessors(chain=true)
@TableName("picture_size")
public class PictureSizeEntity extends BaseEntity<PictureSizeEntity> {
	private static final long serialVersionUID = 1L;
							/**
	 * 类型id（0：web，1：app）
	 */
    private Integer typeId;
			/**
	 * 提示语
	 */
    private String tips;
			/**
	 * 菜单id
	 */
    private Integer menuId;
    
		/**
	 * 菜单name
	 */
    @TableField(exist=false)
	private String menuName;
	}

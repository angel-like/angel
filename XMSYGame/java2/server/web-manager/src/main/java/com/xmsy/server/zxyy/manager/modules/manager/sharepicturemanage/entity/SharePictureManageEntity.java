package com.xmsy.server.zxyy.manager.modules.manager.sharepicturemanage.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.xmsy.server.zxyy.manager.base.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;


/**
 * 分享图片管理
 * 
 * @author axiong
 * @email xxxxx
 * @date 2019-07-08 15:13:55
 */
@Data
@EqualsAndHashCode(callSuper=false)
@Accessors(chain=true)
@TableName("share_picture_manage")
public class SharePictureManageEntity extends BaseEntity<SharePictureManageEntity> {
	private static final long serialVersionUID = 1L;
							/**
	 * 图片ID
	 */
    private Long enclosureId;
			/**
	 * 分享标题
	 */
    private String shareTitle;
			/**
	 * 分享url
	 */
    private String shareUrl;
			/**
	 * 分享内容
	 */
    private String shareContent;
			/**
	 * 是否启用
	 */
    private Boolean enable;
	/**
	 * 图片路径
	 */
	@TableField(exist = false)
	private String pictureUrl;
}

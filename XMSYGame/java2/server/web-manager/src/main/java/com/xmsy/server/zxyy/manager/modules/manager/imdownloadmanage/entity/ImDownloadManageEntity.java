package com.xmsy.server.zxyy.manager.modules.manager.imdownloadmanage.entity;

import com.baomidou.mybatisplus.annotations.TableName;
import com.xmsy.server.zxyy.manager.base.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 33推荐下载管理
 * 
 * @author xiaoliu
 * @email xxxxx
 * @date 2019-06-21 19:44:44
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("im_download_manage")
public class ImDownloadManageEntity extends BaseEntity<ImDownloadManageEntity> {
	private static final long serialVersionUID = 1L;
	/**
	 * 标题
	 */
	private String title;
	/**
	 * 内容
	 */
	private String content;
	/**
	 * 下载地址
	 */
	private String downloadUrl;
	/**
	 * 附件ID
	 */
	private Long enclosureId;
	/**
	 * 状态(启用，禁用)
	 */
	private Boolean enable;
	/**
	 * 排序号
	 */
	private Integer orderNo;
	/**
	 * 类型
	 */
	private String type;
	/**
	 * 平台
	 */
	private String platform;

}

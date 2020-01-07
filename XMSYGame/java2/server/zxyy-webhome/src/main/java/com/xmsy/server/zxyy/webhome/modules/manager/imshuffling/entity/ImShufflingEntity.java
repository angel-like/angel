package com.xmsy.server.zxyy.webhome.modules.manager.imshuffling.entity;

import com.baomidou.mybatisplus.annotations.TableName;
import com.xmsy.server.zxyy.webhome.base.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 33轮播图管理
 * 
 * @author xiaoliu
 * @email xxxxx
 * @date 2019-06-21 11:09:52
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("im_shuffling")
public class ImShufflingEntity extends BaseEntity<ImShufflingEntity> {
	private static final long serialVersionUID = 1L;
	/**
	 * 附件ID
	 */
	private Long enclosureId;
	/**
	 * 图片标题
	 */
	private String title;
	/**
	 * 状态(启用，禁用)
	 */
	private Boolean enable;
	/**
	 * 排序号
	 */
	private Integer orderNo;
	/**
	 * 游戏ID
	 */
	private Long gameId;
}

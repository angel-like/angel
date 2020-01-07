package com.xmsy.server.zxyy.webhome.modules.manager.updatelog.entity;

import com.baomidou.mybatisplus.annotations.TableName;
import com.xmsy.server.zxyy.webhome.base.BaseEntity;

import java.util.Date;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;


/**
 * 更新日志
 * 
 * @author axiong
 * @email xxxxx
 * @date 2019-07-25 16:47:37
 */
@Data
@EqualsAndHashCode(callSuper=false)
@Accessors(chain=true)
@TableName("update_log")
public class UpdateLogEntity extends BaseEntity<UpdateLogEntity> {
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
	 * 项目名
	 */
    private String projectName;
			/**
	 * 类型(0:后台 1:前台 2：游戏管理服）
	 */
    private Integer type;
			/**
	 * 预计更新时间
	 */
    private Date expectUpdateTime;
			/**
	 * 是否更新(0:否，1：是)
	 */
    private Boolean updateStatus;
			/**
	 * 完成后更新时间
	 */
    private Date finishTime;
	}

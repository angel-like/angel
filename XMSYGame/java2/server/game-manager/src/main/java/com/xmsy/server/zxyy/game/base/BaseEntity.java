package com.xmsy.server.zxyy.game.base;

import java.io.Serializable;
import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableLogic;
import com.baomidou.mybatisplus.annotations.Version;
import com.baomidou.mybatisplus.enums.FieldFill;
import com.baomidou.mybatisplus.enums.IdType;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 
 * @ClassName: BaseEntity
 * @Description: TODO
 * @author chenjisi
 * @date 2017年12月3日 下午9:07:16
 *
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public abstract class BaseEntity<T extends Model<T>> extends Model<T> {
	/**
	 * @Fields serialVersionUID : TODO
	 */
	private static final long serialVersionUID = 1L;

	@TableId(type = IdType.AUTO)
	@TableField("id")
	private Long id;

	// 数据版本（乐观锁实现）
	@Version
	@JSONField(serialize = false)
	@TableField(value = "version")
	private Integer version;

	// 删除状态
	@TableField(value = "delete_status", fill = FieldFill.INSERT)
	@TableLogic
	@JSONField(serialize = false)
	private Integer deleteStatus;
	// 添加时间
	@TableField(value = "create_time", fill = FieldFill.INSERT)
	private Date createTime;

	// 修改时间
	@TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
	@JSONField(serialize = false)
	private Date updateTime;

	@Override
	protected Serializable pkVal() {
		return this.id;
	}
}

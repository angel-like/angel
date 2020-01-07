package com.xmsy.server.zxyy.manager.modules.manager.usernamemodify.entity;

import com.baomidou.mybatisplus.annotations.TableName;
import com.xmsy.server.zxyy.manager.base.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;


/**
 * 真实姓名审核表
 * 
 * @author axiong
 * @email xxxxx
 * @date 2019-10-28 16:05:16
 */
@Data
@EqualsAndHashCode(callSuper=false)
@Accessors(chain=true)
@TableName("user_name_modify")
public class UserNameModifyEntity extends BaseEntity<UserNameModifyEntity> {
	private static final long serialVersionUID = 1L;
							/**
	 * 会员id
	 */
    private Long userId;
			/**
	 * 会员账号
	 */
    private String userAccount;
			/**
	 * 修改前姓名
	 */
    private String userOldName;
			/**
	 * 修改后姓名
	 */
    private String userNewName;
			/**
	 * 申请人操作账号
	 */
    private String applicantAccount;
			/**
	 * 状态0：未审核，1:驳回 2：成功
	 */
    private Integer status;
			/**
	 * 审核人账号
	 */
    private String checkerAccount;
			/**
	 * 备注
	 */
    private String remake;
	}

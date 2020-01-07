package com.xmsy.server.zxyy.webhome.modules.app.userquestion.entity;


import com.baomidou.mybatisplus.annotations.TableName;
import com.xmsy.server.zxyy.webhome.base.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 用户问题表
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-11-15 16:56:34
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("user_question")
public class UserQuestionEntity extends BaseEntity<UserQuestionEntity>{
	private static final long serialVersionUID = 1L;
	/**
	 * 问题
	 */
	private String question;
	/**
	 * 是否启用
	 */
	private boolean  enable;

}

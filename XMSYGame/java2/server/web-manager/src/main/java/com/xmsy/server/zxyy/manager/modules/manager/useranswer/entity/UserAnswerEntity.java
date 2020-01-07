package com.xmsy.server.zxyy.manager.modules.manager.useranswer.entity;


import com.baomidou.mybatisplus.annotations.TableName;
import com.xmsy.server.zxyy.manager.base.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 用户答案表
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-11-15 16:56:34
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("user_answer")
public class UserAnswerEntity extends BaseEntity<UserAnswerEntity>{
	private static final long serialVersionUID = 1L;

	/**
	 * 问题id
	 */
	private Long questionId;
	/**
	 * 用户id（唯一）
	 */
	private Long userId;
	/**
	 * 答案
	 */
	private String answer;

}

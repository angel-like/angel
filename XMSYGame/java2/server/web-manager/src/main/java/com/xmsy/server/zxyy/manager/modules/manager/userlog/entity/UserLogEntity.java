package com.xmsy.server.zxyy.manager.modules.manager.userlog.entity;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotations.TableName;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 操作会员日志
 * 
 * @author lpp
 * @email xxxxx
 * @date 2019-01-04 16:04:27
 */
@Data
@EqualsAndHashCode(callSuper=false)
@Accessors(chain=true)
@TableName("user_log")
public class UserLogEntity  implements Serializable{
	private static final long serialVersionUID = 1L;
	private Long id;
			/**
	 * 用户名
	 */
    private String username;
			/**
	 * 用户操作
	 */
    private String operation;
			/**
	 * 请求方法
	 */
    private String method;
			/**
	 * 请求参数
	 */
    private String params;
			/**
	 * 执行时长(毫秒)
	 */
    private Long time;
			/**
	 * IP地址
	 */
    private String ip;
			/**
	 * 创建时间
	 */
    private Date createDate;
			/**
	 * 备注
	 */
    private String remark;
			/**
	 * 
	 */
    private Long memberId;
			/**
	 * 用户账号
	 */
    private String memberAccount;
	}

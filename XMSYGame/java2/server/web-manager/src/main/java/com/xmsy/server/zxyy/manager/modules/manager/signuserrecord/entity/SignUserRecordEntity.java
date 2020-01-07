package com.xmsy.server.zxyy.manager.modules.manager.signuserrecord.entity;

import java.util.Date;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.xmsy.server.zxyy.manager.base.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;


/**
 * 用户签到记录表
 *
 * @author aleng
 * @email xxxxx
 * @date 2019-05-13 15:50:02
 */
@Data
@EqualsAndHashCode(callSuper=false)
@Accessors(chain=true)
@TableName("sign_user_record")
public class SignUserRecordEntity extends BaseEntity<SignUserRecordEntity> {
	private static final long serialVersionUID = 1L;
							/**
	 * 签到奖励ID
	 */
    private Long dayId;
			/**
	 * 用户ID
	 */
    private Long userId;
	/**
	 * 用户账号
	 */
	private String userAccount;
			/**
	 * 奖励
	 */
    private Long reward;
			/**
	 * 连续签到天数
	 */
    private Integer continuousNum;
    /**
	 * 连续签到天数
	 */
    private Date signDate;
    /**
     * vip等级名和第几天
     */
    @TableField(exist=false)
    private String vipNameAndDayName;

}

package com.xmsy.server.zxyy.webhome.modules.manager.userextendinfo.entity;

import com.baomidou.mybatisplus.annotations.TableName;
import com.xmsy.server.zxyy.webhome.base.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;


/**
 * 微信信息表
 * 
 * @author aleng
 * @email xxxxx
 * @date 2019-05-20 16:18:56
 */
@Data
@EqualsAndHashCode(callSuper=false)
@Accessors(chain=true)
@TableName("user_wechat_info")
public class UserExtendInfoEntity extends BaseEntity<UserExtendInfoEntity> {
	private static final long serialVersionUID = 1L;
							/**
	 * 昵称
	 */
    private String nickname;
			/**
	 * 省份
	 */
    private String province;
			/**
	 * 城市
	 */
    private String city;
			/**
	 * 乡村
	 */
    private String country;
			/**
	 * 头像
	 */
    private String headimgurl;
			/**
	 * 用户id（唯一）
	 */
    private Long userId;
	}

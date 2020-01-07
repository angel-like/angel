package com.xmsy.server.zxyy.webhome.modules.manager.kygameloginrecord.entity;

import com.baomidou.mybatisplus.annotations.TableName;
import com.xmsy.server.zxyy.webhome.base.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;


/**
 * 活动分类
 * 
 * @author sanqian
 * @email xxxxx
 * @date 2019-12-04 15:16:53
 */
@Data
@EqualsAndHashCode(callSuper=false)
@Accessors(chain=true)
@TableName("ky_gamelogin_record")
public class KyGameloginRecordEntity extends BaseEntity<KyGameloginRecordEntity> {
	private static final long serialVersionUID = 1L;
							/**
	 * 用户ID
	 */
    private Long userId;
			/**
	 * 用户账号
	 */
    private String account;
			/**
	 * 订单号
	 */
    private String orderId;
			/**
	 * 响应体
	 */
    private String gameUrl;
			/**
	 * 游戏id
	 */
    private Long gameId;

	private Long status;
	}

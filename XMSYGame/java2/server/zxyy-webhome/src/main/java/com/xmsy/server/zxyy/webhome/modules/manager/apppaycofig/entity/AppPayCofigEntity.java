package com.xmsy.server.zxyy.webhome.modules.manager.apppaycofig.entity;

import com.baomidou.mybatisplus.annotations.TableName;
import com.xmsy.server.zxyy.webhome.base.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;


/**
 * app支付配置
 * 
 * @author aleng
 * @email xxxxx
 * @date 2019-03-04 10:32:45
 */
@Data
@EqualsAndHashCode(callSuper=false)
@Accessors(chain=true)
@TableName("app_pay_cofig")
public class AppPayCofigEntity extends BaseEntity<AppPayCofigEntity> {
	private static final long serialVersionUID = 1L;
							/**
	 * 客服名称
	 */
    private String name;
		/**
	* 客服类型(1.QQ,2.微信,3.在线充值)
	*/
	private Integer type;
			/**
	 * 客服号
	 */
    private String typeNum;
    /**
	 * 状态（1.启用，0：禁用）
	 */
    private Boolean enable;
    /**
	 * 提示语
	 */
    private String tips;
    /**
   	 * 图标(附件ID)
   	 */
    private Long icon;
    /**
   	 * 图标md5值
   	 */
    private String iconMd5;
    
    
	}

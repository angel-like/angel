package com.xmsy.server.zxyy.webhome.modules.manager.webhomefilepackage.entity;

import com.baomidou.mybatisplus.annotations.TableName;
import com.xmsy.server.zxyy.webhome.base.BaseEntity;

import java.util.Date;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;


/**
 * 安装包
 * 
 * @author axiong
 * @email xxxxx
 * @date 2019-08-22 17:49:18
 */
@Data
@EqualsAndHashCode(callSuper=false)
@Accessors(chain=true)
@TableName("webhome_file_package")
public class WebhomeFilePackageEntity extends BaseEntity<WebhomeFilePackageEntity> {
	private static final long serialVersionUID = 1L;
							/**
	 * 代理编号
	 */
    private String proxyNo;
			/**
	 * 代理别名
	 */
    private String proxyAlias;
    /**
	 * 渠道名称
	 */
	private String channelName;
	/**
	 * 渠道码
	 */
	private String channelCode;
			/**
	 * 苹果签名
	 */
    private String iosName;
			/**
	 * 过期时间
	 */
    private Date expireTime;
			/**
	 * 更新地址
	 */
    private String updateUrl;
			/**
	 * 备注
	 */
    private String remark;
    /**
	 * 苹果文件打包地址
	 */
	private String iosFileUrl;
	/**
	 * 安卓文件打包地址
	 */
	private String androidFileUrl;
	}

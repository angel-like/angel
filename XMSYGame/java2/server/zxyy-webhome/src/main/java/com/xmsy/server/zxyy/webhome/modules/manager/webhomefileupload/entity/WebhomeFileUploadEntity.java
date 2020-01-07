package com.xmsy.server.zxyy.webhome.modules.manager.webhomefileupload.entity;

import com.baomidou.mybatisplus.annotations.TableName;
import com.xmsy.server.zxyy.webhome.base.BaseEntity;

import java.util.Date;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;


/**
 * 苹果企业证书
 * 
 * @author axiong
 * @email xxxxx
 * @date 2019-08-16 11:52:13
 */
@Data
@EqualsAndHashCode(callSuper=false)
@Accessors(chain=true)
@TableName("webhome_file_upload")
public class WebhomeFileUploadEntity extends BaseEntity<WebhomeFileUploadEntity> {
	private static final long serialVersionUID = 1L;
							/**
	 * 平台（ios,android）
	 */
    private String platform;
			/**
	 * 文件名称
	 */
    private String fileName;
			/**
	 * 打包时间
	 */
    private Date filePackageTime;
			/**
	 * p12文件内容
	 */
    private String p12Content;
			/**
	 * profile文件内容
	 */
    private String profileContent;
			/**
	 * 密码
	 */
    private String pwd;
			/**
	 * 打包的文件地址
	 */
    private String fileUrl;
    
    // 新增3个字段 表名改为苹果企业证书
 	/**
 	 * 证书别名
 	 */
 	private String certificateName;
 	/**
 	 * 到期时间
 	 */
 	private Date expireTime;
 	/**
 	 * 备注
 	 */
 	private String remark;
	}

package com.xmsy.server.zxyy.manager.modules.manager.webhomeenclosure.entity;

import com.xmsy.server.zxyy.manager.base.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;


/**
 * 文件附件表
 * 
 * @author aleng
 * @email xxxxx
 * @date 2018-12-31 15:05:47
 */
@Data
@EqualsAndHashCode(callSuper=false)
@Accessors(chain=true)
//@EqualsAndHashCode(callSuper=false)
//@Accessors(chain=true)
//@TableName("webhome_enclosure")
public class EnclosureResult extends BaseEntity<EnclosureResult> {
/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
//	private static final long serialVersionUID = 1L;
    private  Long groupId;
	private  String name;
	private  Integer num;



	}

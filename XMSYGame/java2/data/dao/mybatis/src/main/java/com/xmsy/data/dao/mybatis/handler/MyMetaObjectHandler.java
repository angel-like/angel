package com.xmsy.data.dao.mybatis.handler;

import java.util.Date;

import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;


/** mybatisplus自定义填充公共字段 ,即没有传的字段自动填充 */
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {
	// 新增填充
	@Override
	public void insertFill(MetaObject metaObject) {
		Object createTime = getFieldValByName("createTime", metaObject);
		Object deleteStatus = getFieldValByName("deleteStatus", metaObject);
		Object updateTime = getFieldValByName("updateTime", metaObject);
		if (createTime == null) {
			setFieldValByName("createTime", new Date(), metaObject);
		}
		if (deleteStatus == null) {
			setFieldValByName("deleteStatus", 0, metaObject);
		}
		if (updateTime == null) {
			setFieldValByName("updateTime", new Date(), metaObject);
		}
	}

	// 更新填充
	@Override
	public void updateFill(MetaObject metaObject) {
		Object updateTime = getFieldValByName("updateTime", metaObject);
		if (updateTime == null) {
			setFieldValByName("updateTime", new Date(), metaObject);
		}
	}
}

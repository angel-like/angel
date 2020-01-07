package com.xmsy.server.zxyy.webhome.utils;

import java.io.IOException;
import java.lang.reflect.Type;

import com.alibaba.fastjson.serializer.IntegerCodec;
import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SerializeWriter;
import com.alibaba.fastjson.serializer.SerializerFeature;

/**
 * JSON序列
 * 
 * @ClassName: SerializeUtil
 * @author liwenjian
 * @date 2016年12月15日下午6:15:14
 * @version 1.0.0
 */
public class SerializeUtil {
	private static SerializeConfig globalInstance = SerializeConfig.getGlobalInstance();
	static {
		// 配置需要序列的指定类
		globalInstance.put(Long.class, new NumberSerializerConfig());
		globalInstance.put(Integer.class, new NumberSerializerConfig());
	}

	public static SerializeConfig instance() {
		return globalInstance;
	}

	public static class NumberSerializerConfig extends IntegerCodec {

		@Override
		public void write(JSONSerializer serializer, Object object, Object fieldName, Type fieldType, int features)
				throws IOException {
			SerializeWriter out = serializer.out;
			Number value = (Number) object;
			if (value == null) {
				out.writeNull(SerializerFeature.WriteNullNumberAsZero);
				return;
			}
			if (object instanceof Long) {
				out.writeString(String.valueOf(value));
			} else {
				out.writeString(String.valueOf(value));
			}
			if (out.isEnabled(SerializerFeature.WriteClassName)) {
				Class<?> clazz = value.getClass();
				if (clazz == Byte.class) {
					out.write('B');
				} else if (clazz == Short.class) {
					out.write('S');
				}
			}
			super.write(serializer, object, fieldName, fieldType, features);
		}
	}

}

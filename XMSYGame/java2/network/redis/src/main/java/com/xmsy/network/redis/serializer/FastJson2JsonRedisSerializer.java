package com.xmsy.network.redis.serializer;

import java.nio.charset.Charset;

import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.ParserConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;

/**
 * Redis序列化和反序列化，以对象形式存储
 * 
 * @author aleng
 * @date 2018年10月9日
 */
public class FastJson2JsonRedisSerializer<T> implements RedisSerializer<T> {

	public static final Charset DEFAULT_CHARSET = Charset.forName("UTF-8");

	private Class<T> clazz;

	public FastJson2JsonRedisSerializer(Class<T> clazz) {
		super();
		this.clazz = clazz;
	}

	public byte[] serialize(T t) throws SerializationException {
		if (t == null) {
			return new byte[0];
		}
		return JSON.toJSONString(t, SerializerFeature.WriteClassName).getBytes(DEFAULT_CHARSET);
	}

	@SuppressWarnings("unchecked")
	public T deserialize(byte[] bytes) throws SerializationException {
		if (bytes == null || bytes.length <= 0) {
			return null;
		}
		String str = new String(bytes, DEFAULT_CHARSET);
		ParserConfig jcParserConfig = new ParserConfig();
		jcParserConfig.setAutoTypeSupport(true);
		return (T) JSON.parseObject(str, clazz, jcParserConfig);
	}

}

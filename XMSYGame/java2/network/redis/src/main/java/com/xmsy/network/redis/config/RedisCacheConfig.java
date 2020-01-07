package com.xmsy.network.redis.config;

import java.time.Duration;

import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import com.xmsy.network.redis.serializer.FastJson2JsonRedisSerializer;

/**
 * Redis工具类
 *
 * @author aleng
 * @email xxxxxx
 * @date 2017-07-17 21:12
 */
@Configuration
public class RedisCacheConfig {
	// @Autowired
	// private RedisTemplate<String, Object> redisTemplate;

	@Bean
	public RedisSerializer<Object> fastJson2JsonRedisSerializer() {
		return new FastJson2JsonRedisSerializer<Object>(Object.class);
	}

	@Bean
	public RedisSerializer<String> StringRedisSerializer() {
		return new StringRedisSerializer();
	}

	/**
	 * 解决redis插入中文乱码
	 * 
	 * @return
	 */
	@Bean
	public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory factory) {
		RedisTemplate<String, Object> template = new RedisTemplate<>();
		// 配置连接工厂
		template.setConnectionFactory(factory);
		// 使用StringRedisSerializer来序列化和反序列化redis的key值
		template.setKeySerializer(StringRedisSerializer());
		// 值采用json序列化
		template.setValueSerializer(fastJson2JsonRedisSerializer());
		// 设置hash key 和value序列化模式
		template.setHashKeySerializer(StringRedisSerializer());
		template.setHashValueSerializer(fastJson2JsonRedisSerializer());
		template.afterPropertiesSet();
		return template;
	}

	@Primary
	@Bean
	public CacheManager cacheManager(RedisConnectionFactory redisConnectionFactory) {
		// 缓存配置对象
		RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig();
		redisCacheConfiguration = redisCacheConfiguration.entryTtl(Duration.ofHours(2)) // 设置缓存的默认超时时间：2个小时
				.disableCachingNullValues() // 如果是空值，不缓存
				.serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(StringRedisSerializer())) // 设置key序列化器
				.serializeValuesWith(
						RedisSerializationContext.SerializationPair.fromSerializer((fastJson2JsonRedisSerializer()))); // 设置value序列化器

		return RedisCacheManager.builder(RedisCacheWriter.nonLockingRedisCacheWriter(redisConnectionFactory))
				.cacheDefaults(redisCacheConfiguration).build();
	}

	/**
	 * 对hash类型的数据操作
	 *
	 * @param redisTemplate
	 * @return
	 */
	@Bean
	public HashOperations<String, String, Object> hashOperations(RedisTemplate<String, Object> redisTemplate) {
		return redisTemplate.opsForHash();
	}

	/**
	 * 对redis字符串类型数据操作
	 *
	 * @param redisTemplate
	 * @return
	 */
	@Bean
	public ValueOperations<String, Object> valueOperations(RedisTemplate<String, Object> redisTemplate) {
		return redisTemplate.opsForValue();
	}

	/**
	 * 对链表类型的数据操作
	 *
	 * @param redisTemplate
	 * @return
	 */
	@Bean
	public ListOperations<String, Object> listOperations(RedisTemplate<String, Object> redisTemplate) {
		return redisTemplate.opsForList();
	}

	/**
	 * 对无序集合类型的数据操作
	 *
	 * @param redisTemplate
	 * @return
	 */
	@Bean
	public SetOperations<String, Object> setOperations(RedisTemplate<String, Object> redisTemplate) {
		return redisTemplate.opsForSet();
	}

	/**
	 * 对有序集合类型的数据操作
	 *
	 * @param redisTemplate
	 * @return
	 */
	@Bean
	public ZSetOperations<String, Object> zSetOperations(RedisTemplate<String, Object> redisTemplate) {
		return redisTemplate.opsForZSet();
	}
}

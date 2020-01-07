package com.xmsy.network.redis.utils;

import java.util.Collections;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import lombok.extern.slf4j.Slf4j;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisCommands;

@Component
@Slf4j
public class RedisLockUtil {

	@Resource
	private RedisTemplate<String, Object> redisTemplate;

	// 默认失效时间20秒
	private static final int TIME_OUT = 20;

	public static final String UNLOCK_LUA;

	static {
		StringBuilder sb = new StringBuilder();
		sb.append("if redis.call(\"get\",KEYS[1]) == ARGV[1] ");
		sb.append("then ");
		sb.append("    return redis.call(\"del\",KEYS[1]) ");
		sb.append("else ");
		sb.append("    return 0 ");
		sb.append("end ");
		UNLOCK_LUA = sb.toString();
	}

	/**
	 * 限流判断
	 * 
	 * @param scripts
	 *            锁
	 * @param keys
	 *            参数
	 * 
	 * @param limitCount
	 *            限流数量
	 * 
	 * @param limitPeriod
	 *            限流时长
	 * @return 数量
	 */
	public Integer execute(RedisScript<Number> scripts, List<String> keys, int limitCount, int limitPeriod) {
		try {
			Number result = redisTemplate.execute(scripts, keys, limitCount, limitPeriod);
			return result == null ? null : result.intValue();
		} catch (Exception e) {
			log.error("set redis occured an exception", e);
		}
		return null;
	}

	/**
	 * 获取分布式锁
	 * 
	 * @param key
	 *            锁
	 * @param requestId
	 *            请求标识
	 * @return 是否释放成功
	 */
	public boolean setLock(String key, String requsetId) {
		try {
			RedisCallback<String> callback = (connection) -> {
				JedisCommands commands = (JedisCommands) connection.getNativeConnection();
				return commands.set(key, requsetId, "NX", "EX", TIME_OUT);
			};
			String result = redisTemplate.execute(callback);
			return !StringUtils.isEmpty(result);
		} catch (Exception e) {
			log.error("set redis occured an exception", e);
		}
		return false;
	}

	public String get(String key) {
		try {
			RedisCallback<String> callback = (connection) -> {
				JedisCommands commands = (JedisCommands) connection.getNativeConnection();
				return commands.get(key);
			};
			String result = redisTemplate.execute(callback);
			return result;
		} catch (Exception e) {
			log.error("get redis occured an exception", e);
		}
		return "";
	}

	/**
	 * 释放分布式锁
	 * 
	 * @param key
	 *            锁
	 * @param requestId
	 *            请求标识
	 * @return 是否释放成功
	 */
	public boolean releaseLock(String key, String requestId) {
		// 释放锁的时候，有可能因为持锁之后方法执行时间大于锁的有效期，此时有可能已经被另外一个线程持有锁，所以不能直接删除
		try {
			List<String> keys = Collections.singletonList(key);
			List<String> args = Collections.singletonList(requestId);

			// 使用lua脚本删除redis中匹配value的key，可以避免由于方法执行时间过长而redis锁自动过期失效的时候误删其他线程的锁
			// spring自带的执行脚本方法中，集群模式直接抛出不支持执行脚本的异常，所以只能拿到原redis的connection来执行脚本
			RedisCallback<Long> callback = (connection) -> {
				Object nativeConnection = connection.getNativeConnection();
				// 集群模式和单机模式虽然执行脚本的方法一样，但是没有共同的接口，所以只能分开执行
				// 集群模式
				if (nativeConnection instanceof JedisCluster) {
					return (Long) ((JedisCluster) nativeConnection).eval(UNLOCK_LUA, keys, args);
				}
				// 单机模式
				else if (nativeConnection instanceof Jedis) {
					return (Long) ((Jedis) nativeConnection).eval(UNLOCK_LUA, keys, args);
				}
				return 0L;
			};
			Long result = redisTemplate.execute(callback);
			return result != null && result > 0;
		} catch (Exception e) {
			log.error("release lock occured an exception", e);
		} finally {
			// 清除掉ThreadLocal中的数据，避免内存溢出
			// lockFlag.remove();
		}
		return false;
	}

	// public static void main(String[] args) {
	// // 创建jedis对象
	// Jedis jedis = new Jedis("85.208.56.202", 6379);
	// // 操作string数据类型
	// jedis.set("username", "helloworld");
	// // 根据key取出对应的value值
	// String value = jedis.get("username");
	// // 值输出
	// System.out.println(value);
	// // 关闭连接
	// jedis.close();
	// }
}

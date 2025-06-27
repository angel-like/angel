package com.ljx.redis.demo;

import lombok.extern.slf4j.Slf4j;
import redis.clients.jedis.Jedis;

@Slf4j
public class JedisDemo {
    public static void main(String[] args) {
        Jedis jedis = new Jedis("localhost", 6379);
        //jedis.auth("111111");
        log.info("redis conn status:{}","连接成功");
        log.info("redis ping retvalue:{}",jedis.ping());
        // string
        jedis.set("k1","jedis");
        log.info("string value, k1 :{}",jedis.get("k1"));

        //key
        log.info("keys:{}",jedis.keys("*"));
        log.info("key exists:{}",jedis.exists("k1"));

        // list
        jedis.lpush("list1","a","b","c");
        log.info("list1:{}",jedis.lrange("list1",0,-1));
        // set
        jedis.sadd("set1","a","b","c","a");
        log.info("set1:{}",jedis.smembers("set1"));
        // hash
        jedis.hset("hash1","name","jedis");
        jedis.hset("hash1","age","18");
        log.info("hash1:{}",jedis.hgetAll("hash1"));
        // zset
        jedis.zadd("zset1",70,"a");
        jedis.zadd("zset1",80,"b");
        jedis.zadd("zset1",90,"c");
        log.info("zset1:{}",jedis.zrangeWithScores("zset1",0,-1));
    }
}

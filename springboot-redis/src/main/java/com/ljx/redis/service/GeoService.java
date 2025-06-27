package com.ljx.redis.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.*;
import org.springframework.data.redis.connection.ReactiveGeoCommands;
import org.springframework.data.redis.connection.RedisGeoCommands;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import redis.clients.jedis.commands.GeoCommands;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ClassName: GeoService
 * Package: com.ljx.redis.service
 * Description:
 *
 * @Author Xu, Luqin
 * @Create 2024/11/2 12:39
 * @Version 1.0
 */
@Service
public class GeoService {
    @Autowired
    private RedisTemplate redisTemplate;

    private final static String CITY = "city";

    public String geoAdd() {
        Map<String, Point> map = new HashMap<>();
        map.put("天安门", new Point(116.403963, 39.915119));
        map.put("故宫", new Point(116.403414, 39.924091));
        map.put("长城", new Point(116.024067, 40.362639));
        redisTemplate.opsForGeo().add(CITY, map);
        return map.toString();
    }


    public Point position(String member) {
        List<Point> list = redisTemplate.opsForGeo().position(CITY, member);
        return list.get(0);
    }


    public String hash(String member) {
        List<String> list = redisTemplate.opsForGeo().hash(CITY, member);
        return list.get(0);
    }


    public Distance distance(String member1, String member2) {

        return redisTemplate.opsForGeo()
                .distance(CITY, member1, member2, RedisGeoCommands.DistanceUnit.KILOMETERS);
    }


    public GeoResults radiusByxy() {
        // 王府井地址
        Circle circle = new Circle(116.418017, 39.914402, Metrics.KILOMETERS.getMultiplier());
        GeoResults<RedisGeoCommands.GeoLocation<String>> radius = redisTemplate.opsForGeo()
                .radius(CITY,
                        circle,
                        RedisGeoCommands.GeoRadiusCommandArgs.newGeoRadiusArgs()
                                .includeDistance()
                                .includeCoordinates()
                                .limit(10).sortDescending());
        return radius;
    }

    /**
     * 通过地方查找附近,本例写死天安门作为地址
     * @return
     */
    public GeoResults radiusByMember() {
        GeoResults<RedisGeoCommands.GeoLocation<String>> radius = redisTemplate.opsForGeo()
                .radius(CITY, "天安门",
                        new Distance(10, RedisGeoCommands.DistanceUnit.KILOMETERS),
                        RedisGeoCommands.GeoRadiusCommandArgs.newGeoRadiusArgs()
                                .includeDistance()
                                .includeCoordinates()
                                .limit(10));
        return radius;
    }
}

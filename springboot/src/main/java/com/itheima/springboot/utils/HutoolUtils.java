package com.itheima.springboot.utils;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;

public class HutoolUtils {

    //雪花算法主要就是  workerId(5bit) +DatacenterId(5bit) 这两个属于机器集群标识的id，最大10bit即1024个
    private Snowflake snowflake = IdUtil.getSnowflake(0, 1);

    /**
     * 获取雪花算法id
     */
    public synchronized  Long fetchSnowFlakeId(){

        long autoId = snowflake.nextId();
        return autoId;
    }

    /**
     * 获取雪花算法id,指定其他标识
     */
    public synchronized  Long fetchSnowFlakeId(long workerId, long datacenterId){
        //雪花算法主要就是  workerId(5bit) +DatacenterId(5bit) 这两个属于机器集群标识的id，最大10bit即1024个
        Snowflake snowflake2 = IdUtil.getSnowflake(0, 1);
        long autoId = snowflake2.nextId();
        return autoId;
    }
}

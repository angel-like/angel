package com.itheima.springboot.demo;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * LRU算法 ，坑位算法 哈希+链表 == HashMap +LinkList   -》 LinkedHashMap
 * 自己手写；
 *      更新时：先移除，然后再添加，就在第一个了
 *      插入时：满了先移除最后一个，插入在第一个
 */
public class LRUDemo<K,V> extends LinkedHashMap<K,V>{
    private int capacity;//缓存坑位

    public LRUDemo(int capacity) {
        //插入权重高就用true，查询权重高就用false(有点问题)
        super(capacity,0.75F,false);
        this.capacity = capacity;
    }
    //读写时，要把排序排第一个
    @Override
    protected boolean removeEldestEntry(Map.Entry<K,V> eldest) {
        //坑位大于capacity个时，把最近最少使用的 移除
        return super.size()>capacity;
    }

    public static void main(String[] args) {
        //
        LRUDemo lruMap = new LRUDemo(3);
        lruMap.put("1",1);
        lruMap.put("2",2);
        lruMap.put("3",3);
        System.out.println(lruMap.keySet());//[1, 2, 3]
        lruMap.put("4",4);
        System.out.println(lruMap.keySet());//[2, 3, 4]
        lruMap.put("3",5);
        System.out.println(lruMap.keySet());//fasle[2, 3, 4]   true:[2, 4, 3]
        lruMap.get("3");
        System.out.println(lruMap.keySet());//fasle[2, 3, 4]   true:[2, 4, 3]
        lruMap.put("5",5);
        lruMap.put("6",6);
        System.out.println(lruMap.keySet());//fasle[4, 5, 6]   true: [3, 5, 6]
    }
}

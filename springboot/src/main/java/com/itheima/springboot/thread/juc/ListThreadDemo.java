package com.itheima.springboot.thread.juc;

import org.junit.Test;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.*;


public class ListThreadDemo {
    public static void main(String[] args) throws InterruptedException {
        //1. 集合的锁(ArrayList,Set,Map)
        //a.Vector（ArrayList）
        Vector<Integer> list = new Vector<>();
        list.add(1);
        //b.Collections
        Collections.synchronizedList(new ArrayList<>());
        Collections.synchronizedSet(new HashSet<>());
        Collections.synchronizedMap(new HashMap<>());
        //c. juc里的
        CopyOnWriteArrayList<Object> objectList =  new CopyOnWriteArrayList<>();
        objectList.add(1); //这里加锁了
        CopyOnWriteArraySet<Object> objectSet =  new CopyOnWriteArraySet<>();
        ConcurrentHashMap<Object, Object> hashMap = new ConcurrentHashMap<>();

        //2.公平锁
        ReentrantLock reentrantLock = new ReentrantLock(true);
        Condition condition = reentrantLock.newCondition();
        condition.await();//等待
        condition.signalAll();//唤醒
        //非公平
        ReentrantLock lock = new ReentrantLock();
        lock.lock();
        lock.unlock();
    }



    //读写分离 （该手写案例失败）
    @Test
    public void readWriteSync(){
        MyCache demo=new MyCache();
        //并发写
        for (int i=0;i<5;i++){
            final Integer tem = i;
            new Thread(()->{
                demo.writeSync(String.valueOf(tem),String.valueOf(tem));
            },"线程:"+i).start();
        }
        //并发读
        for (int i=0;i<5;i++){
            final Integer tem = i;
            new Thread(()->{
                demo.readSync(String.valueOf(tem));
            },"线程:"+i).start();
        }
        try {
            TimeUnit.MILLISECONDS.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    class MyCache{
        //读写锁
        private ReadWriteLock lock =new ReentrantReadWriteLock();
        private volatile Map map =new HashMap();
        //写锁
        public void writeSync(String key,Object value){
            //写锁
            lock.writeLock().lock();
            try {
                System.out.println(Thread.currentThread().getName()+"\t正在写入:"+key);
                try {TimeUnit.MILLISECONDS.sleep(300);}catch (Exception e){}
                map.put(key,value);
                System.out.println(Thread.currentThread().getName()+"\t写入完成:");
            }catch (Exception e){
                e.printStackTrace();
            }finally {
                lock.writeLock().unlock();
            }
        }
        //读锁
        public void readSync(String key){
            //读锁
            lock.readLock().lock();
            try {
                System.out.println(Thread.currentThread().getName()+"\t正在读取:");
                try {TimeUnit.MILLISECONDS.sleep(300);}catch (Exception e){}
                Object value = map.get(key);
                System.out.println(Thread.currentThread().getName()+"\t读取完成:"+value);
            }catch (Exception e){
                e.printStackTrace();
            }finally {
                lock.readLock().unlock();
            }
        }
    }


}

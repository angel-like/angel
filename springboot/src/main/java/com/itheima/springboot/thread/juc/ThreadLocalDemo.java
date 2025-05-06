package com.itheima.springboot.thread.juc;

import lombok.Data;
import org.junit.Test;

import java.util.Random;
import java.util.concurrent.CountDownLatch;

public class ThreadLocalDemo {
    @Data
    static class Su7{
        private int saleTotal;
        //调用一次加一一辆 。多线程调用  ，要加锁
        public synchronized void saleTotal() {
            saleTotal++;
        }

        //成员变量 初始化参数为0
        ThreadLocal<Integer> salePersonal=ThreadLocal.withInitial(()->0);

        //每个线程都会初始化一个salePersonal参数=0的，代表自己卖出的车辆信息
        public void salePersonal(){
            salePersonal.set(1+salePersonal.get());
        }

    }

    public static void main(String[] args) throws InterruptedException {
        Su7 su7=new Su7();
        //这个多线程代表其他线程都关闭了，才能关闭
        CountDownLatch countDownLatch=new CountDownLatch(3);

        //3个销售代表3个线程
        for(int i=1;i<=3;i++){
            new Thread(()->{
                try {
                    Integer max=new Random().nextInt(3)+1;
                    for(int j=1;j<=max;j++){
                        su7.saleTotal(); //卖出总辆
                        su7.salePersonal();//每个人(每个线程)自己卖的
                    }
                    System.out.println(Thread.currentThread().getName()+"每个人(每个线程)自己卖的："+su7.salePersonal.get());
                }catch (Exception e){
                    e.printStackTrace();
                }finally {
                    //countDownLatch当前线程关闭
                    countDownLatch.countDown();
                    //用完要关闭，防止内存泄漏
                    su7.salePersonal.remove();
                }
            },"线程名"+i ).start();

        }
        countDownLatch.await();
        System.out.println(Thread.currentThread().getName()+"卖出总辆："+su7.saleTotal);
    }



}

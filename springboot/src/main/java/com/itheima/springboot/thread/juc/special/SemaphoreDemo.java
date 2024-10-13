package com.itheima.springboot.thread.juc.special;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.Semaphore;

public class SemaphoreDemo {
    public static void main(String[] args) {
        //模拟3个车位
        Semaphore semaphore=new Semaphore(3);
        //10个线程区抢3个车位
        for (int i = 1; i <= 10; i++) {
            new Thread(() -> {
                //--开始--
                try {
                    semaphore.acquire();
                    System.out.println(Thread.currentThread().getName() + "  抢到车位");
                    Thread.sleep(3000);
                    System.out.println(Thread.currentThread().getName() + "  停车3s后离开车位");
                } catch (Exception e) {
                    e.printStackTrace();
                }finally {
                    semaphore.release();
                }
                //--结束--
            }, "线程名" + i).start();

        }
    }
}

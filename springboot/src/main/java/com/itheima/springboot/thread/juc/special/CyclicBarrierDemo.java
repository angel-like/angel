package com.itheima.springboot.thread.juc.special;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class CyclicBarrierDemo {
    public static void main(String[] args) {
        //所有线程执行完等待，直到全部完成，才关闭   定义七个线程，全部执行完才会执行 这里定义的Runnable线程里的代码
        CyclicBarrier cyclicBarrier = new CyclicBarrier(7, () -> {
            System.out.println("******召唤神龙");
        });
        //7个线程手机龙珠
        for (int i = 1; i <= 7; i++) {
            new Thread(() -> {
                System.out.println(Thread.currentThread().getName() + "******每个线程收集龙珠");
                //--开始--
                try {
                    cyclicBarrier.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                } finally {
                }
                //--结束--
            }, "线程名" + i).start();

        }
    }
}

package com.itheima.springboot.thread;

public class WaitNotifyExample {
    static Object lock = new Object();

    public static void main(String[] args) {
        Thread producer = new Thread(new Producer());
        Thread consumer = new Thread(new Consumer());

        producer.start();
        try {
            // 确保Producer先运行
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        consumer.start();
    }

    static class Producer implements Runnable {
        @Override
        public void run() {
            // 获取锁
            synchronized (lock) {
                System.out.println("生产者：生产包子");
                try {
                    // 等待唤醒
                    lock.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                // 唤醒后执行的操作
                System.out.println("唤醒后，生产者：不继续生产");
            }
        }
    }

    static class Consumer implements Runnable {
        @Override
        public void run() {
            // 获取锁
            synchronized (lock) {
                // 消费者线程等待1秒后唤醒
                try {
                    System.out.println("消费者: 吃包子");
                    Thread.sleep(1000);
                    System.out.println("消费者: 吃完了");
                    // 通知等待的线程
                    lock.notify();
                    System.out.println("唤醒了等待的线程");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

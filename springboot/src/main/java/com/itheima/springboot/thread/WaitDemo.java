package com.itheima.springboot.thread;

/**
 * wait()在 synchronized里面使用，使线程暂时挂起
 */
public class WaitDemo {
    public static void main(String[] args) throws InterruptedException {

        // 新建两个线程 测试wait方法。
        new Thread(new Runnable() {
            public void run() {
                // 4.synchronized后面括号里是类,此时,线程获得的是对象锁.
                synchronized (WaitDemo.class) {
                    try {
                        System.out.println("线程一开始");

                        //wait 方法 线程会放弃对象锁，进入等待此对象的等待锁定池，此时线程挂起。
                        WaitDemo.class.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("线程一结束");
                }

            }
        }).start();

        new Thread(new Runnable() {
            public void run() {
                synchronized (WaitDemo.class) {
                    System.out.println("线程二开始");

                    //只有针对此对象调用notify方法后，本线程才进入对象锁定池，准备获取对象锁，进入运行状态。
                    //如果不调用这个方法，线程永远都是刮起的状态。
                    //WaitDemo.class.notify();
                    System.out.println("线程二结束了");
                }

            }
        }).start();
    }
}

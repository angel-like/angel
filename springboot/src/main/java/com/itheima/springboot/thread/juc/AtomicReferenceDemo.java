package com.itheima.springboot.thread.juc;

import com.itheima.springboot.test.Student;
import org.junit.Test;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicReference;

public class AtomicReferenceDemo {
    public static void main(String[] args) throws InterruptedException {
        Student stu=new Student("123",10);
        AtomicReference<Student> atomic=new AtomicReference(stu);
        Student stuAtomic = atomic.get();
        ExecutorService threadPool = Executors.newFixedThreadPool(10);
        CountDownLatch countDownLatch=new CountDownLatch(50);
        for(int i=0;i<50;i++){
            CompletableFuture.runAsync(()->{
                //synchronized (AtomicReferenceDemo.class){
                    Integer age = stu.getAge();
                    stu.setAge(age+1);
                //}
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName()+"===="+stu.getAge());
                countDownLatch.countDown();
            },threadPool);
        }
        countDownLatch.await();
        //用stu非线程安全去操作，会导致 main====最终结果：59    ，
        // 用 stuAtomic 也不能能保证最终结果 60,因为 stuAtomic.get()的数据可能不是最新的
        //只能通过 synchronized，对获取数据与set数据两部进行同一加锁才行
        System.out.println(Thread.currentThread().getName()+"====最终结果："+stu.getAge());
        threadPool.shutdown();
    }

    @Test
    public  void threadPoolTest() throws InterruptedException, ExecutionException {
        ExecutorService threadPool = Executors.newFixedThreadPool(5);
        //pool.submit(()->{}); 该方法是只执行一个线程，可以有返回值，用的Future<T> ，有可能被吞异常
        //submitResult.get()可以在主线程里获取异常信息，出现异常就会抛出
        Future<Student> submitResult = threadPool.submit(() -> {
            int a = 10;
            System.out.println("submit测试是否会吞异常");
            int b = a / 0;
            Thread.sleep(5000);
            return new Student("学生submit",9);
        });
        //该方法是只执行一个线程，并不返回 。 是会抛异常的 ，不会被吞
        threadPool.execute(()->{
            int a=10;
            System.out.println("execute测试是否会吞异常");
            String ss=null;
            ss.length();
        });
        Thread.sleep(500);
        threadPool.shutdown();
        Student student = submitResult.get();//用了这个出异常就会影响到其他main方法执行
        System.out.println("继续执行其他代码"+student.getName());
        Thread.sleep(500);
    }
}

package com.itheima.springboot.thread.juc;

import org.junit.Test;

import java.util.Random;
import java.util.concurrent.*;

/**
 * junit不支持多线程，主线程结束自定义的线程池就关闭了，所以不要在单元测试中测试上述代码
 */
public class CompletableFutureDemo{
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        //用自定义线程池 ,记住抛异常要用finally才能关闭
        ExecutorService threadPool = Executors.newFixedThreadPool(5);
        CompletableFuture<Integer> intResult = CompletableFuture.supplyAsync(() -> {
            System.out.println(Thread.currentThread().getName() + "进来了");
            Integer res = new Random().nextInt(5);

            //睡2秒
            try {TimeUnit.SECONDS.sleep(2);} catch (InterruptedException e) { e.printStackTrace();}
            System.out.println("睡两秒出结果："+res);
            //int a=5/0;
            return res;
        },threadPool);

        //whenComplete表示第一阶段完成了，result表示运行结果，exception表示可能发生异常
        CompletableFuture<Integer> intResult2 = intResult.whenComplete((result, exception) -> {
            if (result != null) {
                System.out.println("第一阶段结果是： " + result + "第二阶段完成");

            }
        });
        //抛异常情况
        intResult2.exceptionally(e->{
            e.printStackTrace();
            System.out.println("这里抛异常： +++++++++++++++++++++++++++");
            return null;
        });
        //这里不执行get不会打印 "睡两秒出结果" + res 语句，因为main线程关闭，所以导致后面的不执行,用线程池解决了该问题
        //intResult.get();
        //不需要get()来阻塞了，shutdown要等里面的执行完才关闭
        threadPool.shutdown();
    }

    /**
     * 连着写
     * @throws ExecutionException
     * @throws InterruptedException
     */
    @Test
    public void supplyAsyncExceptionTest() throws ExecutionException, InterruptedException {
        CompletableFuture<Integer> intResult = CompletableFuture.supplyAsync(() -> {
            System.out.println(Thread.currentThread().getName() + "进来了");
            Integer res = new Random().nextInt(5);

            //睡2秒
            try {TimeUnit.SECONDS.sleep(2);} catch (InterruptedException e) { e.printStackTrace();}
            System.out.println("睡两秒出结果："+res);
            //int a=5/0;
            return res;
        }). thenApply(ta-> {
            //thenApply()可以多次使用，入参与whenComplete独立且先于whenComplete执行。链式thenApply和多次thenApply效果不同。
            System.out.println("ta+5前的数据："+ta);
            return ta+5;
        }).whenComplete((result, exception) -> {
            if (result != null) { //也可以exception==null判断
                System.out.println("第一阶段thenApply结果是： " + result + "  第二阶段完成");
            }
        }).exceptionally(e->{
            e.printStackTrace();
            System.out.println("这里抛异常： +++++++++++++++++++++++++++"+e);
            return null;
        });
        //这里不执行get不会打印 "睡两秒出结果" + res 语句，因为main线程关闭，所以导致后面的不执行
        intResult.get();
    }

    @Test
    public void supplyAsyncTest() throws ExecutionException, InterruptedException {
        CompletableFuture<Integer> intResult = CompletableFuture.supplyAsync(() -> {
            System.out.println(Thread.currentThread().getName() + "进来了");
            Integer res = new Random().nextInt(5);

            //睡2秒
            try {TimeUnit.SECONDS.sleep(2);} catch (InterruptedException e) { e.printStackTrace();}
            System.out.println("睡两秒出结果："+res);
            return res;
        });
        //这里不执行get不会打印 "睡两秒出结果" + res 语句，因为main线程关闭，所以导致后面的不执行
        //intResult.get();
    }


    @Test
    public void runAsyncTest() throws ExecutionException, InterruptedException {
        CompletableFuture<Void> completableFuture = CompletableFuture.runAsync(() -> {
            System.out.println(Thread.currentThread().getName() + "进来了");
            Integer res = new Random().nextInt(5);
            //睡2秒
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("睡两秒出结果：" + res);
        });
        //这里不执行get不会打印 "睡两秒出结果" + res 语句，因为main线程关闭，所以导致后面的不执行,用线程池可以避免
        completableFuture.get();
    }
}

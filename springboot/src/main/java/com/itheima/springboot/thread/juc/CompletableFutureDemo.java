package com.itheima.springboot.thread.juc;

import org.junit.Test;

import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;

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

    /**
     * 测试不打印出结果
     */
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

    /**
     * 测试get()阻塞打印出结果
     */
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

    /**
     * CompletableFuture异步处理多个不同任务并获取返回值
     */
    @Test
    public void fetcManyResult(){
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        // 1. 创建多个异步任务
        CompletableFuture<String> task1 = CompletableFuture.supplyAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "任务1返回值";
        }, executorService);

        CompletableFuture<String> task2 = CompletableFuture.supplyAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "任务2返回值";
        }, executorService);

        CompletableFuture<String> task3 = CompletableFuture.supplyAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "任务3返回值";
        }, executorService);
        // 2. 将多个任务组合成一个新的任务
        CompletableFuture<Void> allTasks = CompletableFuture.allOf(task1, task2, task3);
        // 3. 获取各个异步任务结果
        try {
            // 等待所有任务完成
            allTasks.get();
            // 获取每个任务的返回值
            String result1 = task1.join();
            String result2 = task2.join();
            String result3 = task3.join();
            System.out.println("任务1结果为：" + result1);
            System.out.println("任务2结果为：" + result2);
            System.out.println("任务3结果为：" + result3);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        } finally {
            // 关闭线程池
            executorService.shutdown();
        }
    }
    /**
     * CompletableFuture异步处理多个相同任务并获取返回值
     */
    @Test
    public void fetchSameTaskResult(){
        // 创建一个线程池
        ExecutorService executorService = Executors.newFixedThreadPool(20);
        //1.存储所有异步任务的CompletableFuture对象
        List<CompletableFuture<Integer>> futures = new ArrayList<>();
        // 在for循环中创建异步任务
        for (int i = 0; i < 50; i++) {
            int taskNumber = i;
            CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> {
                // 模拟异步任务
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return taskNumber * 2;
            }, executorService);
            // 将CompletableFuture对象添加到列表中
            futures.add(future);
        }
        //2.将所有任务组合成一个新的CompletableFuture对象
        CompletableFuture<Void> allFutures = CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]));
        //3.获取结果
        try {
            // 等待所有任务完成
            allFutures.get();
            // 获取每个异步任务的结果
            for (CompletableFuture<Integer> future : futures) {
                Integer result = future.join(); // 使用join()方法获取结果
                System.out.println("Task result: " + result);
            }
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        } finally {
            // 关闭线程池
            executorService.shutdown();
        }
    }
    /**
     * CompletableFuture异步处理多个相同任务并获取返回值
     * 与CountDown连用
     */
    @Test
    public void sameTaskResult() throws InterruptedException {
        //0. 创建一个线程池
        ExecutorService executorService = Executors.newFixedThreadPool(20);
        //1. 创建一个 countDownLatch 与循环次数一样
        CountDownLatch countDownLatch=new CountDownLatch(50);
        //2. 创建一个线程安全的集合
        List<Integer> list = new CopyOnWriteArrayList<>();
        //3. 在for循环中创建异步任务
        for (int i = 0; i < 50; i++) {
            int taskNumber = i;
            CompletableFuture.runAsync(() -> {
                //4. 模拟异步任务，countDownLatch.countDown()代表线程完成
                try {
                    Thread.sleep(1000);
                    list.add(taskNumber * 2);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }finally {
                    countDownLatch.countDown();
                }
            }, executorService);
        }
        countDownLatch.await();
        list.stream().filter(e->{
            System.out.println(e);
            return false;
        }).collect(Collectors.toList());;

    }

}

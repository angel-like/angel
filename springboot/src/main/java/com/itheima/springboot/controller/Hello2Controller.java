package com.itheima.springboot.controller;

import com.itheima.springboot.config.filter.MDCUtils;
import com.itheima.springboot.test.Student;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Slf4j
@RestController
public class Hello2Controller {

    @Autowired
    ThreadPoolTaskExecutor executor;

	@RequestMapping("hello2")//路径直接http://localhost:8080/hello   不要写项目名
    @ResponseBody//返回给页面字符数据
    public String hello() {
        fetchSameTaskResult();
        return "hello2 world！第二个！";
    }

    /**
     * 增加日志打印后
     * @param stu
     * @return
     */
    @PostMapping("hello3")
    public String hello3(@RequestBody Student stu) {
        return "hello3 world！第三个！"+stu.getName();
    }


    public void fetchSameTaskResult(){
        //存储所有异步任务的CompletableFuture对象
        List<CompletableFuture<Integer>> futures = new ArrayList<>();
        log.info("测试日志-多线程获取返回结果");
        //1.先获取MDC的追踪id，TraceId
        String traceId = MDCUtils.getTraceId();
        for (int i = 0; i < 5; i++) {
            int taskNumber = i;
            CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> {
                //2.该线程添加TraceId
                MDCUtils.generaterMdcTraceId(traceId);
                // 模拟异步任务
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                log.info("线程池里面做日志打印---测试MDC");
                return taskNumber * 2;
            }, executor);
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
                log.info("Task result: " + result);
            }
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        } finally {
        }
    }
}

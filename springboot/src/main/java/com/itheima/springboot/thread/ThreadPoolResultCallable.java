package com.itheima.springboot.thread;

import java.util.concurrent.Callable;

public class ThreadPoolResultCallable implements Callable<Integer>{
	 private final int begin;
     private final int end;

     public ThreadPoolResultCallable(int begin, int end) {
         this.begin = begin;
         this.end = end;
     }
     
    //这个线程有返回值
	@Override
	public Integer call() throws Exception {
		int result = 0;
        //for (int i = begin; i <= end; i++) {
            //result += i;
			result=begin+end;
            Thread.sleep(1000);
        //}
        System.out.printf("(%s) - 运行结束，结果为 %d\n",
                Thread.currentThread().getName(), result);

        return result;
	}

}

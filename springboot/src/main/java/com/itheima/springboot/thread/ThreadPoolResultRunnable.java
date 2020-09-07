package com.itheima.springboot.thread;

public class ThreadPoolResultRunnable  implements Runnable{
	private final int begin;
    private final int end;

    private int result;

    public ThreadPoolResultRunnable(int begin, int end) {
        this.begin = begin;
        this.end = end;
    }
    
	@Override
	public void run() {
		result = 0;
        try {
            //for (int i = begin; i <= end; i++) {
                //result += i;
        	    result=begin+end;
                Thread.sleep(100);
            //}
        } catch (InterruptedException ex) {
            ex.printStackTrace(System.err);
        }
        System.out.printf("(%s) - 运行结束，结果为 %d\n",
                Thread.currentThread().getName(), result);
		
	}
	
	
	//返回的结果
	public int getResult() {
        return result;
    }
}

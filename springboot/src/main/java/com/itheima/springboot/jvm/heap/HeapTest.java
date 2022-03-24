package com.itheima.springboot.jvm.heap;

public class HeapTest {
    public static void main(String[] args ) {
        //数值会偏小，survice区每次只使用一个，所以少了1/30
        long initialMemory = Runtime.getRuntime().totalMemory() / 1024/1024;
        long maxMemory = Runtime.getRuntime().maxMemory() / 1024/1024;
        long system=initialMemory *64/1024;
        long system2= maxMemory *4/1024;
        System.out.println("初始内存大小："+initialMemory+"M");
        System.out.println("最大内存大小："+maxMemory+"M");
        System.out.println("系统初始内存大小："+system+"G");
        System.out.println("最大系统初始内存大小："+system2+"G");
        try {
            System.out.println("开始"+args[0]);
            Thread.sleep(1000000);
            System.out.println("结束");
        }catch (Exception e){
            System.out.println("异常结束："+e.getMessage());
        }
    }
}

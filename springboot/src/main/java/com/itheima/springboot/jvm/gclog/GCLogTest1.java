package com.itheima.springboot.jvm.gclog;

import java.util.Scanner;

/**
 * 在jdk7 和 jdk8中分别执行
 * -verbose:gc -Xms20M -Xmx20M -Xmn10M -XX:+PrintGCDetails -XX:SurvivorRatio=8 -XX:+UseSerialGC -Xloggc:./logs/gc.log   -XX:+PrintCommandLineFlags
 * -XX:+UsePerfData 是否被jps发现
 */
public class GCLogTest1 {
    private static final int _1MB = 1024 * 1024;

    public static void testAllocation() {
        byte[] allocation1, allocation2, allocation3, allocation4;
        allocation1 = new byte[2 * _1MB];
        allocation2 = new byte[2 * _1MB];
        allocation3 = new byte[2 * _1MB];
        allocation4 = new byte[4 * _1MB];
        Scanner scanner=new Scanner(System.in);
        String next = scanner.next();
        System.out.println(next);
        System.out.println("12312");
    }

    public static void main(String[] agrs) {
        testAllocation();
        A[] a=new A[1];//调用该对象不会触发初始化
        //A ac=new A();
        //int aa=A.num;
    }

}

class A{
    public static int num=1;
    static{
        System.out.println("调用clinit()方法");
    };
}
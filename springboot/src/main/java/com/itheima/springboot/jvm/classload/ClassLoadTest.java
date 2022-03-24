package com.itheima.springboot.jvm.classload;

import sun.misc.Launcher;

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryManagerMXBean;
import java.lang.management.MemoryUsage;
import java.net.URL;
import java.util.List;

/**
 * 扩展类加载器和系统类加载器都间接继承了ClassLoader，而引导类加载器使用c语言实现的
 *

 *
 */
public class ClassLoadTest {
    public static void main(String[] args) {
        long initialMemory = Runtime.getRuntime().totalMemory() / 1024/1024; //
        long maxMemory = Runtime.getRuntime().maxMemory() / 1024/1024;
        long system=initialMemory *64/1024;
        long system2= maxMemory *4/1024;
        System.out.println("当前堆初始内存大小："+initialMemory+"M");
        System.out.println("当前堆最大可用内存大小："+maxMemory+"M");
        System.out.println("系统初始内存大小："+system+"G");
        System.out.println("最大系统初始内存大小："+system2+"G");
        MemoryMXBean memoryMXBean = ManagementFactory.getMemoryMXBean();
        MemoryUsage heapMemoryUsage = memoryMXBean.getHeapMemoryUsage();
        System.out.println("堆内存使用情况："+heapMemoryUsage);
        //系统类加载器
        ClassLoader systemClassLoader = ClassLoader.getSystemClassLoader();//
        System.out.println(systemClassLoader);//sun.misc.Launcher$AppClassLoader@18b4aac2
        //扩展类加载器    （java.ext ....）
        ClassLoader extendlassLoader = systemClassLoader.getParent();
        System.out.println(extendlassLoader);//sun.misc.Launcher$ExtClassLoader@5f150435
        String property = System.getProperty("java.ext.dirs");
        for(String s : property.split(";")){
            System.out.println("扩展类加载器加载器的加载路径"+s);
        }
        //引导类加载器(启动类加载器)  JAVA核心类库(rt.jar、resources.jar、sun.boot.class.path ,c/c++实现 嵌套在jvm内部)
        ClassLoader bootstraplassLoader = extendlassLoader.getParent();
        System.out.println(bootstraplassLoader);//获取不到  为null
        URL[] urLs = Launcher.getBootstrapClassPath().getURLs();//Launcher 虚拟机入口应用
        for(URL e: urLs){
            System.out.println("启动类加载器的加载路径"+e);
        }
        //用户自定义 加载器。默认使用系统加载器
        System.out.println(ClassLoadTest.class.getClassLoader());//sun.misc.Launcher$AppClassLoader@18b4aac2
        //JAVA核心类库(rt.jar、resources.jar、sun.boot.class.path ,c/c++实现 嵌套在jvm内部)都是      用  引导类加载器
        System.out.println(String.class.getClassLoader());//获取不到  为null   引导类加载器

        //获取类加载器
        gainClassLoader();
    }

    private static void gainClassLoader() {
        //1. 反射获取  Class.forName()就是反射
        try {
            ClassLoader classLoader = Class.forName("java.lang.String").getClassLoader();// Class.forName("java.lang.String") 也是获取 String.class
            System.out.println("引导类加载器"+classLoader);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        //2.
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        System.out.println("系统类加载器"+classLoader);
        //3.
        ClassLoader systemClassLoaderClassLoader = ClassLoader.getSystemClassLoader();
        System.out.println("系统类加载器"+systemClassLoaderClassLoader);
    }
}

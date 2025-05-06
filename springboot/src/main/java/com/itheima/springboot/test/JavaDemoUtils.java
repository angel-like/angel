package com.itheima.springboot.test;

public class JavaDemoUtils {
    /**
     * 测试公共静态方法
     */
    public static String publicStaticTest(){
        //没mock成功直接抛异常
        throw new RuntimeException("public static方法mock失败抛异常");
    }

    /**
     * 测试公共静态无返回值方法
     */
    public static void publicStaticVoidTest(String aaa){
        //没mock成功直接抛异常
        throw new RuntimeException("public static void方法mock失败抛异常"+aaa);
    }

    public  String publicTest(String str){
        if("1".equals(str)){
            //测试传1 没mock成功直接抛异常
            throw new RuntimeException("public static void方法mock失败抛异常"+str);
        }
        return "publicTest";
    }
}

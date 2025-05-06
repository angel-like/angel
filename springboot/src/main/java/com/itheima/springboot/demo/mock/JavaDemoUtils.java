package com.itheima.springboot.demo.mock;

public class JavaDemoUtils {
    /**
     * 测试公共静态方法
     */
    public static String publicStaticTest(){
        //测试没mock成功直接抛异常
        throw new RuntimeException("Static方法mock失败抛异常");
    }


    /**
     * 测试 new 对象 调用的方法
     */
    public static String publicTest(String str){
        if("1".equals(str)){
            throw new RuntimeException("new JavaDemoUtils()调用抛异常");
        }
        //测试每mock成功直接抛异常
        return "new";
    }

}

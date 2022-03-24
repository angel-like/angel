package com.itheima.springboot.jvm.classload;

/**
 * * 为什么需要自定义类加载器？
 *  a.隔离加载类
 *  b.修改类加载方式
 *  c.扩展加载源
 *  d.防止源码泄露
 */
public class MyClassLoader extends ClassLoader{
    //没有复杂需求  可以继承 UrlClassLoader，不需要重写findClass 及 获取字节码流

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        try {
            Byte[] result=getClassFrom(name); //获取字节码流
        }catch (Exception e){

        }
        return super.findClass(name);
    }

    private Byte[] getClassFrom(String name) {
        //  如果指定路径的字节码文件进行加密，在此方法进行解密操作 d.防止源码泄露
        return null;
    }

}

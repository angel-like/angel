package com.itheima.springboot.proxy;

import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * 1.JDK动态代理机制能对 实现了接口的类(实现类) 生成代理，而不是针对类
 * 2.CGLIB是针对类实现代理，主要对指定的类生成一个子类，覆盖其中的方法，添加额外功能，因为是继承，所以该类方法不能用final来声明.
 */
public class CglibProxy implements MethodInterceptor {
    //代理的目标对象
    private Object target;

    /**
     *
     * @param proxy 获取代理的对象 userManager  包含有 CGLIB@BOUND=TRUE ，CGLIB$CALLBACK_0=CglibProxy@544（里面包含了new UserManagerImpl()的地址）
     * @param method    获取代理的对象 userManager 包含的几个方法
     * @param arr   方法的入参 args[0]=admin   args[1]=123456
     * @param methodProxy   暂时没用到
     * @return
     * @throws Throwable
     */
    @Override
    public Object intercept(Object proxy, Method method, Object[] arr, MethodProxy methodProxy) throws Throwable {
        System.out.println("cglib动态代理，监听开始！");
        //代理的     目标对象  及其要执行 方法名称
        Object result = method.invoke(target, arr);
        System.out.println("cglib动态代理，监听结束！");
        return result;
    }
    private Object getCglibProxy(Object targetObject){
        this.target=targetObject;
        Enhancer enhancer=new Enhancer();
        //设置父类，因为Cglib是针对指定的类 生成一个字类，所以需要指定父类
        Class<?> aClass = targetObject.getClass();
        enhancer.setSuperclass(aClass);
        //设置回调
        enhancer.setCallback(this);
        //创建并返回代理对象
        Object result = enhancer.create();
        return result;
    }

    public static void main(String[] args) {
        CglibProxy cglibProxy=new CglibProxy();
        //调用 获取代理的对象 userManager  包含有 CGLIB@BOUND=TRUE ，CGLIB$CALLBACK_0=CglibProxy@544（里面包含了new UserManagerImpl()的地址）
        UserManager userManager = (UserManager)cglibProxy.getCglibProxy(new UserManagerImpl());
        userManager.delUser("admin");
        userManager.addUser("user","123546");
    }


}

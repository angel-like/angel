package com.itheima.springboot.design.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * 1.JDK动态代理机制能对 实现了接口的类(接口的实现类) 生成代理，而不是针对类（单纯的一个类就行）
 *      利用反射机制生成一个实现代理接口的匿名类，在调用具体方法前调用InvokeHandler来处理         JDK是基于实现接口生成代理类
 *      必须要有实现接口的类才行，没有的话报错java.lang.ClassCastException:
 * 2.CGLIB是 针对类(该类不需要实现接口) 实现代理，主要对指定的类生成一个子类，覆盖其中的方法，添加额外功能，
 *      因为是继承，所以该类方法不能用final来声明.            Cglib是基于继承父类生成的代理类.
 *
 * 在Spirng当中动态代理的使用
 * a. 当Bean实现接口时，Spring就会用JDK的动态代理
 * b. 当Bean没有实现接口时，Spring使用CGLib来实现
 * c. 可以强制使用CGLib（在Spring配置中加入<aop:aspectj-autoproxy proxy-target-class=“true”/>）
 *
 * JDK和CGLib的性能对比
 * a. 使用CGLib实现动态代理，CGLib底层采用ASM字节码生成框架，使用字节码技术生成代理类，在JDK1.6之前比使用Java反射效率要高。
 *    唯一需要注意的是，CGLib不能对声明为final的方法进行代理，因为CGLib原理是动态生成被代理类的子类。
 * b. 在JDK1.6、JDK1.7、JDK1.8逐步对JDK动态代理优化之后，在调用次数较少的情况下，JDK代理效率高于CGLib代理效率，
 *    只有当进行大量调用的时候，JDK1.6和JDK1.7比CGLib代理效率低一点，但是到JDK1.8的时候，JDK代理效率高于CGLib代理
 *
 *
 *
 */
public class JdkProxy implements InvocationHandler {
    //代理的目标对象
    private Object target;

    /**
     *
     * @param proxy  获取代理的对象 userManager包含有 h=jdkProxy@572 ，h里面再有个UserManagerImpl@573
     * @param method  获取代理的对象 userManager 包含的几个方法
     * @param args    方法的入参 args[0]=admin   args[1]=123456
     * @return
     * @throws Throwable
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("jdk动态代理，监听开始！");
        //代理的  方法返回值       目标对象  及其要执行 方法名称
        Object result = method.invoke(target, args);
        System.out.println("jdk动态代理，监听结束！");
        return result;
    }

    private Object getJdkProxy(Object targetObject){
        this.target=targetObject;
        Class<?> aClass = targetObject.getClass();
        //基于实现接口生成代理类
        Object result = Proxy.newProxyInstance(aClass.getClassLoader(), aClass.getInterfaces(), this);
        return result;
    }

    public static void main(String[] args) {
        JdkProxy jdkProxy=new JdkProxy();
        //只包含 UserManagerImpl@594  指向堆的地址
        UserManager userManager1=new UserManagerImpl();

        //1. 调用 获取代理的对象 userManager包含有 h=jdkProxy@572 ，h里面再有个UserManagerImpl@573(即new UserManagerImpl()的地址)
        UserManager userManager = (UserManager)jdkProxy.getJdkProxy(new UserManagerImpl());
        //2. 调用对应的方法，再调用方法前会先调用invoke 方法做类似AOP切面    AOP切面就是基于cglib动态代理实现
        userManager.addUser("admin","123546");
        userManager.addUser("user","123546");
        userManager.delUser("123");

        //报错
        System.out.println("===============测试错误的代理，不实现接口的代理===============");
        UserManagerAAAImpl userManager22 = (UserManagerAAAImpl)jdkProxy.getJdkProxy(new UserManagerAAAImpl());
    }

    /**
     * 静态代理和动态代理
     *
     * 1.静态代理需要手动实现被代理接口的所有方法，而动态代理通过反射可以自动代理接口的所有方法
     * 2.静态代理在编译的时候就生成了代理类的class文件；动态代理在运行的时候动态生成代理类
     * 3.代理模式有很多用途：比如在方法调用前后添加日志；AIDL中就生成了远程接口的代理类，代理类帮助我们实现Binder底层的跨进程通信机制，让客户端用起来跟本地调用一样；日志系统、事务、拦截器、权限控制等。
     * 4.代理的优点：如果我们要为具体类增加功能的时候，我们只需要在代理类上调用具体类方法的前后进行处理就好了，避免了修改具体实现类，符合开闭原则
     * 5.静态代理的缺点：代理类需要跟委托类实现相同的接口，会出现大量的代码重复；当接口增加方法的时候，也要在代理类中添加相应方法。
     * 6.使用动态代理可以统一对委托类的所有方法在InvokeHandler的invoke中进行处理
     * 7.代理模式符合面向切面编程（AOP）思想，它强调在切入点的前后做增强处理
     */
}

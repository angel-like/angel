package com.itheima.springboot.jvm.classload;

import com.itheima.springboot.callback.Student;
import org.junit.Test;

/**
 *
 *  A a = (A)Class.forName(“pacage.A”).newInstance();   和    A a = new A()； 是一样的效果。
 *
 * Class.forName("a.class.Name")时，JVM会在classapth中去找对应的类并加载，这时JVM会执行该类的静态代码段。
 * 在使用newInstance()方法的时候，必须保证这个类已经加载并且已经连接了，而这可以通过Class的静态方法forName()来完成的。
 * 使用关键字new创建一个类的时候，这个类可以没有被加载，一般也不需要该类在classpath中设定，但可能需要通过classlaoder来加载
 *
 * 首先，newInstance( )是一个方法，而new是一个关键字；
 * 其次，Class下的newInstance()的使用有局限，它生成对象只能调用无参的构造函数，而使用 new关键字生成对象没有这个限制。
 */
public class LoadClass {
    @Test
    public void loadClass() throws InstantiationException, IllegalAccessException, ClassNotFoundException {
        ClassLoader classLoader1 = Thread.currentThread().getContextClassLoader();

        ClassLoader classLoader2 = ClassLoader.getSystemClassLoader();
        ClassLoader classLoader3 = Class.forName("com.itheima.springboot.jvm.classload.LoadClass").getClassLoader();

        Class<?> aClass1 = classLoader1.loadClass("com.itheima.springboot.callback.Student");
        Student stu1 = (Student)aClass1.newInstance();
        stu1.callHelp(9,10);

        Class<?> aClass2 = Class.forName("com.itheima.springboot.callback.Student");
        Student stu2 = (Student)aClass2.newInstance();
        stu2.callHelp(7,8);


        //Class的newInstance跟 new一样能获取对象
        // a. Class.forName（）这样做的好处是无需通过持有该类的实例对象引用而去获取Class对象，
        // b. getClass()是通过一个实例对象获取一个类的Class对象，其中的getClass()是从顶级类Object继承而来的，它将返回表示该对象的实际类型的Class对象引用
        // c. .class 这种方式相对前面两种方法更加简单，更安全。因为它在编译器就会受到编译器的检查同时由于无需调用forName方法效率也会更高
        Class<? extends Student> aClass3=Student.class;
        Student stu3 = Student.class.newInstance();
        stu3.callHelp(5,6);
        Class<? extends Student> aClass4 = stu3.getClass();// aClass1 === aClass2 === aClass3 === aClass4  是一样的地址值

        /**
         * 工产模式，从配置文件加载类位置，并实例化该类，默认调用无参构造
         * //String className = Example类名称;
         * String className = readfromXMlConfig;//从xml 配置文件中获得字符串
         * class c = Class.forName(className);
         * factory = (ExampleInterface)c.newInstance();
         * 上面代码已经不存在Example的类名称
         * 优点是，无论Example类怎么变化，上述代码不变，甚至可以更换Example的兄弟类Example2 , Example3 , Example4……，只要他们继承ExampleInterface就可以。
         */
    }
}

package com.itheima.springboot.test;

import cn.hutool.log.Log;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.core.classloader.annotations.SuppressStaticInitializationFor;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import java.io.File;
import java.lang.reflect.Field;

import static org.mockito.ArgumentMatchers.any;

@RunWith(PowerMockRunner.class)//以PowerMock方式启动
@PowerMockIgnore({"javax.crypto.*", "javax.management.*,", "cn.hutool.log.Log"})//解决使用PowerMock后，提示classloader类加载器错误
@SuppressStaticInitializationFor({"javax.crypto.*", "javax.management.*"})//不让对应静态类加载
@PrepareForTest({JavaDemoImpl.class, JavaDemoUtils.class, System.class})
//mock new出来的对象，private、static、final方法所在的类，需要用该注解
public class JavaDemoImplPowerTest {
    @InjectMocks
    private JavaDemoImpl javaDemoImpl;

    @Mock
    private JavaDemoDao dao;

    @Before
    public void setup() throws Exception {
        //isNull() 可以用在匹配null值
    }

    /**
     * 1.1 mock方法里new出来的对象
     */
    @Test
    public void newTest() throws Exception {
        JavaDemoUtils utils = PowerMockito.mock(JavaDemoUtils.class);
        PowerMockito.whenNew(JavaDemoUtils.class).withAnyArguments().thenReturn(utils);
        //注意类上要加@PrepareForTest({JavaDemoUtils.class})
        PowerMockito.when(utils.publicTest(any())).thenReturn("123");
        String result = javaDemoImpl.powerNewTest();
        Assert.assertEquals(result, "123");
    }

    /**
     * 1.2 mock方法里new出来的对象  PowerMockito.
     */
    @Test
    public void newTest2() throws Exception {
        //mock 方法里new 出来的对象
        File file = PowerMockito.mock(File.class);
        PowerMockito.whenNew(File.class).withAnyArguments().thenReturn(file);
        PowerMockito.doReturn(true).when(file, "exist");
        //调用要单测的方法
        String result = javaDemoImpl.powerNewTest();
        //断言
        Assert.assertEquals(result, true);
    }

    /**
     * 2. mock私有方法
     */
    @Test
    public void privateMethodTest() throws Exception {
        //注意类上要加@PrepareForTest({JavaDemoImpl.class})，表示调该javaDemo对象所有方法默认mock,返回默认值
        JavaDemoImpl javaDemo = PowerMockito.mock(JavaDemoImpl.class);
        //对priMethod入参进行填充，几个入参要几个any()
        PowerMockito.when(javaDemo, "priMethod", any()).thenReturn("123");
        //thenCallRealMethod 表示调用真实的方法，不再是mock
        PowerMockito.when(javaDemo.privateTest()).thenCallRealMethod();
        //PowerMockito.doCallRealMethod().when(javaDemo,"priMethod",any());//无返回值，调用真实方法  推荐用
        String result = javaDemo.privateTest();
        //断言
        Assert.assertEquals(result, "123");
    }

    /**
     * 3.1 mock静态方法(对于异步多线程也适用)
     */
    @Test
    public void syncStaticTest() throws Exception {
        //a.普通静态方法 注意类上要加@PrepareForTest({JavaDemoUtils.class})
        PowerMockito.mockStatic(JavaDemoUtils.class);
        PowerMockito.when(JavaDemoUtils.publicStaticTest()).thenReturn("123");
        //b. 异步静态方法，也是一样的
        //c. 普通的dao 正常mock即可
        PowerMockito.when(dao.syncAddBatch(any())).thenReturn("55");
        javaDemoImpl.voidSyncTest();
        Thread.sleep(500);//防止异步线程执行一半因主线程结束导致中断
    }

    /**
     * 3.2 mock系统类静态方法 + 静态无返回值方法
     */
    @Test
    public void staticTest() throws Exception {
        //1.静态无返回值方法 注意类上要加@PrepareForTest({JavaDemoUtils.class})
        PowerMockito.mockStatic(JavaDemoUtils.class);
        PowerMockito.doNothing().when(JavaDemoUtils.class, "publicStaticVoidTest", any());
        //PowerMockito.doNothing().when(JavaDemoUtils.class);//对该类所有静态方法mock，不推荐用，导致2.2.报错missing thenReturn()
        //2.1 mock系统静态类方法，注意要加 @PrepareForTest({System.class})
        PowerMockito.mockStatic(System.class);
        //2.2 指定特定入参生效
        PowerMockito.when(System.getProperty("ccc")).thenReturn("bbb");
        //3.调用覆盖的方法
        String result = javaDemoImpl.systemStaticTest();
        //断言
        Assert.assertEquals(result, "bbb");
    }


    /**
     * 4 mock final方法
     */
    @Test
    public void finalTest() throws Exception {
        //mock final方法 注意类上要加@PrepareForTest({JavaDemoImpl.class})
        JavaDemoImpl javaDemo = PowerMockito.mock(JavaDemoImpl.class);
        PowerMockito.when(javaDemo.finalMethod(any())).thenReturn("123");
        //调用覆盖的方法
        PowerMockito.when(javaDemo.finalTest()).thenCallRealMethod();
        String result = javaDemo.finalTest();
        //断言
        Assert.assertEquals(result, "123");
    }

    /**
     * 5 private static方法
     */
    @Test
    public void priStaticTest() throws Exception {
        //1.1 mock private static方法 注意类上要加@PrepareForTest({JavaDemoImpl.class})
        PowerMockito.mockStatic(JavaDemoImpl.class);
        PowerMockito.spy(JavaDemoImpl.class);//保证外部类调用JavaDemoImpl类的 public方法保持实际代码的调用
        //1.2 不能用PowerMockito.when这种方式，该方法还是会进入 priStaticTest去执行
        //PowerMockito.when(JavaDemoImpl.class,"priStaticMethod",any()).thenReturn("私有静态成员方法");
        PowerMockito.doReturn("私有静态成员方法").when(JavaDemoImpl.class, "priStaticMethod", any());
        String result = javaDemoImpl.priStaticTest();
        //断言
        Assert.assertEquals(result, "私有静态成员方法");
    }

    /**
     * 6.1  private私有成员变量
     */
    @Test
    public void testDao() throws Exception {
        //mock private私有成员变量,注意类上要加@PrepareForTest({JavaDemoImpl.class})
        JavaDemoImpl javaDemo = PowerMockito.mock(JavaDemoImpl.class);
        //1.1先把javaDemoDao放入 JavaDemoDaoUtil
        JavaDemoDao javaDemoDao = PowerMockito.mock(JavaDemoDao.class);
        JavaDemoDaoUtil daoUtil = PowerMockito.mock(JavaDemoDaoUtil.class);
        Field javaDemoDaoFiled = daoUtil.getClass().getDeclaredField("javaDemoDao");
        javaDemoDaoFiled.setAccessible(true);//允许修改成员变量
        javaDemoDaoFiled.set(daoUtil, javaDemoDao);//重新设置值
        //1.2 mock对应方法
        PowerMockito.when(daoUtil.getJavaDemoDao()).thenReturn(javaDemoDao);
        PowerMockito.when(javaDemoDao.insertOne(any())).thenReturn(5);
        //2.把JavaDemoDaoUtil 放入javaDemo
        Field daoField = javaDemo.getClass().getDeclaredField("dao");
        daoField.setAccessible(true);
        daoField.set(javaDemo, daoUtil);
        //3.执行要覆盖的方法
        PowerMockito.when(javaDemo.utilsDao(any())).thenCallRealMethod();
        String result = javaDemo.utilsDao("123");
        //断言
        Assert.assertEquals(result, "5");
    }

    /**
     * 6.2  private static 私有静态成员变量
     */
    @Test
    public void testLog() throws Exception {
        //1. 单元测试类上加上 @SuppressStaticInitializationFor({"cn.hutool.log.Log"})，不让对应静态类加载
        //2. 私有静态成员变量做变量 mock替换
        Log log = PowerMockito.mock(Log.class);
        Field logField = javaDemoImpl.getClass().getDeclaredField("log");
        logField.setAccessible(true);
        logField.set(javaDemoImpl, log);//替换成当前生产的log
        //3.调用方法测试
        String result = javaDemoImpl.logInfo("123");
        //断言
        Assert.assertEquals(result, "1");
        //Whitebox.setInternalState(javaDemoImpl,"log",log);
        //4. 其他   该方法可以 但是无效果
        //JavaDemoDaoUtil daoUtil = PowerMockito.mock(JavaDemoDaoUtil.class);
        //Whitebox.setInternalState(javaDemoImpl,"dao",daoUtil);
        //PowerMockito.when(daoUtil.getJavaDemoDao()).thenReturn(PowerMockito.mock(JavaDemoDao.class));
        //5. mock私有变量   无效果
        //Log log= org.easymock.EasyMock.createNiceMock(Log.class);//easymock-1.0.jar包
        //Whitebox.setInternalState(javaDemoImpl,log);
        //Field logField = javaDemoImpl.getClass().getDeclaredField("log");
        //PowerMockito.field(javaDemoImpl.getClass(),"logField");

    }
}

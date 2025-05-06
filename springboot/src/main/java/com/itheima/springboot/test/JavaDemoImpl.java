package com.itheima.springboot.test;

import cn.hutool.log.Log;
import com.itheima.springboot.utils.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class JavaDemoImpl {
    //对私有静态变量做mock
    private static Log log=null;

    @Autowired
    private JavaDemoDao javaDemoDao;

    //对私有变量做mock
    private JavaDemoDaoUtil  dao=new JavaDemoDaoUtil();

    public String commonTest(){
        int  i=0;
        try {
            i=javaDemoDao.insertOne("insert");
        }catch (Exception e){
            e.printStackTrace();
            return "error";
        }
        return String.valueOf(i);
    }

    public void voidTest(){
        String update=javaDemoDao.updateOne("a");
        if(!"update".equals(update)){
            throw new RuntimeException();
        }
        javaDemoDao.addOne();
        System.out.println("无返回值mock,mock静态方法测试正常");
    }

    public String powerNewTest(){
        JavaDemoUtils newUtils=new JavaDemoUtils();
        String s=newUtils.publicTest("1");
        return s;
    }

    public Boolean powerFileTest(){
        File file=new File("a");
        Boolean exist=file.exists();
        return exist;
    }

    public String privateTest(){
        //默认传1，mock失败抛异常
        String s=priMethod("1");
        return s;
    }

    private String priMethod(String str){
        if("1".equals(str)){
            throw new RuntimeException("私有方法mock失败抛异常");
        }
        return str;
    }

    public void voidSyncTest(){
        //普通静态方法
        String s1=JavaDemoUtils.publicStaticTest();
        System.out.println("普通静态方法mock成功："+s1);
        //异步
        ThreadPoolExecutor poolExecutor=new ThreadPoolExecutor(2,2,60, TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(2),new ThreadPoolExecutor.CallerRunsPolicy());
        CompletableFuture.runAsync(()->{
            try {
                System.out.println("测试异步mock开始：");
                String s=javaDemoDao.syncAddBatch("1");
                System.out.println("测试异步mock成功："+s);
            }catch (Exception e){
                System.out.println("测试异步mock失败：");
            }
        },poolExecutor);
    }

    public String systemStaticTest(){
        //静态无返回值，mock失败抛异常
        JavaDemoUtils.publicStaticVoidTest("");
        String property=System.getProperty("ccc");
        return property;
    }

    public String finalTest(){
        String finalRes=finalMethod("1");
        System.out.println("final方法mock成功："+finalRes);
        return finalRes;
    }

    public final String finalMethod(String str){
        if("1".equals(str)){
            throw new RuntimeException("final方法mock失败抛异常");
        }
        return "1";
    }

    /**
     * 测试 private static私有静态成员方法mock
     * @return
     */
    public String priStaticTest(){
        String pirResult=priStaticMethod("1");
        System.out.println("private方法mock成功："+pirResult);
        return pirResult;
    }

    private static String priStaticMethod(String str){
        //测试没mock成功直接抛异常
        if(!"1".equals(str)){
            throw new RuntimeException("priStatic方法mock失败抛异常");
        }
        return "mock失败了";
    }

    public final String utilsDao(String str){
        JavaDemoDao baseDao = dao.getJavaDemoDao();
        int i = baseDao.insertOne(str);
        return String.valueOf(i);
    }

    public final String logInfo(String str){
        log.info("2313");
        return "1";
    }
}

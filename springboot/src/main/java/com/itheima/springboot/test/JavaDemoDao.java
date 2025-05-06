package com.itheima.springboot.test;

public class JavaDemoDao {

    public int insertOne(String insertStr){
        System.out.println("进入insertOne方法插入一条数据："+insertStr);
        return 1;
    }

    public String updateOne(String b){
        System.out.println("进入updateOne方法更新一条数据："+b);
        throw new RuntimeException("更新数据异常");
    }

    public void addOne(){
        System.out.println("进入addOne方法不返回数据：");
        throw new RuntimeException("进入addOne方法异常");
    }

    public String syncAddBatch(String b){
        System.out.println("进入syncAddBatch方法："+b);
        throw new RuntimeException("进入syncAddBatch方法异常");
    }
}

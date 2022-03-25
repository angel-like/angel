package com.itheima.springboot.demo;

import java.math.BigDecimal;
import java.util.Random;
import java.util.concurrent.Callable;

public class Sports implements Callable<Float> {
    @Override
    public Float call(){
        Random randow=new Random();
        try{
            Thread.sleep(10000);
        }catch(Exception e){

        }
        //随机生成运动员跑100米时长
        Float runTime = randow.nextFloat()+10;
        System.out.printf("(%s) - 跑步结束，结果为: %s",
                Thread.currentThread().getName(), runTime);
        System.out.println();
        return runTime;
    }
}

package com.itheima.springboot.test;

import org.springframework.beans.BeanUtils;

import java.math.BigDecimal;

public class test {
    public static void main(String[] args) {
        Student old=new Student();
        old.setName("鲜明");
        Student newStu=new Student();
        newStu.setAge(5);
        //会把null值也复制过去的  ，但是各自的地址都没变，所以只是属性一个个全部set过去
        BeanUtils.copyProperties(old,newStu);
        System.out.println(newStu.getAge());

        String s="ddasd1-1";
        String[] split = s.split("-");
        int a=split.length;
        if(a>=2){
            System.out.println(split[0]);
            System.out.println(split[1]);
        }
    }
}

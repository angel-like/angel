package com.itheima.springboot.design.decorator;

/**
 * 汉堡基类（被装饰者）
 */
public abstract class Humburger {

    protected  String name ;

    public String getName(){
        return name;
    }

    public abstract double getPrice();

}
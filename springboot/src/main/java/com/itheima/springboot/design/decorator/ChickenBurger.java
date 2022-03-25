package com.itheima.springboot.design.decorator;

/**
 * 鸡腿堡类（被装饰者的初始状态，有些自己的简单装饰）
 */
public class ChickenBurger extends Humburger {
    //这边的name ，父类有就用父类的
    public ChickenBurger(){
        name = "鸡腿堡";
    }

    @Override
    public double getPrice() {
        return 10;
    }
}

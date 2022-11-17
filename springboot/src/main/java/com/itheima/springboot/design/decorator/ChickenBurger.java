package com.itheima.springboot.design.decorator;

/**
 * 鸡腿堡类（被装饰者的初始状态，有些自己的简单装饰）
 */
public class ChickenBurger extends Humburger {
    private String name="123";

    /**
     * 这边的name ，本类有的话，要用super指定父类，父类有就用父类的。
     *  只有在编译运行时，除了成员方法是看子类，成员变量，静态方法都是看父类的
     */
    public ChickenBurger(){
        super.name = "鸡腿堡";
    }

    @Override
    public double getPrice() {
        return 10;
    }
}

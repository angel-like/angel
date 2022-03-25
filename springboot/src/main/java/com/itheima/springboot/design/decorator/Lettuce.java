package com.itheima.springboot.design.decorator;

/**
 * 生菜（装饰者的第一层
 */
public class Lettuce extends Condiment{

    Humburger humburger;

    public Lettuce(Humburger humburger){
        this.humburger = humburger;
    }

    /**
     * 装饰者模式 在原基础上 ，增加配料与生菜
     * @return
     */
    @Override
    public String getName() {
        return humburger.getName()+"生菜";
    }

    @Override
    public double getPrice() {
        return humburger.getPrice()+1.5;
    }
}

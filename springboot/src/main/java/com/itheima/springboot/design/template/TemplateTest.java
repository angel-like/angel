package com.itheima.springboot.design.template;

/**
 * 模板方法模式
 */
public class TemplateTest {
    public static void main(String[] args) {
        AbstractTemplate at=new Registry();
        at.registry();
    }
}

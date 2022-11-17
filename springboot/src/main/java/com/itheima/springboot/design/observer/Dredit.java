package com.itheima.springboot.design.observer;

/**
 * 贷款方  （张三王五把贷带出，借钱出去）
 * 小明有钱，发起一个通知，这边就会拿到钱
 */
public interface Dredit {
    void takeMoney();
}

class ZhangSan implements Dredit {

    @Override
    public void takeMoney() {
        System.out.println("张三从小明那拿钱");
    }
}

class WangWu implements Dredit {

    @Override
    public void takeMoney() {
        System.out.println("王五从小明那拿钱");
    }
}
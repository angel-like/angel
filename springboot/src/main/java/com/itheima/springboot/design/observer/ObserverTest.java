package com.itheima.springboot.design.observer;

/**
 * 观察者模式测试
 */
public class ObserverTest {
    public static void main(String[] args) {
        //1. 小明找张三、王五借钱
        Credit xiaoMing=new XiaoMing();
        xiaoMing.borrow(new ZhangSan());
        xiaoMing.borrow(new WangWu());
        //2.小明有钱了（状态置为1） 通知他们拿钱
        xiaoMing.setState(1);
        //xiaoMing.nofifyDredit();//这个应该是私有方法，可以不暴露
    }
}

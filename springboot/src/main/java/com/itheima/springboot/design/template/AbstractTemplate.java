package com.itheima.springboot.design.template;

/**
 * 模板方法模式
 */
public abstract class AbstractTemplate {

    public abstract String getNamePwd();

    public void registry() {
        String namePwd = getNamePwd();
        System.out.println("通过账号密码：" + namePwd + "实现业务逻辑");
    }
}

package com.itheima.springboot.design.proxy;

public class UserManagerImpl implements UserManager{
    @Override
    public Integer addUser(String userName, String password) {
        System.out.println("调用新增");
        System.out.println("用户名："+userName+"    密码："+password);
        return 1;
    }

    @Override
    public void delUser(String userName) {
        System.out.println("调用删除");
        System.out.println("用户名："+userName );
    }
}

package com.itheima.springboot.design.proxy;

public interface UserManager {
    public abstract Integer addUser(String userName,String password);
    public abstract void delUser(String userName);
}

package com.itheima.springboot.proxy;

public interface UserManager {
    public abstract Integer addUser(String userName,String password);
    public abstract void delUser(String userName);
}

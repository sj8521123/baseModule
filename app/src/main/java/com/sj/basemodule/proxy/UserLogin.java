package com.sj.basemodule.proxy;


public class UserLogin implements ILogin {
    @Override
    public void userLogin() {
        System.out.println("用户登录");
    }
}

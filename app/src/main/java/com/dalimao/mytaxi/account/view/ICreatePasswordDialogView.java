package com.dalimao.mytaxi.account.view;

/**
 * Created by TYZ on 2017/11/9.
 */

public interface ICreatePasswordDialogView   extends  IView{
    /**
     * 显示注册成功
     */
    void showRegisterSuc();

    /**
     * 显示登录成功
     */
    void showLoginSuc();

    /**
     * 显示密码为空
     */
    void showPasswordNull();

    /**
     *  显示两次输入密码不一样
     */
    void showPasswordNotEqual();
}

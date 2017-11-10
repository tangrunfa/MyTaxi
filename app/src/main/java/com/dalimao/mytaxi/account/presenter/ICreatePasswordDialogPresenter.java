package com.dalimao.mytaxi.account.presenter;

/**
 * Created by TYZ on 2017/11/9.
 */

public interface ICreatePasswordDialogPresenter {
    /**
     * 校验密码输入合法性
     */
    boolean checkPw(String pw, String pw1);
    /**
     *  提交注册
     */
    void requestRegister(String phone,String code);
    /**
     * 登录
     */
    void requestLogin(String phone,String pw);
}

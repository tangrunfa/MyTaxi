package com.dalimao.mytaxi.account.presenter;

/**
 * Created by TYZ on 2017/11/9.
 */

public interface ISmsCodeDialogPresenter {
    /**
     *  请求下发验证码
     */
        void  requestSendSmsCode(String phone);
    /**
     * 请求校验验证码
     */
    void requestCheckSmsCode(String phone, String smsCode);
    /**
     * 用户是否存在
     */
    void requestCheckUserExist(String phone);
}

package com.dalimao.mytaxi.account.view;

/**
 * Created by TYZ on 2017/11/9.
 */

public interface ISmsCodeDialogView  extends  IView {
    /**
     * 显示倒计时
     */
    void showCountDownTimer();
    /**
     * 显示验证状态
     * @param b
     */
    void showSmsCodeCheckState(boolean b);


    /**
     * 用户是否存在
     * @param b
     */
    void showUserExist(boolean b);
}

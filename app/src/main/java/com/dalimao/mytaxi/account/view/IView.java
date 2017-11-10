package com.dalimao.mytaxi.account.view;

/**
 * Created by TYZ on 2017/11/9.
 */

public interface IView {
    /**
     * 显示loading
     */
    void showLoading();
    /**
     *  显示错误
     */
    void showError(int Code, String msg);
}

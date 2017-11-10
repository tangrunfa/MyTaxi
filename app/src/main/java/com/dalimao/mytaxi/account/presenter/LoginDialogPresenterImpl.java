package com.dalimao.mytaxi.account.presenter;

import android.os.Handler;
import android.os.Message;

import com.dalimao.mytaxi.account.model.IAccountManager;
import com.dalimao.mytaxi.account.view.ILoginView;

import java.lang.ref.WeakReference;

/**
 * Created by TYZ on 2017/11/10.
 */

public class LoginDialogPresenterImpl implements ILoginDialogPresenter{
    private ILoginView view;
    private IAccountManager accountManager;



    /**
     * 接受消息并处理
     */
    private static class MyHandler extends Handler {
        WeakReference<LoginDialogPresenterImpl> refContext;
        public MyHandler(LoginDialogPresenterImpl context) {
            refContext = new WeakReference(context);
        }

        @Override
        public void handleMessage(Message msg) {

            LoginDialogPresenterImpl presenter = refContext.get();
            // 处理UI 变化
            switch (msg.what) {
                case IAccountManager.LOGIN_SUC:
                    // 登录成功
                    presenter.view.showLoginSuc();
                    break;
                case IAccountManager.PW_ERROR:
                    // 密码错误
                    presenter.view.showError(IAccountManager.PW_ERROR, "");
                    break;
                case IAccountManager.SERVER_FAIL:
                    // 服务器错误
                    presenter.view.showError(IAccountManager.SERVER_FAIL, "");
                    break;

            }
        }
    }
    public LoginDialogPresenterImpl(ILoginView view,
                                    IAccountManager accountManager) {
        this.view = view;
        this.accountManager = accountManager;
        accountManager.setHandler(new MyHandler(this));
    }


    @Override
    public void requestLogin(String phone, String password) {
        accountManager.login(phone, password);
    }
}

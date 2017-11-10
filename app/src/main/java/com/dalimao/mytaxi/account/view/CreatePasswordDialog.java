package com.dalimao.mytaxi.account.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.dalimao.mytaxi.MyTaxiApplication;
import com.dalimao.mytaxi.R;
import com.dalimao.mytaxi.account.model.AccountManagerImpl;
import com.dalimao.mytaxi.account.model.IAccountManager;
import com.dalimao.mytaxi.account.presenter.CreatePasswordDialogPresenterImpl;
import com.dalimao.mytaxi.account.presenter.ICreatePasswordDialogPresenter;
import com.dalimao.mytaxi.common.http.IHttpClient;
import com.dalimao.mytaxi.common.http.impl.OkHttpClientImpl;
import com.dalimao.mytaxi.common.storage.SharedPreferencesDao;
import com.dalimao.mytaxi.common.util.ToastUtil;


/**
 * 密码创建/修改
 * Created by liuguangli on 17/2/26.
 */

public class CreatePasswordDialog extends Dialog  implements  ICreatePasswordDialogView{

    private  static final String TAG = "CreatePasswordDialog";

    private TextView mTitle;
    private TextView mPhone;
    private EditText mPw;
    private EditText mPw1;
    private Button mBtnConfirm;
    private View mLoading;
    private TextView mTips;

    private String mPhoneStr;
    private ICreatePasswordDialogPresenter mPresenter;

    public CreatePasswordDialog(Context context, String phone) {
        this(context, R.style.Dialog);
        // 上一个页面传来的手机号
        mPhoneStr = phone;

        IHttpClient httpClient = new OkHttpClientImpl();
        SharedPreferencesDao dao =
                new SharedPreferencesDao(MyTaxiApplication.getInstance(),
                        SharedPreferencesDao.FILE_ACCOUNT);
        IAccountManager iAccountManager = new AccountManagerImpl(httpClient, dao);
        mPresenter=new CreatePasswordDialogPresenterImpl(this,iAccountManager);
    }

    public CreatePasswordDialog(Context context, int theme) {
        super(context, theme);

    }

    @Override
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = (LayoutInflater) getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View root = inflater.inflate(R.layout.dialog_create_pw, null);
        setContentView(root);
        initViews();
    }

    private void initViews() {


        mPhone = (TextView) findViewById(R.id.phone);
        mPw = (EditText) findViewById(R.id.pw);
        mPw1 = (EditText) findViewById(R.id.pw1);
        mBtnConfirm = (Button) findViewById(R.id.btn_confirm);
        mLoading = findViewById(R.id.loading);
        mTips = (TextView) findViewById(R.id.tips);
        mTitle = (TextView) findViewById(R.id.dialog_title);
        findViewById(R.id.close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        mBtnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submit();
            }
        });

        mPhone.setText(mPhoneStr);

    }

    @Override
    public void dismiss() {
        super.dismiss();

    }





    /**
     * 提交注册
     */
    private void submit() {
           String password = mPw.getText().toString();
            String password1 = mPw1.getText().toString();
            boolean check = mPresenter.checkPw(password, password1);
            if (check) {
                mPresenter.requestRegister(mPhoneStr, password1);
            }

    }

//    /**
//     * 检查密码输入
//     * @return
//     */
//    private boolean checkPassword() {
//        String password = mPw.getText().toString();
//        if (TextUtils.isEmpty(password)) {
//            mTips.setVisibility(View.VISIBLE);
//            mTips.setText(getContext().getString(R.string.password_is_null));
//            mTips.setTextColor(getContext().
//                    getResources().getColor(R.color.error_red));
//            return false;
//        }
//        if (!password.equals(mPw1.getText().toString())) {
//            mTips.setVisibility(View.VISIBLE);
//            mTips.setText(getContext()
//                    .getString(R.string.password_is_not_equal));
//            mTips.setTextColor(getContext()
//                    .getResources().getColor(R.color.error_red));
//            return false;
//        }
//        return true;
//    }




    public void showOrHideLoading(boolean show) {
        if (show) {
            mLoading.setVisibility(View.VISIBLE);
            mBtnConfirm.setVisibility(View.GONE);
        } else {
            mLoading.setVisibility(View.GONE);
            mBtnConfirm.setVisibility(View.VISIBLE);
        }

    }


    /**
     *  处理注册成功
     */
    public void showRegisterSuc() {

        mLoading.setVisibility(View.VISIBLE);
        mBtnConfirm.setVisibility(View.GONE);
        mTips.setVisibility(View.VISIBLE);
        mTips.setTextColor(getContext()
                .getResources()
                .getColor(R.color.color_text_normal));
        mTips.setText(getContext()
                .getString(R.string.register_suc_and_loging));
        // 请求网络，完成自动登录
        mPresenter.requestLogin(mPhoneStr, mPw.getText().toString());
    }

    public void showLoginSuc() {
        dismiss();
        ToastUtil.show(getContext(),
                getContext().getString(R.string.login_suc));
    }

    @Override
    public void showPasswordNull() {
        mTips.setVisibility(View.VISIBLE);
        mTips.setText(getContext().getString(R.string.password_is_null));
        mTips.setTextColor(getContext().
                getResources().getColor(R.color.error_red));
    }

    @Override
    public void showPasswordNotEqual() {
        mTips.setVisibility(View.VISIBLE);
        mTips.setText(getContext()
                .getString(R.string.password_is_not_equal));
        mTips.setTextColor(getContext()
                .getResources().getColor(R.color.error_red));
    }


    public void showUserExist(boolean exist) {
        if (exist) {
            mTitle.setText(getContext().getString(R.string.modify_pw));
        } else {
            mTitle.setText(getContext().getString(R.string.create_pw));
        }
        mLoading.setVisibility(View.GONE);
        mTips.setVisibility(View.GONE);
    }


    public void showServerError() {
        mTips.setTextColor(getContext()
                .getResources().getColor(R.color.error_red));
        mTips.setText(getContext().getString(R.string.error_server));
    }

    @Override
    public void showLoading() {
        showOrHideLoading(true);
    }

    @Override
    public void showError(int code, String msg) {
        showOrHideLoading(false);
        switch (code) {
            case IAccountManager.PW_ERROR:
                showLoginFail();
                break;
            case IAccountManager.SERVER_FAIL:
                showServerError();
                break;
        }
    }
    private void showLoginFail() {
        dismiss();
        ToastUtil.show(getContext(),
                getContext().getString(R.string.error_server));
    }
}

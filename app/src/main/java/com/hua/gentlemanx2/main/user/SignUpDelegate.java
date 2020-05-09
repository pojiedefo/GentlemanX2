package com.hua.gentlemanx2.main.user;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hua.gentlemanx2.R;
import com.hua.gentlemanx2.app.GxLogger;
import com.hua.gentlemanx2.delegate.bottom.BottomItemDelegate;
import com.hua.gentlemanx2.launcher.ISignListener;
import com.hua.gentlemanx2.launcher.SignHandler;
import com.hua.gentlemanx2.net.RestClient;
import com.hua.gentlemanx2.net.callback.ISuccess;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class SignUpDelegate extends BottomItemDelegate {

    @BindView(R.id.edit_sign_up_name)
    TextInputEditText editSignUpName;
    @BindView(R.id.edit_sign_up_email)
    TextInputEditText editSignUpEmail;
    @BindView(R.id.edit_sign_up_phone)
    TextInputEditText editSignUpPhone;
    @BindView(R.id.edit_sign_up_password)
    TextInputEditText editSignUpPassword;
    @BindView(R.id.edit_sign_up_re_password)
    TextInputEditText editSignUpRePassword;
    @BindView(R.id.btn_sign_up)
    AppCompatButton btnSignUp;
    @BindView(R.id.tv_link_sign_in)
    AppCompatTextView tvLinkSignIn;
    Unbinder unbinder;

    private ISignListener mISignListener = null;

    @Override
    public Object setLayout() {
        return R.layout.delegate_sign_up;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView) {

    }

    @Override
    public void post(Runnable runnable) {

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof ISignListener) {
            mISignListener = (ISignListener) activity;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.btn_sign_up, R.id.tv_link_sign_in})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_sign_up:
                if (localChecker()) {
                    //本地校验完成 把数据提交给服务器
                    RestClient.builder()
                            .url("http://192.168.1.105:8080/gs_war/member")
                            .params("name", editSignUpName.getText().toString())
                            .params("email", editSignUpEmail.getText().toString())
                            .params("phone", editSignUpPhone.getText().toString())
                            .params("password", editSignUpPassword.getText().toString())
                            .success(new ISuccess() {
                                @Override
                                public void onSuccess(String response) {
                                    GxLogger.json("USER_PROFILE", response);
                                    SignHandler.onSignUp(response, mISignListener);
                                }
                            })
                            .build()
                            .post();
                }
                break;
            case R.id.tv_link_sign_in:
                getSupportDelegate().start(new SignInDelegate());
                break;
        }
    }

    private boolean localChecker() {
        final String name = editSignUpName.getText().toString();
        final String email = editSignUpEmail.getText().toString();
        final String phone = editSignUpPhone.getText().toString();
        final String password = editSignUpPassword.getText().toString();
        final String rePassword = editSignUpRePassword.getText().toString();
        boolean isPass = true;

        if (name.isEmpty()) {
            editSignUpName.setError("请输入姓名");
            isPass = false;
        } else {
            editSignUpName.setError(null);
        }

        if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editSignUpEmail.setError("错误的邮箱格式");
            isPass = false;
        } else {
            editSignUpEmail.setError(null);
        }

        if (phone.length() != 11) {
            editSignUpPhone.setError("手机号码错误");
            isPass = false;
        } else {
            editSignUpPhone.setError(null);
        }

        if (password.isEmpty() || password.length() < 6) {
            editSignUpPassword.setError("请填写至少6位数的密码");
            isPass = false;
        } else {
            editSignUpPassword.setError(null);
        }

        if (rePassword.isEmpty() || rePassword.length() < 6 || !rePassword.equals(password)) {
            editSignUpRePassword.setError("密码验证错误");
            isPass = false;
        } else {
            editSignUpRePassword.setError(null);
        }
        return isPass;
    }


}

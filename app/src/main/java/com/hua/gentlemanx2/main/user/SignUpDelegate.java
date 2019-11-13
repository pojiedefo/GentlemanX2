package com.hua.gentlemanx2.main.user;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.hua.gentlemanx2.R;
import com.hua.gentlemanx2.delegate.GxDelegate;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import me.yokeyword.fragmentation.ISupportFragment;

public class SignUpDelegate extends GxDelegate {

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
                break;
            case R.id.tv_link_sign_in:
                getSupportDelegate().start(new SignInDelegate(), ISupportFragment.SINGLETASK);
                break;
        }
    }

}

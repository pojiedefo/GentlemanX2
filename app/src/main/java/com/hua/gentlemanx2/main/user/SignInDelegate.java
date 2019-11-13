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

import com.hua.gentlemanx2.R;
import com.hua.gentlemanx2.delegate.GxDelegate;
import com.joanzapata.iconify.widget.IconTextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import me.yokeyword.fragmentation.ISupportFragment;

import static me.yokeyword.fragmentation.SupportHelper.findFragment;

public class SignInDelegate extends GxDelegate {
    @BindView(R.id.edit_sign_in_email)
    TextInputEditText editSignInEmail;
    @BindView(R.id.edit_sign_in_password)
    TextInputEditText editSignInPassword;
    @BindView(R.id.btn_sign_in)
    AppCompatButton btnSignIn;
    @BindView(R.id.tv_link_sign_up)
    AppCompatTextView tvLinkSignUp;
    @BindView(R.id.icon_sign_in_wechat)
    IconTextView iconSignInWechat;
    Unbinder unbinder;

    @Override
    public Object setLayout() {
        return R.layout.delegte_sign_in;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView) {

    }

    @Override
    public void post(Runnable runnable) {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.btn_sign_in, R.id.tv_link_sign_up, R.id.icon_sign_in_wechat})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_sign_in:
                break;
            case R.id.tv_link_sign_up:
                //回到注册页面,以任务栈的方式开启SignUpDelegate
                getSupportDelegate().start(new SignUpDelegate(), ISupportFragment.SINGLETASK);
                break;
            case R.id.icon_sign_in_wechat:
                break;
        }
    }
}

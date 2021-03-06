package com.hua.gentlemanx2.main.user;

import android.app.Activity;
import android.content.SharedPreferences;
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
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hua.gentlemanx2.R;
import com.hua.gentlemanx2.app.Constants;
import com.hua.gentlemanx2.app.GxLogger;
import com.hua.gentlemanx2.app.MyApplication;
import com.hua.gentlemanx2.delegate.GxDelegate;
import com.hua.gentlemanx2.launcher.ISignListener;
import com.hua.gentlemanx2.launcher.SignHandler;
import com.hua.gentlemanx2.net.RestClient;
import com.hua.gentlemanx2.net.callback.ISuccess;
import com.joanzapata.iconify.widget.IconTextView;
import com.tencent.mm.opensdk.modelmsg.SendAuth;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import me.yokeyword.fragmentation.ISupportFragment;

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

    private ISignListener mISignListener = null;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof ISignListener) {
            mISignListener = (ISignListener) activity;
        }
    }

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
                if (checkForm()) {
                    loginGx();
                }
                break;
            case R.id.tv_link_sign_up:
                //回到注册页面,以任务栈的方式开启SignUpDelegate
                getSupportDelegate().start(new SignUpDelegate(), ISupportFragment.SINGLETASK);
                break;
            case R.id.icon_sign_in_wechat:
                //微信登录
                wechatSignIn();
                break;
        }
    }

    private void wechatSignIn() {
        if (!MyApplication.mWxApi.isWXAppInstalled()) {
            Toast.makeText(getContext(), "您的设备未安装微信客户端", Toast.LENGTH_SHORT).show();
        } else {
            final SendAuth.Req req = new SendAuth.Req();
            req.scope = "snsapi_userinfo";
            req.state = "app_wechat";
            MyApplication.mWxApi.sendReq(req);
        }
    }

    //登录到Gx
    private void loginGx() {
        RestClient.builder()
                .url(Constants.URL + "member/login")
                .params("email", editSignInEmail.getText().toString())
                .params("password", editSignInPassword.getText().toString())
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
                        GxLogger.json("USER_PROFILE", response);
                        SignHandler.onSignIn(response, mISignListener);
                        initUserData(response);
                    }
                })
                .build()
                .post();
    }

    public static String name;
    public static String image;
    public static int member_id;

    private void initUserData(String response) {
        final JSONObject profileJson = JSON.parseObject(response).getJSONObject("data");
        name = profileJson.getString("name");
        image = profileJson.getString("name");
        member_id = profileJson.getInteger("member_id");
        SharedPreferences.Editor localEditor = getSupportDelegate().getActivity().
                getSharedPreferences("user", 0).edit();
        localEditor.putInt("member_id", member_id);//用户id
        localEditor.putString("name", name);//用户名
        localEditor.putString("image", image);//用户头像
        localEditor.apply();
    }

    //本地校验
    private boolean checkForm() {
        final String email = editSignInEmail.getText().toString();
        final String password = editSignInPassword.getText().toString();

        boolean isPass = true;

        if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editSignInEmail.setError("错误的邮箱格式");
            isPass = false;
        } else {
            editSignInEmail.setError(null);
        }

        if (password.isEmpty() || password.length() < 6) {
            editSignInPassword.setError("请填写至少6位数密码");
            isPass = false;
        } else {
            editSignInPassword.setError(null);
        }
        return isPass;
    }
}

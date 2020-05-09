package com.hua.gentlemanx2.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.hua.gentlemanx2.delegate.GxDelegate;
import com.hua.gentlemanx2.launcher.ILauncherListener;
import com.hua.gentlemanx2.launcher.ISignListener;
import com.hua.gentlemanx2.launcher.LauncherDelegate;
import com.hua.gentlemanx2.launcher.OnLauncherFinishTag;
import com.hua.gentlemanx2.main.IntroduceBottomDelegate;
import com.hua.gentlemanx2.main.user.SignInDelegate;

public class ExampleActivity extends ProxyActivity implements ISignListener, ILauncherListener {
    @Override
    public GxDelegate setRootDelegate() {
        return new LauncherDelegate();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        final ActionBar actionBar = getSupportActionBar();
////        if (actionBar != null) {
////            actionBar.hide();
////        }
////        StatusBarCompat.translucentStatusBar(this, true);
    }

    @Override
    public void post(Runnable runnable) {

    }

    @Override
    public void onLauncherFinish(OnLauncherFinishTag tag) {
        switch (tag) {
            case SIGNED:
                getSupportDelegate().startWithPop(new IntroduceBottomDelegate());
                break;
            case NOT_SIGNED:
                getSupportDelegate().startWithPop(new SignInDelegate());
                break;
            default:
                break;
        }
    }

    @Override
    public void onSignInSuccess() {
        Toast.makeText(this, "登录成功", Toast.LENGTH_LONG).show();
        getSupportDelegate().startWithPop(new IntroduceBottomDelegate());
    }

    @Override
    public void onSignUpSuccess() {
        Toast.makeText(this, "注册成功", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onSignUpFailure(String msg) {
        Toast.makeText(this, "注册失败:" + msg, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onSignInFailure(String msg) {
        Toast.makeText(this, "登录失败:" + msg, Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SharedPreferences.Editor localEditor = this.getSharedPreferences("user", 0).edit();
        localEditor.remove("name");
        localEditor.apply();
    }
}

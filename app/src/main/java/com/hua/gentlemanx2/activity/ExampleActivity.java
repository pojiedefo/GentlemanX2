package com.hua.gentlemanx2.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;

import com.hua.gentlemanx2.delegate.GxDelegate;
import com.hua.gentlemanx2.main.IntroduceBottomDelegate;

import qiu.niorgai.StatusBarCompat;

public class ExampleActivity extends ProxyActivity {
    @Override
    public GxDelegate setRootDelegate() {
        return new IntroduceBottomDelegate();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        StatusBarCompat.translucentStatusBar(this, true);
    }

    @Override
    public void post(Runnable runnable) {

    }
}

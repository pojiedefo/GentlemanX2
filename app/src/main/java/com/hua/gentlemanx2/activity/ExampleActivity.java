package com.hua.gentlemanx2.activity;

import com.hua.gentlemanx2.delegate.ExampleDelegate;
import com.hua.gentlemanx2.delegate.GxDelegate;

public class ExampleActivity extends ProxyActivity{
    @Override
    public GxDelegate setRootDelegate() {
        return new ExampleDelegate();
    }

    @Override
    public void post(Runnable runnable) {

    }
}

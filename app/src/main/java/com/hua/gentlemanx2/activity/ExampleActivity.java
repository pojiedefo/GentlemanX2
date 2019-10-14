package com.hua.gentlemanx2.activity;

import com.hua.gentlemanx2.delegate.GxDelegate;
import com.hua.gentlemanx2.main.IntroduceBottomDelegate;

public class ExampleActivity extends ProxyActivity{
    @Override
    public GxDelegate setRootDelegate() {
        return new IntroduceBottomDelegate();
    }

    @Override
    public void post(Runnable runnable) {

    }
}

package com.hua.gentlemanx2.app;

import android.app.Application;

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Gx.init(this)
                .withApiHost("")
                .configure();

    }
}
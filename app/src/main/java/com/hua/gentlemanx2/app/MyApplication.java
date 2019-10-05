package com.hua.gentlemanx2.app;

import android.app.Application;

import com.joanzapata.iconify.fonts.FontAwesomeModule;

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Gx.init(this)
                .withApiHost("http://127.0.0.1/")
                .withIcon(new FontAwesomeModule())
                .configure();

    }
}

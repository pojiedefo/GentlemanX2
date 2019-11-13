package com.hua.gentlemanx2.app;

import android.app.Application;

import com.hua.gentlemanx2.icon.FontIntroduceMudule;
import com.joanzapata.iconify.fonts.FontAwesomeModule;

import me.yokeyword.fragmentation.BuildConfig;
import me.yokeyword.fragmentation.Fragmentation;
import me.yokeyword.fragmentation.SupportHelper;

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Gx.init(this)
                .withApiHost("http://127.0.0.1/")
                .withIcon(new FontAwesomeModule())
                .withIcon(new FontIntroduceMudule())
                .configure();
    }
}

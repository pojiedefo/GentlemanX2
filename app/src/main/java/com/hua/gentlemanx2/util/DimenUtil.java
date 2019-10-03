package com.hua.gentlemanx2.util;

import android.content.res.Resources;
import android.util.DisplayMetrics;

import com.hua.gentlemanx2.app.Gx;

public final class DimenUtil {

    public static int getScreenWidth() {
        final Resources resources = Gx.getApplicationContext().getResources();
        final DisplayMetrics dm = resources.getDisplayMetrics();
        return dm.widthPixels;
    }

    public static int getScreenHeight() {
        final Resources resources = Gx.getApplicationContext().getResources();
        final DisplayMetrics dm = resources.getDisplayMetrics();
        return dm.heightPixels;
    }




}

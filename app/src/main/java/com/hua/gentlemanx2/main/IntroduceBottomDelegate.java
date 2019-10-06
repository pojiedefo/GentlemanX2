package com.hua.gentlemanx2.main;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.hua.gentlemanx2.delegate.BaseDelegate;
import com.hua.gentlemanx2.delegate.bottom.BaseBottomDelegate;
import com.hua.gentlemanx2.delegate.bottom.BottomItemDelegate;
import com.hua.gentlemanx2.delegate.bottom.BottomTabBean;
import com.hua.gentlemanx2.delegate.bottom.ItemBuilder;
import com.hua.gentlemanx2.main.compass.CompassDelegate;
import com.hua.gentlemanx2.main.index.IndexDelegate;
import com.hua.gentlemanx2.main.sort.SortDelegate;
import com.hua.gentlemanx2.main.user.UserDelegate;

import java.util.LinkedHashMap;

public class IntroduceBottomDelegate extends BaseBottomDelegate {

    @Override
    public LinkedHashMap<BottomTabBean, BottomItemDelegate> setItems(ItemBuilder builder) {
        final LinkedHashMap<BottomTabBean, BottomItemDelegate> items = new LinkedHashMap<>();
        items.put(new BottomTabBean("{fa-home}","首页"),new IndexDelegate());
        items.put(new BottomTabBean("{fa-sort}","分类"),new SortDelegate());
        items.put(new BottomTabBean("{fa-compass}","发现"),new CompassDelegate());
        items.put(new BottomTabBean("{fa-user}","我的"),new UserDelegate());
        return builder.addItems(items).build();
    }

    @Override
    public int setIndexDelegate() {
        return 0;
    }

    @Override
    public int setClickedColor() {
        return Color.parseColor("#87CEFA");
    }

    @Override
    public void post(Runnable runnable) {

    }
}

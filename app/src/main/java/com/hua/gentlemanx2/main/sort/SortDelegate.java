package com.hua.gentlemanx2.main.sort;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.ContentFrameLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hua.gentlemanx2.R;
import com.hua.gentlemanx2.delegate.bottom.BottomItemDelegate;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class SortDelegate extends BottomItemDelegate {
    @BindView(R.id.vertical_list_container)
    ContentFrameLayout mLeftList;
    @BindView(R.id.sort_content_container)
    ContentFrameLayout mRightList;
    Unbinder unbinder;

    @Override
    public Object setLayout() {
        return R.layout.delegate_sort;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView) {

    }

    @Override
    public void post(Runnable runnable) {

    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        final SortLeftListDelegate leftDelegate = new SortLeftListDelegate();
        getSupportDelegate().loadRootFragment(R.id.vertical_list_container,leftDelegate);
        //设置右边第一个分类显示
        getSupportDelegate().loadRootFragment(R.id.sort_content_container,ContentDelegate.newInstance(1));
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
}

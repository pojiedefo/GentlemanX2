package com.hua.gentlemanx2.main.index;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hua.gentlemanx2.R;
import com.hua.gentlemanx2.delegate.bottom.BottomItemDelegate;
import com.hua.gentlemanx2.main.IntroduceBottomDelegate;
import com.hua.gentlemanx2.main.index.entity.IndexDataConverter;
import com.hua.gentlemanx2.net.RestClient;
import com.hua.gentlemanx2.net.callback.ISuccess;
import com.hua.gentlemanx2.ui.decoration.BaseDecoration;
import com.hua.gentlemanx2.ui.refresh.RefreshHandler;
import com.joanzapata.iconify.widget.IconTextView;

import butterknife.BindView;
import butterknife.Unbinder;

public class IndexDelegate extends BottomItemDelegate {
    @BindView(R.id.rv_index)
    RecyclerView mRecyclerView;
    @BindView(R.id.srl_index)
    SwipeRefreshLayout mRefreshLayout;
    @BindView(R.id.icon_index_scan)
    IconTextView mIconScan;
    @BindView(R.id.et_search_view)
    AppCompatEditText mSearchView;
    @BindView(R.id.icon_index_message)
    IconTextView mIconMessage;
    @BindView(R.id.tb_index)
    Toolbar mToolbar;
    Unbinder unbinder;

    private RefreshHandler mRefreshHandler;

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView) {
        mRefreshHandler = RefreshHandler.create(mRefreshLayout, mRecyclerView, new IndexDataConverter());
    }

    @Override
    public Object setLayout() {
        return R.layout.delegate_index;
    }

    private void initRefreshLayout() {
        mRefreshLayout.setColorSchemeColors(
                getResources().getColor(android.R.color.holo_blue_bright),
                getResources().getColor(android.R.color.holo_orange_light),
                getResources().getColor(android.R.color.holo_red_light)
        );
        mRefreshLayout.setProgressViewOffset(true, 120, 300);
    }

    private void initRecyclerView() {
        final GridLayoutManager manager = new GridLayoutManager(getContext(), 4);
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.addItemDecoration
                (BaseDecoration.create(ContextCompat.getColor(getContext(), R.color.app_background0), 5));
//        final IntroduceBottomDelegate introduceBottomDelegate = getParentDelegate();
////        mRecyclerView.addOnItemTouchListener(IndexItemClickListener.create(introduceBottomDelegate));
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        initRefreshLayout();
        initRecyclerView();
        mRefreshHandler.firstPage("http://192.168.1.105:8080/Gx/index.json");
    }

    @Override
    public void post(Runnable runnable) {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}

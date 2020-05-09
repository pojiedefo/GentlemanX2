package com.hua.gentlemanx2.main.sort;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.hua.gentlemanx2.R;
import com.hua.gentlemanx2.app.Constants;
import com.hua.gentlemanx2.delegate.GxDelegate;
import com.hua.gentlemanx2.main.index.details.GoodsDetailDelegate;
import com.hua.gentlemanx2.net.RestClient;
import com.hua.gentlemanx2.net.callback.ISuccess;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class ContentDelegate extends GxDelegate {

    private static final String ARG_CONTENT_ID = "CONTENT_ID";
    private int mContentId = -1;
    private List<SectionContentItemEntity> mData = null;

    @BindView(R.id.rv_list_content)
    RecyclerView mRecyclerView;

    private List<SectionContentItemEntity> rightData = new ArrayList<>();
    private ContentRecyclerAdapter rightAdapter;

    @Override
    public Object setLayout() {
        return R.layout.delegate_list_content;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final Bundle args = getArguments();
        if (args != null) {
            mContentId = args.getInt(ARG_CONTENT_ID);
        }
    }

    public static ContentDelegate newInstance(int contentId) {
        final Bundle args = new Bundle();
        args.putInt(ARG_CONTENT_ID, contentId);
        final ContentDelegate delegate = new ContentDelegate();
        delegate.setArguments(args);
        return delegate;
    }

    private int goods_id;
    private String name;
    private String big;
    private SectionContentItemEntity entity;

    private void initData(int contentId) {
        RestClient.builder()
                .url(Constants.URL + "goods/cat/" + contentId)
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
                        JSONArray data = JSON.parseObject(response).getJSONArray("data");
                        int size = data.size();
                        for (int i = 0; i < size; i++) {
                            goods_id = data.getJSONObject(i).getInteger("goods_id");
                            name = data.getJSONObject(i).getString("name");
                            big = data.getJSONObject(i).getString("big");
                            entity = new SectionContentItemEntity();
                            entity.setGoodsName(name);
                            entity.setGoodsId(goods_id);
                            entity.setGoodsThumb(big);
                            rightData.add(entity);
                        }
                        rightAdapter.notifyDataSetChanged();
                    }
                })
                .build()
                .get();
    }

    private GxDelegate delegate;
    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView) {
        final GridLayoutManager manager = new GridLayoutManager(getContext(), 2);
        mRecyclerView.setLayoutManager(manager);
        rightAdapter = new ContentRecyclerAdapter(getActivity(), rightData);
        mRecyclerView.setAdapter(rightAdapter);
        rightAdapter.setOnItemClickListener(new OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, SectionContentItemEntity entity) {
                toGoodsDetailDelegate(view, entity);
            }
        });
        initData(mContentId);
    }

    private int mGoodsId;

    private void toGoodsDetailDelegate(View view, SectionContentItemEntity entity) {
        mGoodsId = entity.getGoodsId();
        final GoodsDetailDelegate delegate = GoodsDetailDelegate.create(mGoodsId);
        getParentDelegate().getParentDelegate().getSupportDelegate().start(delegate);
    }

    @Override
    public void post(Runnable runnable) {

    }

}

package com.hua.gentlemanx2.main.index;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.hua.gentlemanx2.R;
import com.hua.gentlemanx2.app.Constants;
import com.hua.gentlemanx2.delegate.GxDelegate;
import com.hua.gentlemanx2.main.IntroduceBottomDelegate;
import com.hua.gentlemanx2.main.user.MyCollectionAdapter;
import com.hua.gentlemanx2.main.user.ProductEntity;
import com.hua.gentlemanx2.net.RestClient;
import com.hua.gentlemanx2.net.callback.ISuccess;
import com.joanzapata.iconify.widget.IconTextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


public class SearchGoodsDelegate extends GxDelegate {

    @BindView(R.id.title_back)
    IconTextView titleBack;
    @BindView(R.id.common_title)
    FrameLayout commonTitle;
    @BindView(R.id.product_list)
    RecyclerView productList;
    @BindView(R.id.favo_empty_tv)
    TextView favoEmptyTv;
    @BindView(R.id.favorite_empty)
    LinearLayout favoriteEmpty;
    Unbinder unbinder;

    private MyCollectionAdapter adapter;
    private List<ProductEntity> datalist = new ArrayList<ProductEntity>();

    @Override
    public Object setLayout() {
        return R.layout.delegate_search_goods;
    }

    //搜索功能
    private void searchInput(String input) {
        RestClient.builder()
                .url(Constants.URL + "goods/find")
                .params("input", input)
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
                        String status = JSON.parseObject(response).getString("status");
                        if (status.equals("0")) {
                            //有此产品
                            initData(response);
                        } else {
                            //没有此产品
                            Toast.makeText(getContext(), "不存在此产品", Toast.LENGTH_LONG).show();
                        }
                    }
                })
                .build()
                .post();
    }

    private int goods_id;
    private String name;
    private String description;
    private String big;
    private ProductEntity entity;

    private void initData(String response) {
        JSONArray data = JSON.parseObject(response).getJSONArray("data");
        int size = data.size();
        for (int i = 0; i < size; i++) {
            goods_id = data.getJSONObject(i).getInteger("goods_id");
            name = data.getJSONObject(i).getString("name");
            description = data.getJSONObject(i).getString("description");
            big = data.getJSONObject(i).getString("big");
            entity = new ProductEntity();
            entity.setName(name);
            entity.setDescription(description);
            entity.setBig(big);
            entity.setGoods_id(goods_id);
            datalist.add(entity);
        }
        Log.e("huage666", datalist.toString());
        if (size == 0) {
            productList.setVisibility(View.GONE);
            favoriteEmpty.setVisibility(View.VISIBLE);
        } else {
            favoriteEmpty.setVisibility(View.GONE);
            productList.setVisibility(View.VISIBLE);
            //更新数据
            adapterData(datalist);
            adapter.notifyDataSetChanged();
        }
    }

    private void adapterData(List<ProductEntity> datalist) {
        adapter = new MyCollectionAdapter(getContext(), datalist);
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
        productList.setLayoutManager(layoutManager);
        productList.setAdapter(adapter);
        adapter.setOnGoodsItemClickListener(new MyCollectionAdapter.OnGoodsItemClickListener() {
            @Override
            public void onClick(View view, ProductEntity entity) {

            }
        });
    }


    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView) {
        Bundle bundle = getArguments();
        searchInput(IndexDelegate.input);
    }

    @Override
    public void post(Runnable runnable) {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.title_back)
    public void onViewClicked() {
        getSupportDelegate().start(new IntroduceBottomDelegate());
    }
}

package com.hua.gentlemanx2.main.user;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.hua.gentlemanx2.R;
import com.hua.gentlemanx2.app.Constants;
import com.hua.gentlemanx2.delegate.GxDelegate;
import com.hua.gentlemanx2.main.IntroduceBottomDelegate;
import com.hua.gentlemanx2.main.index.details.GoodsDetailDelegate;
import com.hua.gentlemanx2.net.RestClient;
import com.hua.gentlemanx2.net.callback.ISuccess;
import com.joanzapata.iconify.widget.IconTextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class MyCollectionDelegate extends GxDelegate {
    @BindView(R.id.title_back)
    IconTextView titleBack;
    @BindView(R.id.common_title)
    FrameLayout commonTitle;
    @BindView(R.id.product_list)
    RecyclerView productList;
    @BindView(R.id.favo_empty_tv)
    TextView favoEmptyTv;
    @BindView(R.id.favorite_index_btn)
    Button favoriteIndexBtn;
    @BindView(R.id.favorite_empty)
    LinearLayout favoriteEmpty;
    Unbinder unbinder;

    private MyCollectionAdapter adapter;
    private List<ProductEntity> datalist = new ArrayList<ProductEntity>();

    @Override
    public Object setLayout() {
        return R.layout.delegate_my_favorite;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView) {
    }

    @Override
    public void post(Runnable runnable) {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences user = getSupportDelegate().getActivity().getSharedPreferences("user", 0);
        int member_id = user.getInt("member_id", 0);
        requestList(member_id);
    }

    //给菜单项添加事件
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId()) {
            case 0:
                remove(menuInfo.position - 1);
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    /**
     * 取消收藏
     *
     * @param position
     */
    private void remove(int position) {
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

    private void requestList(int member_id) {
        RestClient.builder()
                .url(Constants.URL + "like/member/" + member_id)
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
                        initData(response);
                    }
                })
                .build()
                .get();
    }


    private String name;
    private String description;
    private String big;
    private Integer goods_id;
    private ProductEntity entity;

    //初始化收藏产品的数据
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

    //适配数据
    private void adapterData(List<ProductEntity> datalist) {
        adapter = new MyCollectionAdapter(getContext(), datalist);
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
        productList.setLayoutManager(layoutManager);
        productList.setAdapter(adapter);
        adapter.setOnGoodsItemClickListener(new MyCollectionAdapter.OnGoodsItemClickListener() {
            @Override
            public void onClick(View view, ProductEntity entity) {
                toGoodsDetailDelegate(view, entity);
            }
        });
        productList.setOnCreateContextMenuListener(new View.OnCreateContextMenuListener() {
            @Override
            public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
                menu.setHeaderTitle("选择操作");
                menu.add(0, 0, 0, "移除收藏");
            }
        });
    }

    private int mGoodsId;

    private void toGoodsDetailDelegate(View view, ProductEntity entity) {
        mGoodsId = entity.getGoods_id();
        final GoodsDetailDelegate delegate = GoodsDetailDelegate.create(mGoodsId);
        getSupportDelegate().start(delegate);
    }

    @OnClick(R.id.title_back)
    public void onViewClicked() {
        getSupportDelegate().start(new IntroduceBottomDelegate());
    }
}

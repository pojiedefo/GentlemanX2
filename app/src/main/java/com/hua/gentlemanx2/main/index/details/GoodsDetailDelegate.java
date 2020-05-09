package com.hua.gentlemanx2.main.index.details;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.ContentFrameLayout;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bigkoo.convenientbanner.ConvenientBanner;
import com.hua.gentlemanx2.R;
import com.hua.gentlemanx2.app.Constants;
import com.hua.gentlemanx2.delegate.GxDelegate;
import com.hua.gentlemanx2.main.IntroduceBottomDelegate;
import com.hua.gentlemanx2.net.RestClient;
import com.hua.gentlemanx2.net.callback.ISuccess;
import com.hua.gentlemanx2.ui.banner.HolderCreator;
import com.joanzapata.iconify.widget.IconTextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import cn.sharesdk.onekeyshare.OnekeyShare;

public class GoodsDetailDelegate extends GxDelegate implements AppBarLayout.OnOffsetChangedListener {

    private static final String ARG_GOODS_ID = "ARG_GOODS_ID";
    @BindView(R.id.detail_banner)
    ConvenientBanner<String> mBanner;
    @BindView(R.id.frame_goods_info)
    ContentFrameLayout frameGoodsInfo;
    @BindView(R.id.icon_goods_back)
    IconTextView iconGoodsBack;
    @BindView(R.id.tv_detail_title_text)
    AppCompatTextView tvDetailTitleText;
    @BindView(R.id.goods_detail_toolbar)
    Toolbar goodsDetailToolbar;
    @BindView(R.id.collapsing_toolbar_detail)
    CollapsingToolbarLayout collapsingToolbarDetail;
    @BindView(R.id.atv_detail_data)
    AppCompatTextView atv_detail_data;
    @BindView(R.id.icon_favor)
    IconTextView iconFavor;
    @BindView(R.id.rl_favor)
    RelativeLayout rlFavor;
    @BindView(R.id.icon_forward)
    IconTextView icon_forward;
    @BindView(R.id.rl_shop_cart)
    RelativeLayout rlShopCart;
    @BindView(R.id.ll_bottom)
    LinearLayoutCompat llBottom;
    Unbinder unbinder;
    private int mGoodsId = -1;

    private String big;

    public static GoodsDetailDelegate create(int goodsId) {
        final Bundle args = new Bundle();
        args.putInt(ARG_GOODS_ID, goodsId);
        final GoodsDetailDelegate delegate = new GoodsDetailDelegate();
        delegate.setArguments(args);
        return delegate;
    }

    @Override
    public Object setLayout() {
        return R.layout.delegate_goods_detail;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final Bundle args = getArguments();
        if (args != null) {
            mGoodsId = args.getInt(ARG_GOODS_ID);
        }
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView) {
        initData();
    }

    private void initData() {
        RestClient.builder()
                .url(Constants.URL + "goods/" + mGoodsId)
                .loader(getContext())
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
                        final JSONObject data =
                                JSON.parseObject(response).getJSONObject("data");
                        initImages(data);
                        initProductDetail(data);
                    }
                })
                .build()
                .get();
    }

    //初始化产品详情的文字数据
    private void initProductDetail(JSONObject data) {
        String description = data.getString("description");
        atv_detail_data.setText(description);
    }

    //初始化产品详情的图片
    private void initImages(JSONObject data) {
        big = data.getString("big");
        final List<String> images = new ArrayList<>();
        images.add(big);
        mBanner
                .setPages(new HolderCreator(), images)
                .startTurning(3000)
                .setCanLoop(true);
    }

    @Override
    public void post(Runnable runnable) {

    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int i) {

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

    @OnClick({R.id.rl_favor, R.id.rl_shop_cart})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_favor:
                myFavorite();
                break;
            case R.id.rl_shop_cart:
                wechatForward(big);
                break;
        }
    }


    private int member_id;

    //收藏产品
    private void myFavorite() {
        SharedPreferences user = getSupportDelegate().getActivity().getSharedPreferences("user", 0);
        member_id = user.getInt("member_id", 0);
        Log.e("dsadsa", member_id + "");
        RestClient.builder()
                .url(Constants.URL + "like")
                .params("member_id", member_id)
                .params("goods_id", mGoodsId)
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
                        String msg = JSON.parseObject(response).getString("msg");
                        Toast.makeText(getContext(), msg, Toast.LENGTH_LONG).show();
                        //同步到收藏列表
                        refreshLike();
                    }
                })
                .build()
                .post();
    }

    private String name = null;
    private String description = null;

    //同步到收藏列表
    private void refreshLike() {
        RestClient.builder()
                .url(Constants.URL + "like/member/" + member_id)
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
                        JSONArray data = JSON.parseObject(response).getJSONArray("data");
                        int size = data.size();
                        for (int i = 0; i < size; i++) {
                            name = data.getJSONObject(i).getString("name");
                            description = data.getJSONObject(i).getString("description");
                            big = data.getJSONObject(i).getString("big");
                        }
                        Intent intent = new Intent();
                        intent.setAction("com.goods.favoritelist");
                        intent.putExtra("name", name);
                        intent.putExtra("description", description);
                        intent.putExtra("big", big);
                        getProxyActivity().sendBroadcast(intent);
                    }
                })
                .build()
                .get();
    }

    //微信转发功能的实现
    private void wechatForward(String big) {
        OnekeyShare onekeyShare = new OnekeyShare();
        onekeyShare.setText("good");
        onekeyShare.setTitle("6666");
        onekeyShare.setUrl("www.baidu.com");
        onekeyShare.show(getContext());
    }

    @OnClick(R.id.icon_goods_back)
    public void onViewClicked() {
        getSupportDelegate().start(new IntroduceBottomDelegate());
    }
}

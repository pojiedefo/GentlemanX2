package com.hua.gentlemanx2.main.index.adapter;

import android.support.v7.widget.GridLayoutManager;
import android.view.View;
import android.widget.ImageView;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.listener.OnItemClickListener;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.hua.gentlemanx2.R;
import com.hua.gentlemanx2.main.index.entity.DataConverter;
import com.hua.gentlemanx2.main.index.entity.IndexEntity;
import com.hua.gentlemanx2.main.index.entity.IndexFields;
import com.hua.gentlemanx2.main.index.entity.IndexItemType;
import com.hua.gentlemanx2.ui.banner.BannerCreator;

import java.util.ArrayList;
import java.util.List;

public class IndexRecyclerAdapter extends
        BaseMultiItemQuickAdapter<IndexEntity, IndexViewHolder>
        implements
        BaseQuickAdapter.SpanSizeLookup, OnItemClickListener {

    //确保初始化一次Banner，防止重复Item加载
    private boolean mIsInitBanner = false;

    private IndexRecyclerAdapter(List<IndexEntity> data) {
        super(data);
        init();
    }

    //设置图片加载策略
    private static final RequestOptions RECYCLER_OPTIONS =
            new RequestOptions()
                    .centerCrop()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .dontAnimate();

    public static IndexRecyclerAdapter create(List<IndexEntity> data) {
        return new IndexRecyclerAdapter(data);
    }

    public static IndexRecyclerAdapter create(DataConverter converter) {
        return new IndexRecyclerAdapter(converter.convert());
    }

    public void refresh(List<IndexEntity> data) {
        getData().clear();
        setNewData(data);
        notifyDataSetChanged();
    }

    private void init() {
        //设置不同item布局
        addItemType(IndexItemType.TEXT, R.layout.item_index_text);
        addItemType(IndexItemType.IMAGE, R.layout.item_index_image);
        addItemType(IndexItemType.TEXT_IMAGE, R.layout.item_index_image_text);
        addItemType(IndexItemType.BANNER, R.layout.item_index_banner);
        //设置宽度监听
        setSpanSizeLookup(this);
        openLoadAnimation();
        //多次执行动画
        isFirstOnly(false);
    }

    protected IndexViewHolder createBaseViewHolder(View view) {
        return IndexViewHolder.create(view);
    }

    @Override
    protected void convert(IndexViewHolder holder, IndexEntity entity) {
        final String text;
        final String imageUrl;
        final ArrayList<String> bannerImages;

        switch (holder.getItemViewType()) {
            case IndexItemType.TEXT:
                text = entity.getField(IndexFields.TEXT);
                holder.setText(R.id.text_single, text);
                break;
            case IndexItemType.IMAGE:
                imageUrl = entity.getField(IndexFields.IMAGE_URL);
                Glide.with(mContext)
                        .load(imageUrl)
                        .apply(RECYCLER_OPTIONS)
                        .into((ImageView) holder.getView(R.id.img_single));
                break;
            case IndexItemType.TEXT_IMAGE:
                text = entity.getField(IndexFields.TEXT);
                imageUrl = entity.getField(IndexFields.IMAGE_URL);
                Glide.with(mContext)
                        .load(imageUrl)
                        .apply(RECYCLER_OPTIONS)
                        .into((ImageView) holder.getView(R.id.img_multiple));
                holder.setText(R.id.tv_multiple, text);
                break;
            case IndexItemType.BANNER:
                if (!mIsInitBanner) {
                    bannerImages = entity.getField(IndexFields.BANNERS);
                    final ConvenientBanner<String> convenientBanner = holder.getView(R.id.banner_recycler_item);
                    BannerCreator.setDefault(convenientBanner, bannerImages, this);
                    mIsInitBanner = true;
                }
                break;
            default:
                break;
        }

    }

    @Override
    public int getSpanSize(GridLayoutManager gridLayoutManager, int position) {
        return getData().get(position).getField(IndexFields.SPAN_SIZE);
    }

    @Override
    public void onItemClick(int position) {

    }
}

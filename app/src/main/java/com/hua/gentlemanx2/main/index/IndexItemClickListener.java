package com.hua.gentlemanx2.main.index;

import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.SimpleClickListener;
import com.hua.gentlemanx2.delegate.GxDelegate;
import com.hua.gentlemanx2.main.entity.MultipleFields;
import com.hua.gentlemanx2.main.entity.MultipleItemEntity;
import com.hua.gentlemanx2.main.index.details.GoodsDetailDelegate;

public class IndexItemClickListener extends SimpleClickListener {

    private final GxDelegate DELEGATE;

    private IndexItemClickListener(GxDelegate delegate) {
        this.DELEGATE = delegate;
    }

    public static SimpleClickListener create(GxDelegate delegate) {
        return new IndexItemClickListener(delegate);
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        final MultipleItemEntity entity = (MultipleItemEntity) baseQuickAdapter.getData().get(position);
        final int goodsId = entity.getField(MultipleFields.ID);
        final GoodsDetailDelegate delegate = GoodsDetailDelegate.create(goodsId);
        DELEGATE.getSupportDelegate().start(delegate);
    }

    @Override
    public void onItemLongClick(BaseQuickAdapter adapter, View view, int position) {

    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {

    }

    @Override
    public void onItemChildLongClick(BaseQuickAdapter adapter, View view, int position) {

    }
}

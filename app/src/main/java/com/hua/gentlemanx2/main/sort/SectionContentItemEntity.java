package com.hua.gentlemanx2.main.sort;

import java.io.Serializable;

public class SectionContentItemEntity implements Serializable {

    private int mGoodsId = 0;
    private String mGoodsName = null;
    private String mGoodsThumb = null;

    public int getGoodsId() {
        return mGoodsId;
    }

    public void setGoodsId(int goodsId) {
        this.mGoodsId = goodsId;
    }

    public String getGoodsName() {
        return mGoodsName;
    }

    public void setGoodsName(String goodsName) {
        this.mGoodsName = goodsName;
    }

    public String getGoodsThumb() {
        return mGoodsThumb;
    }

    public void setGoodsThumb(String goodsThumb) {
        this.mGoodsThumb = goodsThumb;
    }
}

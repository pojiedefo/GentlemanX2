package com.hua.gentlemanx2.main.index.entity;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;

public final class IndexDataConverter extends DataConverter {
    @Override
    public ArrayList<IndexEntity> convert() {
        final JSONArray dataArray = JSON.parseObject(getJsonData()).getJSONArray("data");
        final int size = dataArray.size();
        for (int i = 0; i < size; i++) {
            final JSONObject data = dataArray.getJSONObject(i);
            final String imageUrl = data.getString("imageUrl");
            final String text = data.getString("text");
            final int spanSize = data.getInteger("spanSize");
            final int id = data.getInteger("goodsId");
            final JSONArray banners = data.getJSONArray("banners");

            final ArrayList<String> bannerImages = new ArrayList<>();
            int type = 0;
            if (imageUrl == null && text != null) {
                type = IndexItemType.TEXT;
            } else if (imageUrl != null && text == null) {
                type = IndexItemType.IMAGE;
            } else if (imageUrl != null && text != null) {
                type = IndexItemType.TEXT_IMAGE;
            } else if (banners != null) {
                type = IndexItemType.BANNER;
                //Banner的初始化
                final int bannerSize = banners.size();
                for (int j = 0; j < bannerSize; j++) {
                    final String banner = banners.getString(j);
                    bannerImages.add(banner);
                }
            }

            final IndexEntity entity = IndexEntity.builder()
                    .setField(IndexFields.ITEM_TYPE, type)
                    .setField(IndexFields.SPAN_SIZE, spanSize)
                    .setField(IndexFields.ID, id)
                    .setField(IndexFields.TEXT, text)
                    .setField(IndexFields.IMAGE_URL, imageUrl)
                    .setField(IndexFields.BANNERS, bannerImages)
                    .builder();
            ENTITIES.add(entity);
        }
        return ENTITIES;
    }
}

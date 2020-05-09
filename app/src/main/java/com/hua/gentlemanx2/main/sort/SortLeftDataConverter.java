package com.hua.gentlemanx2.main.sort;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hua.gentlemanx2.main.entity.MultipleItemEntity;
import com.hua.gentlemanx2.main.entity.DataConverter;
import com.hua.gentlemanx2.main.entity.ItemType;
import com.hua.gentlemanx2.main.entity.MultipleFields;

import java.util.ArrayList;

public class SortLeftDataConverter extends DataConverter {
    @Override
    public ArrayList<MultipleItemEntity> convert() {

        final ArrayList<MultipleItemEntity> datalist = new ArrayList<>();
        final JSONArray dataArray = JSON
                .parseObject(getJsonData())
                .getJSONArray("data");

        final int size = dataArray.size();
        for (int i = 0; i < size; i++) {
            final JSONObject data = dataArray.getJSONObject(i);
            final Integer id = data.getInteger("cat_id");
            final String name = data.getString("name");

            final MultipleItemEntity entity = MultipleItemEntity.builder()
                    .setField(MultipleFields.ITEM_TYPE, ItemType.VERTICAL_MENU_LIST)
                    .setField(MultipleFields.ID, id)
                    .setField(MultipleFields.TEXT, name)
                    .setField(MultipleFields.TAG, false)
                    .builder();

            datalist.add(entity);
            //设置第一个被选中
            datalist.get(0).setField(MultipleFields.TAG, true);
        }
        return datalist;
    }
}

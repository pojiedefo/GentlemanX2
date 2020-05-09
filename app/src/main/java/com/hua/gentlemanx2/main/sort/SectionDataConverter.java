package com.hua.gentlemanx2.main.sort;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SectionDataConverter {

    final List<SectionBean> convert(String json) {
        final List<SectionBean> dataList = new ArrayList<>();
        final JSONArray dataArray = JSON.parseObject(json).getJSONArray("data");
        final int size = dataArray.size();
        for (int i = 0; i < size; i++) {
            final JSONObject data = dataArray.getJSONObject(i);
            final Integer id = data.getInteger("goods_id");
            final String title = data.getString("name");

            //添加title
//            final SectionBean sectionTitleBean = new SectionBean(true, title);
//            sectionTitleBean.setId(id);
//            sectionTitleBean.setIsMore(true);
//            dataList.add(sectionTitleBean);

            //产品详细
            final String big = data.getString("big");
            //获取内容
            final SectionContentItemEntity itemEntity = new SectionContentItemEntity();
            itemEntity.setGoodsId(id);
            itemEntity.setGoodsName(title);
            itemEntity.setGoodsThumb(big);
            //添加内容
            dataList.add(new SectionBean(itemEntity));
        }
        //商品内容循环结束
        //Section循环结束
        return dataList;
    }
}

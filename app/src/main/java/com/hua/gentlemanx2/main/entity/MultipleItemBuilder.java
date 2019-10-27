package com.hua.gentlemanx2.main.entity;

import java.util.LinkedHashMap;

public class MultipleItemBuilder {

    private static final LinkedHashMap<Object, Object> FIELDS = new LinkedHashMap<>();

    public MultipleItemBuilder() {
        //先清除之前的数据
        FIELDS.clear();
    }

    public final MultipleItemBuilder setItemType(int itemType) {
        FIELDS.put(MultipleFields.ITEM_TYPE, itemType);
        return this;
    }

    public final MultipleItemBuilder setField(Object key, Object value) {
        FIELDS.put(key, value);
        return this;
    }

    public final MultipleItemBuilder setFields(LinkedHashMap<?, ?> map) {
        FIELDS.putAll(map);
        return this;
    }

    public final MultipleItemEntity builder() {
        return new MultipleItemEntity(FIELDS);
    }

}

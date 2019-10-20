package com.hua.gentlemanx2.main.index.entity;

import java.util.LinkedHashMap;

public class IndexEntityBuilder {

    private static final LinkedHashMap<Object,Object> FIELDS = new LinkedHashMap<>();

    public IndexEntityBuilder(){
        //先清除之前的数据
        FIELDS.clear();
    }

    public final IndexEntityBuilder setItemType(int itemType){
        FIELDS.put(IndexFields.ITEM_TYPE,itemType);
        return this;
    }

    public final IndexEntityBuilder setField(Object key ,Object value){
        FIELDS.put(key,value);
        return this;
    }

    public final IndexEntityBuilder setFields(LinkedHashMap<?,?> map){
        FIELDS.putAll(map);
        return this;
    }

    public final IndexEntity builder(){
        return new IndexEntity(FIELDS);
    }





}

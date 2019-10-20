package com.hua.gentlemanx2.main.index.entity;

import java.util.ArrayList;

/*
* Created by hua
* 2019/10/19 22:25
* 数据转换的基类
*/
public abstract class DataConverter {

    //用一个ArrayList 存储 IndexEntity
    protected final ArrayList<IndexEntity> ENTITIES = new ArrayList<>();

    private String mJsonData = null;//json字符串

    //抽象方法 convert
    public abstract ArrayList<IndexEntity> convert();

    public DataConverter setJsonData(String json){
        this.mJsonData = json;
        return this;
    }

    protected String getJsonData(){
        if (mJsonData == null||mJsonData.isEmpty()){
            throw new NullPointerException("data is null");
        }
        return mJsonData;
    }

}
package com.hua.gentlemanx2.main.user;

import java.io.Serializable;

public class ProductEntity implements Serializable {
    public int getGoods_id() {
        return goods_id;
    }

    @Override
    public String toString() {
        return "ProductEntity{" +
                "goods_id=" + goods_id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", big='" + big + '\'' +
                '}';
    }

    public void setGoods_id(int goods_id) {
        this.goods_id = goods_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBig() {
        return big;
    }

    public void setBig(String big) {
        this.big = big;
    }

    private int goods_id;
    private String name;
    private String description;
    private String big;
}

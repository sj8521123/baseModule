package com.sj.basemodule;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.chad.library.adapter.base.entity.SectionEntity;

public class OtherModel implements MultiItemEntity {
    //标题
    public static final int TEXT_HEAD = 11;
    public static final int TEXT_CONTENT = 2;
    //当前类型
    private int itemType;
    private String str;

    public OtherModel(int itemType, String str) {
        this.itemType = itemType;
        this.str = str;
    }

    @Override
    public int getItemType() {
        return itemType;
    }

    public void setItemType(int itemType) {
        this.itemType = itemType;
    }

    public String getStr() {
        return str;
    }

    public void setStr(String str) {
        this.str = str;
    }
}

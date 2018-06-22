package com.sj.basemodule;

import android.os.Parcel;
import android.os.Parcelable;

import org.litepal.crud.LitePalSupport;

import java.io.Serializable;

/**
 * Created by 13658 on 2018/6/5.
 */

public class User  extends LitePalSupport{

    private int id;
    private String name;
    private boolean isMale;

    public User(int id, String name, boolean isMale) {
        this.id = id;
        this.name = name;
        this.isMale = isMale;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name == null ? "" : name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isMale() {
        return isMale;
    }

    public void setMale(boolean male) {
        isMale = male;
    }
}

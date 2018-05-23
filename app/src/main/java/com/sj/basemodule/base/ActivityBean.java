package com.sj.basemodule.base;

import java.io.Serializable;

import butterknife.Unbinder;

/**
 * content:
 * author：sj
 * time: 2017/11/21 15:47
 * email：13658029734@163.com
 * phone:13658029734
 */

public class ActivityBean implements Serializable {
    private static final long serialVersionUID = 8711368828010083044L;
    private Unbinder unbinder;

    public void setUnbinder(Unbinder unbinder) {
        this.unbinder = unbinder;
    }

    public Unbinder getUnbinder() {
        return unbinder;
    }
}

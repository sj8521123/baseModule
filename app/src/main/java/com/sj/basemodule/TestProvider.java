package com.sj.basemodule;

import android.content.Context;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.facade.template.IProvider;

@Route(path = "/provider/testP")
public class TestProvider implements IProvider {
    @Override
    public void init(Context context) {
    }
    public String test(){
        return ("Hello Provider!");
    }
}
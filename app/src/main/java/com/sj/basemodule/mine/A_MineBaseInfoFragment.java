package com.sj.basemodule.mine;

import android.os.Bundle;

import com.sj.basemodule.R;

import basemodule.sj.com.basic.base.BasePageFragment;

public class A_MineBaseInfoFragment extends BasePageFragment {

    public static A_MineBaseInfoFragment newInstance() {
        A_MineBaseInfoFragment AMineBaseInfoFragment = new A_MineBaseInfoFragment();
        Bundle bundle = new Bundle();
        AMineBaseInfoFragment.setArguments(bundle);
        return AMineBaseInfoFragment;
    }



    @Override
    protected int initLayout() {
        return R.layout.fragment_mine_baseinfo;
    }

    @Override
    protected void initLayoutView() {
    }

    @Override
    public void fetchData() {

    }

}

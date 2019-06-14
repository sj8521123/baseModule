package com.sj.basemodule.mine;

import android.os.Bundle;

import com.sj.basemodule.R;

import basemodule.sj.com.basic.base.BasePageFragment;

public class B_MineBaseInfoFragment extends BasePageFragment {

    public static B_MineBaseInfoFragment newInstance() {
        B_MineBaseInfoFragment mineBaseInfoFragment = new B_MineBaseInfoFragment();
        Bundle bundle = new Bundle();
        mineBaseInfoFragment.setArguments(bundle);
        return mineBaseInfoFragment;
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

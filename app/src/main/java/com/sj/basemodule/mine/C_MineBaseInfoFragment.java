package com.sj.basemodule.mine;

import android.os.Bundle;

import com.sj.basemodule.R;
import com.sj.basemodule.base.BasePageFragment;

public class C_MineBaseInfoFragment extends BasePageFragment {

    public static C_MineBaseInfoFragment newInstance() {
        C_MineBaseInfoFragment mineBaseInfoFragment = new C_MineBaseInfoFragment();
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

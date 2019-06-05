package com.sj.basemodule.mine;

import android.os.Bundle;

import com.sj.basemodule.R;
import com.sj.basemodule.base.BasePageFragment;

/**
 * create by shijun@lixin360.com on 2019/5/23.
 */
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

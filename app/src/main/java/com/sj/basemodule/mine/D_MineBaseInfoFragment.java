package com.sj.basemodule.mine;

import android.os.Bundle;

import com.sj.basemodule.R;
import com.sj.basemodule.base.BasePageFragment;

/**
 * on 2019/5/23.
 */
public class D_MineBaseInfoFragment extends BasePageFragment {

    public static D_MineBaseInfoFragment newInstance() {
        D_MineBaseInfoFragment mineBaseInfoFragment = new D_MineBaseInfoFragment();
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

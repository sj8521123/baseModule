package com.sj.basemodule.mine;

import android.os.Bundle;

import com.sj.basemodule.R;
import com.sj.basemodule.base.BasePageFragment;

/**
 * create by shijun@lixin360.com on 2019/5/23.
 */
public class MineBaseInfoFragment4 extends BasePageFragment {

    public static MineBaseInfoFragment4 newInstance() {
        MineBaseInfoFragment4 mineBaseInfoFragment = new MineBaseInfoFragment4();
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

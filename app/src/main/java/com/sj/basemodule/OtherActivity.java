package com.sj.basemodule;

import android.content.Intent;

import com.sj.basemodule.base.BaseActivity;

import butterknife.OnClick;

public class OtherActivity extends BaseActivity {
    private static final String TAG = "OtherActivity";

    @Override
    protected void reConnect() {

    }

    @Override
    public int initLayout() {
        return R.layout.activity_other;
    }

    @Override
    public void initFromData() {

    }

    @Override
    public void initLayoutView() {

    }

    @Override
    public void initLocalData() {

    }

    @OnClick(R.id.cancel)
    public void onViewClicked() {
        startActivity(new Intent(OtherActivity.this, MainActivity.class));
    }
}

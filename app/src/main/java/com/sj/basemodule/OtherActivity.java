package com.sj.basemodule;

import android.content.Intent;
import android.widget.TextView;

import basemodule.sj.com.basic.base.BaseActivity;
import basemodule.sj.com.basic.util.ScreenUtil;
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
        TextView textView = findViewById(R.id.text);
        textView.setText("123");
        textView.setTextSize(ScreenUtil.px2sp(30));
    }

    @Override
    public void initLocalData() {

    }

    @OnClick(R.id.cancel)
    public void onViewClicked() {
        startActivity(new Intent(OtherActivity.this, MainActivity.class));
    }
}

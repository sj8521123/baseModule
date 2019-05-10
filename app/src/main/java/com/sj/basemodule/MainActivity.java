package com.sj.basemodule;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.sj.basemodule.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity {
    private static final String TAG = "MainActivity";
    @BindView(R.id.common_title)
    TextView commonTitle;

    @Override
    protected void reConnect() {

    }

    @Override
    public int initLayout() {
        return R.layout.activity_main;
    }

    @Override
    public void initFromData() {

    }

    @Override
    public void initLayoutView() {
        commonTitle.setText("hello");
    }

    @Override
    public void initLocalData() {
    }
}

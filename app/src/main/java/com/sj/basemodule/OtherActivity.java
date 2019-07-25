package com.sj.basemodule;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;

import basemodule.sj.com.basic.base.BaseActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class OtherActivity extends BaseActivity {
    private static final String TAG = "OtherActivity";
    @BindView(R.id.webView)
    LinearLayout webView;
    @BindView(R.id.btn)
    Button btn;

    @Override
    protected void reConnect() {
    }

    @Override
    public int initLayout() {
        return R.layout.fragment_loansign_layout;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btn)
    public void onViewClicked() {
        startActivity(new Intent(this, MainActivity.class));
    }
}

package com.sj.basemodule;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import com.sj.basemodule.base.BaseActivity;
import com.sj.basemodule.service.BookManagerService;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
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

    }

    @Override
    public void initLocalData() {
        Uri uri = Uri.parse("content://com.sj.basemodule.provider");
        getContentResolver().query(uri,null,null,null,null);
        getContentResolver().query(uri,null,null,null,null);
        getContentResolver().query(uri,null,null,null,null);
    }

    @OnClick({R.id.send, R.id.aidl})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.send:
                break;
            case R.id.aidl:
                break;
        }
    }
}

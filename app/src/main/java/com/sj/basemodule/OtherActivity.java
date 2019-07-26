package com.sj.basemodule;

import android.os.Bundle;
import android.widget.LinearLayout;

import com.hjq.toast.ToastUtils;

import androidx.recyclerview.widget.RecyclerView;
import basemodule.sj.com.basic.base.BaseActivity;
import basemodule.sj.com.basic.util.ToastUtil;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class OtherActivity extends BaseActivity {
    private static final String TAG = "OtherActivity";
    @BindView(R.id.webView)
    LinearLayout webView;

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

        ToastUtil.show("hello");
    }
}

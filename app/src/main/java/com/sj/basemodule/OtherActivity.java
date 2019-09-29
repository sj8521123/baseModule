package com.sj.basemodule;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import java.io.File;

import basemodule.sj.com.basic.base.BaseActivity;
import basemodule.sj.com.basic.util.ToastUtil;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class OtherActivity extends BaseActivity {
    private static final String TAG = "OtherActivity";
    @BindView(R.id.iv_CameraImg)
    ImageView ivCameraImg;

    private int test(int... a) {
        return a[0];
    }

    private static final String FILE_PATH = "/sdcard/syscamera.jpg";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
        String name = "张三";
        if (name.equals(null)) {
            Log.i(TAG, "onCreate: ");
        } else {
            Log.i(TAG, "onCreate: ");
        }

        Log.i(TAG, "onCreate: " + "12.345-6.A".split("."));
        Log.i(TAG, "onCreate: " + test(1, 2, 3, 4, 5));
    }

    @Override
    protected void reConnect() {
        ToastUtil.show("重新刷新");

    }

    @Override
    public int initLayout() {
        return R.layout.fragment_loansign_layout;
    }

    @Override
    public void initFromData() {
        TestInterface t = new TestInterface() {

            @Override
            public void run() {

            }

            @Override
            public void eat() {

            }
        };
        t.test("haha");
    }


    private void initRefresh() {
    }

    @Override
    public void initLayoutView() {
      /*  mDatas = new ArrayList<>();

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat format = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss", Locale.CHINA);
        String date = format.format(calendar.getTime());
        //GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        mRecycleView.setLayoutManager(new LinearLayoutManager(this));
        //mRecycleView.addItemDecoration(new GridSectionAverageGapItemDecoration(2, 2, 2, 2));
        mRecycleView.addItemDecoration(new StickyItemDecoration());


       *//* mAdapter = new TestAdapter(mDatas);
        mRecycleView.setAdapter(mAdapter);
*//*
        LinearSmoothScroller mScroller = new TopSmoothScroller(this);
        for (int i = 0; i < 50; i++) {
            if (i % 10 == 0) {
                OtherModel otherModel = new OtherModel(OtherModel.TEXT_HEAD, "head" + i);
                mDatas.add(otherModel);
            }
            OtherModel otherModel = new OtherModel(OtherModel.TEXT_CONTENT, "content" + i);
            mDatas.add(otherModel);
        }
        mAdapter = new TestAdapter(mDatas);
        mRecycleView.setAdapter(mAdapter);
        mRecycleView.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {

                mScroller.setTargetPosition(10);
                mRecycleView.getLayoutManager().startSmoothScroll(mScroller);

            }
        });*/
       /* container.setDataCallback(new StickyHeadContainer.DataCallback() {
            @Override
            public void onDataChange(int pos) {
                mStickyPosition = pos;
                OtherModel otherModel1 = mAdapter.getData().get(pos);
                head.setText(otherModel1.header);


            }
        });
        container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtil.show("点击了粘性头部：" + head.getText().toString());
                *//* Toast.makeText(MainActivity.this, "点击了粘性头部：" + tvStockName.getText(), Toast.LENGTH_SHORT).show();*//*
            }
        });*/
       /* StickyItemDecoration stickyItemDecoration = new StickyItemDecoration(container, RecyclerViewAdapter.TYPE_STICKY_HEAD);
        stickyItemDecoration.setOnStickyChangeListener(new OnStickyChangeListener() {
            @Override
            public void onScrollable(int offset) {
                container.scrollChild(offset);
                container.setVisibility(View.VISIBLE);
            }

            @Override
            public void onInVisible() {
                container.reset();
                container.setVisibility(View.INVISIBLE);
            }
        });
        mRecycleView.addItemDecoration(stickyItemDecoration);*/

    }

    @Override
    public void initLocalData() {

    }

    @OnClick({R.id.btn1, R.id.btn2})
    public void onViewClicked(View view) {
        Intent intent = null;
        switch (view.getId()) {
            case R.id.btn1:
                break;
            case R.id.btn2:
                break;
        }
    }
}


package com.sj.basemodule;


import android.os.Bundle;
import android.util.ArrayMap;
import android.util.SparseArray;
import android.util.SparseIntArray;

import com.hjq.toast.ToastUtils;
import com.sj.basemodule.adapter.TestAdapter2;

import java.lang.ref.PhantomReference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import basemodule.sj.com.basic.base.BaseActivity;
import butterknife.BindView;
import butterknife.ButterKnife;

public class OtherActivity extends BaseActivity {

    @BindView(R.id.mRecycleView1)
    RecyclerView mRecycleView1;
    @BindView(R.id.mRecycleView2)
    RecyclerView mRecycleView2;
    @BindView(R.id.mRecycleView3)
    RecyclerView mRecycleView3;
    @BindView(R.id.mRecycleView4)
    RecyclerView mRecycleView4;
    private List<String> mDatas1;
    private List<String> mDatas2;
    private List<String> mDatas3;
    private List<String> mDatas4;
    private TestAdapter2 testAdapter1;
    private TestAdapter2 testAdapter2;
    private TestAdapter2 testAdapter3;
    private TestAdapter2 testAdapter4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @Override
    protected void reConnect() {
        ToastUtils.show("重新刷新");

    }

    @Override
    public int initLayout() {
        return R.layout.fragment_loansign_layout;
    }

    @Override
    public void initFromData() {
        char c = '5';
        int num = Integer.valueOf(c);

        int num2 = c;

        int num1 = Character.getNumericValue(c);
    }


    private void initRefresh() {
    }

    @Override
    public void initLayoutView() {
        mDatas1 = new ArrayList<>();
        mDatas2 = new ArrayList<>();
        mDatas3 = new ArrayList<>();
        mDatas4 = new ArrayList<>();
        for (int i = 0; i < 1; i++) {
            mDatas1.add(i + "");
        }
        for (int i = 0; i < 2; i++) {
            mDatas2.add(i + "");
        }
        for (int i = 0; i < 3; i++) {
            mDatas3.add(i + "");
        }
        for (int i = 0; i < 4; i++) {
            mDatas4.add(i + "");
        }
        ReentrantLock reentrantLock = new ReentrantLock();

        reentrantLock.tryLock();
        reentrantLock.unlock();
        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        Condition condition = reentrantLock.newCondition();
        ArrayMap<String, String> map = new ArrayMap<>();
        SparseIntArray intArray = new SparseIntArray();
        SparseArray<String> stringSparseArray = new SparseArray<>();
        stringSparseArray.put(1, "hello");
        stringSparseArray.put(2, "world");
        stringSparseArray.put(3, "!");
        mRecycleView1.setLayoutManager(new GridLayoutManager(this, 4));
        mRecycleView3.setLayoutManager(new GridLayoutManager(this, 4));
        mRecycleView4.setLayoutManager(new GridLayoutManager(this, 4));
        testAdapter1 = new TestAdapter2(R.layout.adapter_item_content, mDatas1);
        testAdapter2 = new TestAdapter2(R.layout.adapter_item_content, mDatas2);
        testAdapter3 = new TestAdapter2(R.layout.adapter_item_content, mDatas3);
        testAdapter4 = new TestAdapter2(R.layout.adapter_item_content, mDatas4);
        mRecycleView1.setAdapter(testAdapter1);
        mRecycleView2.setAdapter(testAdapter2);
        mRecycleView3.setAdapter(testAdapter3);
        mRecycleView4.setAdapter(testAdapter4);
    }

    @Override
    public void initLocalData() {

    }

}


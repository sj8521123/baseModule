package com.sj.basemodule;


import android.os.Bundle;
import android.util.ArrayMap;
import android.util.Log;
import android.util.LruCache;
import android.util.SparseArray;
import android.util.SparseIntArray;

import com.hjq.toast.ToastUtils;
import com.sj.basemodule.adapter.TestAdapter2;
import com.sj.basemodule.net.request.LoginRequest;

import java.lang.ref.PhantomReference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import basemodule.sj.com.basic.base.BaseActivity;
import butterknife.BindView;
import butterknife.ButterKnife;

public class OtherActivity extends BaseActivity {
    private static final String TAG = "OtherActivity";
/*
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
    private TestAdapter2 testAdapter4;*/

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
        return R.layout.fragment_huaxia_introduce;
    }

    @Override
    public void initFromData() {
    }


    private void initRefresh() {
    }

    @Override
    public void initLayoutView() {
        Map<String, String> map = new HashMap<>();
        map.put(null, "22");
        map.put(null, "23");
        map.put(null, "24");

        map.put("1", "25");
        map.put("1", "26");
        map.put("1", "27");
        map.get(null);
        map.get("1");
        SparseArray<String> stringSparseArray = new SparseArray<>();
        stringSparseArray.put(1,"21");
        LruCache<Integer, Integer> lruCache = new LruCache<>(5);
        lruCache.put(1, 1);
        lruCache.put(2, 2);
        lruCache.put(3, 3);
        lruCache.put(4, 4);
        lruCache.put(5, 5);
        for (Map.Entry<Integer, Integer> entry : lruCache.snapshot().entrySet()) {
            Log.i(TAG, "key = " + entry.getKey() + "  value = " + entry.getValue());
        }
        int i = lruCache.get(1);
        Log.i(TAG, "使用1");
        Log.i(TAG, "超出存储设定容量后");
        lruCache.put(6, 6);
        for (Map.Entry<Integer, Integer> entry : lruCache.snapshot().entrySet()) {
            Log.i(TAG, "key = " + entry.getKey() + "  value = " + entry.getValue());
        }
    }

    @Override
    public void initLocalData() {

    }

}


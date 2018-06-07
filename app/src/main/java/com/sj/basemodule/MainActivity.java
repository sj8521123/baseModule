package com.sj.basemodule;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.sj.basemodule.base.BaseActivity;
import com.sj.basemodule.util.file.STGFileUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import hprose.client.HproseClient;
import hprose.util.concurrent.Action;

public class MainActivity extends BaseActivity {
    private static final String TAG = "MainActivity";
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    DataAdapter mAdapter;
    List<String> datas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i(TAG, "onStop: ");
    }

    @Override
    public int initLayout() {
        return R.layout.activity_main;
    }

    @Override
    public void initFromData() {
        User user = new User(0, "jake", true);
        ObjectOutputStream out = null;
        try {
            File f = new File(STGFileUtil.fileUtil.getAppIndependentStorage() + "cache.txt");
            if (f.isFile() && !f.exists()) {
                STGFileUtil.fileUtil.createFile(f);
            }
            out = new ObjectOutputStream(new FileOutputStream(f));
            out.writeObject(user);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void initLayoutView() {
        datas = new ArrayList<>();
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        mAdapter = new DataAdapter(R.layout.activity_data_item, datas);
        mAdapter.openLoadAnimation();
        recyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent i = new Intent(MainActivity.this, Other.class);
                startActivity(i);
            }
        });
        refreshLayout.setDragRate(0.5f);
       /* refreshLayout.setEnablePureScrollMode(true);
        refreshLayout.setEnableOverScrollDrag(true);*/
        refreshLayout.setEnableFooterFollowWhenLoadFinished(true);
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                mAdapter.notifyDataSetChanged();
                refreshlayout.finishRefresh(2000);//传入false表示刷新失败
                refreshLayout.setNoMoreData(false);
            }
        });
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshlayout) {
                refreshlayout.finishLoadMore(2000);//传入false表示加载失败
                refreshLayout.finishLoadMoreWithNoMoreData();

            }
        });
        refreshLayout.autoRefresh();
    }

    @Override
    public void initLocalData() {
        for (int i = 0; i < 20; i++) {
            datas.add(i + "");
        }
        mAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(TAG, "onPause: ");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy: ");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.i(TAG, "onSaveInstanceState: ");
    }
}

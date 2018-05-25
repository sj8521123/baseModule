package com.sj.basemodule;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.LoginFilter;
import android.util.Log;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.sj.basemodule.base.BaseActivity;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import hprose.client.HproseClient;
import hprose.util.concurrent.Action;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends BaseActivity {
    private static final String TAG = "MainActivity";
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    DataAdapter mAdapter;
    List<String> datas;

    @Override
    public int initLayout() {
        return R.layout.activity_main;
    }

    @Override
    public void initFromData() {

    }

    @Override
    public void initLayoutView(){
     /*   Observable<Long> observable1 = Observable.timer(2, TimeUnit.SECONDS);
        Observable<Long> observable2 = Observable.timer(5, TimeUnit.SECONDS);
        //数据是否匹对 匹对就发送一次  可多次
        Observable.zip(observable2, observable1, new BiFunction<Long, Long, Long>() {
            @Override
            public Long apply(@NonNull Long aLong, @NonNull Long aLong2) throws Exception {
                return aLong + aLong2;
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull Long aLong) {
                        Log.i(TAG, "onNextZip: " + aLong);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
        //按时间发送被观察者对象
        Observable<Integer> observable3 = Observable.just(3).delay(2, TimeUnit.SECONDS);
        Observable<Integer> observable4 = Observable.just(4).delay(5, TimeUnit.SECONDS);
        Observable.merge(observable4, observable3)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull Integer aLong) {
                        Log.i(TAG, "onNextMerge: " + aLong);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });*/
       /* datas = new ArrayList<>();
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        mAdapter = new DataAdapter(R.layout.activity_data_item, datas);
        mAdapter.openLoadAnimation();
        recyclerView.setAdapter(mAdapter);

        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                mAdapter.notifyDataSetChanged();
                refreshlayout.finishRefresh(5000*//*,false*//*);//传入false表示刷新失败
            }
        });
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshlayout) {
                mAdapter.notifyDataSetChanged();
                refreshlayout.finishLoadMore(2000*//*,false*//*);//传入false表示加载失败
            }
        });
        refreshLayout.autoRefresh();*/
    }

    @Override
    public void initLocalData() {
      /*  for (int i = 0; i < 100; i++) {
            datas.add(i + "");
        }
        mAdapter.notifyDataSetChanged();*/
    }

}

package com.sj.basemodule;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.sj.basemodule.base.BaseActivity;
import com.sj.basemodule.database.Book;

import org.litepal.LitePal;
import org.litepal.LitePalDB;
import org.litepal.tablemanager.Connector;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {
    private static final String TAG = "MainActivity";
    @BindView(R.id.database)
    Button database;
    @BindView(R.id.add)
    Button add;
    @BindView(R.id.delete)
    Button delete;
    @BindView(R.id.update)
    Button update;
    @BindView(R.id.find)
    Button find;
    @BindView(R.id.mRecycle)
    RecyclerView mRecycle;
    List<Book> books;
    DataAdapter mAdapter;
    private int count;
    private Handler mhandler = new Handler(Looper.myLooper()) {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 10:
                    Bundle bundle = msg.getData();
                    int count = bundle.getInt("data");
                    database.setText(String.valueOf(count));
                    break;
            }
        }
    };

    private void initRecycle() {
        books = new ArrayList<>();
        mRecycle.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        mAdapter = new DataAdapter(R.layout.activity_data_item, books);
        mRecycle.setAdapter(mAdapter);
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Message message = new Message();
                        Bundle bundle = new Bundle();
                        bundle.putInt("data", count++);
                        message.setData(bundle);
                        message.what = 10;
                        mhandler.sendMessage(message);
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    @OnClick({R.id.database, R.id.add, R.id.delete, R.id.update, R.id.find, R.id.databaseDefault, R.id.database0})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.database:
                //创建数据库
                Connector.getDatabase();
                /*LitePalDB litePalDB = new LitePalDB("newdb", 1);
                litePalDB.addClassName(Book.class.getName());
                litePalDB.setExternalStorage(true);
                LitePal.use(litePalDB);*/

              /*  LitePalDB litePalDB = LitePalDB.fromDefault("newdb" + (count++));
                LitePal.use(litePalDB);*/
                break;
            case R.id.add:
                Book book = new Book("sj", 15.36, 400, "Hello World!");
                book.save();
                initData();
                break;
            case R.id.delete:
                LitePal.deleteAll(Book.class, "id > ?", "4");
                initData();
                break;
            case R.id.update:
                Book book1 = LitePal.findFirst(Book.class);
                book1.setAuthor("张三");
                book1.save();
                initData();
                break;
            case R.id.find:
                initData();
                break;
            case R.id.databaseDefault:
                //检查本地有重复的库则直接引用，不会添加表以及storage，否则会创建
                LitePalDB litePalDB2 = new LitePalDB("newdb", 1);
                litePalDB2.addClassName(Book.class.getName());
                litePalDB2.setStorage("external");
                LitePal.use(litePalDB2);
                /*LitePal.useDefault();*/
                break;
            case R.id.database0:
                //检查本地有重复的库则直接引用，不会添加表以及storage，否则会创建
                LitePalDB litePalDB0 = LitePalDB.fromDefault("newdb0");
                LitePal.use(litePalDB0);
                break;
        }
    }

    private void initData() {
        mAdapter.getData().clear();
        mAdapter.getData().addAll(LitePal.findAll(Book.class));
        mAdapter.notifyDataSetChanged();
    }

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
        initRecycle();
    }

    @Override
    public void initLocalData() {
    }

}

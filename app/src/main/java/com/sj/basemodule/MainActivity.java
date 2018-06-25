package com.sj.basemodule;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.sj.basemodule.base.BaseActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {
    private static final String TAG = "MainActivity";

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
        //  Uri uri = Uri.parse("content://com.sj.basemodule.provider/book");
        Cursor cursor = getContentResolver().query(BookProvider.BOOK_CONTENT_URI, null, null, null, null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                Book book = new Book(cursor.getInt(0), cursor.getString(1));
                Log.i(TAG, "initLocalData: " + book);
            }
            cursor.close();
        }
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

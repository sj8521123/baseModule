package com.sj.basemodule;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.Manifest;

import com.sj.basemodule.base.BaseActivity;
import com.skateboard.zxinglib.CaptureActivity;
import com.tbruyelle.rxpermissions2.RxPermissions;


import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;


public class LoginActivity extends BaseActivity implements GestureDetector.OnGestureListener, GestureDetector.OnDoubleTapListener {
    Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void reConnect() {
        initLocalData();
    }

    @Override
    public int initLayout() {
        return R.layout.activity_login;
    }

    @Override
    public void initFromData() {
    }

    @Override
    public void initLayoutView() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Observable.just(1, 2, 3)
                        .flatMap(new Function<Integer, ObservableSource<Integer>>() {
                            @Override
                            public ObservableSource<Integer> apply(@NonNull Integer integer) throws Exception {
                                Log.i(TAG, "flatMap: " + Thread.currentThread().getId());
                                return Observable.just(integer + 2);
                            }
                        }).map(new Function<Integer, Integer>() {

                    @Override
                    public Integer apply(@NonNull Integer integer) throws Exception {
                        Log.i(TAG, "flatMap: " + Thread.currentThread().getId());
                        return integer + 2;
                    }
                }).observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Consumer<Integer>() {
                            @Override
                            public void accept(@NonNull Integer integer) throws Exception {
                                Log.i(TAG, "flatMap: " + Thread.currentThread().getId());
                                Log.i(TAG, "accept: " + integer);

                            }
                        });
            }
        }).start();
    }

    @Override
    public void initLocalData() {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1001 && resultCode == Activity.RESULT_OK) {
            String result = data.getStringExtra(CaptureActivity.KEY_DATA);
            Toast.makeText(this, "qrcode result is " + result, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.i(TAG, "onTouchEvent: ");
        return true;
    }

    @Override
    public boolean onDown(MotionEvent e) {
        Log.i(TAG, "onDown: ");
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {
        Log.i(TAG, "onShowPress: ");
    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        Log.i(TAG, "onSingleTapUp: ");
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        Log.i(TAG, "onScroll: ");
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {
        Log.i(TAG, "onLongPress: ");
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        Log.i(TAG, "onFling: ");
        return false;
    }

    @Override
    public boolean onSingleTapConfirmed(MotionEvent e) {
        Log.i(TAG, "onSingleTapConfirmed: ");
        return false;
    }

    @Override
    public boolean onDoubleTap(MotionEvent e) {
        Log.i(TAG, "onDoubleTap: ");
        return false;
    }

    @Override
    public boolean onDoubleTapEvent(MotionEvent e) {
        Log.i(TAG, "onDoubleTapEvent: ");
        return false;
    }

}

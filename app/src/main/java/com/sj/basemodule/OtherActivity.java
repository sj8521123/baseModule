package com.sj.basemodule;

import android.content.Intent;
import android.util.Log;
import android.widget.TextView;


import java.util.concurrent.TimeUnit;

import basemodule.sj.com.basic.base.BaseActivity;
import basemodule.sj.com.basic.util.ScreenUtil;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;

public class OtherActivity extends BaseActivity {
    private static final String TAG = "OtherActivity";

    @Override
    protected void reConnect() {

    }

    @Override
    public int initLayout() {
        return R.layout.activity_other;
    }

    @Override
    public void initFromData() {
    }

    @Override
    public void initLayoutView() {
        TextView textView = findViewById(R.id.text);
        textView.setText("123");
        textView.setTextSize(ScreenUtil.px2sp(30));

        Observable.create(new ObservableOnSubscribe<Integer>() {

            @Override
            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                e.onNext(1);
                e.onNext(2);
                e.onError(new NullPointerException());
                e.onNext(3);
            }
        }).retryWhen(new Function<Observable<Throwable>, ObservableSource<?>>() {
            @Override
            public ObservableSource<?> apply(Observable<Throwable> throwableObservable) throws Exception {
                return Observable.empty();
                //  return throwableObservable.delay(1,TimeUnit.SECONDS);
            }
        }).subscribe(new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Integer integer) {
                Log.i(TAG, "onNext: " + integer);
            }

            @Override
            public void onError(Throwable e) {
                Log.i(TAG, "onError: ");
            }

            @Override
            public void onComplete() {
                Log.i(TAG, "onComplete: ");
            }
        });

       /* Observable.just(1, 2, 3)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .repeatWhen(new Function<Observable<Object>, ObservableSource<?>>() {
                    @Override
                    public ObservableSource<?> apply(Observable<Object> objectObservable) throws Exception {
                        return objectObservable.zipWith(Observable.range(1, 3), new BiFunction<Object, Integer, Object>() {
                            @Override
                            public Object apply(Object o, Integer integer) throws Exception {
                                return o;
                            }
                        }).flatMap(new Function<Object, ObservableSource<?>>() {
                            @Override
                            public ObservableSource<?> apply(Object o) throws Exception {
                                return Observable.timer(5,TimeUnit.SECONDS);
                            }
                        });
                    }
                })
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Integer integer) {
                        Log.i(TAG, "onNext: " + integer);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i(TAG, "onError ");
                    }

                    @Override
                    public void onComplete() {
                        Log.i(TAG, "onComplete ");
                    }
                });*/
    }

    @Override
    public void initLocalData() {

    }

    @OnClick(R.id.cancel)
    public void onViewClicked() {
        startActivity(new Intent(OtherActivity.this, MainActivity.class));
    }
}

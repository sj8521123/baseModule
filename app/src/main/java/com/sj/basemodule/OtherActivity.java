package com.sj.basemodule;

import android.content.Intent;
import android.os.Build;

import androidx.annotation.RequiresApi;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.widget.LinearLayout;

import com.app.idea.net.common.DefaultObserver;
import com.just.agentweb.AgentWeb;
import com.just.agentweb.MiddlewareWebClientBase;

import java.util.concurrent.TimeUnit;

import basemodule.sj.com.basic.base.BaseActivity;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Function;

public class OtherActivity extends BaseActivity {
    private static final String TAG = "OtherActivity";
    private AgentWeb preAgentWeb;
    private MiddlewareWebClientBase mMiddleWareWebClient;
    private View view;

    @Override
    protected void reConnect() {

    }

    @Override
    public int initLayout() {
        return R.layout.activity_other;
    }

    @Override
    public void initFromData() {
        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                emitter.onComplete();
            }
        }).doOnComplete(new Action() {
            @Override
            public void run() throws Exception {
                Log.d(TAG, "触发重订阅");
            }
        }).retryWhen(new Function<Observable<Throwable>, ObservableSource<?>>() {
            private int n = 0;

            @Override
            public ObservableSource<?> apply(Observable<Throwable> throwableObservable) throws Exception {
                return throwableObservable.flatMap(new Function<Throwable, ObservableSource<?>>() {
                    @Override
                    public ObservableSource<?> apply(Throwable o) throws Exception {
                        if (n != 3) {
                            n++;
                            return Observable.timer(3, TimeUnit.SECONDS);
                        } else {
                            return Observable.empty();
                        }
                    }
                });

            }
        })

                .repeatWhen(new Function<Observable<Object>, ObservableSource<?>>() {
                    private int n = 0;

                    @Override
                    public ObservableSource<?> apply(Observable<Object> objectObservable) throws Exception {

                        return objectObservable.flatMap(new Function<Object, ObservableSource<?>>() {
                            @Override
                            public ObservableSource<?> apply(Object o) throws Exception {
                                if (n != 3) {
                                    n++;
                                    return Observable.timer(3, TimeUnit.SECONDS);
                                } else {
                                    return Observable.empty();
                                }
                            }
                        });
                    }
                }).subscribe(new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(String s) {
                Log.d(TAG, "onNext: " + s);
            }

            @Override
            public void onError(Throwable e) {
                Log.d(TAG, "onError: " + e);
            }

            @Override
            public void onComplete() {
                Log.d(TAG, "onComplete: ");
            }
        });
    }

    @Override
    public void initLayoutView() {
        ViewGroup content = this.getWindow().getDecorView().findViewById(android.R.id.content);
        AgentWeb.with(OtherActivity.this)
                .setAgentWebParent((LinearLayout) content.getChildAt(0).findViewById(R.id.webView), new LinearLayout.LayoutParams(-1, -1))
                .useDefaultIndicator()
                .useMiddlewareWebClient(getMiddlewareWebClient())
                .createAgentWeb()
                .ready()
                .go("file:///android_asset/test.html");
    }

    @Override
    public void initLocalData() {

    }

    @OnClick(R.id.cancel)
    public void onViewClicked() {
        startActivity(new Intent(OtherActivity.this, MainActivity.class));
    }

    protected MiddlewareWebClientBase getMiddlewareWebClient() {
        return this.mMiddleWareWebClient = new MiddlewareWebViewClient() {
            /**
             *
             * @param view
             * @param url
             * @return
             */
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                Log.e(TAG, "MiddlewareWebClientBase#shouldOverrideUrlLoading url:" + url);
                if (url.contains("www.baidu.com")) { // 拦截 url，不执行 DefaultWebClient#shouldOverrideUrlLoading
                    return false;
                }

                if (super.shouldOverrideUrlLoading(view, url)) { // 执行 DefaultWebClient#shouldOverrideUrlLoading
                    return true;
                }
                // do you work
                return false;
            }

            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                Log.e(TAG, "MiddlewareWebClientBase#shouldOverrideUrlLoading request url:" + request.getUrl().toString());
                return super.shouldOverrideUrlLoading(view, request);
            }
        };
    }
}

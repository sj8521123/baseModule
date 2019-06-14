package com.app.idea.net.common;

import android.app.Activity;
import android.util.Log;

import com.app.idea.dialog.DialogUtils;

import java.lang.ref.WeakReference;

import androidx.annotation.NonNull;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;

/**
 * 请求时的加载进度框
 * 请求时显示
 * 终止时停止
 */
public class ProgressUtils {
    private static final String TAG = "ProgressUtils";

    public static <T> ObservableTransformer<T, T> applyProgressBar(
            @NonNull final Activity activity, String msg) {
        final WeakReference<Activity> activityWeakReference = new WeakReference<>(activity);
        final DialogUtils dialogUtils = new DialogUtils();
        return new ObservableTransformer<T, T>() {
            @Override
            public ObservableSource<T> apply(Observable<T> upstream) {
                //准备时
                return upstream.doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) {
                        dialogUtils.showProgress(activityWeakReference.get());
                    }
                })
                        //结束时
                        .doOnTerminate(new Action() {
                            @Override
                            public void run() {
                                Activity context;
                                if ((context = activityWeakReference.get()) != null
                                        && !context.isFinishing()) {
                                    dialogUtils.dismissProgress();
                                }
                            }
                        });
            }
        };
    }

    public static <T> ObservableTransformer<T, T> applyProgressBar(
            @NonNull final Activity activity) {
        return applyProgressBar(activity, "");
    }
}

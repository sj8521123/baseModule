package com.sj.basemodule.net;

import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;

import com.google.gson.JsonParseException;
import com.jakewharton.retrofit2.adapter.rxjava2.HttpException;
import com.sj.basemodule.MainActivity;
import com.sj.basemodule.base.BaseActivity;
import com.sj.basemodule.base.MyApplication;
import com.sj.basemodule.config.KeyAndValueUserPrefs;
import com.sj.basemodule.config.SPUtils;
import com.sj.basemodule.net.response.base.ResponseBaseModel;
import com.sj.basemodule.util.CookieUtil;
import com.sj.basemodule.util.ToastUtil;
import com.sj.basemodule.weight.CustomProgressDialog;

import org.json.JSONException;

import java.io.InterruptedIOException;
import java.net.ConnectException;
import java.net.UnknownHostException;
import java.text.ParseException;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created by sj on 2017/12/4.
 * Description:封装Observer对象
 */
public abstract class DefaultObserver<T extends ResponseBaseModel> implements Observer<T> {
    /**
     * 请求成功
     */
    public final static int REQUEST_SUCCESS = 200;
    /**
     * token错误
     */
    public final static int TOKEN_INCORRECT = 201;
    /**
     * token过期
     */
    public final static int TOKEN_EXPIRED = 401;

    /**
     * refresh_token过期
     */
    public final static int REFRESH_TOKEN_EXPIRED = 203;

    private Activity activity;

    private CustomProgressDialog dialogUtils;

    //不要进度框
   /* public DefaultObserver() {

    }*/

    //弹出进度框 不带文字
    public DefaultObserver(Activity activity, boolean isShowDialog) {
        this.activity = activity;
        if (isShowDialog) {
            dialogUtils = new CustomProgressDialog();
            dialogUtils.showProgress(activity);
        }
    }

    //弹出进度框 带文字
    public DefaultObserver(Activity activity, String message) {
        this.activity = activity;
        dialogUtils = new CustomProgressDialog();
        dialogUtils.showProgress(activity, message);
    }

    @Override
    public void onSubscribe(Disposable d) {

    }

    @Override
    public void onNext(T response) {
        dismissProgress();
        //判断Status状态
        if (response.isStatus()) {
            onSuccess(response);
        } else {
            onFail(response);
        }
    }

    private void dismissProgress() {
        if (dialogUtils != null) {
            dialogUtils.dismissProgress();
        }
    }

    /**
     * 请求成功
     *
     * @param response 服务器返回的数据
     */
    abstract public void onSuccess(T response);

    /**
     * 请求到服务端所返回的异常情况
     *
     * @param response 服务器返回的数据
     */
    public void onFail(T response) {
        dismissProgress();
        String code = response.getCode();
        String message = response.getMessage();
        //需要做判断
        if (code != null) {
            switch (code) {
                //请求刷新token
                case "SESSION_EXPIRED":
                    onException(ExceptionReason.SESSION_EXPIRED);
                    break;
                //token失效 重新登录
                case "TOKEN_INVALID":
                    onException(ExceptionReason.TOKEN_INVALID);
                    break;
                default:
                    ToastUtil.fail(message);
                    break;
            }
        }
        //直接提示
        else {
            ToastUtil.fail(message);
        }
    }

    /**
     * 请求异常
     *
     * @param reason
     */
    private void onException(ExceptionReason reason) {
        switch (reason) {
            case CONNECT_ERROR:
                ToastUtil.fail("网络连接失败,请检查网络");
                break;

            case CONNECT_TIMEOUT:
                ToastUtil.fail("连接超时,请稍后再试");
                break;

            case BAD_NETWORK:
                ToastUtil.fail("服务器异常");
                break;

            case PARSE_ERROR:
                ToastUtil.fail("解析服务器响应数据失败");
                break;

            case TOKEN_INVALID:
                jumpToLogin("登录过期，请重新登录");
                break;

            case SESSION_EXPIRED:
                sessionExpired();
                break;

            default:
                ToastUtil.fail("未知网络错误类型");
                break;
        }
    }

    private void jumpToLogin(String message) {
        ToastUtil.info(message);
        CookieUtil.cleanCookieAndToken();
        MyApplication.exit();
        activity.startActivity(new Intent(activity, MainActivity.class));
    }

    private void sessionExpired() {
        String token = SPUtils.getInstance(MyApplication.currentUserPrefsName).getString(KeyAndValueUserPrefs.Key.ACCESS_TOKEN);
        if (!TextUtils.isEmpty(token)) {
            new RequestHelper((BaseActivity) activity).refreshToken();
        } else {
            jumpToLogin("切换环境导致的token值为空的提示，用户看不见，请测试忽略！");
        }
    }

    /**
     * http异常以及解析异常
     *
     * @param e
     */
    @Override
    public void onError(Throwable e) {
        dismissProgress();
        if (e instanceof HttpException) {     //   HTTP错误
            if (((HttpException) e).code() == TOKEN_EXPIRED) {//TOKEN失效
                onException(ExceptionReason.TOKEN_INVALID);
            } else {
                onException(ExceptionReason.BAD_NETWORK);
            }
        } else if (e instanceof ConnectException
                || e instanceof UnknownHostException) {   //   连接错误
            onException(ExceptionReason.CONNECT_ERROR);
        } else if (e instanceof InterruptedIOException) {   //  连接超时
            onException(ExceptionReason.CONNECT_TIMEOUT);
        } else if (e instanceof JsonParseException
                || e instanceof JSONException
                || e instanceof ParseException) {   //  解析错误
            onException(ExceptionReason.PARSE_ERROR);
        } else {
            ToastUtil.info(e.getMessage());
            /*onException(ExceptionReason.UNKNOWN_ERROR);*/
        }
    }

    @Override
    public void onComplete() {
    }

    /**
     * 请求网络失败原因
     */
    public enum ExceptionReason {
        /**
         * 解析数据失败
         */
        PARSE_ERROR,
        /**
         * 网络问题
         */
        BAD_NETWORK,
        /**
         * 连接错误
         */
        CONNECT_ERROR,
        /**
         * 连接超时
         */
        CONNECT_TIMEOUT,
        /**
         * 未知错误
         */
    /*    UNKNOWN_ERROR,*/
        /**
         * 提示错误
         */
        HINT_ERROR,
        /**
         * token失效
         */
        TOKEN_INVALID,
        /**
         * session超时
         */
        SESSION_EXPIRED
    }
}

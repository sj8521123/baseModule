package com.app.idea.net.common;

import android.widget.Toast;

import com.app.idea.utils.ToastUtil;
import com.google.gson.JsonParseException;
import com.jakewharton.retrofit2.adapter.rxjava2.HttpException;
import com.app.idea.R;
import com.app.idea.net.exception.NoDataExceptionException;
import com.app.idea.net.exception.ServerResponseException;
import com.app.idea.utils.LogUtil;

import org.json.JSONException;

import java.io.InterruptedIOException;
import java.net.ConnectException;
import java.net.UnknownHostException;
import java.text.ParseException;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;


public abstract class DefaultObserver<T> implements Observer<T> {
    @Override
    public void onSubscribe(Disposable d) {

    }

    @Override
    public void onNext(T response) {
        onSuccess(response);
        onFinish();
    }

    @Override
    public void onError(Throwable e) {
        LogUtil.e("Retrofit", e.getMessage());
        //   HTTP错误（外部条件非正常）
        if (e instanceof HttpException) {
            onException(ExceptionReason.BAD_NETWORK);
        }
        //   连接错误（外部条件非正常）
        else if (e instanceof ConnectException
                || e instanceof UnknownHostException) {
            onException(ExceptionReason.CONNECT_ERROR);
        }
        //  连接超时（外部条件非正常）
        else if (e instanceof InterruptedIOException) {
            onException(ExceptionReason.CONNECT_TIMEOUT);
        }
        //  解析错误（外部条件非正常）
        else if (e instanceof JsonParseException
                || e instanceof JSONException
                || e instanceof ParseException) {
            onException(ExceptionReason.PARSE_ERROR);
        }
        //服务产生的异常异常（外部条件正常）
        else if (e instanceof ServerResponseException) {
            onFail(e.getMessage());
        }
        //没有数据错误（外部条件正常）
        else if (e instanceof NoDataExceptionException) {
            onSuccess(null);
        }
        //未知错误（外部条件非正常）
        else {
            onException(ExceptionReason.UNKNOWN_ERROR);
        }
        onFinish();
    }

    @Override
    public void onComplete() {
    }

    /**
     * 请求成功
     *
     * @param response 服务器返回的数据
     */
    abstract public void onSuccess(T response);

    /**
     * 服务器返回数据，但响应码不为1000和200
     */
    private void onFail(String message) {
        ToastUtil.show(message);
    }

    /**
     * 结束情况的最后一个方法调用（正常结束、异常结束）
     */
    private void onFinish() {

    }

    /**
     * 请求异常
     *
     * @param reason
     */
    private void onException(ExceptionReason reason) {
        switch (reason) {
            case CONNECT_ERROR:
                ToastUtil.show(R.string.connect_error, Toast.LENGTH_SHORT);
                break;

            case CONNECT_TIMEOUT:
                ToastUtil.show(R.string.connect_timeout, Toast.LENGTH_SHORT);
                break;

            case BAD_NETWORK:
                ToastUtil.show(R.string.bad_network, Toast.LENGTH_SHORT);
                break;

            case PARSE_ERROR:
                ToastUtil.show(R.string.parse_error, Toast.LENGTH_SHORT);
                break;

            case UNKNOWN_ERROR:
            default:
                ToastUtil.show(R.string.unknown_error, Toast.LENGTH_SHORT);
                break;
        }
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
        UNKNOWN_ERROR,
    }
}

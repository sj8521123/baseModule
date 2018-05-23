package com.sj.basemodule.util;

import android.app.Activity;

import com.sj.basemodule.R;
import com.sj.basemodule.weight.CustomProgressDialog;


/**
 * Created by zhpan on 2017/5/26.
 * Description:
 */

public class CommonDialogUtils {
    //  加载进度的dialog
    private CustomProgressDialog mProgressDialog;

    /**
     * 显示ProgressDialog
     */
    public void showProgress(Activity activity, String msg) {
        if (activity == null || activity.isFinishing()) {
            return;
        }
        if (mProgressDialog == null) {
            mProgressDialog = new CustomProgressDialog.Builder(activity)
                    .setTheme(R.style.ProgressDialogStyle)
                    .setMessage(msg)
                    .build();
        }
        if (mProgressDialog != null && !mProgressDialog.isShowing()) {
            mProgressDialog.show();
        }
    }

    /**
     * 显示ProgressDialog
     */
    public void showProgress(Activity activity) {
        if (activity == null || activity.isFinishing()) {
            return;
        }
        if (mProgressDialog == null) {
            mProgressDialog = new CustomProgressDialog.Builder(activity)
                    .setTheme(R.style.ProgressDialogStyle)
                    .build();
        }
        if (mProgressDialog != null && !mProgressDialog.isShowing()) {
            mProgressDialog.show();
        }
    }

    /**
     * 取消ProgressDialog
     */
    public void dismissProgress() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }
}

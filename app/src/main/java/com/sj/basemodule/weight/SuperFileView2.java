package com.sj.basemodule.weight;

import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.sj.basemodule.util.LogUtil;
import com.sj.basemodule.util.ToastUtil;
import com.tencent.smtt.sdk.TbsReaderView;

import java.io.File;

/**
 * Created by 12457 on 2017/8/29.
 */

public class SuperFileView2 extends FrameLayout implements TbsReaderView.ReaderCallback {

    private static String TAG = "SuperFileView";
    private TbsReaderView mTbsReaderView;
    private Context context;
    private OnGetFilePathListener mOnGetFilePathListener;

    public SuperFileView2(Context context) {
        this(context, null, 0);
    }

    public SuperFileView2(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mTbsReaderView = new TbsReaderView(context, this);
        this.addView(mTbsReaderView, new LinearLayout.LayoutParams(-1, -1));
        this.context = context;
    }


    public SuperFileView2(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public void setOnGetFilePathListener(OnGetFilePathListener mOnGetFilePathListener) {
        this.mOnGetFilePathListener = mOnGetFilePathListener;
    }

    public void displayFile(File mFile) {

        if (mFile != null && !TextUtils.isEmpty(mFile.toString())) {
            //增加下面一句解决没有TbsReaderTemp文件夹存在导致加载文件失败
            String bsReaderTemp = Environment.getExternalStorageDirectory() + "/" + "TbsReaderTemp";
            File bsReaderTempFile = new File(bsReaderTemp);
            if (!bsReaderTempFile.exists()) {
                boolean mkdir = bsReaderTempFile.mkdir();
                if (!mkdir) {
                    ToastUtil.fail("创建/storage/emulated/0/TbsReaderTemp失败！！！！！");
                }
            }

            //加载文件
            Bundle localBundle = new Bundle();
            localBundle.putString("filePath", mFile.toString());
            localBundle.putString("tempPath", Environment.getExternalStorageDirectory() + "/" + "TbsReaderTemp");

            if (this.mTbsReaderView == null)
                this.mTbsReaderView = getTbsReaderView(context);
            boolean bool = this.mTbsReaderView.preOpen(getFileType(mFile.toString()), false);
            if (bool) {
                this.mTbsReaderView.openFile(localBundle);
            }
        } else {
            ToastUtil.fail("文件路径无效！");
        }

    }

    private TbsReaderView getTbsReaderView(Context context) {
        return new TbsReaderView(context, this);
    }

    /***
     * 获取文件类型
     *
     * @param paramString
     * @return
     */
    private String getFileType(String paramString) {
        String str = "";

        if (TextUtils.isEmpty(paramString)) {
            LogUtil.d(TAG, "paramString---->null");
            return str;
        }
        LogUtil.d(TAG, "paramString:" + paramString);
        int i = paramString.lastIndexOf('.');
        if (i <= -1) {
            LogUtil.d(TAG, "i <= -1");
            return str;
        }


        str = paramString.substring(i + 1);
        LogUtil.d(TAG, "paramString.substring(i + 1)------>" + str);
        return str;
    }

    public void show() {
        if (mOnGetFilePathListener != null) {
            mOnGetFilePathListener.onGetFilePath(this);
        }
    }

    @Override
    public void onCallBackAction(Integer integer, Object o, Object o1) {
        LogUtil.e("****************************************************" + integer);
    }

    public void onStopDisplay() {
        if (mTbsReaderView != null) {
            mTbsReaderView.onStop();
        }
    }

    /***
     * 将获取File路径的工作，“外包”出去
     */
    public interface OnGetFilePathListener {
        void onGetFilePath(SuperFileView2 mSuperFileView2);
    }
}

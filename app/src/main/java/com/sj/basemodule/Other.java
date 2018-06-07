package com.sj.basemodule;

import android.os.Bundle;
import android.util.Log;

import com.sj.basemodule.base.BaseActivity;
import com.sj.basemodule.util.file.STGFileUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class Other extends BaseActivity {
    private static final String TAG = "Other";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @Override
    public int initLayout() {
        return R.layout.content_other;
    }

    @Override
    public void initFromData() {

    }

    @Override
    public void initLayoutView() {

    }

    @Override
    public void initLocalData() {

    }

    @OnClick(R.id.read)
    public void onViewClicked() {
        //对象写入文件 序列化的工程
        ObjectInputStream in = null;
        try {
            File f = new File(STGFileUtil.fileUtil.getAppIndependentStorage() + "cache.txt");
            in = new ObjectInputStream(new FileInputStream(f));
            User user1 = (User) in.readObject();

            Log.i(TAG, "initLayoutView: " + user1);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

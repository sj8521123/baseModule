package com.sj.basemodule;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import basemodule.sj.com.basic.weight.SuperFileView2;

import java.io.File;

import basemodule.sj.com.basic.base.BaseActivity;
import basemodule.sj.com.basic.util.KeyTools;
import basemodule.sj.com.basic.util.NetWorkUtil;
import basemodule.sj.com.basic.util.STGFileUtil;
import basemodule.sj.com.basic.util.ToastUtil;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 文件浏览器
 */
public class FileDisplayActivity extends BaseActivity {
    SuperFileView2 mSuperFileView;
    String filePath;
    Retrofit retrofit;

    public static void show(Context context, String url) {
        Intent intent = new Intent(context, FileDisplayActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("path", url);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mSuperFileView != null) {
            mSuperFileView.onStopDisplay();
        }
    }

    @Override
    protected void reConnect() {

    }

    @Override
    public int initLayout() {
        return R.layout.activity_file_display;
    }

    @Override
    public void initFromData() {
        retrofit = new Retrofit.Builder()
                .baseUrl("https://www.baidu.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    @Override
    public void initLayoutView() {
        init();
    }

    @Override
    public void initLocalData() {

    }

    public void init() {
        mSuperFileView = (SuperFileView2) findViewById(R.id.mSuperFileView);
        mSuperFileView.setOnGetFilePathListener(new SuperFileView2.OnGetFilePathListener() {
            @Override
            public void onGetFilePath(SuperFileView2 mSuperFileView2) {
                getFilePathAndShowFile(mSuperFileView2);
            }
        });

        Intent intent = this.getIntent();
        String path = (String) intent.getSerializableExtra("path");

        if (!TextUtils.isEmpty(path)) {
            filePath = path;
        }
        mSuperFileView.show();

    }

    private void getFilePathAndShowFile(SuperFileView2 mSuperFileView2) {
        //本地是否存在
        File cacheFile = getCacheFile(filePath);

        //直接显示
        if (cacheFile.exists()) {
            //MD5命名的文件
            mSuperFileView2.displayFile(cacheFile);
        }
        //下载后显示
        else {
            if (NetWorkUtil.isConnected()) {
                if (filePath.contains("http")) {
                    /*downLoadFromNet(filePath, mSuperFileView2);*/
                } else {
                    ToastUtil.show("未知的Url");
                }
            } else {
                ToastUtil.show("请先打开网络!");
            }
        }
    }

    /***
     * 绝对路径获取缓存文件
     *
     * @param url
     * @return
     */
    private File getCacheFile(String url) {
        return new File(STGFileUtil.getCacheFileDir()
                + getFileName(url));
    }

   /* private void downLoadFromNet(final String url, final SuperFileView2 mSuperFileView2) {
        //1.网络下载、存储路径、
        File cacheFile = getCacheFile(url);
        if (cacheFile.exists()) {
            if (cacheFile.length() <= 0) {
                cacheFile.delete();
                return;
            }
        }
        STGApiService stgApiService = retrofit.create(STGApiService.class);
        Call<ResponseBody> call = stgApiService.loadPdfFile(url);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                InputStream is = null;
                byte[] buf = new byte[2048];
                int len;
                FileOutputStream fos = null;
                try {
                    ResponseBody responseBody = response.body();
                    is = responseBody.byteStream();
                    *//*long total = responseBody.contentLength();*//*
                    File fileN = getCacheFile(url);
                    if (!fileN.exists()) {
                        boolean mkFile = fileN.createNewFile();
                        if (!mkFile) {
                            ToastUtil.fail("创建下载文件出错");
                            return;
                        }
                    }
                    fos = new FileOutputStream(fileN);
                    *//*long sum = 0;*//*
                    while ((len = is.read(buf)) != -1) {
                        fos.write(buf, 0, len);
                       *//* sum += len;
                        int progress = (int) (sum * 1.0f / total * 100);*//*
                    }
                    fos.flush();
                    //2.ACache记录文件的有效期
                    mSuperFileView2.displayFile(fileN);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    try {
                        if (is != null)
                            is.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    try {
                        if (fos != null)
                            fos.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                File file = getCacheFile(url);
                if (!file.exists()) {
                    file.delete();
                }
            }
        });
    }*/

    /***
     * 根据链接获取文件名（带类型的），具有唯一性
     *
     * @param url
     * @return
     */
    private String getFileName(String url) {
        return KeyTools.getMD5(url) + "." + STGFileUtil.fileUtil.getFileType(url);
    }

}

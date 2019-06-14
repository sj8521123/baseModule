package basemodule.sj.com.basic.util;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.StatFs;
import android.widget.RemoteViews;

import com.app.idea.utils.LogUtil;
import com.app.idea.utils.ToastUtil;
import com.sj.basemodule.R;
import com.sj.basemodule.base.MyApplication;
import com.sj.basemodule.util.file.STGFileUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import androidx.core.content.FileProvider;


/**
 * Created by sj on 2016/4/20.
 */
public class UpdateAppUtil {
    //apk保存路径以及名称
    private final String apkSavePath = STGFileUtil.getAppDownLoadDir() + "stgApp.apk";
    private final int DOWNLOAD = 1;
    private final int DOWNLOAD_FINISH = 2;
    private final int DOWNLOADMAXLENGTH = 3;
    ProgressDialog progressDialog;
    private Notification notify;
    private NotificationManager manager;
    private int maxLength;
    private int progress;
    private Context mContext;
    /**
     * 强制更新显示进度条
     */
    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case DOWNLOADMAXLENGTH:
                    progressDialog.setMax(maxLength / 1024 / 1024);
                    break;
                case DOWNLOAD:
                    progressDialog.setProgress(progress / 1024 / 1024);
                    break;
                case DOWNLOAD_FINISH:
                    installApk((Context) msg.obj);
                    break;
                default:
                    break;
            }
        }
    };
    /**
     * 非强制更新 后台显示
     */
    private Handler completeHandler = new Handler() {
        public void handleMessage(Message msg) {
            if (msg.what < 100 && msg.what > -2) {
                notify.contentView.setTextViewText(
                        R.id.notify_updata_values_tv, msg.what + "%");
                notify.contentView.setProgressBar(R.id.notify_updata_progress,
                        100, msg.what, false);
                manager.notify(100, notify);
            } else if (msg.what == -2) {
                ToastUtil.show("手机存储空间不足！");
            } else {
                notify.contentView.setTextViewText(
                        R.id.notify_updata_values_tv, "下载完成");
                notify.contentView.setProgressBar(R.id.notify_updata_progress,
                        100, msg.what, false);// 清除通知栏
                manager.cancel(100);
                installApk((Context) msg.obj);

            }
        }
    };

    public UpdateAppUtil() {
        mContext = MyApplication.mAppContext;
    }

    /**
     * 如果是强制更新 调用该方法
     */
    public void ForceDownLoadApk(Context context, String apkLoadUri) {
        progressDialog = new ProgressDialog(context);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setTitle("提示");
        progressDialog.setMessage("数据下载中...");
        progressDialog.setIndeterminate(false);
        //设置ProgressDialog 是否可以按退回按键取消
        progressDialog.setCancelable(false);
        progressDialog.setProgressNumberFormat("%1dMB/%2dMB");
        progressDialog.show();

        new downloadApkThread(apkLoadUri).start();
    }

    /**
     * 如果非强制更新 则调用该方法
     */
    public void noForceDownLoadApk(String apkLoadUri) {
        manager = (NotificationManager) mContext
                .getSystemService((Context.NOTIFICATION_SERVICE));
        notify = new Notification();
        notify.icon = R.drawable.logo;
        // 通知栏显示所用到的布局文件
        notify.contentView = new RemoteViews(mContext.getPackageName(),
                R.layout.view_notify_item);
        manager.notify(100, notify);
        downLoadSchedule(apkLoadUri, completeHandler, mContext,
                apkLoadUri);
    }

    /**
     * 连接网络,下载一个文件,并传回进度
     *
     * @param uri
     * @param handler
     * @param context
     */
    private void downLoadSchedule(final String uri,
                                  final Handler handler, final Context context, final String urlPath) {
        // 每次读取文件的长度
        final int perLength = 30 * 1024;
        new Thread() {
            @Override
            public void run() {
                super.run();
                try {
                    URL url = new URL(uri);
                    HttpURLConnection conn = (HttpURLConnection) url
                            .openConnection();
                    conn.setDoInput(true);
                    conn.connect();
                    InputStream in = conn.getInputStream();
                    // 2865412
                    long length = conn.getContentLength();
                    //判断是否有足够的空间
                    if (hasMemory(length)) {

                        // 每次读取1k
                        byte[] buffer = new byte[perLength];
                        int len = -1;
                        //下载保存的路径
                        File file = new File(apkSavePath);
                        if (!file.exists()) {
                            file.createNewFile();
                        }
                        FileOutputStream out = new FileOutputStream(file);
                        int temp = 0;
                        while ((len = in.read(buffer)) != -1) {
                            // 写入文件
                            out.write(buffer, 0, len);
                            // 当前进度
                            int schedule = (int) ((file.length() * 100) / length);
                            // 通知更新进度（10,7,4整除才通知，没必要每次都更新进度）
                            if (temp != schedule
                                    ) {
                                // 保证同一个数据只发了一次
                                temp = schedule;
                                Message message = new Message();
                                message.what = schedule;
                                message.obj = context;
                                handler.sendMessage(message);
                            }
                        }
                        out.flush();
                        out.close();
                        in.close();

                    } else { // 存储空间不够

                        Message message = new Message();
                        message.what = -2;
                        message.obj = context;
                        handler.sendMessage(message);


                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    // ---------------------------------通知栏下载形式-----------------------------

    /**
     * 判断还有这么大的空间不
     *
     * @param len
     * @return
     */
    private boolean hasMemory(long len) {

        /** 获取存储卡路径 */
        File sdcardDir = Environment.getExternalStorageDirectory();
        /** StatFs 看文件系统空间使用情况 */
        StatFs statFs = new StatFs(sdcardDir.getPath());
        /** Block 的 size*/
        int blockSize = statFs.getBlockSize();
        //可使用的数量
        int freeBlocks = statFs.getFreeBlocks();

        len = len / blockSize;

        return len <= freeBlocks;
    }

    /**
     * 获取扩展SD卡存储目录
     * <p>
     * 如果有外接的SD卡，并且已挂载，则返回这个外置SD卡目录
     * 否则：返回内置SD卡目录
     *
     * @return
     */
    private String getExternalSdCardPath() {

        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            LogUtil.i("SDCard", "挂载的.." + Environment.getExternalStorageState() + Environment.getExternalStorageDirectory().getAbsolutePath());
            File sdCardFile = new File(Environment.getExternalStorageDirectory().getAbsolutePath());
            return sdCardFile.getPath();
        } else {
            LogUtil.i("SDCard", "没挂在.." + Environment.getExternalStorageState());
        }

        String path = null;

        File sdCardFile;

        ArrayList<String> devMountList = getDevMountList();

        for (String devMount : devMountList) {
            File file = new File(devMount);

            if (file.isDirectory() && file.canWrite()) {
                path = file.getAbsolutePath();

                String timeStamp = new SimpleDateFormat("ddMMyyyy_HHmmss").format(new Date());
                File testWritable = new File(path, "test_" + timeStamp);

                if (testWritable.mkdirs()) {
                    testWritable.delete();
                } else {
                    path = null;
                }
            }
        }

        if (path != null) {
            sdCardFile = new File(path);
            return sdCardFile.getAbsolutePath();
        }

        return null;
    }

    /**
     * 遍历 "system/etc/vold.fstab” 文件，获取全部的Android的挂载点信息
     *
     * @return
     */
    private ArrayList<String> getDevMountList() {
        String[] toSearch = STGFileUtil.fileUtil.readFile("/etc/vold.fstab").split(" ");
        ArrayList<String> out = new ArrayList<>();
        for (int i = 0; i < toSearch.length; i++) {
            if (toSearch[i].contains("dev_mount")) {
                if (new File(toSearch[i + 2]).exists()) {
                    out.add(toSearch[i + 2]);
                }
            }
        }
        return out;
    }

    /**
     * 安装apk
     */
    private void installApk(Context context) {
        File apkFile = new File(apkSavePath); // 适配所有机型的手机
        if (!apkFile.exists()) {
            return;
        }
        //判断是否是AndroidN以及更高的版本

        Intent i = new Intent(Intent.ACTION_VIEW);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {

            Uri contentUri = FileProvider.getUriForFile(context, "com.shundian.smart.fileProvider", apkFile);

            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            i.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

            i.setDataAndType(contentUri, "application/vnd.android.package-archive");

        } else {
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            i.setDataAndType(Uri.fromFile(apkFile), "application/vnd.android.package-archive");

        }
        context.startActivity(i);
    }

    /**
     * 下载程序
     */
    private class downloadApkThread extends Thread {
        String urlPath;

        private downloadApkThread(String urlPath) {
            this.urlPath = urlPath;
        }

        @Override
        public void run() {
            try {
                if (Environment.getExternalStorageState().equals(
                        Environment.MEDIA_MOUNTED)) {

                    URL url = new URL(urlPath);
                    HttpURLConnection conn = (HttpURLConnection) url
                            .openConnection();
                    conn.connect();
                    maxLength = conn.getContentLength();
                    mHandler.sendEmptyMessage(DOWNLOADMAXLENGTH);
                    InputStream is = conn.getInputStream();

                    //下载保存的路径
                    File file = new File(apkSavePath);
                    if (!file.exists()) {
                        file.createNewFile();
                    }
                    FileOutputStream fos = new FileOutputStream(file);
                    int count = 0;
                    byte buf[] = new byte[1024];
                    do {
                        int numRead = is.read(buf);
                        count += numRead;
                        progress = count;
                        mHandler.sendEmptyMessage(DOWNLOAD);
                        if (numRead <= 0) {
                            Message message = new Message();
                            message.what = DOWNLOAD_FINISH;
                            message.obj = mContext;
                            mHandler.sendMessage(message);
                            break;
                        }
                        fos.write(buf, 0, numRead);
                    } while (true);
                    fos.close();
                    is.close();
                }
                progressDialog.dismiss();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

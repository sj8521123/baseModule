package basemodule.sj.com.basic.util.file;

import android.os.Environment;
import android.text.TextUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import basemodule.sj.com.basic.util.Util;


/**
 * content: 文件存储包（独立、专属）
 * author：sj
 * time: 2017/6/27 14:32
 * email：13658029734@163.com
 * phone:13658029734
 */

public class StorageFileUtil {
    //判断SD卡是否存在
    private boolean hasSdcard() {
        return Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState());
    }

    /**
     * app专属文件的存储 包名关联 随着app应用的删除而删除
     * 如果是图片 不能在系统相册里检出
     *
     * @return
     */
    String getAppExclusiveStorage() {
        String sdcardPath;
        //判断SD卡是否存在
        if (hasSdcard()) {
            //path : /mnt/sdcard/Android/data/com.shundian.smart/files/
            sdcardPath = Util.getContext().getExternalFilesDir(null) + File.separator;
        } else {
            //path : data/data/com.shundian.smart/files/
            sdcardPath = Util.getContext().getFilesDir() + File.separator;
        }
        return sdcardPath;
    }

    /**
     * app独立文件的存储 不随着app应用的删除而删除
     * 如果是图片 能在系统相册里检出
     *
     * @return
     */
    public String getAppIndependentStorage() {
        String sdcardPath;
        //判断SD卡是否存在
        if (hasSdcard()) {
            // path : /mnt/sdcard/
            sdcardPath = Environment.getExternalStorageDirectory() + File.separator;
        } else {
            // path : /data/data/
            sdcardPath = "data" + File.separator + "data" + File.separator;
        }
        return sdcardPath;
    }
}

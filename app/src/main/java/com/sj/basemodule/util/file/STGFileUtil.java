package com.sj.basemodule.util.file;

import java.io.File;
import java.io.IOException;

/**
 * content:只涉及溯源宝对文件的操作
 * author：sj
 * time: 2017/6/27 14:32
 * email：13658029734@163.com
 * phone:13658029734
 */

public class STGFileUtil {
    // TODO: 2018/5/23 根据项目变化而变化
    private final static String PHOTO_DIR = "stgPhoto/";
    private final static String APKDOWN_DIR = "stgApkDown/";
    public static FileUtil fileUtil = new FileUtil();

    //创建项目所有目录
    public static void createAllDirs() throws IOException {
        // TODO: 2017/6/27 依次添加目录地址
        fileUtil.createDir(new File(getPhotoFullDir()));
        fileUtil.createDir(new File(getAppDownLoadDir()));
    }

    //app专属的图片文件
    public static String getPhotoFullDir() {
        return fileUtil.getAppExclusiveStorage() + PHOTO_DIR;
    }

    //app专属的apk文件
    public static String getAppDownLoadDir() {
        return fileUtil.getAppExclusiveStorage() + APKDOWN_DIR;
    }
}

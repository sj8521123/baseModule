package com.sj.basemodule.util;

import java.io.File;
import java.io.IOException;

import basemodule.sj.com.basic.util.file.FileUtil;

/**
 * content:
 * author：sj
 * time: 2017/6/27 14:32
 * email：13658029734@163.com
 * phone:13658029734
 */

public class STGFileUtil {
    // TODO: 2018/5/23 根据项目变化而变化
    //图片目录
    private final static String PHOTO_DIR = "Photo/";
    //apk下载文件
    private final static String APKDOWN_DIR = "ApkDown/";
    //针对网上下载的word ppt的存储
    private final static String CACHE_FILE = "cacheFile/";

    public static FileUtil fileUtil = new FileUtil();

    //创建项目所有目录
    public static void createAllDirs() throws IOException {
        // TODO: 2017/6/27 依次添加目录地址
        fileUtil.createDir(new File(getPhotoFullDir()));
        fileUtil.createDir(new File(getAppDownLoadDir()));
        fileUtil.createDir(new File(getCacheFileDir()));
    }

    //app专属的图片文件
    public static String getPhotoFullDir() {
        return fileUtil.getAppExclusiveStorage() + PHOTO_DIR;
    }

    //app专属的apk文件
    public static String getAppDownLoadDir() {
        return fileUtil.getAppExclusiveStorage() + APKDOWN_DIR;
    }

    //app专属的其他文件（word，ppt）
    public static String getCacheFileDir() {
        return fileUtil.getAppExclusiveStorage() + CACHE_FILE;
    }
}

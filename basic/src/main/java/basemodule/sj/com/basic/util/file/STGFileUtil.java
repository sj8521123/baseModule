package basemodule.sj.com.basic.util.file;

import java.io.File;
import java.io.IOException;

import basemodule.sj.com.basic.util.file.FileUtil;
import basemodule.sj.com.basic.util.file.StorageFileUtil;

/**
 * content:项目文件操作类
 * author：sj
 * time: 2017/6/27 14:32
 * email：13658029734@163.com
 * phone:13658029734
 */

public class STGFileUtil {
    // TODO: 2018/5/23 根据项目变化而变化
    //图片目录
    private final static String PHOTO_DIR = "Photo";
    //apk下载文件
    private final static String APKDOWN_DIR = "ApkDown";
    //针对网上下载的word ppt的存储
    private final static String CACHE_FILE = "cacheFile";
    //专属、独立
    private static StorageFileUtil storageFileUtil = new StorageFileUtil();
    //基础的文件操作工具类
    public static FileUtil fileUtil = new FileUtil();


    //创建项目所有目录
    public static boolean createAllDirs() {
        // TODO: 2017/6/27 依次添加目录地址
        return fileUtil.createOrExistsDir(new File(getPhotoFullDir()))
                && fileUtil.createOrExistsDir(new File(getAppDownLoadDir()))
                && fileUtil.createOrExistsDir(new File(getCacheFileDir()));
    }

    //app专属的图片文件
    public static String getPhotoFullDir() {
        return storageFileUtil.getAppExclusiveStorage() + PHOTO_DIR + File.separator;
    }

    //app专属的apk文件
    public static String getAppDownLoadDir() {
        return storageFileUtil.getAppExclusiveStorage() + APKDOWN_DIR + File.separator;
    }

    //app专属的其他文件（word，ppt）
    public static String getCacheFileDir() {
        return storageFileUtil.getAppExclusiveStorage() + CACHE_FILE + File.separator;
    }
}

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
 * content:只涉及底层的File原始操作
 * author：sj
 * time: 2017/6/27 14:32
 * email：13658029734@163.com
 * phone:13658029734
 */

public class FileUtil {
    /**
     * 单纯判断文件或目录是否存在返回true false
     */
    public boolean isFileExist(File file) {
        boolean hasFile = false;
        try {
            hasFile = file.exists();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return hasFile;
    }

    /**
     * 创建文件
     *
     * @throws IOException
     */
    public void createFile(File file) throws IOException {
        boolean isOk = file.createNewFile();
        if (!isOk) {
            throw new IOException("创建文件失败!");
        }
    }

    /**
     * 验证文件目录是否存在，不存在则进行创建操作
     *
     * @param dirPath 目录路径
     */
    public void createDir(File dirPath) throws IOException {
        if (!dirPath.exists()) {
            boolean isOk = dirPath.mkdirs();
            if (!isOk) {
                throw new IOException("创建目录失败!");
            }
        }
    }

    /**
     * app专属文件的存储 包名关联 随着app应用的删除而删除
     * 如果是图片 不能在系统相册里检出
     *
     * @return
     */
    public String getAppExclusiveStorage() {
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

    //判断SD卡是否存在
    private boolean hasSdcard() {
        return Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState());
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

    //删除文件安全方式(防止进程还持有文件对象)
    public void deleteFile(File file) throws IOException {
        if (file.isFile()) {
            deleteFileSafely(file);
            return;
        }
        if (file.isDirectory()) {
            File[] childFiles = file.listFiles();
            if (childFiles == null || childFiles.length == 0) {
                deleteFileSafely(file);
                return;
            }
            for (int i = 0; i < childFiles.length; i++) {
                deleteFile(childFiles[i]);
            }
            deleteFileSafely(file);
        }
    }

    /**
     * 安全删除文件.
     *
     * @param file
     * @return
     */
    private boolean deleteFileSafely(File file) throws IOException {
        if (file != null) {
            String tmpPath = file.getParent() + File.separator + System.currentTimeMillis();
            File tmp = new File(tmpPath);
            boolean isOk = file.renameTo(tmp);
            if (!isOk) {
                throw new IOException("安全删除文件出错");
            }
            return tmp.delete();
        }
        return false;
    }

    public String readFile(String filePath) {
        String fileContent = "";
        File file = new File(filePath);
        if (!file.isFile()) {
            return null;
        }

        BufferedReader reader = null;
        try {
            InputStreamReader is = new InputStreamReader(new FileInputStream(file));
            reader = new BufferedReader(is);
            String line;
            while ((line = reader.readLine()) != null) {
                fileContent += line + " ";
            }
            reader.close();
            return fileContent;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return fileContent;
    }

    /***
     * 获取文件类型 .ppt .jpg
     *
     * @param paramString
     * @return
     */
    public String getFileType(String paramString) {
        String str = "";
        if (TextUtils.isEmpty(paramString)) {
            return str;
        }
        int i = paramString.lastIndexOf('.');
        if (i <= -1) {
            return str;
        }
        str = paramString.substring(i + 1);
        return str;
    }
}

package basemodule.sj.com.basic.util;

import android.annotation.SuppressLint;
import android.os.Build;
import android.provider.Settings;


import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * content:针对设备唯一标志
 * author：sj
 * time: 2017/12/29 10:21
 * email：13658029734@163.com
 * phone:13658029734
 */

public class DeviceUtil {
    public static String getUniqueId(){
        @SuppressLint("HardwareIds")
        String androidID = Settings.Secure.getString(Util.getContext().getContentResolver(), Settings.Secure.ANDROID_ID);

        String id = androidID + Build.SERIAL;
        try {
            return toMD5(id);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return id;
        }
    }


    private static String toMD5(String text) throws NoSuchAlgorithmException {
        //获取摘要器 MessageDigest
        MessageDigest messageDigest = MessageDigest.getInstance("MD5");
        //通过摘要器对字符串的二进制字节数组进行hash计算
        byte[] digest = messageDigest.digest(text.getBytes());

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < digest.length; i++) {
            //循环每个字符 将计算结果转化为正整数;
            int digestInt = digest[i] & 0xff;
            //将10进制转化为较短的16进制
            String hexString = Integer.toHexString(digestInt);
            //转化结果如果是个位数会省略0,因此判断并补0
            if (hexString.length() < 2) {
                sb.append(0);
            }
            //将循环结果添加到缓冲区
            sb.append(hexString);
        }
        //返回整个结果
        return sb.toString();
    }
}

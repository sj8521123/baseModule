package basemodule.sj.com.basic.config;


import basemodule.sj.com.basic.util.Util;

/**
 * 获取sp中常用的数据
 */
public class GetValueOfSP {
    public static String getToken() {
        return SPUtils.getInstance(Util.getCurrentUserPrefsName()).getString(KeyAndValueUserPrefs.Key.ACCESS_TOKEN);
    }
}

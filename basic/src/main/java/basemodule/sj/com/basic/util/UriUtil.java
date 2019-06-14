package basemodule.sj.com.basic.util;

import android.app.Activity;
import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;


import java.io.File;

/**
 * content:
 * author：sj
 * time: 2018/1/2 11:53
 * email：13658029734@163.com
 * phone:13658029734
 */

public class UriUtil {
    /**
     * uri转path (适用于本地地址图片)
     *
     * @param uri
     * @return path
     */
    public static String uriParseToStrLocal(final Uri uri) {
        if (null == uri)
            return null;
        final String scheme = uri.getScheme();
        String data = null;
        if (scheme == null)
            data = uri.getPath();
        else if (ContentResolver.SCHEME_FILE.equals(scheme)) {
            data = uri.getPath();
        } else if (ContentResolver.SCHEME_CONTENT.equals(scheme)) {
            Cursor cursor = Util.getContext().getContentResolver().query(uri, new String[]{MediaStore.Images.ImageColumns.DATA}, null, null, null);
            if (null != cursor) {
                if (cursor.moveToFirst()) {
                    int index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                    if (index > -1) {
                        data = cursor.getString(index);
                    }
                }
                cursor.close();
            }
        }
        return data;
    }

    /**
     * uri转path (适用于网络地址图片)
     *
     * @param uri
     * @return path
     */
    public static String uriParseToStrNetWork(final Uri uri) {
        return uri.toString();
    }

    /**
     * 字符串转uri
     *
     * @param str
     * @return uri
     */
    public static Uri stringParseUri(String str) {
        return Uri.parse(str);
    }

    private static File uri2File(Uri uri, Activity activity) {
        String img_path;
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor actualimagecursor = activity.managedQuery(uri, proj, null,
                null, null);
        if (actualimagecursor == null) {
            img_path = uri.getPath();
        } else {
            int actual_image_column_index = actualimagecursor
                    .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            actualimagecursor.moveToFirst();
            img_path = actualimagecursor
                    .getString(actual_image_column_index);
        }
        File file = new File(img_path);
        return file;
    }
}

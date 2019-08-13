package basemodule.sj.com.basic.util;

import android.annotation.SuppressLint;
import android.text.TextUtils;
import android.widget.Toast;


/**
 * ToastUtil
 */

public class ToastUtil {

    private static Toast toast;

    public static void show(int resId) {
        show(Util.getContext().getResources().getText(resId), Toast.LENGTH_SHORT);
    }

    public static void show(int resId, int duration) {
        show(Util.getContext().getResources().getText(resId), duration);
    }

    public static void show(CharSequence text) {
        show(text, Toast.LENGTH_SHORT);
    }

    @SuppressLint("ShowToast")
    public static void show(CharSequence text, int duration) {
        text = TextUtils.isEmpty(text == null ? "" : text.toString()) ? "请检查您的网络！"
                : text;
        if (toast == null) {
            toast = Toast.makeText(Util.getContext(), text, duration);
        } else {
            toast.setText(text);
        }
        toast.show();
    }

    public static void show(int resId, Object... args) {
        show(String.format(Util.getContext().getResources().getString(resId), args),
                Toast.LENGTH_SHORT);
    }

    public static void show(String format, Object... args) {
        show(String.format(format, args), Toast.LENGTH_SHORT);
    }

    public static void show(int resId, int duration, Object... args) {
        show(String.format(Util.getContext().getResources().getString(resId), args),
                duration);
    }

    public static void show(String format, int duration, Object... args) {
        show(String.format(format, args), duration);
    }
}

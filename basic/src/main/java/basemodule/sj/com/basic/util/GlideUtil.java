package basemodule.sj.com.basic.util;

import android.content.Context;
import android.net.Uri;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.sj.basemodule.R;
import com.sj.basemodule.weight.transform.GlideRoundTransform;

import java.io.File;

/**
 * content:Glide图片加载库
 * author：sj
 * time: 2017/12/22 17:33
 * email：13658029734@163.com
 * phone:13658029734
 */

public class GlideUtil {
    //设置图片变化tag
    //中心裁剪
    public static final int TRANS_CENTER_CROP = 1;
    //裁剪加圆角
    public static final int TRANS_CENTER_CROP_AND_ROUND = 2;
    //不变换(针对没有Imageview大的图片，使用时需要将ImageView scaleStyle设置为center)
    public static final int NOT_TRANSFORM = 3;
    //默认圆角10
    private int radio = 10;
    private int errorImgSourceId = R.drawable.default_image;
    public void setRadio(int radio) {
        this.radio = radio;
    }

    public void setErrorImgSourceId(int errorImgSourceId) {
        this.errorImgSourceId = errorImgSourceId;
    }

    public void load(Context context,
                     Uri uri,
                     ImageView imageView,
                     int transFormTag) {
        Glide.with(context)
                .load(uri)
                .apply(setRequestOptions(context, transFormTag))
                .into(imageView);
    }

    private RequestOptions setRequestOptions(Context context, int transFormTag) {
        RequestOptions options = new RequestOptions();
        options.placeholder(R.drawable.default_image)
                .error(errorImgSourceId);
        switch (transFormTag) {
            case NOT_TRANSFORM:
                options.override(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL);
                break;
            case TRANS_CENTER_CROP:
                options.centerCrop();
                break;
            case TRANS_CENTER_CROP_AND_ROUND:
                options.transform(new GlideRoundTransform(context, radio));
                break;
        }
        return options;
    }

    public void load(Context context,
                     File file,
                     ImageView imageView,
                     int transFormTag) {
        Glide.with(context)
                .load(file)
                .apply(setRequestOptions(context, transFormTag))
                .into(imageView);
    }

    public void load(Context context,
                     int resource,
                     ImageView imageView,
                     int transFormTag) {
        Glide.with(context)
                .load(resource)
                .apply(setRequestOptions(context, transFormTag))
                .into(imageView);
    }

    public void load(Context context,
                     byte[] imageBytes,
                     ImageView imageView,
                     int transFormTag) {
        Glide.with(context)
                .load(imageBytes)
                .apply(setRequestOptions(context, transFormTag))
                .into(imageView);
    }

    public void load(Context context,
                     String url,
                     ImageView imageView,
                     int transFormTag) {
        Glide.with(context)
                .load(url)
                .apply(setRequestOptions(context, transFormTag))
                .into(imageView);
    }
}

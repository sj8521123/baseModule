package com.sj.basemodule.common;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.PointF;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import androidx.viewpager.widget.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.sj.basemodule.R;
import com.sj.basemodule.base.MyApplication;
import com.sj.basemodule.util.ImageUtil;
import com.sj.basemodule.util.UriUtil;
import com.sj.basemodule.weight.subscaleview.ImageSource;
import com.sj.basemodule.weight.subscaleview.ImageViewState;
import com.sj.basemodule.weight.subscaleview.SubsamplingScaleImageView;

import java.io.File;
import java.util.List;

/**
 * content:图片放大查看器
 * author：sj
 * time: 2017/7/25 11:40
 * email：13658029734@163.com
 * phone:13658029734
 */

public class ImagePreviewAdapter extends PagerAdapter {
    private Context context;
    private List<Uri> uris;

    public ImagePreviewAdapter(Context context, List<Uri> uris) {
        this.context = context;
        this.uris = uris;
    }

    @Override
    public int getCount() {
        return uris.size();
    }

    @Override
    public Object instantiateItem(ViewGroup view, final int position) {
        View imageLayout = LayoutInflater.from(context).inflate(R.layout.image_preview, view, false);
        //设置显示图片
        final SubsamplingScaleImageView imageView = imageLayout.findViewById(R.id.image);
        imageView.setMinimumScaleType(SubsamplingScaleImageView.SCALE_TYPE_CUSTOM);
        Uri uri = uris.get(position);
        String filePath;
        //网络图片
        if (uri.toString().startsWith("http://")) {
            filePath = uri.toString();
        } else {
            if (Build.VERSION.SDK_INT >= 24) {
                //拍照图片
                if (uri.toString().indexOf("content://com.shundian.smart.fileProvider/") == 0) {
                    filePath = Environment.getExternalStorageDirectory() + uri.getPath().substring("/stg_images".length());
                } else {
                    filePath = UriUtil.uriParseToStrLocal(uri);
                }
            } else {
                filePath = UriUtil.uriParseToStrLocal(uri);
            }
        }
        Glide.with(context).load(filePath).downloadOnly(new SimpleTarget<File>() {
            @Override
            public void onResourceReady(File resource, Transition<? super File> transition) {
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inJustDecodeBounds = true;
                BitmapFactory.decodeFile(resource.getPath(), options);

                if (ImageUtil.readPictureDegree(resource.getAbsolutePath()) % 180 != 0) {
                    int oldWidth = options.outWidth;
                    options.outWidth = options.outHeight;
                    options.outHeight = oldWidth;
                }
                float scaleRate = MyApplication.screenWidth * 1.0f / options.outWidth;
                imageView.setImage(ImageSource.uri(resource.getAbsolutePath()), new ImageViewState(scaleRate, new PointF(0, 0), ImageUtil.readPictureDegree(resource.getAbsolutePath())));
                imageView.setInitScale(scaleRate);
                imageView.setMinScale(scaleRate / 2);
                imageView.setMaxScale(scaleRate * 3);
                imageView.resetScaleAndCenter();
                imageView.setDoubleTapZoomScale(scaleRate * 3);
            }
        });
        view.addView(imageLayout, 0);
        return imageLayout;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }

    //确保图片能正常删除
    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }
}

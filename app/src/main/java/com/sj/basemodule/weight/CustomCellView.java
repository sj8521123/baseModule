package com.sj.basemodule.weight;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.sj.basemodule.util.ScreenUtil;

/**
 * create by shijun@lixin360.com on 2019/5/31.
 */
public class CustomCellView extends LinearLayout {
    private ImageView mImageView;
    private TextView mTextView;
    public CustomCellView(Context context) {
        super(context);
        init();
    }

    public CustomCellView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CustomCellView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }
    private void init(){
        setOrientation(VERTICAL);
        setGravity(Gravity.CENTER);
        int padding = ScreenUtil.dip2px(10);
        setPadding(padding,padding,padding,padding);
        mImageView = new ImageView(getContext());
        addView(mImageView,ScreenUtil.dip2px(110),ScreenUtil.dip2px(72));
        mTextView = new TextView(getContext());
        mTextView.setPadding(0,padding,0,0);
        addView(mTextView,LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
    }
    public void setImageUrl(String url){
        Glide.with(this).load(url).into(mImageView);
    }
    public void setText(String text){
        mTextView.setText(text);
    }
}

package com.sj.basemodule.weight;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sj.basemodule.R;
import com.sj.basemodule.util.ScreenUtil;
import com.tmall.wireless.tangram.structure.BaseCell;
import com.tmall.wireless.tangram.structure.view.ITangramViewLifeCycle;

import java.util.Locale;

public class CustomInterfaceView extends LinearLayout implements ITangramViewLifeCycle {
    private ImageView mImageView;
    private TextView mTextView;

    public CustomInterfaceView(Context context) {
        super(context);
        init();
    }

    public CustomInterfaceView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CustomInterfaceView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setOrientation(VERTICAL);
        setGravity(Gravity.CENTER);
        int padding = ScreenUtil.dip2px(10);
        setPadding(padding, padding, padding, padding);
        mImageView = new ImageView(getContext());
        addView(mImageView, LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        mTextView = new TextView(getContext());
        mTextView.setPadding(0, padding, 0, 0);
        addView(mTextView, LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
    }

    @Override
    public void cellInited(BaseCell cell) {

    }

    @Override
    public void postBindView(BaseCell cell) {
        if (cell.pos % 2 == 0) {
            setBackgroundColor(0xffff0000);
            mImageView.setImageResource(R.mipmap.ic_launcher);
        } else {
            setBackgroundColor(0xff00ff00);
            mImageView.setImageResource(R.mipmap.ic_launcher_round);
        }
        mTextView.setText(String.format(Locale.CHINA, "%s%d : %s", getClass().getSimpleName(), cell.pos, cell.optParam("text")));
    }

    @Override
    public void postUnBindView(BaseCell cell) {

    }
}

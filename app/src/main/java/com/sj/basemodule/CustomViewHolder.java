package com.sj.basemodule;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.tmall.wireless.tangram.structure.viewcreator.ViewHolderCreator;


/**
 * create by shijun@lixin360.com on 2019/5/31.
 */
public class CustomViewHolder extends ViewHolderCreator.ViewHolder {
    public TextView textView;

    public CustomViewHolder(Context context) {
        super(context);
    }

    @Override
    protected void onRootViewCreated(View view) {
        textView = (TextView) view;
    }
}
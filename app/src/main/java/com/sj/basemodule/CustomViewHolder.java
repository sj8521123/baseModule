package com.sj.basemodule;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.tmall.wireless.tangram.structure.viewcreator.ViewHolderCreator;

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
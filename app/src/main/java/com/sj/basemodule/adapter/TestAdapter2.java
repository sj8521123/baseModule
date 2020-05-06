package com.sj.basemodule.adapter;

import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.sj.basemodule.R;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class TestAdapter2 extends BaseQuickAdapter<String, BaseViewHolder> {
    private List<String> datas;

    public TestAdapter2(int layoutResId, @Nullable List<String> data) {
        super(layoutResId, data);
        this.datas = data;
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, String item) {
        helper.setText(R.id.name, item);
        int pos = helper.getAdapterPosition();
        TextView textView = helper.getView(R.id.name);
        LinearLayout linearLayout = helper.getView(R.id.linear);
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) textView.getLayoutParams();
        if (pos == 0) {
            linearLayout.setGravity(Gravity.LEFT);
            //layoutParams.gravity = Gravity.LEFT;
        } else if (pos == datas.size() - 1) {
            //layoutParams.gravity = Gravity.RIGHT;
            linearLayout.setGravity(Gravity.RIGHT);
        } else {
            linearLayout.setGravity(Gravity.CENTER);
            //layoutParams.gravity = Gravity.CENTER;
        }
        textView.setLayoutParams(layoutParams);
    }
}
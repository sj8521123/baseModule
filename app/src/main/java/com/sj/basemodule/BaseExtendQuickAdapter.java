package com.sj.basemodule;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * Created by 13658 on 2018/8/8.
 */

public abstract class BaseExtendQuickAdapter<T, K extends BaseExtendQuickAdapter.ExtendBaseViewHolder> extends BaseQuickAdapter<T,K> {
    List<T> data;

    public BaseExtendQuickAdapter(@Nullable List<T> data) {
        super(data);
    }
    public class ExtendBaseViewHolder extends BaseViewHolder {
        public List payloads;

        public ExtendBaseViewHolder(View view) {
            super(view);
        }
    }
}

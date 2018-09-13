package com.sj.basemodule;

import android.view.View;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.util.List;

/**
 * Created by 13658 on 2018/8/8.
 */

public abstract class BaseExtendMultiItemQuickAdapter<T extends MultiItemEntity, VH extends BaseExtendMultiItemQuickAdapter.ExtendBaseViewHolder> extends BaseMultiItemQuickAdapter<T, VH> {
    public BaseExtendMultiItemQuickAdapter(List<T> data) {
        super(data);
    }

    @Override
    public void onBindViewHolder(VH holder, int position, List<Object> payloads) {
        holder.payloads = payloads;
        super.onBindViewHolder(holder, position);
    }

    public class ExtendBaseViewHolder extends BaseViewHolder {
        public List payloads;

        public ExtendBaseViewHolder(View view) {
            super(view);
        }
    }
}
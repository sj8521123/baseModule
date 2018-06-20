package com.sj.basemodule;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * Created by 13658 on 2018/5/23.
 */

public class DataAdapter extends BaseQuickAdapter<Book, BaseViewHolder> {
    public DataAdapter(@LayoutRes int layoutResId, @Nullable List<Book> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Book item) {
    /*    helper.setText(R.id.id, String.valueOf(item.getId()))
                .setText(R.id.author, item.getAuthor())
                .setText(R.id.price, String.valueOf(item.getPrice()))
                .setText(R.id.pages, String.valueOf(item.getPages()))
                .setText(R.id.name, item.getName());*/
    }
}

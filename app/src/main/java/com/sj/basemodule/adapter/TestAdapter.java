package com.sj.basemodule.adapter;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseSectionQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.sj.basemodule.OtherModel;
import com.sj.basemodule.R;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class TestAdapter extends BaseMultiItemQuickAdapter<OtherModel, BaseViewHolder> {
    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */
    public TestAdapter(List<OtherModel> data) {
        super(data);
        addItemType(OtherModel.TEXT_HEAD, R.layout.item_head);
        addItemType(OtherModel.TEXT_CONTENT, R.layout.item_content);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, OtherModel item) {

        switch (helper.getItemViewType()) {
            case OtherModel.TEXT_HEAD:
                helper.itemView.setTag(true);
                helper.setText(R.id.head, item.getStr());
                break;
            case OtherModel.TEXT_CONTENT:
                helper.itemView.setTag(false);
                helper.setText(R.id.name, item.getStr());
                break;
        }
    }
}

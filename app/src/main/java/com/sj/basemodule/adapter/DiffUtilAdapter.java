package com.sj.basemodule.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.sj.basemodule.R;

import java.util.List;

import androidx.annotation.NonNull;

/**
 * Create adapter
 */
public class DiffUtilAdapter extends BaseQuickAdapter<DiffUtilDemoEntity, BaseViewHolder> {
    public static final int TITLE_PAYLOAD = 899;
    public static final int CONTENT_PAYLOAD = 900;
    public static final int ITEM_0_PAYLOAD = 901;

    public DiffUtilAdapter(List<DiffUtilDemoEntity> list) {
        super(R.layout.layout_animation, list);
    }

    @Override
    protected void convert(BaseViewHolder helper, DiffUtilDemoEntity item) {
        helper.setText(R.id.tweetName, item.getTitle())
                .setText(R.id.tweetText, item.getContent())
                .setText(R.id.tweetDate, item.getDate());
    }

    /**
     * This method will only be executed when there is payload info
     * <p>
     * 当有 payload info 时，只会执行此方法
     *
     * @param helper   A fully initialized helper.
     * @param item     The item that needs to be displayed.
     * @param payloads payload info.
     */
    @Override
    protected void convertPayloads(@NonNull BaseViewHolder helper, DiffUtilDemoEntity item, @NonNull List<Object> payloads) {
        for (Object p : payloads) {
            int payload = (int) p;
            if (payload == TITLE_PAYLOAD) {
                helper.setText(R.id.tweetName, item.getTitle());
            } else if (payload == CONTENT_PAYLOAD) {
                helper.setText(R.id.tweetText, item.getContent());
            } else if (payload == ITEM_0_PAYLOAD) {
                helper.setText(R.id.tweetName, item.getTitle())
                        .setText(R.id.tweetText, item.getContent());
            }
        }
    }
}

package com.sj.basemodule.model;

import android.support.annotation.NonNull;

import com.sj.basemodule.weight.CustomCellView;
import com.tmall.wireless.tangram.MVHelper;
import com.tmall.wireless.tangram.structure.BaseCell;

import org.json.JSONException;
import org.json.JSONObject;
public class CustomCell extends BaseCell<CustomCellView> {
    private String imageUrl;
    private String text;

    @Override
    public void parseWith(@NonNull JSONObject data, @NonNull MVHelper resolver) {
        try {
            if (data.has("imageUrl")) {
                imageUrl = data.getString("imageUrl");
            }
            if (data.has("text")) {
                text = data.getString("text");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void bindView(@NonNull CustomCellView view) {
        if (pos % 2 == 0) {
            view.setBackgroundColor(0xffff00ff);
        } else {
            view.setBackgroundColor(0xffffff00);
        }
        view.setImageUrl(imageUrl);
        view.setText(view.getClass().getSimpleName() + pos + ": " + text);
    }
}
package com.sj.basemodule;

import androidx.annotation.NonNull;
import android.widget.TextView;


import com.tmall.wireless.tangram.structure.BaseCell;

import java.util.Locale;

public class CustomHolderCell extends BaseCell<TextView> {

    @Override
    public void bindView(@NonNull TextView view) {
        if (pos % 2 == 0) {
            view.setBackgroundColor(0xff000fff);
        } else {
            view.setBackgroundColor(0xfffff000);
        }
        view.setText(String.format(Locale.CHINA, "%s%d: %s", getClass().getSimpleName(), pos,
                optParam("text")));
    }

}

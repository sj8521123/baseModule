package com.sj.basemodule.weight;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.TextView;

import com.sj.basemodule.R;


/**
 * 两个按钮的通用对话框
 * 史俊
 */
public class CommonDoubleButtonDialog {
    public CommonDoubleButtonDialog() {
    }

    public static class Builder {
        /**
         * 上个界面传来的数据或者对象
         */
        Context context;
        //标题
        private String title;
        //内容
        private String message;
        //按钮内容
        private String confirmTxt;
        private String cancleTxt;
        private int confirmColor;
        private int cancleColor;
        //标题Icon
        private int iconId;
        //确定
        private DialogInterface.OnClickListener positiveListener;
        //取消
        private DialogInterface.OnClickListener negativeListener;
        //Dialog dimiss的监听
        private DialogInterface.OnDismissListener onDismissListener;
        //Dialog show的监听
        private DialogInterface.OnShowListener onShowListener;

        public Builder(Context context) {
            this.context = context;
        }

        public Builder setMessage(String message) {
            this.message = message;
            return this;
        }

        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }

        public Builder setTitle(int resId) {
            this.title = context.getString(resId);
            return this;
        }

        public Builder setIconId(int iconId) {
            this.iconId = iconId;
            return this;
        }

        public Builder setPositiveButton(String confirm, int confirmColor, DialogInterface.OnClickListener confirmClickListener) {
            this.positiveListener = confirmClickListener;
            this.confirmTxt = confirm;
            this.confirmColor = confirmColor;
            return this;
        }

        public Builder setNegativeButton(String cancle, int cancleColor, DialogInterface.OnClickListener cancleClickListener) {
            this.negativeListener = cancleClickListener;
            this.cancleTxt = cancle;
            this.cancleColor = cancleColor;
            return this;
        }

        public Builder setOnDismissListener(DialogInterface.OnDismissListener onDismissListener) {
            this.onDismissListener = onDismissListener;
            return this;
        }

        public Builder setOnShowListener(DialogInterface.OnShowListener onShowListener) {
            this.onShowListener = onShowListener;
            return this;
        }

        public Dialog create() {
            ViewHolder viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            final Dialog dialog = new Dialog(context, R.style.baseDialog);
            View layout = inflater.inflate(R.layout.dialog_common_duble_button, null);
            //初始化布局文件
            initView(layout, viewHolder);
            //绑定数据到布局文件对应的控件
            bindViewHolder(viewHolder, dialog);
            dialog.setContentView(layout);
            return dialog;
        }

        class ViewHolder {
            ImageView icon;
            TextView titleTxt;
            TextView messageTxt;
            TextView positive;
            TextView negative;
        }

        private void initView(View layout, ViewHolder viewHolder) {
            viewHolder.icon = (ImageView) layout.findViewById(R.id.icon);
            viewHolder.titleTxt = (TextView) layout.findViewById(R.id.title);
            viewHolder.messageTxt = (TextView) layout.findViewById(R.id.message);
            viewHolder.positive = (TextView) layout.findViewById(R.id.ok);
            viewHolder.negative = (TextView) layout.findViewById(R.id.cancle);
        }

        private void bindViewHolder(final ViewHolder viewHolder, final Dialog dialog) {
            //绑定数据
            if (iconId != 0) {
                viewHolder.icon.setImageResource(iconId);
            }
            if (!TextUtils.isEmpty(title)) {
                viewHolder.titleTxt.setText(title);
            }
            if (!TextUtils.isEmpty(message)) {
                viewHolder.messageTxt.setText(message);
                //未超过1行居中显示，超过1行默认左对齐
                viewHolder.messageTxt.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
                    @Override
                    public boolean onPreDraw() {
                        viewHolder.messageTxt.getViewTreeObserver().removeOnPreDrawListener(this);
                        if (viewHolder.messageTxt.getLineCount() == 1) {
                            viewHolder.messageTxt.setGravity(Gravity.CENTER);
                        }
                        return true;
                    }
                });
            }
            if (!TextUtils.isEmpty(confirmTxt)) {
                viewHolder.positive.setText(confirmTxt);
            }
            if (!TextUtils.isEmpty(cancleTxt)) {
                viewHolder.negative.setText(cancleTxt);
            }
            if (cancleColor != 0) {
                viewHolder.negative.setTextColor(cancleColor);
            }
            if (confirmColor != 0) {
                viewHolder.positive.setTextColor(confirmColor);
            }

            if (positiveListener != null) {
                viewHolder.positive.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        positiveListener.onClick(dialog, DialogInterface.BUTTON_POSITIVE);
                    }
                });
            }
            if (negativeListener != null) {
                viewHolder.negative.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        negativeListener.onClick(dialog, DialogInterface.BUTTON_NEGATIVE);
                    }
                });
            }
            if (onDismissListener != null) {
                dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        onDismissListener.onDismiss(dialog);
                    }
                });
            }
            if (onShowListener != null) {
                dialog.setOnShowListener(new DialogInterface.OnShowListener() {
                    @Override
                    public void onShow(DialogInterface dialog) {
                        onShowListener.onShow(dialog);
                    }
                });
            }
        }

    }
}

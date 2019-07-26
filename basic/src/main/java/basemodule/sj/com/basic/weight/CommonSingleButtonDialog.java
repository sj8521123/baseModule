package basemodule.sj.com.basic.weight;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import basemodule.sj.com.basic.R;


/**
 * Created by sj on 2016/3/30.
 */
public class CommonSingleButtonDialog extends Dialog {
    public CommonSingleButtonDialog(Context context, int themeResId) {
        super(context, themeResId);
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
        private int confirmColor;
        //标题Icon
        private int iconId;
        //确定
        private OnClickListener positiveListener;
        //Dialog dimiss的监听
        private OnDismissListener onDismissListener;
        //Dialog show的监听
        private OnShowListener onShowListener;

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

        public Builder setIconId(int iconId) {
            this.iconId = iconId;
            return this;
        }

        public Builder setPositiveButton(String confirm, int confirmColor, OnClickListener confirmClickListener) {
            this.positiveListener = confirmClickListener;
            this.confirmTxt = confirm;
            this.confirmColor = confirmColor;
            return this;
        }

        public Builder setOnDismissListener(OnDismissListener onDismissListener) {
            this.onDismissListener = onDismissListener;
            return this;
        }

        public Builder setOnShowListener(OnShowListener onShowListener) {
            this.onShowListener = onShowListener;
            return this;
        }

        public Dialog create() {
            ViewHoder viewHoder = new ViewHoder();
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            final Dialog dialog = new Dialog(context, R.style.baseDialog);
            View layout = inflater.inflate(R.layout.dialog_common_sigle_button, null);
            //初始化布局文件
            initView(layout, viewHoder);
            //为对话框添加布局
            dialog.addContentView(layout, new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            //绑定数据到布局文件对应的控件
            bindViewHolder(viewHoder, dialog);
            //layout添加到dialog上
            dialog.setContentView(layout);
            return dialog;
        }

        private void initView(View layout, ViewHoder viewHoder) {
            viewHoder.icon = (ImageView) layout.findViewById(R.id.icon);
            viewHoder.titletxt = (TextView) layout.findViewById(R.id.title);
            viewHoder.messagetxt = (TextView) layout.findViewById(R.id.message);
            viewHoder.positive = (Button) layout.findViewById(R.id.ok);
        }

        private void bindViewHolder(final ViewHoder viewHoder, final Dialog dialog) {
            if (iconId != 0) {
                viewHoder.icon.setImageResource(iconId);
            }
            if (!TextUtils.isEmpty(title)) {
                viewHoder.titletxt.setText(title);
            }
            if (!TextUtils.isEmpty(message)) {
                viewHoder.messagetxt.setText(message);
                //未超过1行居中显示，超过1行默认左对齐
                viewHoder.messagetxt.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
                    @Override
                    public boolean onPreDraw() {
                        viewHoder.messagetxt.getViewTreeObserver().removeOnPreDrawListener(this);
                        //未超过一行，居中对齐
                        if (viewHoder.messagetxt.getLineCount() == 1) {
                            viewHoder.messagetxt.setGravity(Gravity.CENTER_HORIZONTAL);
                        }
                        //两行及以上，居左对齐
                        return true;
                    }
                });
            }
            if (!TextUtils.isEmpty(confirmTxt)) {
                viewHoder.positive.setText(confirmTxt);
            }
            if (confirmColor != 0) {
                viewHoder.positive.setTextColor(confirmColor);
            }
            /**
             * 绑定监听
             */
            if (positiveListener != null) {
                viewHoder.positive.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        positiveListener.onClick(dialog, DialogInterface.BUTTON_POSITIVE);
                    }
                });
            }
            if (onDismissListener != null) {
                dialog.setOnDismissListener(new OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        onDismissListener.onDismiss(dialog);
                    }
                });
            }
            if (onShowListener != null) {
                dialog.setOnShowListener(new OnShowListener() {
                    @Override
                    public void onShow(DialogInterface dialog) {
                        onShowListener.onShow(dialog);
                    }
                });
            }
        }

        class ViewHoder {
            ImageView icon;
            TextView titletxt;
            TextView messagetxt;
            Button positive;
        }
    }

}

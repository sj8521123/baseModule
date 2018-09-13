package com.sj.basemodule.common;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


import com.sj.basemodule.R;
import com.sj.basemodule.base.BaseActivity;
import com.sj.basemodule.weight.CommonDoubleButtonDialog;
import com.sj.basemodule.weight.CustomViewPager;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnPageChange;


public class ImagePreviewActivity extends BaseActivity {
    //开始页
    public static final String START_PAGE = "startPage";
    //图片数据
    public static final String IMAGE_DATA = "imageData";
    //预览时是否显示删除图标
    public static final String IS_SHOW_DELETE_ICON = "isShowDeleteIcon";
    @BindView(R.id.delete)
    ImageView delete;
    @BindView(R.id.imageNote_vp_pager)
    CustomViewPager viewPager;
    @BindView(R.id.content)
    TextView content;
    ImagePreviewAdapter mAdapter;
    private boolean isDelete;
    private boolean isShowDeleteIcon;
    private ArrayList<Uri> imageUris;

    @Override
    protected void reConnect() {

    }

    @Override
    public int initLayout() {
        return R.layout.activity_image_preview;
    }

    @Override
    public void initFromData() {

    }

    @Override
    public void initLayoutView() {
        imageUris = getIntent().getParcelableArrayListExtra(IMAGE_DATA);
        isShowDeleteIcon = getIntent().getBooleanExtra(IS_SHOW_DELETE_ICON, false);
        if (isShowDeleteIcon) {
            delete.setVisibility(View.VISIBLE);
        } else {
            delete.setVisibility(View.INVISIBLE);
        }
        int startPage = getIntent().getIntExtra(START_PAGE, 0);
        content.setText((startPage + 1) + " / " + imageUris.size());

        mAdapter = new ImagePreviewAdapter(ImagePreviewActivity.this, imageUris);
        viewPager.setAdapter(mAdapter);
        viewPager.setCurrentItem(startPage);

    }

    @Override
    public void initLocalData() {

    }

    @OnClick({R.id.common_back, R.id.delete})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.common_back:
                onBackPressed();
                break;
            case R.id.delete:
                new CommonDoubleButtonDialog.Builder(ImagePreviewActivity.this).setTitle("是否删除?")
                        .setMessage("删除后该张图片后不再显示").setIconId(R.drawable.tanchukuang_jingshi)
                        .setPositiveButton("删除", 0xffEC4444, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                isDelete = true;
                                dialogInterface.dismiss();
                                int current = viewPager.getCurrentItem();
                                int size = imageUris.size();
                                // TODO: 2017/12/26  只剩一张的情况
                                imageUris.remove(current);
                                if (size == 1) {
                                    if (isDelete) {
                                        Intent intent = new Intent();
                                        intent.putParcelableArrayListExtra(IMAGE_DATA, imageUris);
                                        intent.putExtra("isDeleteImg", isDelete);
                                        setResult(Activity.RESULT_OK, intent);
                                    }
                                    ImagePreviewActivity.this.finish();
                                } else {
                                    if (current + 1 == size) {
                                        content.setText((current) + " / " + (size - 1));
                                    } else {
                                        content.setText((current + 1) + " / " + (size - 1));
                                    }
                                    mAdapter.notifyDataSetChanged();
                                }
                            }
                        }).setNegativeButton("取消", 0xff303134, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                }).create().show();
                break;
        }
    }

    @Override
    public void onBackPressed() {
        if (isDelete) {
            Intent intent = new Intent();
            intent.putExtra("isDeleteImg", isDelete);
            intent.putParcelableArrayListExtra(IMAGE_DATA, imageUris);
            setResult(Activity.RESULT_OK, intent);
        }
        super.onBackPressed();
    }

    @OnPageChange(R.id.imageNote_vp_pager)
    public void onPageSelected(int position) {
        content.setText((position + 1) + " / " + imageUris.size());
    }

}

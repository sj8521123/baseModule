package com.sj.basemodule;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.sj.basemodule.base.BaseActivity;
import com.sj.basemodule.common.HomePagerAdapter;
import com.sj.basemodule.mine.MineBaseInfoFragment;
import com.sj.basemodule.mine.MineBaseInfoFragment2;
import com.sj.basemodule.mine.MineBaseInfoFragment3;
import com.sj.basemodule.mine.MineBaseInfoFragment4;
import com.sj.basemodule.util.ToastUtil;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.circlenavigator.CircleNavigator;

import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {
    @BindView(R.id.viewPager)
    ViewPager mViewPager;
    @BindView(R.id.magicIndicator)
    MagicIndicator magicIndicator;
    private static final String TAG = "MainActivity";
    private static final String[] TITLES = new String[]{"日常", "专项", "工作台", "主体"};
    private List<String> mTitleDataList = Arrays.asList(TITLES);
    private HomePagerAdapter mPagerAdapter;

    @Override
    protected void reConnect() {
        ToastUtil.info("reConnect");
    }

    @Override
    public int initLayout() {
        return R.layout.activity_main;
    }

    @Override
    public void initFromData() {

    }

    @Override
    public void initLayoutView() {
        initViewPage();
        initMagicIndicator();
    }

    @Override
    public void initLocalData() {
    }

    private void initViewPage() {
        mPagerAdapter = new HomePagerAdapter(getSupportFragmentManager());

        //基础信息
        mPagerAdapter.addFragment(MineBaseInfoFragment.newInstance());
        //居住信息
        mPagerAdapter.addFragment(MineBaseInfoFragment2.newInstance());
        //工作信息
        mPagerAdapter.addFragment(MineBaseInfoFragment3.newInstance());
        //联系人
        mPagerAdapter.addFragment(MineBaseInfoFragment4.newInstance());

        mViewPager.setAdapter(mPagerAdapter);
    }

    private void initMagicIndicator() {
        CircleNavigator circleNavigator = new CircleNavigator(this);
        circleNavigator.setFollowTouch(false);
        circleNavigator.setCircleCount(4);
        circleNavigator.setCircleColor(Color.RED);
        circleNavigator.setCircleClickListener(new CircleNavigator.OnCircleClickListener() {
            @Override
            public void onClick(int index) {
                mViewPager.setCurrentItem(index);
            }
        });
        magicIndicator.setNavigator(circleNavigator);
        ViewPagerHelper.bind(magicIndicator, mViewPager);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.btn1, R.id.btn2, R.id.btn3,R.id.btn4, R.id.btn5, R.id.btn6})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn1:
                showErrorView(1);
                break;
            case R.id.btn2:
                showErrorView(2);
                break;
            case R.id.btn3:
                showErrorView(3);
                break;
            case R.id.btn4:
                hideErrorView();
                break;
            case R.id.btn5:
                hideErrorView();
                break;
            case R.id.btn6:
                hideErrorView();
                break;
        }
    }
}

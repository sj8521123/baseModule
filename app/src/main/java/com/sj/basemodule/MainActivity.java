package com.sj.basemodule;

import android.graphics.Color;
import android.os.Bundle;
import android.util.ArrayMap;
import android.util.Log;
import android.util.SparseArray;
import android.view.MotionEvent;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import androidx.lifecycle.Observer;
import androidx.viewpager.widget.ViewPager;

import com.alibaba.android.arouter.launcher.ARouter;
import com.hjq.toast.ToastUtils;
import com.jeremyliao.liveeventbus.LiveEventBus;
import com.sj.basemodule.mine.A_MineBaseInfoFragment;
import com.sj.basemodule.mine.B_MineBaseInfoFragment;
import com.sj.basemodule.mine.C_MineBaseInfoFragment;
import com.sj.basemodule.mine.D_MineBaseInfoFragment;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.circlenavigator.CircleNavigator;

import java.util.Arrays;
import java.util.List;

import basemodule.sj.com.basic.adapter.HomePagerAdapter;
import basemodule.sj.com.basic.base.BaseActivity;
import basemodule.sj.com.basic.base.BaseEvent;
import basemodule.sj.com.basic.util.ScreenUtil;
import basemodule.sj.com.basic.weight.transform.AlphaAndScalePageTransformer;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {
    int c = 10;
    @BindView(R.id.viewPager)
    ViewPager mViewPager;
    @BindView(R.id.magicIndicator)
    MagicIndicator magicIndicator;
    private static final String TAG = "MainActivity";
    private static final String[] TITLES = new String[]{"日常", "专项", "工作台", "主体"};
    @BindView(R.id.switch2)
    Switch switch3;
    private List<String> mTitleDataList = Arrays.asList(TITLES);
    private HomePagerAdapter mPagerAdapter;

    @Override
    protected void reConnect() {
        ToastUtils.show("reConnect");
    }

    @Override
    public int initLayout() {
        return R.layout.activity_main;
    }

    private static void p(Object o) {
        System.out.println(o);

    }

    //
    @Override
    public void initFromData() {

        LiveEventBus.get("hello").postDelay("hello World！", 5000);
        LiveEventBus.get("haha", Boolean.class).observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean) {
                    Log.i(TAG, "onChanged: true");
                } else {
                    Log.i(TAG, "onChanged: false");
                }
            }
        });
        LiveEventBus.get("haha", Boolean.class).observeSticky(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean) {
                    Log.i(TAG, "onChanged: true1");
                } else {
                    Log.i(TAG, "onChanged: false1");
                }
            }
        });
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

        ArrayMap<String, String> arrayMap = new ArrayMap<>();
        SparseArray<String> array = new SparseArray<>();

        Toast.makeText(this, "he", Toast.LENGTH_LONG).show();
        initPager();
        switch3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    ToastUtils.show("打开");
                } else {
                    ToastUtils.show("关闭");
                }
            }
        });
        switch3.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    switch3.setChecked(switch3.isChecked());
                }
                return false;
            }
        });
    }

    private void initPager() {
        mPagerAdapter = new HomePagerAdapter(getSupportFragmentManager());

        //基础信息
        mPagerAdapter.addFragment(A_MineBaseInfoFragment.newInstance());
        //居住信息
        mPagerAdapter.addFragment(B_MineBaseInfoFragment.newInstance());
        //工作信息
        mPagerAdapter.addFragment(C_MineBaseInfoFragment.newInstance());
        //联系人
        mPagerAdapter.addFragment(D_MineBaseInfoFragment.newInstance());
        mViewPager.setOffscreenPageLimit(3);
        mViewPager.setPageMargin(-ScreenUtil.dp2px(140));
        mViewPager.setPageTransformer(true, new AlphaAndScalePageTransformer());

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
    public void onEventMainThread(BaseEvent event) {
        super.onEventMainThread(event);
        Log.i(TAG, "onEventMainThread: " + "A");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick(R.id.text)
    public void onViewClicked() {
        ToastUtils.show("haha");
        ARouter.getInstance().build("/app/proxy")
                .withLong("key1",22)
                .withString("key2","haha")
                .withParcelable("key3",new Student(21,"zhangsan"))
                .navigation();
    }
}

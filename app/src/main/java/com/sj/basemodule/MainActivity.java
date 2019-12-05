package com.sj.basemodule;

import android.graphics.Color;
import android.os.Bundle;
import android.util.ArrayMap;
import android.util.Log;
import android.util.LongSparseArray;
import android.util.SparseArray;
import android.util.SparseBooleanArray;
import android.util.SparseIntArray;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.hjq.toast.ToastUtils;
import com.sj.basemodule.mine.A_MineBaseInfoFragment;
import com.sj.basemodule.mine.B_MineBaseInfoFragment;
import com.sj.basemodule.mine.C_MineBaseInfoFragment;
import com.sj.basemodule.mine.D_MineBaseInfoFragment;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.circlenavigator.CircleNavigator;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

import androidx.viewpager.widget.ViewPager;
import basemodule.sj.com.basic.adapter.HomePagerAdapter;
import basemodule.sj.com.basic.base.BaseActivity;
import basemodule.sj.com.basic.base.BaseEvent;
import basemodule.sj.com.basic.util.ScreenUtil;
import basemodule.sj.com.basic.weight.transform.AlphaAndScalePageTransformer;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity {
    int c = 10;
    @BindView(R.id.viewPager)
    ViewPager mViewPager;
    @BindView(R.id.magicIndicator)
    MagicIndicator magicIndicator;
    private static final String TAG = "MainActivity";
    private static final String[] TITLES = new String[]{"日常", "专项", "工作台", "主体"};
    @BindView(R.id.switch2)
    Switch switch2;
    private List<String> mTitleDataList = Arrays.asList(TITLES);
    private HomePagerAdapter mPagerAdapter;

    @Override
    protected void reConnect() {
        new Button(this).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        ToastUtils.show("reConnect");
    }

    @Override
    public int initLayout() {
        return R.layout.activity_main;
    }

    @Override
    public void initFromData() {
     /*   Map<Integer,String> map = new HashMap<>();
        SparseArray<String> sa = new SparseArray<>();
        LongSparseArray<String> saLong = new LongSparseArray<>();
        saLong.put(132123213,"ccc");


        SparseIntArray s2 = new SparseIntArray();
        s2.put(1,2);

        SparseBooleanArray  sa1 = new SparseBooleanArray();
        sa1.put(1,true);
        sa1.append(2,false);
        sa1.keyAt(0);
        sa1.valueAt(1);

        sa.put(100,"leavesC");


        ArrayMap<Integer,String> map1 = new ArrayMap<>(10);
        map.put(1,"23");*/
    }

    @Override
    public void initLayoutView() {
        initViewPage();
        initMagicIndicator();
    }

    @Override
    public void initLocalData() {

    }

    private Void test() {
        return null;
    }

    private void initViewPage() {
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
        new Thread(new FutureTask<>(new Callable<String>() {
            @Override
            public String call() throws Exception {
                return "hello";
            }
        })).start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                ThreadGroup tg = new ThreadGroup("线程组一");
                for (int i = 0; i < 10; i++) {
                    new Thread(tg, "子线程" + (i + 1)) {
                        @Override
                        public void run() {
                            while (!Thread.currentThread().isInterrupted()) {
                                Log.i(TAG, Thread.currentThread().getName() + ":run");
                            }
                        }
                    }.start();
                }
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                tg.interrupt();
            }
        }).start();
        switch2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    ToastUtils.show("打开");
                } else {
                    ToastUtils.show("关闭");
                }
            }
        });
        switch2.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    switch2.setChecked(switch2.isChecked());
                }
                return false;
            }
        });
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
}

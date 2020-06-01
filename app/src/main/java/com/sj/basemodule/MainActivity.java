package com.sj.basemodule;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.Log;
import android.util.SparseArray;
import android.util.SparseBooleanArray;
import android.util.SparseIntArray;
import android.view.MotionEvent;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import androidx.lifecycle.Observer;
import androidx.viewpager.widget.ViewPager;

import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.facade.callback.NavCallback;
import com.alibaba.android.arouter.facade.callback.NavigationCallback;
import com.alibaba.android.arouter.launcher.ARouter;
import com.google.gson.Gson;
import com.hjq.toast.ToastUtils;
import com.jeremyliao.liveeventbus.LiveEventBus;
import com.just.agentweb.LogUtils;
import com.sj.basemodule.bean.UserBean;
import com.sj.basemodule.mine.A_MineBaseInfoFragment;
import com.sj.basemodule.mine.B_MineBaseInfoFragment;
import com.sj.basemodule.mine.C_MineBaseInfoFragment;
import com.sj.basemodule.mine.D_MineBaseInfoFragment;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.circlenavigator.CircleNavigator;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import basemodule.sj.com.basic.adapter.HomePagerAdapter;
import basemodule.sj.com.basic.base.BaseActivity;
import basemodule.sj.com.basic.base.BaseEvent;
import basemodule.sj.com.basic.util.ScreenUtil;
import basemodule.sj.com.basic.weight.transform.AlphaAndScalePageTransformer;
import butterknife.Action;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Function5;

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
        //int -> integer 装箱， integer ->int 拆箱
        array.put(1, "asd");
        Toast.makeText(this, "he", Toast.LENGTH_LONG).show();
        initPager();
        switch3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    enablingWiFiDisplay();
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
        ArrayMap<String, String> map = new ArrayMap<>();
        map.put("sd", "ds");
     /*   if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            ArraySet<String> set = new ArraySet<>();
        }*/
        SparseArray<String> sparseArray = new SparseArray<>();
        sparseArray.put(1, "sd");
        SparseIntArray sparseIntArray = new SparseIntArray();
        sparseIntArray.put(1, 1);
        SparseBooleanArray sparseBooleanArray = new SparseBooleanArray();
        sparseBooleanArray.put(1, true);
    }

    public void enablingWiFiDisplay() {
        try {
            startActivity(new Intent("android.settings.WIFI_DISPLAY_SETTINGS"));
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
            try {
                startActivity(new Intent("com.samsung.wfd.LAUNCH_WFD_PICKER_DLG"));
            } catch (Exception e2) {
                try {
                    startActivity(new Intent(Settings.ACTION_CAST_SETTINGS));
                } catch (Exception e3) {
                    Toast.makeText(getApplicationContext(), "Device not supported", Toast.LENGTH_LONG).show();
                }
            }
        }
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
        ARouter.getInstance().inject(this);
    }

    @OnClick(R.id.text)
    public void onViewClicked() {
        Log.i(TAG, "onViewClicked: " + ARouter.getInstance().navigation(TestProvider.class).test());
        ToastUtils.show("haha");
        /*Uri testUriMix = Uri.parse("arouter://m.aliyun.com/app/proxy");*/
        ARouter.getInstance().build("/app/proxy")
                .withLong("key1", 22)
                .withString("key2", "haha")
                .withObject("key4", new People(22, "shijun"))
                .withParcelable("key3", new Student(21, "zhangsan"))
                .navigation(this, new LoginNavigationCallbackImpl());
    }

    // 拦截的回调
    public class LoginNavigationCallbackImpl implements NavigationCallback {
        @Override //找到了
        public void onFound(Postcard postcard) {

        }

        @Override //找不到了
        public void onLost(Postcard postcard) {

        }

        @Override    //跳转成功了
        public void onArrival(Postcard postcard) {

        }

        @Override
        public void onInterrupt(Postcard postcard) {
            String path = postcard.getPath();
            Log.i(TAG, "onInterrupt: " + path);
         /*   Bundle bundle = postcard.getExtras();
            // 被登录拦截了下来了
            // 需要调转到登录页面，把参数跟被登录拦截下来的路径传递给登录页面，登录成功后再进行跳转被拦截的页面
            ARouter.getInstance().build(ConfigConstants.LOGIN_PATH)
                    .with(bundle)
                    .withString(ConfigConstants.PATH, path)
                    .navigation();*/
        }
    }

}

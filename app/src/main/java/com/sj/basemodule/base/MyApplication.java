package com.sj.basemodule.base;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.multidex.MultiDexApplication;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.readystatesoftware.systembartint.SystemBarTintManager;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.sj.basemodule.R;
import com.sj.basemodule.config.KeyAndValueAppPrefs;
import com.sj.basemodule.config.SPUtils;
import com.sj.basemodule.util.ToastUtil;
import com.sj.basemodule.util.file.STGFileUtil;
import com.squareup.picasso.Picasso;
import com.tencent.bugly.crashreport.CrashReport;
import com.tmall.wireless.tangram.TangramBuilder;
import com.tmall.wireless.tangram.util.IInnerImageSetter;
import com.zhihu.matisse.ui.MatisseActivity;

import org.litepal.LitePal;
import org.litepal.LitePalApplication;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import es.dmoral.toasty.Toasty;

/**
 * 自定义Application用来做一些初始化配置
 */
public class MyApplication extends LitePalApplication {
    //创建的activity容器
    public static List<Activity> mList = new LinkedList<>();
    //全局上下文
    public static MyApplication mAppContext;
    //包名
    public static String packageName;
    //屏幕宽 与 屏幕高
    public static int screenWidth;
    public static int screenHeight;

    //当前用户配置文件
    public static String currentUserPrefsName;

    // 获取ApplicationContext
    public static Context getContext() {
        return mAppContext;
    }

    //退出所有Activity
    public static void exit() {
        for (Activity activity : mList) {
            activity.finish();
        }
        mList.clear();
    }

    public static void initSystemBar(Activity activity, int colorRes) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setTranslucentStatus(activity, true);
        }
        SystemBarTintManager tintManager = new SystemBarTintManager(activity);
        //开启StatusBar的沉浸式
        tintManager.setStatusBarTintEnabled(true);
        // 使用颜色资源
        tintManager.setStatusBarTintColor(colorRes);
    }

    @TargetApi(19)
    private static void setTranslucentStatus(Activity activity, boolean on) {
        Window win = activity.getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mAppContext = this;
        packageName = this.getPackageName();
        currentUserPrefsName = SPUtils.getInstance().getString(KeyAndValueAppPrefs.Key.CURRENT_USER_PREF_NAME);

        //本地配置
        initLocalConfiguration();
        //第三方配置
        initExternalConfiguration();

        //比BaseActivity更优雅的封装类。统一管理所有Activity
        registerActivityLifecycleCallbacks(new Application.ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(final Activity activity, Bundle bundle) {
                if (activity instanceof BaseActivity) {
                    try {
                        activity.setContentView(((BaseActivity) activity).initLayout());
                    } catch (Exception e) {
                        ToastUtil.fail("activity的layout为空，请在activity中先添加布局");
                    }

                    //统一ButterKnife绑定Activity
                    initButterKnife(activity);
                    //针对toolBar统一处理
                    initToolBar(activity);
                    //刷新统一处理
                    /*  initRefreshLayout(activity);*/
                    //初始化来源数据
                    ((BaseActivity) activity).initFromData();
                    //在初始化布局
                    ((BaseActivity) activity).initLayoutView();
                    //初始化本地数据
                    ((BaseActivity) activity).initLocalData();

                    //设置xml所有viewGroup的fitsSystemWindows="true" 沉浸式
                    ViewGroup content = activity.getWindow().getDecorView().findViewById(android.R.id.content);
                    content.getChildAt(0).setFitsSystemWindows(true);

                } else {
                    //统一ButterKnife绑定Activity
                    initButterKnife(activity);
                    initToolBar(activity);
                }
                //统一添加到list集合中
                mList.add(activity);
                //统一沉浸式颜色 排除MatisseActivity图片选择activity
                if (!(activity instanceof MatisseActivity)) {
                    initSystemBar(activity, Color.parseColor("#ff373E47"));
                }
            }

            @Override
            public void onActivityStarted(Activity activity) {

            }

            @Override
            public void onActivityResumed(Activity activity) {

            }

            @Override
            public void onActivityPaused(Activity activity) {

            }

            @Override
            public void onActivityStopped(Activity activity) {

            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {

            }

            @Override
            public void onActivityDestroyed(Activity activity) {
                //统一移除
                mList.remove(activity);

                ActivityBean bean = (ActivityBean) activity.getIntent().getSerializableExtra("ActivityBean");

                bean.getUnbinder().unbind();

            }
        });
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        if (newConfig.fontScale != 1)//非默认值
            getResources();
        super.onConfigurationChanged(newConfig);
    }

    private void initLocalConfiguration() {
        //数据库操作初始化
        /*LitePal.initialize(this);*/

        screenWidth = getScreenWidth();
        screenHeight = getScreenHeight();
        //创建目录
        try {
            STGFileUtil.createAllDirs();
        } catch (IOException e) {
            e.printStackTrace();
            ToastUtil.fail("创建项目目录出错!");
        }
    }

    private void initExternalConfiguration() {
        //bugly集成
        CrashReport.initCrashReport(getApplicationContext(), "c97fd3e3e2", false);

        TangramBuilder.init(mAppContext, new IInnerImageSetter() {
            @Override
            public <IMAGE extends ImageView> void doLoadImageUrl(@NonNull IMAGE view,
                                                                 @Nullable String url) {
                //假设你使用 Picasso 加载图片
                Glide.with(mAppContext).load(url).into(view);
            }
        }, ImageView.class);
        //toast设置
       /* Toasty.Config.getInstance().setSuccessColor(Color.parseColor("#c832C25E"))
                .setErrorColor(Color.parseColor("#c8F95557"))
                .setInfoColor(Color.parseColor("#c84C5460"))
                .apply();*/

        /*//ZXing初始化
        ZXingLibrary.initDisplayOpinion(this);
        //百度地图初始化
        SDKInitializer.initialize(this);*/
    }

    private void initButterKnife(Activity activity) {
        ActivityBean bean = new ActivityBean();
        Unbinder unbinder = ButterKnife.bind(activity);
        bean.setUnbinder(unbinder);
        activity.getIntent().putExtra("ActivityBean", bean);
    }

    private void initToolBar(final Activity activity) {
        //统一处理toolBar
        if (activity.findViewById(R.id.toolbar) != null) {
            View view = activity.findViewById(R.id.common_title);
            if (view != null) { //找到 Toolbar 的标题栏并设置标题名
                ((TextView) view).setText(activity.getTitle());
            }
            view = activity.findViewById(R.id.common_back);
            if (view != null) { //找到 Toolbar 的返回按钮,并且设置点击事件,点击关闭这个 Activity
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        activity.onBackPressed();
                    }
                });
            }
        }
    }

    /**
     * 获得屏幕宽度
     *
     * @return
     */
    private int getScreenWidth() {
        WindowManager wm = (WindowManager) mAppContext
                .getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.widthPixels;
    }

    /**
     * 获得屏幕高度
     *
     * @return
     */
    private int getScreenHeight() {
        WindowManager wm = (WindowManager) mAppContext
                .getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.heightPixels;
    }

    @Override
    public Resources getResources() {
        Resources res = super.getResources();
        if (res.getConfiguration().fontScale != 1) {//非默认值
            Configuration newConfig = new Configuration();
            newConfig.setToDefaults();//设置默认
            res.updateConfiguration(newConfig, res.getDisplayMetrics());
        }
        return res;
    }

    //具有ios越界弹性滑动
    private void initRefreshLayout(Activity activity) {
   /*     SmartRefreshLayout refreshLayout = activity.findViewById(R.id.refreshLayout);
        refreshLayout.setEnablePureScrollMode(true);
        refreshLayout.setEnableOverScrollDrag(true);*/
    }

}

package basemodule.sj.com.basic.base;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.billy.android.swipe.SmartSwipe;
import com.billy.android.swipe.SmartSwipeRefresh;
import com.billy.android.swipe.ext.refresh.ArrowHeader;
import com.billy.android.swipe.refresh.ClassicFooter;
import com.hjq.toast.IToastStyle;
import com.hjq.toast.ToastUtils;
import com.hjq.toast.style.ToastQQStyle;
import com.readystatesoftware.systembartint.SystemBarTintManager;
import com.simple.spiderman.SpiderMan;
import com.squareup.leakcanary.LeakCanary;
import com.tencent.bugly.crashreport.CrashReport;
import com.zhihu.matisse.ui.MatisseActivity;

import org.litepal.LitePalApplication;

import java.util.LinkedList;
import java.util.List;

import basemodule.sj.com.basic.R;
import basemodule.sj.com.basic.config.KeyAndValueAppPrefs;
import basemodule.sj.com.basic.config.SPUtils;
import basemodule.sj.com.basic.util.file.STGFileUtil;
import basemodule.sj.com.basic.util.Util;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 自定义Application用来做一些初始化配置
 */
public class MyApplication extends LitePalApplication {
    //创建的activity容器
    public static List<Activity> mList = new LinkedList<>();
    /*    //全局上下文
        public static MyApplication mAppContext;*/
    //包名
    public static String packageName;
    //屏幕宽 与 屏幕高
 /*   public static int screenWidth;
    public static int screenHeight;*/

    //当前用户配置文件
    public static String currentUserPrefsName;

    // 获取ApplicationContext
  /*  public static Context getContext() {
        return mAppContext;
    }*/

    //退出所有Activity
    public static void exit() {
        for (Activity activity : mList) {
            activity.finish();
        }
        mList.clear();
        //杀掉进程
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(0);
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
        /*mAppContext = this;*/
        packageName = this.getPackageName();
        Util.init(this);

        currentUserPrefsName = (String) SPUtils.getInstance().get(KeyAndValueAppPrefs.Key.CURRENT_USER_PREF_NAME, "");
        Util.setCurrentUserPrefsName(currentUserPrefsName);


        //本地配置
        initLocalConfiguration();
        //第三方配置
        initExternalConfiguration();

        //比BaseActivity更优雅的封装类。统一管理所有Activity
        registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(final Activity activity, Bundle bundle) {
                //强制竖屏
                activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                //保持常亮
                activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

                if (activity instanceof BaseActivity) {
                    try {
                        activity.setContentView(((BaseActivity) activity).initLayout());
                    } catch (Exception e) {
                        ToastUtils.show("activity的layout为空，请在activity中先添加布局");
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

                    //通过decorView获取到设置contentView所有rootView
                    ViewGroup decorView = activity.getWindow().getDecorView().findViewById(android.R.id.content);
                    View rootView = decorView.getChildAt(0);
                    //沉浸式
                    rootView.setFitsSystemWindows(true);

                 /*   //activity侧滑返回
                    SmartSwipe.wrap(rootView)
                            .addConsumer(new StretchConsumer())
                            .enableVertical()                     //仿MIUI拉伸效果的方向为：上下2个方向
                            .addConsumer(new SpaceConsumer())
                            .enableHorizontal();*/
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
        //全局控制界面返回
        //SmartSwipeBack.activityBezierBack(this,null);
        /*SmartSwipeBack.activitySlidingBack(this,null);*/

/*
        screenWidth = getScreenWidth();
        screenHeight = getScreenHeight();*/
        //创建目录
        boolean isCreateDirSuccess = STGFileUtil.createAllDirs();
        if (!isCreateDirSuccess) {
            ToastUtils.show("项目初始化时目录创建失败！");
        }
    }

    private void initExternalConfiguration() {
        //bugly集成
        CrashReport.initCrashReport(getApplicationContext(), "c97fd3e3e2", false);
        //友盟统计
        /*UMConfigure.init(this, "5d2c44780cafb2a0e20000f3", "Umeng", UMConfigure.DEVICE_TYPE_PHONE, "");*/

        //崩溃信息界面展示
        SpiderMan.init(this);
        //LeakCanary内存泄漏分析
        LeakCanary.install(this);
        //本地记录crash日志
        /* new CrashHandler(this).init();*/

        //toast设置
        ToastUtils.init(this, new ToastQQStyle(this));

      /*  Toasty.Config.getInstance().setSuccessColor(Color.parseColor("#c832C25E"))
                .setErrorColor(Color.parseColor("#c8F95557"))
                .setInfoColor(Color.parseColor("#c84C5460"))
                .apply();*/

        /*//ZXing初始化
        ZXingLibrary.initDisplayOpinion(this);
        //百度地图初始化
        SDKInitializer.initialize(this);*/

        //统一下拉样式
        SmartSwipeRefresh.setDefaultRefreshViewCreator(new SmartSwipeRefresh.SmartSwipeRefreshViewCreator() {
            @Override
            public SmartSwipeRefresh.SmartSwipeRefreshHeader createRefreshHeader(Context context) {
                ArrowHeader arrowHeader = new ArrowHeader(context);
                int height = SmartSwipe.dp2px(100, context);
                ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, height);
                arrowHeader.setLayoutParams(layoutParams);
                arrowHeader.setInitializer(new ArrowHeader.IArrowInitializer() {
                    @Override
                    public void onArrowInit(ArrowHeader arrowHeader, com.wuyr.arrowdrawable.ArrowDrawable arrowDrawable) {
                        arrowDrawable.setBowColor(Color.GRAY);
                        arrowDrawable.setArrowColor(Color.BLACK);
                        arrowDrawable.setStringColor(Color.GRAY);
                        arrowDrawable.setLineColor(Color.GRAY);
                        arrowHeader.setBackgroundColor(Color.LTGRAY);
                    }
                });
                return arrowHeader;
            }

            @Override
            public SmartSwipeRefresh.SmartSwipeRefreshFooter createRefreshFooter(Context context) {
                return new ClassicFooter(context);
            }
        });

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
        WindowManager wm = (WindowManager) this
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
        WindowManager wm = (WindowManager) this
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

package com.sj.basemodule;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.google.gson.Gson;
import com.hjq.toast.ToastUtils;
import com.sj.basemodule.mine.A_MineBaseInfoFragment;
import com.sj.basemodule.mine.B_MineBaseInfoFragment;
import com.sj.basemodule.mine.C_MineBaseInfoFragment;
import com.sj.basemodule.mine.D_MineBaseInfoFragment;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.circlenavigator.CircleNavigator;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
    Student student = new Student();
    StringBuilder x = new StringBuilder();

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

    private static void p(Object o) {
        System.out.println(o);
    }
    //

    /**
     *
     */
    @Override
    public void initFromData() {
        x.append(10);
        System.out.println("修改值之前:" + x.toString());
        System.out.println(student.test(x));
        System.out.println("修改值之后:" + x.toString());
        Log.i(TAG, "initFromData: " + "aa.javacc".matches(".*\\.java$"));
        System.out.println(0.0 / 0.0 == 0.0 / 0.0);
    }

    @Override
    public void initLayoutView() {
        initViewPage();
        initMagicIndicator();
    }

    @Override
    public void initLocalData() {
        Pattern p = Pattern.compile("[a-z]{3,10}[0-9]");
        String s = "1234aaaaa678911a";//10个字符
        Matcher m = p.matcher(s);

        if (m.find()) {
            System.out.println(m.start() + " - " + m.end());
        } else {
            System.out.println("not match!");
        }
        String str = "[家庭联系人信息不符合]";
        List<String> str2 = new Gson().fromJson(str, List.class);
       /* Pattern p = Pattern.compile("\\.*");//正则表达式为3~5个数字跟上两个字母
        String s = "123aaa-5423zxx-642oii-00";
        String s2 = "....";
        Matcher m = p.matcher(s);*/
       /* while(m.find()){
            p(m.group(2));
        }*/
        //CASE_INSENSITIVE忽略大小写
       /* Pattern p = Pattern.compile("java", Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher("java Java JAVA JAva I love Java and you ?");
        StringBuffer sb = new StringBuffer();
        int index = 1;
        while (m.find()) {
            //m.appendReplacement(sb, (index++ & 1) == 0 ? "java" : "JAVA"); 较为简洁的写法
            if ((index & 1) == 0) {//偶数
                m.appendReplacement(sb, "java");
            } else {
                m.appendReplacement(sb, "JAVA");
            }
            index++;
        }
        m.appendTail(sb);//把剩余的字符串加入
        p(sb);*/
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

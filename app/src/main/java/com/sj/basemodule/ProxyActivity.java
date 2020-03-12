package com.sj.basemodule;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.facade.service.SerializationService;
import com.alibaba.android.arouter.launcher.ARouter;
import com.sj.basemodule.proxy.DynamicSubject;
import com.sj.basemodule.proxy.ILogin;
import com.sj.basemodule.proxy.RealSubject;
import com.sj.basemodule.proxy.Subject;
import com.sj.basemodule.proxy.UserLoginProxy;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import basemodule.sj.com.basic.base.BaseActivity;
@Route(path = "/app/proxy" )
public class ProxyActivity extends AppCompatActivity {
    private static final String TAG = "ProxyActivity";
    @Autowired
    long key1;
    @Autowired
    String key2;
    @Autowired(name = "key3")
    Student stu;
    @Autowired
    People key4;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_proxy);
        Student student = getIntent().getExtras().getParcelable("key3");
        SerializationService serializationService = ARouter.getInstance().navigation(SerializationService.class);
        serializationService.init(this);
        Student obj = serializationService.parseObject(getIntent().getStringExtra("key4"), Student.class);
        ARouter.getInstance().inject(this);
        Log.i(TAG, "a:" + key1+"   key2："+key2 +"student :"+stu.getName() +key4);
    }

}

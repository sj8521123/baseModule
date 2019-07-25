package com.sj.basemodule;

import android.content.Intent;
import android.widget.LinearLayout;

import com.sj.basemodule.proxy.ILogin;
import com.sj.basemodule.proxy.UserLogin;
import com.sj.basemodule.proxy.UserLoginProxy;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import basemodule.sj.com.basic.base.BaseActivity;
import butterknife.BindView;
import butterknife.OnClick;

public class OtherActivity extends BaseActivity {
    private static final String TAG = "OtherActivity";
    @BindView(R.id.webView)
    LinearLayout webView;

    @Override
    protected void reConnect() {
    }

    @Override
    public int initLayout() {
        return R.layout.fragment_loansign_layout;
    }


    @Override
    public void initFromData() {
          /*  String text = null;
            text.toUpperCase();*/
        /*Date date = new Date();
        AgentWeb.with(this)
                .setAgentWebParent(webView, new LinearLayout.LayoutParams(-1, -1))
                .useDefaultIndicator()
                .createAgentWeb()
                .ready()
                .go("https://mall.eeext.com/?origin=MjMxNDQ&item_type=iqywz_main");*/
    }

    @Override
    public void initLayoutView() {
      /*  TextView openServiceProtocol = findViewById(R.id.openServiceProtocol);
        openServiceProtocol.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);*/
        ILogin iLogin = new UserLoginProxy();
        iLogin.userLogin();

        try {
            ILogin iLogin1 = (ILogin) loadProxy(new UserLogin());
            iLogin1.userLogin();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Object loadProxy(Object target) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        //通过接口Class对象创建代理Class对象
        Class<?> proxyClass = Proxy.getProxyClass(target.getClass().getClassLoader(), target.getClass().getInterfaces());
        //拿到代理Class对象的有参构造方法
        Constructor<?> constructors = proxyClass.getConstructor(InvocationHandler.class);
        //反射创建代理实例
        Object proxy = constructors.newInstance(new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                System.out.println("登录前...");
                Object result = method.invoke(target, args);
                System.out.println("登录后...");
                return result;
            }
        });
        return proxy;

    }

    @Override
    public void initLocalData() {

    }


    @OnClick(R.id.btn)
    public void onViewClicked() {
        startActivity(new Intent(this,MainActivity.class));
    }
}

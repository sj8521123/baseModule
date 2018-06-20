package com.sj.basemodule;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;

import com.sj.basemodule.base.BaseActivity;
import com.sj.basemodule.service.MessengerService;
import com.sj.basemodule.service.MyConstants;

import java.lang.ref.WeakReference;

public class MainActivity extends BaseActivity {
    private static final String TAG = "MainActivity";
    private Messenger clientMessenger = new Messenger(new MyHandler(MainActivity.this));
    private ServiceConnection mConnect = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Messenger client = new Messenger(service);
            Message msg = new Message();
            msg.what = MyConstants.MSG_FROM_CLIENT;
            Bundle bundle = new Bundle();
            bundle.putString("data", "hi ,服务器，我是客户端!");
            msg.setData(bundle);
            msg.replyTo = clientMessenger;
            try {
                client.send(msg);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    @Override
    public int initLayout() {
        return R.layout.activity_main;
    }

    @Override
    public void initFromData() {

    }

    @Override
    public void initLayoutView() {
        Intent intent = new Intent(MainActivity.this, MessengerService.class);
        bindService(intent, mConnect, BIND_AUTO_CREATE);
    }

    @Override
    public void initLocalData() {

    }

    private static class MyHandler extends Handler {
        WeakReference<Activity> weakReference;

        MyHandler(Activity activity) {
            weakReference = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case MyConstants.MSG_FROM_SERVICE:
                    Log.i(TAG, "handleMessage: " + msg.getData().getString("data"));
                    break;
            }
        }
    }
}

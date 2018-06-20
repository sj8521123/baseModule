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
import android.view.View;

import com.sj.basemodule.base.BaseActivity;
import com.sj.basemodule.service.BookManagerService;
import com.sj.basemodule.service.MessengerService;
import com.sj.basemodule.service.MyConstants;

import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {
    private static final String TAG = "MainActivity";
    Messenger clientMessenger = new Messenger(new ClientHandler(MainActivity.this));
    private ServiceConnection mConn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Messenger messenger = new Messenger(service);
            Message message = Message.obtain(null, MyConstants.MSG_FROM_CLIENT);
            Bundle bundle = new Bundle();
            bundle.putString("clientData", "hi service！");
            message.setData(bundle);
            message.replyTo = clientMessenger;
            try {
                messenger.send(message);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };
    private ServiceConnection mBookConn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            IBoomManager iBoomManager = IBoomManager.Stub.asInterface(service);
            try {
                List<Book> list = iBoomManager.getBookList();
                Log.i(TAG, "query book list,list type:" + list.getClass().getCanonicalName());
                Log.i(TAG, "query book list:" + list.toString());

            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
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
    }

    @Override
    public void initLocalData() {

    }

    @OnClick({R.id.send, R.id.aidl})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.send: {
                Intent intent = new Intent(MainActivity.this, MessengerService.class);
                bindService(intent, mConn, BIND_AUTO_CREATE);
            }
            break;
            case R.id.aidl:
                Intent intent = new Intent(MainActivity.this, BookManagerService.class);
                bindService(intent, mBookConn, BIND_AUTO_CREATE);
                break;
        }

    }

    @Override
    protected void onDestroy() {
        unbindService(mBookConn);
        super.onDestroy();
    }

    private static class ClientHandler extends Handler {
        WeakReference<Activity> weakReference;

        ClientHandler(Activity activity) {
            weakReference = new WeakReference<Activity>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MyConstants.MSG_FROM_SERVICE:
                    Log.i(TAG, "handleMessage: " + "客户端接收，服务端发出" + msg.getData().getString("serviceData"));
                    break;
                default:
                    super.handleMessage(msg);
                    break;
            }

        }
    }
}

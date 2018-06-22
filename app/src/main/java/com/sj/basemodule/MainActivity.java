package com.sj.basemodule;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;

import com.sj.basemodule.base.BaseActivity;
import com.sj.basemodule.service.BookManagerService;
import com.sj.basemodule.service.MyConstants;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {
    private static final String TAG = "MainActivity";
    IBoomManager iBoomManager;
    private Handler mHandler = new Handler(Looper.myLooper()) {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MyConstants.MESSAGE_NEW_BOOK_ARRIVED:
                    Log.i(TAG, "handleMessage: " + Thread.currentThread().getName());
                    Log.i(TAG, "handleMessage: newBook" + msg.obj);
                    break;
                default:
                    super.handleMessage(msg);
            }

        }
    };
    IBookArrievdNotify iBookArrievdNotify = new IBookArrievdNotify.Stub() {
        @Override
        public void bookArrievdNotify(Book book) throws RemoteException {
            Log.i(TAG, "bookArrievdNotify: " + Thread.currentThread().getName());
            Log.i(TAG, "bookArrievdNotify: newBook" + book);
            mHandler.obtainMessage(MyConstants.MESSAGE_NEW_BOOK_ARRIVED, book).sendToTarget();
            try {
                Thread.sleep(20000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    };
    private IBinder.DeathRecipient mDeath = new IBinder.DeathRecipient() {
        @Override
        public void binderDied() {
            Log.i(TAG, "binderDied: " + Thread.currentThread().getName());
            if (iBoomManager == null) {
                return;
            }
            iBoomManager.asBinder().unlinkToDeath(mDeath, 0);
            iBoomManager = null;
            //重新绑定
            Intent i = new Intent(MainActivity.this, BookManagerService.class);
            bindService(i, mConn, BIND_AUTO_CREATE);
        }
    };
    private ServiceConnection mConn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            iBoomManager = IBoomManager.Stub.asInterface(service);
            try {
                //设置binder死亡代理
                service.linkToDeath(mDeath, 0);
                iBoomManager.addBook(new Book(3, "Android 开发艺术探索"));
                List<Book> bookList = iBoomManager.getBookList();
                Log.i(TAG, "onServiceConnected: " + bookList.getClass().getCanonicalName());
                Log.i(TAG, "onServiceConnected: " + bookList);

                iBoomManager.registerBookNotify(iBookArrievdNotify);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.i(TAG, "onServiceDisconnected: " + Thread.currentThread().getName());
        }
    };


    @Override
    protected void onDestroy() {
        if (iBookArrievdNotify != null && iBookArrievdNotify.asBinder().isBinderAlive()) {
            Log.i(TAG, "onDestroy:unRegister Listener" + iBookArrievdNotify);
            try {
                iBoomManager.unRegisterBookNotify(iBookArrievdNotify);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        unbindService(mConn);
        super.onDestroy();
    }

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
            case R.id.send:
                break;
            case R.id.aidl:
                Intent i = new Intent(MainActivity.this, BookManagerService.class);
                bindService(i, mConn, BIND_AUTO_CREATE);
                break;
        }
    }
}

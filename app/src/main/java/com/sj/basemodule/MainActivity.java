package com.sj.basemodule;

import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.view.ViewConfiguration;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.sj.basemodule.base.BaseActivity;
import com.sj.basemodule.service.BinderPool;
import com.sj.basemodule.service.ComputerImpl;
import com.sj.basemodule.service.SecurityCenterImpl;

import java.io.PrintWriter;
import java.net.Socket;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {
    private static final String TAG = "MainActivity";
    IC i = new IC() {
        @Override
        public void run() {

        }

        @Override
        public void eat() {

        }
    };
    TextView textView;
    private static final int MESSAGE_RECEIVE_NEW_MSG = 1;
    private static final int MESSAGE_SOCKET_CONNECTED = 2;
    Socket mClientSocket;
    PrintWriter mPrintWriter;
    @BindView(R.id.mMessageTextView)
    TextView mMessageTextView;
    @BindView(R.id.mMessageEditText)
    EditText mMessageEditText;
    @BindView(R.id.mSendButton)
    Button mSendButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @Override
    protected void reConnect() {

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
        ViewConfiguration.get(MainActivity.this).getScaledTouchSlop();
    }

    @Override
    public void initLocalData() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                doWork();
            }
        }).start();
    }

    private void doWork() {
        BinderPool binderPool = BinderPool.getInstance(MainActivity.this);
        IBinder securityBinder = binderPool.queryBinder(BinderPool.BINDER_SECURITY_CENTER);

        ISecurityCenter mSecurityCenter = SecurityCenterImpl.asInterface(securityBinder);
        Log.i(TAG, "visit ISecurityCenter");
        String msg = "helloWorld-Android";
        Log.i(TAG, "content:" + msg);
        try {
            String password = mSecurityCenter.encrypt(msg);
            Log.i(TAG, "encrypt:" + password);
            Log.i(TAG, "decrypt: " + mSecurityCenter.decrypt(password));
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        IBinder computeBinder = binderPool.queryBinder(BinderPool.BINDER_COMPUTE);
        ICompute computer = ComputerImpl.asInterface(computeBinder);
        Log.i(TAG, "visit ICompute");
        try {
            Log.i(TAG, "3 + 5 =" + computer.add(3, 5));
        } catch (RemoteException e) {
            e.printStackTrace();
        }

    }

    @OnClick(R.id.mSendButton)
    public void onViewClicked() {
    }
}

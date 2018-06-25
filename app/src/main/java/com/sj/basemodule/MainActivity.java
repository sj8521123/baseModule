package com.sj.basemodule;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.sj.basemodule.base.BaseActivity;
import com.sj.basemodule.service.TcpServer;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {
    private static final String TAG = "MainActivity";
    private static final int MESSAGE_RECEIVE_NEW_MSG = 1;
    private static final int MESSAGE_SOCKET_CONNECTED = 2;
    @BindView(R.id.mMessageTextView)
    TextView mMessageTextView;
    @BindView(R.id.mMessageEditText)
    EditText mMessageEditText;
    @BindView(R.id.mSendButton)
    Button mSendButton;
    Socket mClientSocket;
    PrintWriter mPrintWriter;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MESSAGE_RECEIVE_NEW_MSG:
                    mMessageTextView.setText(mMessageTextView.getText().toString() + (String) msg.obj);
                    break;
                case MESSAGE_SOCKET_CONNECTED:
                    Log.i(TAG, "socket Connected");
                    break;
            }
            super.handleMessage(msg);
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
        Intent intent = new Intent(MainActivity.this, TcpServer.class);
        startService(intent);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Socket socket = null;
                    while (socket == null) {
                        try {
                            socket = new Socket("localhost", 8668);
                            mClientSocket = socket;
                            mPrintWriter = new PrintWriter(new OutputStreamWriter(mClientSocket.getOutputStream()), true);
                            mHandler.sendEmptyMessage(MESSAGE_SOCKET_CONNECTED);
                        } catch (IOException e) {
                            SystemClock.sleep(1000);
                            Log.i(TAG, "socket: connect fail ,retry...");
                        }
                    }

                    BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    while (!MainActivity.this.isFinishing()) {
                        String msg = in.readLine();
                        String msgs = "server " + new SimpleDateFormat("HH:mm:ss").format(new Date()) + " :" + msg + "\n";
                        mHandler.obtainMessage(MESSAGE_RECEIVE_NEW_MSG,msgs).sendToTarget();
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }


    //客户端发送消息
    @OnClick(R.id.mSendButton)
    public void onViewClicked() {
        String msg = mMessageEditText.getText().toString();
        String msgs = "client " + new SimpleDateFormat("HH:mm:ss").format(new Date()) + " :" + msg + "\n";
        mMessageTextView.setText(mMessageTextView.getText().toString() + msgs);
        mPrintWriter.println(msg);
    }

    @Override
    protected void onDestroy() {
        if(mClientSocket != null){
            try {
                mClientSocket.shutdownInput();
                mClientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        super.onDestroy();
    }
}

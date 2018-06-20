package com.sj.basemodule.service;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by 13658 on 2018/6/15.
 */

public class MessengerService extends Service {
    private static final String TAG = "MessengerService";
    private Messenger mMessenger = new Messenger(new MessengerHandler());

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mMessenger.getBinder();
    }

    private static class MessengerHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MyConstants.MSG_FROM_CLIENT:
                    //收到
                    Log.i(TAG, "handleMessage: " + msg.getData().getString("data"));

                    //发出
                    Messenger client = msg.replyTo;
                    Message mess = Message.obtain(null, MyConstants.MSG_FROM_SERVICE);
                    Bundle bundle = new Bundle();
                    bundle.putString("data", "hi，客户端，我是服务端");
                    mess.setData(bundle);
                    try {
                        client.send(mess);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                    break;
                default:
                    super.handleMessage(msg);
            }

        }
    }
}

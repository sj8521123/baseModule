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
 * Created by 13658 on 2018/6/20.
 */

public class MessengerService extends Service {
    private static final String TAG = "MessengerService";
    Messenger messenger = new Messenger(new ServiceHandler());

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return messenger.getBinder();
    }

    private static class ServiceHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MyConstants.MSG_FROM_CLIENT:
                    Log.i(TAG, "handleMessage: " + "hi 服务端接收消息，客户端发消息" + msg.getData().getString("clientData"));
                    Messenger messenger = msg.replyTo;
                    Message mess = Message.obtain(null, MyConstants.MSG_FROM_SERVICE);
                    Bundle bundle = new Bundle();
                    bundle.putString("serviceData", "hi Client!");
                    mess.setData(bundle);
                    try {
                        messenger.send(mess);
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

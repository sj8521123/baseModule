package com.sj.basemodule.common;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;


import com.sj.basemodule.util.NetWorkUtil;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by 13658 on 2018/7/31.
 */

public class NetworkConnectChangedReceiver extends BroadcastReceiver {
    private static final String TAG = "NetworkConnectChangedRe";
    private static int lastNetState;
    @Override
    public void onReceive(Context context, Intent intent) {
        boolean isConnected = NetWorkUtil.isConnected();
        int currentState;
        if(isConnected){
            currentState = 1;
        }else{
            currentState = -1;
        }

        if(lastNetState != currentState){
            Log.i(TAG, "onReceive: 当前网路"+isConnected );
            EventBus.getDefault().post(new NetWorkChangeEvent(isConnected));
            lastNetState = currentState;
        }
    }
}

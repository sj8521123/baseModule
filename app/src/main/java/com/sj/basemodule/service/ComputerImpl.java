package com.sj.basemodule.service;

import android.os.RemoteException;

import com.sj.basemodule.ICompute;

/**
 * Created by 13658 on 2018/6/26.
 */

public class ComputerImpl extends ICompute.Stub {

    @Override
    public int add(int a, int b) throws RemoteException {
        return a + b;
    }
}

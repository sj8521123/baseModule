// IBindPool.aidl
package com.sj.basemodule;

// Declare any non-default types here with import statements

interface IBinderPool {
    IBinder queryBinder(int binderCode);
}

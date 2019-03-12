package com.sj.basemodule;

import android.util.Log;

import com.sj.basemodule.base.BaseActivity;
import com.tencent.smtt.utils.LogFileUtils;

public class MainActivity extends BaseActivity {
    private static final String TAG = "MainActivity";
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
    }

    @Override
    public void initLocalData() {
        int[] a = {12,3,5,1,7,4,5,7,8,10,14,51};
        heapSort(a);
    }
    private void heapSort(int[] a){
        for(int i = (a.length - 1)/2; i>=0;i--){
            heapOne(a,a.length,i);
        }

        int n = a.length;
        while(n > 0){
            Log.i(TAG, "heapSort: " + a[0]);
            a[0] = a[n - 1];
            n--;
            heapOne(a,n,0);
        }
    }
    private void heapOne(int[] a,int n,int k){
        int right = 2 * k + 1;
        int left = 2 * k + 2;
        if(right >=n && left>=n){
            Log.i(TAG, "heapOne: " + "已经是叶子节点啊");
        }
        int a1 = Integer.MAX_VALUE;
        int a2 = Integer.MAX_VALUE;

        if(right < n){
            a1 = a[right];
        }
        if(left < n){
            a2 = a[left];
        }
        if(a[k] <= a1 && a[k] <= a2){
            return;
        }
        if(a1 < a2){
            int t = a[k];
            a[k] = a[right];
            a[right] = t;
            heapOne(a,n,right);
        }
        if(a2 < a1){
            int t = a[k];
            a[k] = a[left];
            a[left] = t;
            heapOne(a,n,left);
        }
    }
}

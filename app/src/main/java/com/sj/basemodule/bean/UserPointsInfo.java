package com.sj.basemodule.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author andysong
 * @data 2019-08-06
 * @discription xxx
 */
public class UserPointsInfo implements Parcelable {
    private int balance;

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.balance);
    }

    public UserPointsInfo() {
    }

    protected UserPointsInfo(Parcel in) {
        this.balance = in.readInt();
    }

    public static final Parcelable.Creator<UserPointsInfo> CREATOR = new Parcelable.Creator<UserPointsInfo>() {
        @Override
        public UserPointsInfo createFromParcel(Parcel source) {
            return new UserPointsInfo(source);
        }

        @Override
        public UserPointsInfo[] newArray(int size) {
            return new UserPointsInfo[size];
        }
    };
}

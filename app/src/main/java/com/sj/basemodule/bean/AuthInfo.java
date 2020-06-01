package com.sj.basemodule.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class AuthInfo implements Parcelable {
    private String authToken;

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.authToken);
    }

    public AuthInfo() {
    }

    protected AuthInfo(Parcel in) {
        this.authToken = in.readString();
    }

    public static final Parcelable.Creator<AuthInfo> CREATOR = new Parcelable.Creator<AuthInfo>() {
        @Override
        public AuthInfo createFromParcel(Parcel source) {
            return new AuthInfo(source);
        }

        @Override
        public AuthInfo[] newArray(int size) {
            return new AuthInfo[size];
        }
    };
}

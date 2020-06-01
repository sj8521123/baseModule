package com.sj.basemodule.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class UserBean implements Parcelable {

    private AuthInfo authInfo;
    private BabyInfo babyInfo;
    private UserPointsInfo userPointsInfo;
    private UserInfo userInfo;
    private UserExtInfo userExtInfo;

    public UserExtInfo getUserExtInfo() {
        return userExtInfo;
    }

    public void setUserExtInfo(UserExtInfo userExtInfo) {
        this.userExtInfo = userExtInfo;
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    public UserPointsInfo getUserPointsInfo() {
        return userPointsInfo;
    }

    public void setUserPointsInfo(UserPointsInfo userPointsInfo) {
        this.userPointsInfo = userPointsInfo;
    }

    public AuthInfo getAuthInfo() {
        return authInfo;
    }

    public void setAuthInfo(AuthInfo authInfo) {
        this.authInfo = authInfo;
    }

    public BabyInfo getBabyInfo() {
        return babyInfo;
    }

    public void setBabyInfo(BabyInfo babyInfo) {
        this.babyInfo = babyInfo;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.authInfo, flags);
        dest.writeParcelable(this.babyInfo, flags);
        dest.writeParcelable(this.userPointsInfo, flags);
    }

    public UserBean() {
    }

    protected UserBean(Parcel in) {
        this.authInfo = in.readParcelable(AuthInfo.class.getClassLoader());
        this.babyInfo = in.readParcelable(BabyInfo.class.getClassLoader());
        this.userPointsInfo = in.readParcelable(UserPointsInfo.class.getClassLoader());
    }

    public static final Parcelable.Creator<UserBean> CREATOR = new Parcelable.Creator<UserBean>() {
        @Override
        public UserBean createFromParcel(Parcel source) {
            return new UserBean(source);
        }

        @Override
        public UserBean[] newArray(int size) {
            return new UserBean[size];
        }
    };
}

package com.sj.basemodule.bean;


import android.os.Parcel;
import android.os.Parcelable;

public class BabyInfo implements Parcelable {
    private String birthday;
    private String avatarUrl;
    private int sex;
    private String nickname;
    private int id;
    private String avatar;

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.birthday);
        dest.writeString(this.avatarUrl);
        dest.writeInt(this.sex);
        dest.writeString(this.nickname);
        dest.writeInt(this.id);
        dest.writeString(this.avatar);
    }

    public BabyInfo() {
    }

    protected BabyInfo(Parcel in) {
        this.birthday = in.readString();
        this.avatarUrl = in.readString();
        this.sex = in.readInt();
        this.nickname = in.readString();
        this.id = in.readInt();
        this.avatar = in.readString();
    }

    public static final Parcelable.Creator<BabyInfo> CREATOR = new Parcelable.Creator<BabyInfo>() {
        @Override
        public BabyInfo createFromParcel(Parcel source) {
            return new BabyInfo(source);
        }

        @Override
        public BabyInfo[] newArray(int size) {
            return new BabyInfo[size];
        }
    };
}

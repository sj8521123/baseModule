package com.sj.basemodule;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by 13658 on 2018/6/5.
 */

public class User implements Parcelable {

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel source) {
            return new User(source);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };
    private int id;
    private String name;
    private boolean isMale;

    public User(int id, String name, boolean isMale) {
        this.id = id;
        this.name = name;
        this.isMale = isMale;
    }

    protected User(Parcel in) {
        this.id = in.readInt();
        this.name = in.readString();
        this.isMale = in.readByte() != 0;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.name);
        dest.writeByte(this.isMale ? (byte) 1 : (byte) 0);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", isMale=" + isMale +
                '}';
    }
}

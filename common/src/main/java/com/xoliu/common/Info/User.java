package com.xoliu.common.Info;

import android.os.Parcel;
import android.os.Parcelable;

/***
 * 已序列化的User类
 *
 * @author xoliu
 * @create 23-11-19
 **/

public class User implements Parcelable {
    String userName;
    String userTel;
    String userId;

    String userPassword;


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.userName);
        dest.writeString(this.userTel);
        dest.writeString(this.userId);
        dest.writeString(this.userPassword);
    }

    public void readFromParcel(Parcel source) {
        this.userName = source.readString();
        this.userTel = source.readString();
        this.userId = source.readString();
        this.userPassword = source.readString();
    }

    public User() {
    }

    protected User(Parcel in) {
        this.userName = in.readString();
        this.userTel = in.readString();
        this.userId = in.readString();
        this.userPassword = in.readString();
    }

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
}

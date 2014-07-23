package com.ahandy.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Chinese district model class
 */
public class ChineseDistrict implements Parcelable {

    private String mName;

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        this.mName = name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.mName);
    }

    public ChineseDistrict() {
    }

    private ChineseDistrict(Parcel in) {
        this.mName = in.readString();
    }

    public static final Parcelable.Creator<ChineseDistrict> CREATOR
            = new Parcelable.Creator<ChineseDistrict>() {
        public ChineseDistrict createFromParcel(Parcel source) {
            return new ChineseDistrict(source);
        }

        public ChineseDistrict[] newArray(int size) {
            return new ChineseDistrict[size];
        }
    };
}

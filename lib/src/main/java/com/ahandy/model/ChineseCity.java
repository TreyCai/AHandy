package com.ahandy.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Chinese city model class
 */
public class ChineseCity implements Parcelable {

    private String mName;

    private String mAreaCode;

    private String mChineseName;

    private String mShortName;

    private String mZipCode;

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        this.mName = name;
    }

    public String getAreaCode() {
        return mAreaCode;
    }

    public void setAreaCode(String areaCode) {
        this.mAreaCode = areaCode;
    }

    public String getChineseName() {
        return mChineseName;
    }

    public void setChineseName(String chineseName) {
        this.mChineseName = chineseName;
    }

    public String getShortName() {
        return mShortName;
    }

    public void setShortName(String shortName) {
        this.mShortName = shortName;
    }

    public String getZipCode() {
        return mZipCode;
    }

    public void setZipCode(String zipCode) {
        this.mZipCode = zipCode;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.mName);
        dest.writeString(this.mAreaCode);
        dest.writeString(this.mChineseName);
        dest.writeString(this.mShortName);
        dest.writeString(this.mZipCode);
    }

    public ChineseCity() {
    }

    private ChineseCity(Parcel in) {
        this.mName = in.readString();
        this.mAreaCode = in.readString();
        this.mChineseName = in.readString();
        this.mShortName = in.readString();
        this.mZipCode = in.readString();
    }

    public static final Parcelable.Creator<ChineseCity> CREATOR
            = new Parcelable.Creator<ChineseCity>() {
        public ChineseCity createFromParcel(Parcel source) {
            return new ChineseCity(source);
        }

        public ChineseCity[] newArray(int size) {
            return new ChineseCity[size];
        }
    };
}

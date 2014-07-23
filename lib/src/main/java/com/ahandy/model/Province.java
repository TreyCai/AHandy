package com.ahandy.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Chinese province
 */
public class Province implements Parcelable {

    private String mName;

    private String mAbbreviation;

    private String mChineseName;

    private String mShortName;

    private ProvinceType mType;

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        this.mName = name;
    }

    public String getAbbreviation() {
        return mAbbreviation;
    }

    public void setAbbreviation(String abbreviation) {
        this.mAbbreviation = abbreviation;
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

    public ProvinceType getType() {
        return mType;
    }

    public void setType(ProvinceType type) {
        this.mType = type;
    }

    public static enum ProvinceType {
        MUNICIPALITY,
        PROVINCE,
        AUTONOMOUS_REGION,
        SPECIAL_ADMINISTRATIVE_REGION,
        CLAIMED_PROVINCE
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.mName);
        dest.writeString(this.mAbbreviation);
        dest.writeString(this.mChineseName);
        dest.writeString(this.mShortName);
        dest.writeInt(this.mType == null ? -1 : this.mType.ordinal());
    }

    public Province() {
    }

    private Province(Parcel in) {
        this.mName = in.readString();
        this.mAbbreviation = in.readString();
        this.mChineseName = in.readString();
        this.mShortName = in.readString();
        int tmpMType = in.readInt();
        this.mType = tmpMType == -1 ? null : ProvinceType.values()[tmpMType];
    }

    public static final Parcelable.Creator<Province> CREATOR = new Parcelable.Creator<Province>() {
        public Province createFromParcel(Parcel source) {
            return new Province(source);
        }

        public Province[] newArray(int size) {
            return new Province[size];
        }
    };
}

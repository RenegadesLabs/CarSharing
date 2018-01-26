package com.cardee.domain.owner.entity;

import android.os.Parcel;
import android.os.Parcelable;

public class Image implements Parcelable {

    private final int mImageId;
    private final String mLink;
    private final String mThumbnail;
    private final Boolean mPrimary;

    public Image(Integer imageId, String link, String thumbnail, Boolean primary) {
        mImageId = imageId;
        mLink = link;
        mThumbnail = thumbnail;
        mPrimary = primary;
    }

    protected Image(Parcel in) {
        mImageId = in.readInt();
        mLink = in.readString();
        mThumbnail = in.readString();
        byte tmpMPrimary = in.readByte();
        mPrimary = tmpMPrimary == 0 ? null : tmpMPrimary == 1;
    }

    public static final Creator<Image> CREATOR = new Creator<Image>() {
        @Override
        public Image createFromParcel(Parcel in) {
            return new Image(in);
        }

        @Override
        public Image[] newArray(int size) {
            return new Image[size];
        }
    };

    public Integer getImageId() {
        return mImageId;
    }

    public String getLink() {
        return mLink;
    }

    public String getThumbnail() {
        return mThumbnail;
    }

    public Boolean isPrimary() {
        return mPrimary != null && mPrimary;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(mImageId);
        parcel.writeString(mLink);
        parcel.writeString(mThumbnail);
        parcel.writeByte((byte) (mPrimary == null ? 0 : mPrimary ? 1 : 2));
    }
}

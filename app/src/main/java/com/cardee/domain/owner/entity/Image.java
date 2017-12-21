package com.cardee.domain.owner.entity;

public class Image {

    private final Integer mImageId;
    private final String mLink;
    private final String mThumbnail;
    private final Boolean mPrimary;

    public Image(Integer imageId, String link, String thumbnail, Boolean primary) {
        mImageId = imageId;
        mLink = link;
        mThumbnail = thumbnail;
        mPrimary = primary;
    }

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
}

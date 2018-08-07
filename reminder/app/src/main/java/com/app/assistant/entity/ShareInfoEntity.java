package com.app.assistant.entity;

import android.graphics.Bitmap;
import android.os.Parcel;

/**
 * 友盟分享所需要的数据
 * Created by chinaso on 2015/12/16.
 */
public class ShareInfoEntity {
    private String picUrl;//分享图片链接
    private String title;//分享标题
    private String targetUrl;//分享目标链接
    private String content;//分享内容
    private Bitmap defaultImg;//没有图片URL时默认的图片

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public Bitmap getDefaultImg() {
        return defaultImg;
    }

    public void setDefaultImg(Bitmap defaultImg) {
        this.defaultImg = defaultImg;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTargetUrl() {
        return targetUrl;
    }

    public void setTargetUrl(String targetUrl) {
        this.targetUrl = targetUrl;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "title=" + title + "; picUrl=" + "; targetUrl=" + targetUrl + "; content=" + content;
    }

    public ShareInfoEntity() {
    }

    protected ShareInfoEntity(Parcel in) {
        this.picUrl = in.readString();
        this.title = in.readString();
        this.targetUrl = in.readString();
        this.content = in.readString();
        this.defaultImg = in.readParcelable(Bitmap.class.getClassLoader());
    }

}

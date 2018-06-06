package com.chinaso.record.entity;

import com.chinaso.record.base.BaseEntity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * author: zhanghe
 * created on: 2018/6/5 10:54
 * description: 图片存储的对象类
 */
@Entity
public class PhotoEntity extends BaseEntity {
    @Id
    private Long id;
    private String photoPath;
    private String photoDate;
    private String photoInfo;

    @Generated(hash = 2030433462)
    public PhotoEntity(Long id, String photoPath, String photoDate,
            String photoInfo) {
        this.id = id;
        this.photoPath = photoPath;
        this.photoDate = photoDate;
        this.photoInfo = photoInfo;
    }

    @Generated(hash = 1889335700)
    public PhotoEntity() {
    }

    @Override
    public String toString() {
        return "PhotoEntity{" +
                "id=" + id +
                ", photoPath='" + photoPath + '\'' +
                ", photoDate='" + photoDate + '\'' +
                ", photoInfo='" + photoInfo + '\'' +
                '}';
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPhotoPath() {
        return this.photoPath;
    }

    public void setPhotoPath(String photoPath) {
        this.photoPath = photoPath;
    }

    public String getPhotoDate() {
        return this.photoDate;
    }

    public void setPhotoDate(String photoDate) {
        this.photoDate = photoDate;
    }

    public String getPhotoInfo() {
        return this.photoInfo;
    }

    public void setPhotoInfo(String photoInfo) {
        this.photoInfo = photoInfo;
    }
}

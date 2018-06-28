package com.chinaso.record.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.chinaso.record.base.BaseEntity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * author: zhanghe
 * created on: 2018/6/25 10:42
 * description:闹钟数据库表结构
 */
@Entity
public class AlarmEntity extends BaseEntity implements Parcelable {
    @Id
    private Long id;
    //hour
    private int hour;
    //minute
    private int minute;
    //闹钟标题
    private String title;
    //响铃周期标志
    private int cycleTag;
    //响铃周期星期数
    private String cycleWeeks;
    //响铃方式
    private int bellMode;
    //备注
    private String remark;
    //是否打开
    private boolean isOpen;

    @Generated(hash = 661605996)
    public AlarmEntity(Long id, int hour, int minute, String title, int cycleTag,
                       String cycleWeeks, int bellMode, String remark, boolean isOpen) {
        this.id = id;
        this.hour = hour;
        this.minute = minute;
        this.title = title;
        this.cycleTag = cycleTag;
        this.cycleWeeks = cycleWeeks;
        this.bellMode = bellMode;
        this.remark = remark;
        this.isOpen = isOpen;
    }

    @Generated(hash = 163591880)
    public AlarmEntity() {
    }

    protected AlarmEntity(Parcel in) {
        if (in.readByte() == 0) {
            id = null;
        } else {
            id = in.readLong();
        }
        hour = in.readInt();
        minute = in.readInt();
        title = in.readString();
        cycleTag = in.readInt();
        cycleWeeks = in.readString();
        bellMode = in.readInt();
        remark = in.readString();
        isOpen = in.readByte() != 0;
    }

    public static final Creator<AlarmEntity> CREATOR = new Creator<AlarmEntity>() {
        @Override
        public AlarmEntity createFromParcel(Parcel in) {
            return new AlarmEntity(in);
        }

        @Override
        public AlarmEntity[] newArray(int size) {
            return new AlarmEntity[size];
        }
    };

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getHour() {
        return this.hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMinute() {
        return this.minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getCycleTag() {
        return this.cycleTag;
    }

    public void setCycleTag(int cycleTag) {
        this.cycleTag = cycleTag;
    }

    public String getCycleWeeks() {
        return this.cycleWeeks;
    }

    public void setCycleWeeks(String cycleWeeks) {
        this.cycleWeeks = cycleWeeks;
    }

    public int getBellMode() {
        return this.bellMode;
    }

    public void setBellMode(int bellMode) {
        this.bellMode = bellMode;
    }

    public String getRemark() {
        return this.remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public boolean getIsOpen() {
        return this.isOpen;
    }

    public void setIsOpen(boolean isOpen) {
        this.isOpen = isOpen;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        if (id == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeLong(id);
        }
        dest.writeInt(hour);
        dest.writeInt(minute);
        dest.writeString(title);
        dest.writeInt(cycleTag);
        dest.writeString(cycleWeeks);
        dest.writeInt(bellMode);
        dest.writeString(remark);
        dest.writeByte((byte) (isOpen ? 1 : 0));
    }
}

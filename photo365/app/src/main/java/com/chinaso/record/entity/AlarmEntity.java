package com.chinaso.record.entity;

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
public class AlarmEntity extends BaseEntity {
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


}

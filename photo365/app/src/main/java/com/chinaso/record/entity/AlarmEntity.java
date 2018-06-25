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
    //时间
    private String time;
    //响铃周期
    private String cycle;
    //响铃方式
    private String mode;
    //备注
    private String remark;

    @Generated(hash = 1970029728)
    public AlarmEntity(Long id, String time, String cycle, String mode,
                       String remark) {
        this.id = id;
        this.time = time;
        this.cycle = cycle;
        this.mode = mode;
        this.remark = remark;
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

    public String getTime() {
        return this.time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getCycle() {
        return this.cycle;
    }

    public void setCycle(String cycle) {
        this.cycle = cycle;
    }

    public String getMode() {
        return this.mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getRemark() {
        return this.remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}

package com.app.assistant.entity;

import com.app.assistant.base.BaseEntity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * author: zhanghe
 * created on: 2018/7/13 16:50
 * description:task table
 */

@Entity
public class TaskEntity extends BaseEntity {

    @Id
    private Long id;

    //title
    private String title;
    //日期
    private String date;
    //提前几天
    private int early;

    //当前任务是否已完成
    private boolean status;

    @Generated(hash = 253246754)
    public TaskEntity(Long id, String title, String date, int early,
                      boolean status) {
        this.id = id;
        this.title = title;
        this.date = date;
        this.early = early;
        this.status = status;
    }

    @Generated(hash = 397975341)
    public TaskEntity() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return this.date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getEarly() {
        return this.early;
    }

    public void setEarly(int early) {
        this.early = early;
    }

    public boolean getStatus() {
        return this.status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }


}

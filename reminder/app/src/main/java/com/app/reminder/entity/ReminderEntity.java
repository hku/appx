package com.app.reminder.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * author: zhanghe
 * created on: 2018/7/5 17:20
 * description: 表结构
 */
@Entity
public class ReminderEntity {

    @Id
    private long id;

    private String content;
    private String tagS;

    @Generated(hash = 613567264)
    public ReminderEntity(long id, String content, String tagS) {
        this.id = id;
        this.content = content;
        this.tagS = tagS;
    }

    @Generated(hash = 1994315103)
    public ReminderEntity() {
    }

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTagS() {
        return this.tagS;
    }

    public void setTagS(String tagS) {
        this.tagS = tagS;
    }

}

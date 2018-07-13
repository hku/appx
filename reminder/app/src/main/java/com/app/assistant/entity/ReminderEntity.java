package com.app.assistant.entity;

import com.app.assistant.base.BaseEntity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * author: zhanghe
 * created on: 2018/7/5 17:20
 * description: 表结构
 */
@Entity
public class ReminderEntity extends BaseEntity {

    @Id
    private Long id;

    private String content;
    private String tagS;

    @Generated(hash = 1825889834)
    public ReminderEntity(Long id, String content, String tagS) {
        this.id = id;
        this.content = content;
        this.tagS = tagS;
    }

    @Generated(hash = 1994315103)
    public ReminderEntity() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
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

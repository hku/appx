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
public class MemoEntity extends BaseEntity {

    @Id
    private Long id;

    private String content;
    private String tagS;
    //是否是内置的
    private boolean isBuiltIn;

    @Generated(hash = 1979647587)
    public MemoEntity(Long id, String content, String tagS, boolean isBuiltIn) {
        this.id = id;
        this.content = content;
        this.tagS = tagS;
        this.isBuiltIn = isBuiltIn;
    }

    @Generated(hash = 776636888)
    public MemoEntity() {
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

    public boolean getIsBuiltIn() {
        return this.isBuiltIn;
    }

    public void setIsBuiltIn(boolean isBuiltIn) {
        this.isBuiltIn = isBuiltIn;
    }

}

package com.app.assistant.entity;

import com.app.assistant.base.BaseEntity;

/**
 * author: zhanghe
 * created on: 2018/7/26 17:52
 * description:
 */

public class UpdateEntity extends BaseEntity {

    private String updateUrl;
    private int versionCode;

    public UpdateEntity(String updateUrl, int versionCode) {
        this.updateUrl = updateUrl;
        this.versionCode = versionCode;
    }

    public String getUpdateUrl() {
        return updateUrl;
    }

    public int getVersionCode() {
        return versionCode;
    }

    public void setUpdateUrl(String updateUrl) {
        this.updateUrl = updateUrl;
    }

    public void setVersionCode(int versionCode) {
        this.versionCode = versionCode;
    }
}

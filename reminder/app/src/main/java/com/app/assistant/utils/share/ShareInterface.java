package com.app.assistant.utils.share;


import com.app.assistant.entity.ShareInfoEntity;

/**
 * @说明:
 * @作者: zhanghe
 * @时间: 2017-12-12 15:13
 */
public interface ShareInterface {

    void startShare(ShareInfoEntity shareEntity, int platform);
}

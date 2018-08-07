package com.app.assistant.utils.share;


import com.app.assistant.entity.ShareInfoEntity;

/**
 * @说明:
 * @作者: zhanghe
 * @时间: 2017-12-12 15:13
 */
public abstract class BaseShare implements ShareInterface {

    @Override
    public void startShare(ShareInfoEntity shareEntity, int platform) {
        initShareContent(shareEntity);
        beginShare(platform);
    }


    /**
     * @param shareEntity
     */
    protected abstract void initShareContent(ShareInfoEntity shareEntity);

    /**
     * 开始分享
     */
    protected abstract void beginShare(int platform);
}

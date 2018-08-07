package com.app.assistant.utils.share;

import android.app.Activity;
import android.text.TextUtils;

import com.app.assistant.entity.ShareInfoEntity;
import com.app.assistant.utils.ToastUtils;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;


/**
 * @说明: 分享操作类
 * @作者: zhanghe
 * @时间: 2017-12-12 15:13
 */
public class ShareManager extends BaseShare {
    private Activity mContext;
    private UMImage mShareImage;
    private String mTitle;
    private String mContent;
    private String mTargetUrl;

    //sina UMWeb
    private UMWeb mSinaMedia;
    //WeChat UMWeb
    private UMWeb mWeChatMedia;
    //Ding Ding UMWeb
    private UMWeb mDingMedia;

    public ShareManager(Activity context) {
        mContext = context;
    }

    @Override
    protected void initShareContent(ShareInfoEntity shareEntity) {
        mTitle = shareEntity.getTitle();
        mContent = shareEntity.getContent();
        mShareImage = TextUtils.isEmpty(shareEntity.getPicUrl()) ? (new
                UMImage(mContext, shareEntity.getDefaultImg())) : (new UMImage(mContext, shareEntity.getPicUrl()));
        mTargetUrl = shareEntity.getTargetUrl();
    }

    @Override
    protected void beginShare(int platform) {
        switch (platform) {
            //微信
            case 0:
                mWeChatMedia = new UMWeb(mTargetUrl);
                mWeChatMedia.setTitle(mTitle);
                mWeChatMedia.setDescription(mContent);
                mWeChatMedia.setThumb(mShareImage);
                new ShareAction(mContext).setPlatform(SHARE_MEDIA.WEIXIN).setCallback(umShareListener)
                        .withMedia(mWeChatMedia)
                        .share();
                break;
            //微博
            case 1:
                //新浪微博分享
                mSinaMedia = new UMWeb(mTargetUrl);
                mSinaMedia.setDescription(mTitle + "-" + mContent + "(分享自#*****#)");
                mSinaMedia.setTitle(mTitle);
                mSinaMedia.setThumb(mShareImage);
                mSinaMedia.mText = "分享 【" + mTitle + "】" + mTargetUrl + " (分享自#*****#) ";
                new ShareAction(mContext).setPlatform(SHARE_MEDIA.SINA).setCallback(umShareListener)
                        .withMedia(mWeChatMedia)
                        .share();
                break;
            //钉钉
            case 2:
                //钉钉分享
                mDingMedia = new UMWeb(mTargetUrl);
                mDingMedia.setDescription(mContent);
                mDingMedia.setTitle(mTitle);
                mDingMedia.setThumb(mShareImage);
                new ShareAction(mContext).setPlatform(SHARE_MEDIA.DINGTALK).setCallback(umShareListener)
                        .withMedia(mWeChatMedia)
                        .share();
                break;
        }
    }


    private UMShareListener umShareListener = new UMShareListener() {
        @Override
        public void onStart(SHARE_MEDIA share_media) {

        }

        @Override
        public void onResult(SHARE_MEDIA platform) {
            if ("WEIXIN_FAVORITE".equals(platform.name())) {
                ToastUtils.show(mContext, " 收藏成功啦");
            } else {
                ToastUtils.show(mContext, " 分享成功啦");
            }
        }

        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
            ToastUtils.show(mContext, " 分享失败啦");
        }

        @Override
        public void onCancel(SHARE_MEDIA platform) {
            ToastUtils.show(mContext, " 分享取消了");
        }
    };

}

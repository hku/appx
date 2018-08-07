package com.app.assistant.widget;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import com.app.assistant.R;
import com.app.assistant.entity.ShareInfoEntity;
import com.app.assistant.utils.share.ShareManager;

/**
 * author: zhanghe
 * created on: 2018/8/5 16:55
 * description:
 */

public class SharePopWindow extends PopupWindow {

    private Activity mContext;
    private View mMenuView;
    private ShareManager mShareManager;
    //0：微信 1：微博 2：钉钉
    private int mSharePlatform = -1;

    public SharePopWindow(Activity context, final ShareInfoEntity shareInfoEntity) {
        super(context);
        this.mContext = context;
        mShareManager = new ShareManager(mContext);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mMenuView = inflater.inflate(R.layout.pop_share, null);
        RelativeLayout cancelRLayout = mMenuView.findViewById(R.id.cancel_rlayout);
        cancelRLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        LinearLayout weChatLayout = mMenuView.findViewById(R.id.weChat_llayout);
        weChatLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSharePlatform = 0;
                weChatShare(shareInfoEntity);
                dismiss();
            }
        });
        LinearLayout sinaLayout = mMenuView.findViewById(R.id.sina_llayout);
        sinaLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSharePlatform = 1;
                sinaShare(shareInfoEntity);
                dismiss();
            }
        });
        LinearLayout dingLayout = mMenuView.findViewById(R.id.ding_llayout);
        dingLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSharePlatform = 2;
                dingShare(shareInfoEntity);
                dismiss();
            }
        });
        this.setOutsideTouchable(true);
        // 设置WebMenuPopWindow的View
        this.setContentView(mMenuView);
        // 设置WebMenuPopWindow弹出窗体的宽
        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        // 设置WebMenuPopWindow弹出窗体的高
        this.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        // 设置WebMenuPopWindow弹出窗体可点击
        this.setFocusable(true);
        //设置WebMenuPopWindow弹出窗体动画效果
//        this.setAnimationStyle(R.style.popwin_anim_style);
        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0xb0000000);
        // 设置WebMenuPopWindow弹出窗体的背景
        this.setBackgroundDrawable(dw);
        // mMenuView添加OnTouchListener监听判断获取触屏位置如果在选择框外面则销毁弹出框
        mMenuView.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                int height = mMenuView.findViewById(R.id.menu_llayout).getTop();
                int y = (int) event.getY();
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (y < height) {
                        dismiss();
                    }
                }
                return true;
            }
        });
    }

    /**
     * show
     */
    public void show() {
        Rect frame = new Rect();
        mContext.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
        int winHeight = mContext.getWindow().getDecorView().getHeight();
        showAtLocation(mContext.getWindow().getDecorView(), Gravity.BOTTOM, 0, winHeight - frame.bottom);
    }

    /**
     * weChat platform share
     *
     * @param shareInfoEntity
     */
    private void weChatShare(ShareInfoEntity shareInfoEntity) {
        mShareManager.startShare(shareInfoEntity, mSharePlatform);
    }


    /**
     * sina platform share
     *
     * @param shareInfoEntity
     */
    private void sinaShare(ShareInfoEntity shareInfoEntity) {
        mShareManager.startShare(shareInfoEntity, mSharePlatform);
    }

    /**
     * dingding platform share
     *
     * @param shareInfoEntity
     */
    private void dingShare(ShareInfoEntity shareInfoEntity) {
        mShareManager.startShare(shareInfoEntity, mSharePlatform);
    }
}

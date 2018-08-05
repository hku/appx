package com.app.assistant.widget;


import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.PopupWindow;

import com.app.assistant.R;

/**
 * author: zhanghe
 * created on: 2018/8/1 14:21
 * description:网页menu popWindow
 */
public class WebMenuPopWindow extends PopupWindow {

    private Activity mContext;
    private View mMenuView;

    public WebMenuPopWindow(Activity context) {
        super(context);
        this.mContext = context;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mMenuView = inflater.inflate(R.layout.pop_web_menu, null);
        this.setOutsideTouchable(true);
        // 设置WebMenuPopWindow的View
        this.setContentView(mMenuView);
        // 设置WebMenuPopWindow弹出窗体的宽
        this.setWidth(LayoutParams.MATCH_PARENT);
        // 设置WebMenuPopWindow弹出窗体的高
        this.setHeight(LayoutParams.WRAP_CONTENT);
        // 设置WebMenuPopWindow弹出窗体可点击
        this.setFocusable(true);
        //设置WebMenuPopWindow弹出窗体动画效果
//        this.setAnimationStyle(R.style.popwin_anim_style);
        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0xb0000000);
        // 设置WebMenuPopWindow弹出窗体的背景
        this.setBackgroundDrawable(dw);
        // mMenuView添加OnTouchListener监听判断获取触屏位置如果在选择框外面则销毁弹出框
        mMenuView.setOnTouchListener(new OnTouchListener() {
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

    public void show() {
        Rect frame = new Rect();
        mContext.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
        int winHeight = mContext.getWindow().getDecorView().getHeight();
        showAtLocation(mContext.getWindow().getDecorView(), Gravity.BOTTOM, 0, winHeight - frame.bottom);
    }
}

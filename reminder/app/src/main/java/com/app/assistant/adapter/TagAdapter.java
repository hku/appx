package com.app.assistant.adapter;


import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.app.assistant.R;
import com.app.assistant.utils.ScreenUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

/**
 * author: zhanghe
 * created on: 2018/7/5 15:53
 * description:标签数据源
 */

public class TagAdapter extends BaseQuickAdapter<String, BaseViewHolder> {


    private int mPosition = -1;

    public TagAdapter(int layoutResId) {
        super(layoutResId);
    }


    @Override
    protected void convert(BaseViewHolder helper, String item) {
        helper.setText(R.id.tag_tv, item);
        TextView tagTv = helper.getView(R.id.tag_tv);
        RelativeLayout itemLayout = helper.getView(R.id.item_rlayout);
        int screenWidth = ScreenUtils.getScreenWidth(mContext);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(screenWidth / 4,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        itemLayout.setLayoutParams(params);
        int position = helper.getAdapterPosition();
        if (position == mPosition) {
            tagTv.setBackgroundResource(R.drawable.bg_tag_tv_pressed);
        } else {
            tagTv.setBackgroundResource(R.drawable.bg_tag_tv_normal);
        }
    }
}

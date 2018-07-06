package com.app.reminder.adapter;


import android.content.IntentFilter;
import android.widget.TextView;

import com.app.reminder.R;
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

    public void refesh(int position) {
        this.mPosition = position;
        notifyDataSetChanged();
    }


    @Override
    protected void convert(BaseViewHolder helper, String item) {
        helper.setText(R.id.tag_tv, item);
        TextView tagTv = helper.getView(R.id.tag_tv);
        int position = helper.getAdapterPosition();
        if (position == mPosition) {
            tagTv.setBackgroundResource(R.drawable.bg_tag_tv_pressed);
        } else {
            tagTv.setBackgroundResource(R.drawable.bg_tag_tv_normal);
        }
    }
}

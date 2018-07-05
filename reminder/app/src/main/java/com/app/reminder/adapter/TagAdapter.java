package com.app.reminder.adapter;


import com.app.reminder.R;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;


/**
 * author: zhanghe
 * created on: 2018/7/5 15:53
 * description:标签数据源
 */

public class TagAdapter extends BaseQuickAdapter<String, BaseViewHolder> {


    public TagAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        helper.setText(R.id.tag_tv, item);
    }
}

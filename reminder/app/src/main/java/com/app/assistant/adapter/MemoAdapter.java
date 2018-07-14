package com.app.assistant.adapter;


import com.app.assistant.R;
import com.app.assistant.entity.ReminderEntity;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.ArrayList;

/**
 * author: zhanghe
 * created on: 2018/7/6 9:44
 * description:显示全部reminer 数据源
 */

public class MemoAdapter extends BaseQuickAdapter<ReminderEntity, BaseViewHolder> {


    public MemoAdapter(int layoutResId) {
        super(layoutResId);
    }


    public void clear() {
        this.setNewData(new ArrayList<ReminderEntity>());
    }

    public void remove(int position) {
        if (mData != null && mData.size() > position) {
            mData.remove(position);
            notifyItemRemoved(position);
        }
    }

    public void set(int position, ReminderEntity entity) {
        if (mData != null && mData.size() > position) {
            mData.set(position, entity);
            notifyItemChanged(position);
        }
    }

    @Override
    protected void convert(BaseViewHolder helper, ReminderEntity item) {
        String content = item.getContent();
        String tagS = item.getTagS();
        helper.setText(R.id.content_tv, content);
        helper.setText(R.id.tag_tv, tagS);

        helper.addOnClickListener(R.id.btnDelete);
        helper.addOnClickListener(R.id.contentLayout);
    }
}

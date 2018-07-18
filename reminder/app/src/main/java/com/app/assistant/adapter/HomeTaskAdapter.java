package com.app.assistant.adapter;

import com.app.assistant.R;
import com.app.assistant.entity.ReminderEntity;
import com.app.assistant.entity.TaskEntity;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.ArrayList;

/**
 * author: zhanghe
 * created on: 2018/7/18 11:53
 * description:
 */

public class HomeTaskAdapter extends BaseQuickAdapter<TaskEntity, BaseViewHolder> {

    public HomeTaskAdapter(int layoutResId) {
        super(layoutResId);
    }

    public void clear() {
        this.setNewData(new ArrayList<TaskEntity>());
    }

    public void remove(int position) {
        if (mData != null && mData.size() > position) {
            mData.remove(position);
            notifyItemRemoved(position);
        }
    }

    public void set(int position, TaskEntity entity) {
        if (mData != null && mData.size() > position) {
            mData.set(position, entity);
            notifyItemChanged(position);
        }
    }


    @Override
    protected void convert(BaseViewHolder helper, TaskEntity item) {
        String title = item.getTitle();
        helper.setText(R.id.content_tv, title);
        helper.addOnClickListener(R.id.done_cb);
    }
}

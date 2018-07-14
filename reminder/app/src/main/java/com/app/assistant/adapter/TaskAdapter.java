package com.app.assistant.adapter;

import com.app.assistant.entity.TaskEntity;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

/**
 * author: zhanghe
 * created on: 2018/7/13 17:02
 * description:
 */

public class TaskAdapter extends BaseQuickAdapter<TaskEntity, BaseViewHolder> {


    public TaskAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, TaskEntity item) {

    }
}

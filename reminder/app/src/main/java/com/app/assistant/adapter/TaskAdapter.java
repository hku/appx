package com.app.assistant.adapter;

import android.graphics.Paint;
import android.widget.TextView;

import com.app.assistant.R;
import com.app.assistant.entity.TaskEntity;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.ArrayList;

/**
 * author: zhanghe
 * created on: 2018/7/13 17:02
 * description:
 */

public class TaskAdapter extends BaseQuickAdapter<TaskEntity, BaseViewHolder> {


    public TaskAdapter(int layoutResId) {
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
        String titleS = item.getTitle();
        helper.setText(R.id.content_tv, titleS);
        helper.addOnClickListener(R.id.btnDelete);
        boolean status = item.getStatus();
        TextView contentTv = helper.getView(R.id.content_tv);
        //task finished
        if (status) {
            contentTv.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);
        } else {
            contentTv.getPaint().setFlags(0);
            helper.addOnClickListener(R.id.contentLayout);
        }
    }
}

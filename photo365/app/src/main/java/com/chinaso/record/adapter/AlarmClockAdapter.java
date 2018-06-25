package com.chinaso.record.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chinaso.record.entity.AlarmEntity;

/**
 * author: zhanghe
 * created on: 2018/6/25 10:39
 * description: 闹钟列表数据源
 */

public class AlarmClockAdapter extends BaseQuickAdapter<AlarmEntity, BaseViewHolder> {


    public AlarmClockAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, AlarmEntity item) {

    }
}

package com.app.assistant.adapter;

import com.app.assistant.R;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

/**
 * author: zhanghe
 * created on: 2018/8/2 14:25
 * description:
 */

public class SearchWayAdapter extends BaseQuickAdapter<String, BaseViewHolder> {


    public SearchWayAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        helper.setText(R.id.way_tv, item);
    }
}

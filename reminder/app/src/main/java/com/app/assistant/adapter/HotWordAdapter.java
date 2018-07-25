package com.app.assistant.adapter;

import com.app.assistant.R;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

/**
 * author: zhanghe
 * created on: 2018/7/25 9:16
 * description:热词数据源
 */

public class HotWordAdapter extends BaseQuickAdapter<String, BaseViewHolder> {


    public HotWordAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        helper.setText(R.id.hot_word_tv, item);
    }
}

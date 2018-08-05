package com.app.assistant.adapter;

import com.app.assistant.R;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.ArrayList;

/**
 * author: zhanghe
 * created on: 2018/8/5 13:22
 * description: 搜索建议数据源
 */

public class SearchSuggestAdapter extends BaseQuickAdapter<String, BaseViewHolder> {


    public void clear() {
        this.setNewData(new ArrayList<String>());
    }


    public SearchSuggestAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        helper.setText(R.id.suggest_tv, item);
    }
}

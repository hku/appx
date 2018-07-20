package com.app.assistant.adapter;


import com.app.assistant.R;
import com.app.assistant.entity.MemoEntity;
import com.app.assistant.widget.SwipeMenuLayout;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.ArrayList;

/**
 * author: zhanghe
 * created on: 2018/7/6 9:44
 * description:显示全部reminer 数据源
 */

public class MemoAdapter extends BaseQuickAdapter<MemoEntity, BaseViewHolder> {


    public MemoAdapter(int layoutResId) {
        super(layoutResId);
    }


    public void clear() {
        this.setNewData(new ArrayList<MemoEntity>());
    }

    public void remove(int position) {
        if (mData != null && mData.size() > position) {
            mData.remove(position);
            notifyItemRemoved(position);
        }
    }

    public void set(int position, MemoEntity entity) {
        if (mData != null && mData.size() > position) {
            mData.set(position, entity);
            notifyItemChanged(position);
        }
    }

    @Override
    protected void convert(BaseViewHolder helper, MemoEntity item) {
        String content = item.getContent();
        String tagS = item.getTagS();
        helper.setText(R.id.content_tv, content);
        helper.setText(R.id.tag_tv, tagS);

        SwipeMenuLayout swipeMenuLayout = helper.getView(R.id.memo_swipelayout);
        boolean isBuiltIn = item.getIsBuiltIn();
        //该条数据是内置的
        if (isBuiltIn) {
            swipeMenuLayout.setIos(false);
            swipeMenuLayout.setLeftSwipe(false);
            swipeMenuLayout.setSwipeEnable(false);
        } else {
            swipeMenuLayout.setIos(true);
            swipeMenuLayout.setLeftSwipe(true);
            swipeMenuLayout.setSwipeEnable(true);
            helper.addOnClickListener(R.id.btnDelete);
            helper.addOnClickListener(R.id.contentLayout);
        }
    }
}

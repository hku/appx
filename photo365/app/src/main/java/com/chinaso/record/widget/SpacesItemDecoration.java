package com.chinaso.record.widget;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * author: zhanghe
 * created on: 2018/6/7 11:07
 * description:RecyclerView的分割线
 */

public class SpacesItemDecoration extends RecyclerView.ItemDecoration {

    private int space;

    public SpacesItemDecoration(int space) {
        this.space = space;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        int childPosition = parent.getChildAdapterPosition(view);
        if (childPosition % 2 == 0) {
            outRect.left = space;
            outRect.right = space / 2;
        } else {
            outRect.left = space / 2;
            outRect.right = space;
        }
        outRect.bottom = space;
        outRect.top = space;
    }
}

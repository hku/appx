package com.chinaso.record.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.CardView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chinaso.record.R;
import com.chinaso.record.base.GlideApp;
import com.chinaso.record.entity.PhotoEntity;
import com.chinaso.record.utils.DensityUtil;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;


/**
 * author: zhanghe
 * created on: 2018/6/5 15:04
 * description:
 */

public class PhotoItemAdapter extends BaseQuickAdapter<PhotoEntity, BaseViewHolder> {

    private static final int PADDING_LEFT = 54;
    private static final int PADDING_RIGHT = 54;
    private static final int PADDING_TOP = 54;
    private static final int PADDING_BOTTOM = 54;

    private Context mContext;

    public PhotoItemAdapter(int layoutResId, Context context) {
        super(layoutResId);
        this.mContext = context;
    }

    public void clear() {
        this.setNewData(new ArrayList<PhotoEntity>());
    }

    @Override
    protected void convert(BaseViewHolder helper, PhotoEntity item) {
        ImageView photoIv = helper.getView(R.id.photo_item_iv);
        CardView cardView = helper.getView(R.id.cardview);
        String photoPath = item.getPhotoPath();
        Uri uri = Uri.parse(photoPath);
        GlideApp.with(mContext).load(uri).
                placeholder(R.mipmap.placeholder_common).into(photoIv);
        int halfPaddingLeft = PADDING_LEFT / 2;
        int halfPaddingRight = PADDING_RIGHT / 2;
        long id = item.getId();
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) cardView.getLayoutParams();
        if ((int) id % 2 == 0) {
            params.setMargins(halfPaddingLeft, PADDING_TOP, PADDING_RIGHT, PADDING_BOTTOM);
        } else {
            params.setMargins(PADDING_LEFT, PADDING_TOP, halfPaddingRight, PADDING_BOTTOM);
        }
        cardView.setLayoutParams(params);
    }
}

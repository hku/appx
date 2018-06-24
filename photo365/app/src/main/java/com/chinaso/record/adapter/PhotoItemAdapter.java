package com.chinaso.record.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chinaso.record.R;
import com.chinaso.record.base.GlideApp;
import com.chinaso.record.entity.PhotoEntity;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;


/**
 * author: zhanghe
 * created on: 2018/6/5 15:04
 * description:
 */

public class PhotoItemAdapter extends BaseQuickAdapter<PhotoEntity, BaseViewHolder> {

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
        String photoPath = item.getPhotoPath();
        Uri uri = Uri.parse(photoPath);
        GlideApp.with(mContext).load(uri).
                placeholder(R.mipmap.placeholder_common).into(photoIv);
        String photoDate = item.getPhotoDate();
        helper.setText(R.id.photo_date_tv, photoDate);
    }
}

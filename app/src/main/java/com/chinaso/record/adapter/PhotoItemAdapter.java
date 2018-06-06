package com.chinaso.record.adapter;

import android.content.Context;
import android.net.Uri;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chinaso.record.R;
import com.chinaso.record.entity.PhotoEntity;
import com.orhanobut.logger.Logger;


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

    @Override
    protected void convert(BaseViewHolder helper, PhotoEntity item) {
        ImageView photoIv = helper.getView(R.id.photo_item_iv);
        String photoPath = item.getPhotoPath();
        Uri uri = Uri.parse(photoPath);
        Logger.e("uri = " + uri);
        Glide.with(mContext).load(uri).into(photoIv);
        long id = item.getId();
        if (id % 2 == 0) {
            photoIv.setPadding(20, 20, 10, 20);
        } else {
            photoIv.setPadding(10, 20, 20, 20);
        }
    }
}

package com.chinaso.record.activity;

import android.net.Uri;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chinaso.record.R;
import com.chinaso.record.base.BaseActivity;
import com.chinaso.record.base.GlideApp;
import com.chinaso.record.entity.PhotoEntity;
import com.github.chrisbanes.photoview.PhotoView;
import com.orhanobut.logger.Logger;

import butterknife.BindView;

/**
 * author: zhanghe
 * created on: 2018/6/5 16:36
 * description: 图片详情页面
 */

public class PhotoDetailActivity extends BaseActivity {


    @BindView(R.id.photo_detail)
    PhotoView photoView;
    @BindView(R.id.message)
    TextView messageTv;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    private PhotoEntity mPhotoInfo;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_photo_detail;
    }

    @Override
    protected void initView() {
        super.initView();
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void initData() {
        super.initData();
        mPhotoInfo = (PhotoEntity) getIntent().
                getSerializableExtra(PhotoListActivity.PHOTO_DETAIL_INFO);
    }

    @Override
    protected void business() {
        String message = mPhotoInfo.getPhotoInfo();
        String uriS = mPhotoInfo.getPhotoPath();
        Uri uri = Uri.parse(uriS);
        GlideApp.with(this).load(uri).
                placeholder(R.mipmap.placeholder_common).into(photoView);
        messageTv.setText(message);
    }
}

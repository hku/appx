package com.chinaso.record.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.chinaso.record.R;
import com.chinaso.record.base.BaseActivity;
import com.chinaso.record.base.GlideApp;
import com.chinaso.record.entity.PhotoEntity;
import com.chinaso.record.utils.DensityUtil;
import com.chinaso.record.utils.ScreenUtils;
import com.github.chrisbanes.photoview.PhotoView;
import com.orhanobut.logger.Logger;

import butterknife.BindView;

/**
 * author: zhanghe
 * created on: 2018/6/5 16:36
 * description: 图片详情页面
 */

public class PhotoDetailActivity extends BaseActivity {

    public static final String PHOTO_SHOW_URI = "photo_show_uri";

    @BindView(R.id.photo_detail)
    ImageView photoView;
    @BindView(R.id.message)
    TextView messageTv;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.title_tv)
    TextView titleTv;

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
        String date = mPhotoInfo.getPhotoDate();
        titleTv.setText(date);
        String message = mPhotoInfo.getPhotoInfo();
        messageTv.setText(message);
        String uriS = mPhotoInfo.getPhotoPath();
        Uri uri = Uri.parse(uriS);
        handleImage(uri);
    }


    /**
     * 根据图片的宽高动态显示图片的显示效果
     *
     * @param uri
     */
    private void handleImage(Uri uri) {
        GlideApp.with(this).asBitmap().load(uri).
                placeholder(R.mipmap.placeholder_common).centerCrop().into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                int width = resource.getWidth();
                int height = resource.getHeight();
                int screenWidth = ScreenUtils.getScreenWidth(PhotoDetailActivity.this);
                int screenHeight = ScreenUtils.getScreenHeight(PhotoDetailActivity.this);
                LinearLayout.LayoutParams params;
                if (height > width) {
                    params = new LinearLayout.LayoutParams(screenWidth / 2, screenHeight / 2);
                } else {
                    params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, screenHeight / 2);
                }
                params.gravity = Gravity.CENTER_HORIZONTAL;
                photoView.setLayoutParams(params);
                photoView.setImageBitmap(resource);
            }
        });
        photoView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PhotoDetailActivity.this, PhotoShowActivity.class);
                intent.putExtra(PHOTO_SHOW_URI, mPhotoInfo.getPhotoPath());
                startActivity(intent);
            }
        });
    }
}

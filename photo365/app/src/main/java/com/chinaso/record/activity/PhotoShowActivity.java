package com.chinaso.record.activity;

import android.view.View;

import com.chinaso.record.R;
import com.chinaso.record.base.BaseActivity;
import com.chinaso.record.base.GlideApp;
import com.github.chrisbanes.photoview.PhotoView;

import butterknife.BindView;

/**
 * author: zhanghe
 * created on: 2018/6/24 17:35
 * description:整张图显示
 */

public class PhotoShowActivity extends BaseActivity {

    @BindView(R.id.photo_iv)
    PhotoView photoView;

    private String mUri;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_photo_show;
    }

    @Override
    protected void initData() {
        super.initData();
        mUri = getIntent().getStringExtra(PhotoDetailActivity.PHOTO_SHOW_URI);
    }

    @Override
    protected void initView() {
        super.initView();
        photoView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void business() {
        GlideApp.with(this).load(mUri).
                placeholder(R.mipmap.placeholder_common).centerCrop().into(photoView);
    }
}

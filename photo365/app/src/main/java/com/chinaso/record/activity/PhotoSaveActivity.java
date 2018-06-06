package com.chinaso.record.activity;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chinaso.record.base.BaseActivity;
import com.chinaso.record.R;
import com.chinaso.record.base.GlideApp;
import com.chinaso.record.entity.PhotoEntity;
import com.chinaso.record.utils.ImageRotateUtil;
import com.chinaso.record.utils.PhotoDaoManager;

import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * author: zhanghe
 * created on: 2018/6/4 17:35
 * description:图片保存页面
 */

public class PhotoSaveActivity extends BaseActivity {


    @BindView(R.id.image)
    ImageView imageView;
    @BindView(R.id.btn_save)
    Button saveBtn;
    @BindView(R.id.info_et)
    EditText infoEt;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    private Uri mPhotoUri;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_photo_save;
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
        mPhotoUri = getIntent().getParcelableExtra(MainActivity.PHOTO_URI);
    }

    @Override
    protected void business() {
        ImageRotateUtil.of().correctImage(this, mPhotoUri);
        GlideApp.with(PhotoSaveActivity.this).load(mPhotoUri).
                placeholder(R.mipmap.placeholder_common).into(imageView);
    }

    @OnClick({R.id.btn_save})
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.btn_save:
                save();
                break;
        }
    }

    private void save() {
        PhotoEntity photoEntity = new PhotoEntity();
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String photoInfo = infoEt.getText().toString();
        String photoPath = mPhotoUri.toString();
        photoEntity.setPhotoDate(timeStamp);
        photoEntity.setPhotoPath(photoPath);
        photoEntity.setPhotoInfo(photoInfo);
        PhotoDaoManager.getInstance().insert(photoEntity);
        Intent intent = new Intent(PhotoSaveActivity.this, PhotoListActivity.class);
        startActivity(intent);
        finish();
    }
}

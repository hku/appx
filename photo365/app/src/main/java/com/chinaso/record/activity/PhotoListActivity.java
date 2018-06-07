package com.chinaso.record.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.FileProvider;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chinaso.record.R;
import com.chinaso.record.adapter.PhotoItemAdapter;
import com.chinaso.record.base.BaseActivity;
import com.chinaso.record.base.GlideApp;
import com.chinaso.record.base.RecordApplication;
import com.chinaso.record.entity.PhotoEntity;
import com.chinaso.record.utils.PhotoDaoManager;
import com.chinaso.record.utils.UriParse;
import com.chinaso.record.widget.SpacesItemDecoration;
import com.orhanobut.logger.Logger;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * author: zhanghe
 * created on: 2018/6/5 14:29
 * description:图片列表页面
 */

public class PhotoListActivity extends BaseActivity {

    private static final int CAMERA_REQUEST_CODE = 100;
    private static final int CAMERA_SAVE_REQUEST_CODE = 101;
    public static final String PHOTO_DETAIL_INFO = "photo_detail";
    public static final String PHOTO_URI = "photo_uri";

    @BindView(R.id.refresh_layout)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerView;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.camera_btn)
    FloatingActionButton cameraBtn;

    private PhotoItemAdapter mAdpter;
    private int mPage = 0;
    private Uri mPhotoUri;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_photo_list;
    }

    @Override
    protected void initView() {
        super.initView();
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2,
                StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new SpacesItemDecoration(40));
        mAdpter = new PhotoItemAdapter(R.layout.item_photo_list, PhotoListActivity.this);
        mAdpter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                PhotoEntity entity = (PhotoEntity) adapter.getItem(position);
                Intent intent = new Intent(PhotoListActivity.this, PhotoDetailActivity.class);
                intent.putExtra(PHOTO_DETAIL_INFO, entity);
                startActivity(intent);
            }
        });
        recyclerView.setAdapter(mAdpter);
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                refresh();
            }
        });
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                getPhotoOffset();
            }
        });
    }

    @Override
    protected void business() {
        refreshLayout.autoRefresh();
    }

    private void refresh() {
        mPage = 0;
        mAdpter.clear();
        List<PhotoEntity> photoList = PhotoDaoManager.getInstance().query(mPage);
        mAdpter.addData(photoList);
        ++mPage;
        refreshLayout.finishRefresh(1000);
    }

    private void getPhotoOffset() {
        List<PhotoEntity> photoList = PhotoDaoManager.getInstance().query(mPage);
        mAdpter.addData(photoList);
        ++mPage;
        refreshLayout.finishLoadMore(1000);
    }

    @OnClick({R.id.camera_btn})
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.camera_btn:
                takePhoto();
                break;
        }
    }

    private void takePhoto() {
        Intent takeIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        mPhotoUri = getMediaFileUri();
        takeIntent.putExtra(MediaStore.EXTRA_OUTPUT, mPhotoUri);
        startActivityForResult(takeIntent, CAMERA_REQUEST_CODE);
    }

    /**
     * get uri
     *
     * @return
     */
    public Uri getMediaFileUri() {
        File mediaStorageDir = new File(RecordApplication.getContext().
                getExternalFilesDir(Environment.DIRECTORY_PICTURES), "record_img");
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                return null;
            }
        }
        //创建Media File
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File mediaFile = new File(mediaStorageDir.getPath() + File.separator + "IMG_" + timeStamp + ".jpg");
        Uri uri = null;
        if (Build.VERSION.SDK_INT >= 24) {
            uri = FileProvider.getUriForFile(this,
                    getPackageName() + ".fileprovider", mediaFile);
        } else {
            uri = Uri.fromFile(mediaFile);
        }
        return uri;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case CAMERA_REQUEST_CODE:
                    Intent intent = new Intent(PhotoListActivity.this, PhotoSaveActivity.class);
                    intent.putExtra(PHOTO_URI, mPhotoUri);
                    startActivityForResult(intent, CAMERA_SAVE_REQUEST_CODE);
                    break;
                case CAMERA_SAVE_REQUEST_CODE:
                    refreshLayout.autoRefresh();
                    break;
            }
        }
    }
}

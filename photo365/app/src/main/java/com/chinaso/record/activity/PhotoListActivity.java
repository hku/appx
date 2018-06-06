package com.chinaso.record.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chinaso.record.R;
import com.chinaso.record.adapter.PhotoItemAdapter;
import com.chinaso.record.base.BaseActivity;
import com.chinaso.record.entity.PhotoEntity;
import com.chinaso.record.utils.PhotoDaoManager;
import com.orhanobut.logger.Logger;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.List;

import butterknife.BindView;

/**
 * author: zhanghe
 * created on: 2018/6/5 14:29
 * description:图片列表页面
 */

public class PhotoListActivity extends BaseActivity {

    public static final String PHOTO_DETAIL_INFO = "photo_detail";

    @BindView(R.id.refresh_layout)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerView;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    private PhotoItemAdapter mAdpter;

    private int mPage = 0;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_photo_list;
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
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2,
                StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
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
}

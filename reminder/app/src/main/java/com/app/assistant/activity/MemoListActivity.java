package com.app.assistant.activity;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.app.assistant.R;
import com.app.assistant.adapter.MemoAdapter;
import com.app.assistant.base.BaseActivity;
import com.app.assistant.entity.MemoEntity;
import com.app.assistant.utils.Constant;
import com.app.assistant.utils.MemoDaoManager;
import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * author: zhanghe
 * created on: 2018/7/5 14:00
 * description:memo list
 */

public class MemoListActivity extends BaseActivity {

    private static final int REQUEST_CODE_ADD = 10010;
    private static final int REQUEST_CODE_UPDATE = 10011;


    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.memo_add_btn)
    FloatingActionButton addBtn;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerView;

    private MemoAdapter mAdapter;

    private int mClickPosition = -1;

    private String mTagS = "";

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_memo_list;
    }


    @Override
    protected void initData() {
        super.initData();
        mTagS = getIntent().getStringExtra("memo_tag");
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
        initRecycleView();
    }

    /**
     * init recyclerView
     */
    private void initRecycleView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        mAdapter = new MemoAdapter(R.layout.item_memo);
        recyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                if (view instanceof Button) {
                    List<MemoEntity> data = mAdapter.getData();
                    if (data != null) {
                        MemoEntity entity = data.get(position);
                        MemoDaoManager.getInstance().del(entity);
                        mAdapter.remove(position);
                    }
                } else if (view instanceof RelativeLayout) {
                    mClickPosition = position;
                    MemoEntity entity = mAdapter.getItem(position);
                    Intent intent = new Intent(MemoListActivity.this, MemoAddActivity.class);
                    intent.putExtra(Constant.DELIVER_TAG, entity);
                    startActivityForResult(intent, REQUEST_CODE_UPDATE);
                }
            }
        });
    }

    @Override
    protected void business() {
        List<MemoEntity> data = MemoDaoManager.getInstance().queryAllData(mTagS);
        mAdapter.addData(data);
    }

    @OnClick(R.id.memo_add_btn)
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.memo_add_btn:
                Intent intent = new Intent(MemoListActivity.this, MemoAddActivity.class);
                startActivityForResult(intent, REQUEST_CODE_ADD);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQUEST_CODE_ADD:
                    mAdapter.clear();
                    business();
                    break;
                case REQUEST_CODE_UPDATE:
                    if (mClickPosition != -1) {
                        MemoEntity memoEntity = (MemoEntity) data.getSerializableExtra("back");
                        mAdapter.set(mClickPosition, memoEntity);
                    }
                    break;
            }
        }
    }
}

package com.app.reminder.activity;

import android.media.Image;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.app.reminder.R;
import com.app.reminder.adapter.TagAdapter;
import com.app.reminder.base.BaseActivity;
import com.app.reminder.entity.ReminderEntity;
import com.app.reminder.utils.Constant;
import com.app.reminder.utils.ReminderDaoManager;
import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.Arrays;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * author: zhanghe
 * created on: 2018/7/5 14:15
 * description:添加页面
 */

public class AddActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recyclerview)
    RecyclerView tagRecycleView;
    @BindView(R.id.reminder_et)
    EditText contentEt;
    @BindView(R.id.reminder_add_iv)
    ImageView addIv;

    private TagAdapter mTagAdapter;

    private String mTagS = "";

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_add;
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

    private void initRecycleView() {
        GridLayoutManager layoutManager = new GridLayoutManager(this, 4);
        tagRecycleView.setLayoutManager(layoutManager);
        mTagAdapter = new TagAdapter(R.layout.item_tag);
        tagRecycleView.setAdapter(mTagAdapter);
        mTagAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                mTagS = mTagAdapter.getItem(position);
            }
        });
    }

    @Override
    protected void initData() {
        super.initData();
        String[] tagArray = Constant.TAG_ARRAY;
        mTagAdapter.addData(Arrays.asList(tagArray));
    }

    @Override
    protected void business() {

    }


    @OnClick({R.id.reminder_add_iv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.reminder_add_iv:
                onSave();
                break;
        }
    }

    private void onSave() {
        String content = contentEt.getText().toString();
        ReminderEntity entity = new ReminderEntity();
        entity.setContent(content);
        entity.setTagS(mTagS);
        ReminderDaoManager.getInstance().insert(entity);
    }
}

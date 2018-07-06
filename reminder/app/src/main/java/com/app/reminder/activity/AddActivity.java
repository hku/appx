package com.app.reminder.activity;

import android.content.Intent;
import android.media.Image;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.app.reminder.R;
import com.app.reminder.adapter.TagAdapter;
import com.app.reminder.base.BaseActivity;
import com.app.reminder.entity.ReminderEntity;
import com.app.reminder.utils.Constant;
import com.app.reminder.utils.ReminderDaoManager;
import com.app.reminder.utils.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.Arrays;
import java.util.List;

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

    private ReminderEntity mReminderEntity;

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

    /**
     * init recyclerView
     */
    private void initRecycleView() {
        GridLayoutManager layoutManager = new GridLayoutManager(this, 4);
        tagRecycleView.setLayoutManager(layoutManager);
        mTagAdapter = new TagAdapter(R.layout.item_tag);
        tagRecycleView.setAdapter(mTagAdapter);
        mTagAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                mTagAdapter.refesh(position);
                mTagS = mTagAdapter.getItem(position);
            }
        });
    }

    @Override
    protected void initData() {
        super.initData();
        String[] tagArray = Constant.TAG_ARRAY;
        mTagAdapter.addData(Arrays.asList(tagArray));
        mReminderEntity = (ReminderEntity) getIntent().getSerializableExtra(Constant.DELIVER_TAG);
        if (mReminderEntity != null) {
            String content = mReminderEntity.getContent();
            String tagS = mReminderEntity.getTagS();
            contentEt.setText(content);
            int tagPosition = getAdapterPosition(tagS);
            if (tagPosition != -1) {
                mTagAdapter.refesh(tagPosition);
                mTagS = mTagAdapter.getItem(tagPosition);
            }
        }
    }

    private int getAdapterPosition(String tagS) {
        List<String> tagList = mTagAdapter.getData();
        int result = -1;
        for (int i = 0; i < tagList.size(); i++) {
            String tag = tagList.get(i);
            if (tag.equals(tagS)) {
                result = i;
                break;
            }
        }
        return result;
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

    /**
     * save and back
     */
    private void onSave() {
        String content = contentEt.getText().toString();
        if (TextUtils.isEmpty(content)) {
            ToastUtils.show(AddActivity.this, "请输入内容");
            return;
        }
        if (TextUtils.isEmpty(mTagS)) {
            ToastUtils.show(AddActivity.this, "请点击分类标签");
            return;
        }
        //修改
        if (mReminderEntity != null) {
            mReminderEntity.setTagS(mTagS);
            mReminderEntity.setContent(content);
            ReminderDaoManager.getInstance().update(mReminderEntity);
            Intent intent = new Intent();
            intent.putExtra("back", mReminderEntity);
            setResult(RESULT_OK, intent);
        } else {//添加
            ReminderEntity entity = new ReminderEntity();
            entity.setContent(content);
            entity.setTagS(mTagS);
            ReminderDaoManager.getInstance().insert(entity);
            setResult(RESULT_OK);
        }
        finish();
    }
}

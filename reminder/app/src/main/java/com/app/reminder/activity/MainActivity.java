package com.app.reminder.activity;


import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.reminder.R;
import com.app.reminder.base.BaseActivity;
import com.app.reminder.entity.ReminderEntity;
import com.app.reminder.utils.Constant;
import com.app.reminder.utils.ReminderDaoManager;
import com.app.reminder.utils.ToastUtils;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * author: zhanghe
 * created on: 2018/6/4 15:57
 * description:主页面
 */

public class MainActivity extends BaseActivity {

    private static final int REQUEST = 10010;

    @BindView(R.id.show_iv)
    ImageView addIv;
    @BindView(R.id.content_tv)
    TextView contentTv;
    @BindView(R.id.tag_tv)
    TextView tagTv;
    @BindView(R.id.refesh_iv)
    ImageView refreshIv;
    @BindView(R.id.edit_iv)
    ImageView editIv;

    private ReminderEntity mReminderEntity;

    private long mClickTime = -1L;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initData() {
        super.initData();
        mReminderEntity = ReminderDaoManager.getInstance().getRandomItem();
    }

    @Override
    protected void business() {
        String content = "";
        String tagS = "";
        if (mReminderEntity != null) {
            content = mReminderEntity.getContent();
            tagS = mReminderEntity.getTagS();
        } else {
            content = "暂无更多内容";
            tagS = "暂无标签";
        }
        contentTv.setText(content);
        tagTv.setText(tagS);
    }

    @OnClick({R.id.show_iv, R.id.refesh_iv, R.id.edit_iv, R.id.content_tv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.show_iv:
                Intent intent = new Intent(MainActivity.this, ShowActivity.class);
                startActivity(intent);
                break;
            case R.id.refesh_iv:
                refresh();
                break;
            case R.id.edit_iv:
                if (mReminderEntity != null) {
                    Intent intentAdd = new Intent(MainActivity.this, AddActivity.class);
                    intentAdd.putExtra(Constant.DELIVER_TAG, mReminderEntity);
                    startActivityForResult(intentAdd, REQUEST);
                } else {
                    ToastUtils.show(this, "当前无数据，暂不可修改，请可尝试刷新");
                }
                break;
            case R.id.content_tv:
                doubleClickRefresh();
                break;
        }
    }

    /**
     * 双击刷新
     */
    private void doubleClickRefresh() {
        if (System.currentTimeMillis() - mClickTime < 800) {
            refresh();
        } else {
            mClickTime = System.currentTimeMillis();
        }
    }


    private void refresh() {
        if (mReminderEntity != null) {
            mReminderEntity = ReminderDaoManager.getInstance().getRandomItem(mReminderEntity);
            business();
        } else {
            mReminderEntity = ReminderDaoManager.getInstance().getRandomItem();
            business();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQUEST:
                    if (mReminderEntity != null) {
                        Long id = mReminderEntity.getId();
                        mReminderEntity = ReminderDaoManager.getInstance().queryById(id);
                        business();
                    }
                    break;
            }
        }
    }
}

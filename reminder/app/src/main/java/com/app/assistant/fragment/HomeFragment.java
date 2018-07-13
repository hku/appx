package com.app.assistant.fragment;

import android.content.Intent;
import android.support.v7.widget.PopupMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.assistant.R;
import com.app.assistant.activity.ClockListActivity;
import com.app.assistant.activity.MemoAddActivity;
import com.app.assistant.activity.MemoListActivity;
import com.app.assistant.base.BaseFragment;
import com.app.assistant.entity.ReminderEntity;
import com.app.assistant.utils.Constant;
import com.app.assistant.utils.ReminderDaoManager;
import com.app.assistant.utils.ToastUtils;

import butterknife.BindView;
import butterknife.OnClick;

import static android.app.Activity.RESULT_OK;

/**
 * author: zhanghe
 * created on: 2018/7/13 9:39
 * description: home fragment
 */

public class HomeFragment extends BaseFragment {

    private static final int REQUEST = 10010;

    @BindView(R.id.level_iv)
    ImageView levelIv;
    @BindView(R.id.content_tv)
    TextView contentTv;
    @BindView(R.id.tag_tv)
    TextView tagTv;
    @BindView(R.id.refresh_iv)
    ImageView refreshIv;
    @BindView(R.id.edit_iv)
    ImageView editIv;
    @BindView(R.id.memo_list_iv)
    ImageView memoListIv;

    private ReminderEntity mReminderEntity;

    private long mClickTime = -1L;

    @Override
    protected void initData() {
        super.initData();
        mReminderEntity = ReminderDaoManager.getInstance().getRandomItem();
    }

    @Override
    protected int getViewLayout() {
        return R.layout.fragment_home;
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

    @OnClick({R.id.level_iv, R.id.refresh_iv, R.id.edit_iv, R.id.content_tv, R.id.memo_list_iv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.level_iv:
                showMenu();
                break;
            case R.id.refresh_iv:
                refresh();
                break;
            case R.id.edit_iv:
                if (mReminderEntity != null) {
                    Intent intentAdd = new Intent(mContext, MemoAddActivity.class);
                    intentAdd.putExtra(Constant.DELIVER_TAG, mReminderEntity);
                    this.startActivityForResult(intentAdd, REQUEST);
                } else {
                    ToastUtils.show(mContext, "当前无数据，暂不可修改，请可尝试刷新");
                }
                break;
            case R.id.content_tv:
                doubleClickRefresh();
                break;
            case R.id.memo_list_iv:
                Intent memoIntent = new Intent(mContext, MemoListActivity.class);
                startActivity(memoIntent);
                break;
        }
    }

    /**
     * show menu
     */
    private void showMenu() {
        PopupMenu popupMenu = new PopupMenu(mContext, levelIv);
        popupMenu.getMenuInflater().inflate(R.menu.home, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.option_memo:
                        Intent memoIntent = new Intent(mContext, MemoListActivity.class);
                        startActivity(memoIntent);
                        break;
                    case R.id.option_clock:
                        Intent clockIntent = new Intent(mContext, ClockListActivity.class);
                        startActivity(clockIntent);
                        break;
                    case R.id.option_task:
                        break;
                }
                return false;
            }
        });
        popupMenu.show();
    }

    /**
     * double click to refresh
     */
    private void doubleClickRefresh() {
        if (System.currentTimeMillis() - mClickTime < 800) {
            refresh();
        } else {
            mClickTime = System.currentTimeMillis();
        }
    }

    /**
     * refresh
     */
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
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
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
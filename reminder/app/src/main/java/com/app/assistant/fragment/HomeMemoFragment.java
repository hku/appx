package com.app.assistant.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.app.assistant.R;
import com.app.assistant.activity.MemoAddActivity;
import com.app.assistant.activity.MemoListActivity;
import com.app.assistant.base.BaseFragment;
import com.app.assistant.entity.MemoEntity;
import com.app.assistant.entity.MessageEvent;
import com.app.assistant.utils.CommonUtils;
import com.app.assistant.utils.Constant;
import com.app.assistant.utils.LogUtils;
import com.app.assistant.utils.MemoDaoManager;
import com.app.assistant.utils.PreferenceKeyConstant;
import com.app.assistant.utils.SPUtils;
import com.app.assistant.utils.ToastUtils;
import com.labo.kaji.fragmentanimations.FlipAnimation;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.OnClick;

import static android.app.Activity.RESULT_OK;

/**
 * author: zhanghe
 * created on: 2018/7/23 19:39
 * description: 首页memo
 */

public class HomeMemoFragment extends BaseFragment {

    @BindView(R.id.memo_content_tv)
    TextView contentTv;
    @BindView(R.id.tag_tv)
    TextView memoTagTv;
    @BindView(R.id.refresh_iv)
    ImageView refreshIv;
    @BindView(R.id.edit_iv)
    ImageView memoEditIv;
    @BindView(R.id.memo_list_iv)
    ImageView memoListIv;
    @BindView(R.id.memo_layout)
    LinearLayout memoLLayout;

    private static final long DURATION = 500;
    private static final int REQUEST = 10010;
    private static final String TAG_KEY = "tag_key";

    private long mClickTime = -1L;
    private ScheduledExecutorService mExecService;

    private MemoEntity mMemoEntity;

    private String mTagS = "";


    public static HomeMemoFragment newInstance(String tagS) {
        HomeMemoFragment fragment = new HomeMemoFragment();
        Bundle bundle = new Bundle();
        bundle.putString(TAG_KEY, tagS);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getViewLayout() {
        return R.layout.fragment_home_memo;
    }

    @Override
    protected void initData() {
        super.initData();
        mTagS = getArguments().getString(TAG_KEY);
        mMemoEntity = MemoDaoManager.getInstance().getRandomItem(mTagS);
    }

    @Override
    protected void business() {
        initMemo();
//        scheduledMemoRefresh();
    }


    /**
     * init memo
     */
    private void initMemo() {
        String content = "";
        if (mMemoEntity != null) {
            content = mMemoEntity.getContent();
            boolean isBuiltIn = mMemoEntity.getIsBuiltIn();
            if (isBuiltIn) {
                memoEditIv.setVisibility(View.GONE);
            } else {
                memoEditIv.setVisibility(View.VISIBLE);
            }
        } else {
            content = "暂无更多内容";
            memoEditIv.setVisibility(View.GONE);
        }
        contentTv.setText(content);
        memoTagTv.setText(mTagS);
    }

    /**
     * 在memo显示的情况下，memo10s自动刷新一次
     */
    private void scheduledMemoRefresh() {
        int isShow = memoLLayout.getVisibility();
        if (isShow == View.VISIBLE) {
            mExecService = Executors.newScheduledThreadPool(3);
            mExecService.scheduleAtFixedRate(new Runnable() {
                @Override
                public void run() {
                    try {
                        new Handler(Looper.getMainLooper()).post(new Runnable() {
                            @Override
                            public void run() {
                                refresh();
                            }
                        });
                    } catch (Throwable throwable) {
                        LogUtils.d("zhanghe " + "scheduledMemoRefresh error" + throwable.toString());
                    }
                }
            }, 10, 10, TimeUnit.SECONDS);
        }
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
        if (mMemoEntity != null) {
            mMemoEntity = MemoDaoManager.getInstance().getRandomItem(mMemoEntity, mTagS);
            initMemo();
        } else {
            mMemoEntity = MemoDaoManager.getInstance().getRandomItem(mTagS);
            initMemo();
        }
    }

    @OnClick({R.id.refresh_iv, R.id.edit_iv, R.id.memo_content_tv,
            R.id.memo_list_iv, R.id.tag_tv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.refresh_iv:
                refresh();
                break;
            case R.id.edit_iv:
                if (mMemoEntity != null) {
                    Intent intentAdd = new Intent(mContext, MemoAddActivity.class);
                    intentAdd.putExtra(Constant.DELIVER_TAG, mMemoEntity);
                    this.startActivityForResult(intentAdd, REQUEST);
                } else {
                    ToastUtils.show(mContext, "当前无数据，暂不可修改，请可尝试刷新");
                }
                break;
            case R.id.memo_content_tv:
                doubleClickRefresh();
                break;
            case R.id.memo_list_iv:
                Intent memoIntent = new Intent(mContext, MemoListActivity.class);
                memoIntent.putExtra("memo_tag", mTagS);
                startActivity(memoIntent);
                break;
            case R.id.tag_tv:
                CommonUtils.sendSignal(MessageEvent.IdPool.HOME_MEMO_FLIP_LEFT);
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQUEST:
                    if (mMemoEntity != null) {
                        Long id = mMemoEntity.getId();
                        mMemoEntity = MemoDaoManager.getInstance().queryById(id);
                        initMemo();
                    }
                    break;
            }
        }
    }

    @Override
    public Animation onCreateAnimation(int transit, boolean enter, int nextAnim) {
        return FlipAnimation.create(FlipAnimation.LEFT, enter, DURATION);
    }
}

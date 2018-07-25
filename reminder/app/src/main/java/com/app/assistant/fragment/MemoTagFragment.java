package com.app.assistant.fragment;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.Animation;

import com.app.assistant.R;
import com.app.assistant.adapter.TagAdapter;
import com.app.assistant.base.BaseFragment;
import com.app.assistant.entity.MessageEvent;
import com.app.assistant.utils.CommonUtils;
import com.app.assistant.utils.Constant;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.labo.kaji.fragmentanimations.FlipAnimation;

import java.util.Arrays;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * author: zhanghe
 * created on: 2018/7/23 19:44
 * description: memo tags
 */

public class MemoTagFragment extends BaseFragment {

    @BindView(R.id.recyclerview)
    RecyclerView tagRecycleView;

    private static final long DURATION = 500;
    private TagAdapter mTagAdapter;
    private String mTagS;

    public static MemoTagFragment newInstance() {
        return new MemoTagFragment();
    }


    @Override
    protected void initView() {
        super.initView();
        initRecycleView();
    }

    @Override
    protected int getViewLayout() {
        return R.layout.fragment_memo_tag;
    }

    @Override
    protected void business() {

    }

    /**
     * * init recyclerView
     */
    private void initRecycleView() {
        GridLayoutManager manager = new GridLayoutManager(mContext, 3);
        tagRecycleView.setLayoutManager(manager);
        mTagAdapter = new TagAdapter(R.layout.item_tag);
        String[] tagArray = Constant.TAG_ARRAY;
        mTagAdapter.addData(Arrays.asList(tagArray));
        tagRecycleView.setAdapter(mTagAdapter);
        mTagAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                mTagS = mTagAdapter.getItem(position);
                CommonUtils.sendSignal(MessageEvent.IdPool.HOME_MEMO_FLIP_RIGHT, mTagS);
            }
        });
    }

    @Override
    public Animation onCreateAnimation(int transit, boolean enter, int nextAnim) {
        return FlipAnimation.create(FlipAnimation.LEFT, enter, DURATION);
    }

    @OnClick({R.id.setting_iv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.setting_iv:
                CommonUtils.sendSignal(MessageEvent.IdPool.HOME_SETTING);
                break;
        }
    }
}

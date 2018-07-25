package com.app.assistant.fragment;

import android.content.Intent;
import android.support.v7.widget.SwitchCompat;
import android.view.View;
import android.widget.CompoundButton;

import com.app.assistant.R;
import com.app.assistant.activity.HelpActivity;
import com.app.assistant.base.BaseFragment;
import com.app.assistant.entity.MessageEvent;
import com.app.assistant.utils.CommonUtils;
import com.app.assistant.utils.PreferenceKeyConstant;
import com.app.assistant.utils.SPUtils;

import org.greenrobot.eventbus.util.HasExecutionScope;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * author: zhanghe
 * created on: 2018/7/13 9:39
 * description:设置
 */

public class SettingFragment extends BaseFragment {


    @BindView(R.id.memo_sc)
    SwitchCompat memoSC;
    @BindView(R.id.clock_sc)
    SwitchCompat clockSC;
    @BindView(R.id.task_sc)
    SwitchCompat taskSC;
    @BindView(R.id.search_sc)
    SwitchCompat searchSC;

    @Override
    protected int getViewLayout() {
        return R.layout.fragment_setting;
    }

    @Override
    protected void business() {
        memoSC.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                CommonUtils.sendSignal(MessageEvent.IdPool.HOME_MEMO_SHOW, isChecked);
                SPUtils.getInstance().put(PreferenceKeyConstant.HOME_MEMO_SHOW_KEY, isChecked);
            }
        });
        clockSC.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                CommonUtils.sendSignal(MessageEvent.IdPool.HOME_CLOCK_SHOW, isChecked);
                SPUtils.getInstance().put(PreferenceKeyConstant.HOME_CLOCK_SHOW_KEY, isChecked);
            }
        });
        taskSC.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                CommonUtils.sendSignal(MessageEvent.IdPool.HOME_TASK_SHOW, isChecked);
                SPUtils.getInstance().put(PreferenceKeyConstant.HOME_TASK_SHOW_KEY, isChecked);
            }
        });
        searchSC.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                CommonUtils.sendSignal(MessageEvent.IdPool.HOME_SEARCH_SHOW, isChecked);
                SPUtils.getInstance().put(PreferenceKeyConstant.HOME_SEARCH_SHOW_KEY, isChecked);
            }
        });
        boolean memoChecked = SPUtils.getInstance().getBoolean(
                PreferenceKeyConstant.HOME_MEMO_SHOW_KEY, true);
        memoSC.setChecked(memoChecked);
        boolean clockChecked = SPUtils.getInstance().getBoolean(
                PreferenceKeyConstant.HOME_CLOCK_SHOW_KEY, true);
        clockSC.setChecked(clockChecked);
        boolean taskChecked = SPUtils.getInstance().getBoolean(
                PreferenceKeyConstant.HOME_TASK_SHOW_KEY, true);
        taskSC.setChecked(taskChecked);
        boolean searchChecked = SPUtils.getInstance().getBoolean(
                PreferenceKeyConstant.HOME_SEARCH_SHOW_KEY, true);
        searchSC.setChecked(searchChecked);
    }

    @OnClick({R.id.help_rlayout})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.help_rlayout:
                Intent helpIntent = new Intent(mContext, HelpActivity.class);
                startActivity(helpIntent);
                break;
        }
    }
}

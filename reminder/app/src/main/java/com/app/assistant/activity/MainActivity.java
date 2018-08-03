package com.app.assistant.activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.app.assistant.R;
import com.app.assistant.base.BaseActivity;
import com.app.assistant.entity.MessageEvent;
import com.app.assistant.fragment.HomeFragment;
import com.app.assistant.fragment.SettingFragment;
import com.app.assistant.utils.GsonUtils;
import com.app.assistant.utils.LogUtils;
import com.app.assistant.utils.ToastUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;

/**
 * author: zhanghe
 * created on: 2018/7/13 9:22
 * description:主页面
 */

public class MainActivity extends BaseActivity {

    @BindView(R.id.bottomMenu)
    RadioGroup bottomMenu;
    @BindView(R.id.rb_home)
    RadioButton homeRB;
    @BindView(R.id.rb_other)
    RadioButton settingRB;

    private long mExitTime = 0L;
    private int mCurrIndex = 0;
    private ArrayList<String> mFragmentTags;
    private FragmentManager mFragmentManager;

    @Override
    protected void initData() {
        super.initData();
        String[] tabSArray = getResources().getStringArray(R.array.tab);
        mFragmentTags = new ArrayList<>(Arrays.asList(tabSArray));
        mFragmentManager = getSupportFragmentManager();
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        bottomMenu.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_home:
                        mCurrIndex = 0;
                        break;
                    case R.id.rb_other:
                        mCurrIndex = 1;
                        break;
                    default:
                        mCurrIndex = 0;
                        break;
                }
                showFragment();
            }
        });
        showFragment();
    }

    @Override
    protected void business() {

    }

    private void showFragment() {
        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
        Fragment fragment = mFragmentManager.findFragmentByTag(mFragmentTags.get(mCurrIndex));
        if (fragment == null) {
            fragment = instantFragment(mCurrIndex);
        }
        for (int i = 0; i < mFragmentTags.size(); i++) {
            Fragment f = mFragmentManager.findFragmentByTag(mFragmentTags.get(i));

            if (f != null && f != fragment && f.isAdded()) {
                fragmentTransaction.hide(f);
            }
        }
        if (fragment.isAdded()) {
            fragmentTransaction.show(fragment);
        } else {
            fragmentTransaction.add(R.id.fragment_container, fragment, mFragmentTags.get(mCurrIndex));
        }
        fragmentTransaction.commit();
        mFragmentManager.executePendingTransactions();
    }

    private Fragment instantFragment(int currIndex) {
        switch (currIndex) {
            case 0:
                return new HomeFragment();
            case 1:
                return new SettingFragment();
            default:
                return new HomeFragment();
        }
    }

    @Override
    public void onMessageEvent(MessageEvent event) {
        super.onMessageEvent(event);
        int id = event.getId();
        if (id == MessageEvent.IdPool.HOME_SETTING) {
            settingRB.setChecked(true);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if ((System.currentTimeMillis() - mExitTime) > 2000) {
                ToastUtils.show(this, getResources().getString(R.string.exit_app));
                mExitTime = System.currentTimeMillis();
            } else {
                finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}

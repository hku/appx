package com.app.assistant.activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
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
        List<String> list = new ArrayList<>();
        list.add("人生就像蒲公英，看似自由，却身不由己。有些事，不是不在意，而是在意了又能怎样。自己尽力了就好，人生没有如果，只有后果和结果。");
        list.add("千万别让人偷了你的梦，梦想是你自己的。不要让别人左右你的决定。别人相信的事实，未必是你的。追逐过，你就知道别人认为不可能的事，是有可能的");
        list.add("间是化解证明表白一切的最好良方。有些东西往往越辩解越扯不清，就让时间去解决吧。");
        list.add("男人爱上女人后， 他会做诗；女人爱上男人后， 她会做梦");
        list.add("人生只有走出来的美丽，没有等出来的辉煌");
        list.add("在真实的生命里，每桩伟业都由信心开始，并由信心跨出第一步");
        list.add("宁愿辛苦一阵子，不要辛苦一辈子");
        list.add("人生最大的敌人是自己怯懦");
        list.add("成功的秘诀在于坚持自已的目标和信念");
        list.add("要成功，不要与马赛跑，要骑在马上，马上成功");
        list.add("磨练，使人难以忍受，使人步履维艰，但它能使强者站得更挺，走得更稳，产生更强的斗志");
        list.add("无人理睬时，坚定执着。万人羡慕时，心如止水");
        list.add("如果你向神求助，说明你相信神的能力；如果神没有帮助你，说明神相信你的能力");
        list.add("向着目标奔跑，何必在意折翼的翅膀，只要信心不死，就看的见方向，顺风适合行走，逆风更适合飞翔，人生路上什么都不怕，就怕自己投降。");
        list.add("善待自己，不被别人左右，也不去左右别人，自信优雅");
        list.add("再美好的梦想与目标，再完美的计划和方案，如果不能尽快在行动中落实，最终只能是纸上谈兵，空想一番");
        String json = GsonUtils.toJsonString(list);
        LogUtils.d("zhanghe " + "json");
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

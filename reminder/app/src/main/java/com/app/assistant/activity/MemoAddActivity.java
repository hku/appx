package com.app.assistant.activity;

import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.app.assistant.R;
import com.app.assistant.base.BaseActivity;
import com.app.assistant.entity.MemoEntity;
import com.app.assistant.utils.Constant;
import com.app.assistant.utils.MemoDaoManager;
import com.app.assistant.utils.ToastUtils;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * author: zhanghe
 * created on: 2018/7/5 14:15
 * description:添加页面
 */

public class MemoAddActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.reminder_et)
    EditText contentEt;
    @BindView(R.id.reminder_add_iv)
    ImageView addIv;

    private static final String MEMO_TAG = "memo";

    private MemoEntity mMemoEntity;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_memo_add;
    }

    @Override
    protected void initData() {
        super.initData();
        mMemoEntity = (MemoEntity) getIntent().getSerializableExtra(Constant.DELIVER_TAG);
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
    }

    @Override
    protected void business() {
        if (mMemoEntity != null) {
            String content = mMemoEntity.getContent();
            contentEt.setText(content);
            contentEt.setSelection(contentEt.getText().length());
        }
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
     * save and cabBack
     */
    private void onSave() {
        String content = contentEt.getText().toString();
        if (TextUtils.isEmpty(content)) {
            ToastUtils.show(MemoAddActivity.this, "请输入内容");
            return;
        }
        //修改
        if (mMemoEntity != null) {
            mMemoEntity.setTagS(MEMO_TAG);
            mMemoEntity.setContent(content);
            mMemoEntity.setIsBuiltIn(false);
            MemoDaoManager.getInstance().update(mMemoEntity);
            Intent intent = new Intent();
            intent.putExtra("cabBack", mMemoEntity);
            setResult(RESULT_OK, intent);
        } else {//添加
            MemoEntity entity = new MemoEntity();
            entity.setContent(content);
            entity.setTagS(MEMO_TAG);
            entity.setIsBuiltIn(false);
            MemoDaoManager.getInstance().insert(entity);
            setResult(RESULT_OK);
        }
        finish();
    }
}

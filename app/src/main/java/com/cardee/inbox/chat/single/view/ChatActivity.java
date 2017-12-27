package com.cardee.inbox.chat.single.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;

import com.cardee.R;
import com.cardee.data_source.inbox.local.chat.entity.ChatMessage;
import com.cardee.inbox.chat.single.adapter.SingleChatAdapter;
import com.cardee.inbox.chat.single.presenter.ChatContract;
import com.cardee.inbox.chat.single.presenter.ChatPresenter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ChatActivity extends AppCompatActivity implements ChatContract.View {

    @BindView(R.id.chat_list)
    RecyclerView mRecyclerView;

    private ChatPresenter mPresenter;
    private SingleChatAdapter mAdapter;
    private Toast mCurrentToast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        ButterKnife.bind(this);
        initToolBar();
        initAdapter();
        initPresenter();
    }

    private void initToolBar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(null);
    }

    private void initAdapter() {
        mAdapter = new SingleChatAdapter();
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(mAdapter);
    }

    private void initPresenter() {
        mPresenter = new ChatPresenter(this);
        mPresenter.init(getIntent().getExtras(), findViewById(R.id.chat_root_view));
        mPresenter.onChatDataRequest();
    }

    @Override
    public void notifyAboutInboxDataObtained() {
        mPresenter.onGetMessagesRequest();
    }

    @Override
    public void setMessageList(List<ChatMessage> messageList) {
        mAdapter.setMessageList(messageList);
    }

    @Override
    public void showProgress(boolean show) {

    }

    @Override
    public void showMessage(String message) {
        if (mCurrentToast != null) {
            mCurrentToast.cancel();
        }
        mCurrentToast = Toast.makeText(this, message, Toast.LENGTH_SHORT);
        mCurrentToast.show();
    }

    @Override
    public void showMessage(int messageId) {
        showMessage(getString(messageId));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return true;
    }

    @Override
    protected void onDestroy() {
        if (mPresenter != null) mPresenter.onDestroy();
        super.onDestroy();
    }
}

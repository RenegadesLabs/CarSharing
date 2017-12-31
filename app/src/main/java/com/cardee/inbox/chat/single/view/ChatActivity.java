package com.cardee.inbox.chat.single.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
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
    @BindView(R.id.chat_activity_progress_bar)
    ProgressBar mProgressBar;

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
//        mRecyclerView.addOnLayoutChangeListener((view, left, top, right, bottom, oldLeft, oldTop, oldRight, oldBottom) -> {
//            if (bottom < oldBottom) {
//                mRecyclerView.post(() -> mRecyclerView.smoothScrollToPosition(mRecyclerView.getAdapter().getItemCount() - 1));
//            }
//        });
        mRecyclerView.setLayoutManager(getLayoutManager());
        mRecyclerView.setAdapter(mAdapter);
    }

    private RecyclerView.LayoutManager getLayoutManager() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
//        layoutManager.setStackFromEnd(true);
        return layoutManager;
    }

    private void initPresenter() {
        mPresenter = new ChatPresenter(this);
        mPresenter.init(getIntent().getExtras(), findViewById(R.id.chat_root_view));
        mPresenter.onChatDataRequest();
    }

    @Override
    public void setMessageList(List<ChatMessage> messageList) {
        mAdapter.setMessageList(messageList);
        scrollRecycler(messageList.size() - 1);
    }

    @Override
    public void updateAllMessages(List<ChatMessage> messageList) {
        mAdapter.updateMessageList(messageList);
        scrollRecycler(messageList.size() - 1);
    }

    private void scrollRecycler(int position) {
        mRecyclerView.scrollToPosition(position);
    }

    @Override
    public void showProgress(boolean show) {
        mProgressBar.setVisibility(show ? View.VISIBLE : View.GONE);
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

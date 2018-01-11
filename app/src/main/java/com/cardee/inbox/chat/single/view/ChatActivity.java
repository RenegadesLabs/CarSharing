package com.cardee.inbox.chat.single.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.cardee.R;
import com.cardee.inbox.chat.single.presenter.ChatContract;
import com.cardee.inbox.chat.single.presenter.ChatPresenter;
import com.cardee.owner_profile_info.view.OwnerProfileInfoActivity;
import com.cardee.renter_profile.view.RenterProfileActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ChatActivity extends AppCompatActivity implements ChatContract.View {

    @BindView(R.id.chat_activity_progress_bar)
    ProgressBar mProgressBar;

    private ChatPresenter mPresenter;
    private Toast mCurrentToast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        ButterKnife.bind(this);

        initToolBar();
        initPresenter();
    }

    private void initToolBar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(null);
    }

    private void initPresenter() {
        mPresenter = new ChatPresenter(this);
        mPresenter.init(getIntent().getExtras(), findViewById(R.id.chat_root_view));
        mPresenter.onChatDataRequest();
    }

    @Override
    public void openOwnerAccount(Integer recipientId) {
        Intent intent = new Intent(this, OwnerProfileInfoActivity.class);
        intent.putExtra("editable", false);
        intent.putExtra("profile_id", recipientId);
        startActivity(intent);
    }

    @Override
    public void openRenterAccount(Integer recipientId) {
        Intent intent = new Intent(this, RenterProfileActivity.class);
        intent.putExtra("editable", false);
        intent.putExtra("profile_id", recipientId);
        startActivity(intent);
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
        if (mPresenter != null) {
            mPresenter.onDestroy();
            mPresenter = null;
        }
        super.onDestroy();
    }
}

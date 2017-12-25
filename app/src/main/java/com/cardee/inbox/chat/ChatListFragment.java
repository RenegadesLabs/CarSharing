package com.cardee.inbox.chat;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cardee.R;
import com.cardee.data_source.inbox.local.entity.Chat;
import com.cardee.inbox.chat.adapter.ChatListAdapter;
import com.cardee.inbox.chat.item.view.ChatActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ChatListFragment extends Fragment implements ChatListContract.View {

    @BindView(R.id.chat_recycler)
    RecyclerView mChatRecycler;

    private ChatListPresenterImp mPresenterImp;
    private ChatListAdapter mChatAdapter;

    public static ChatListFragment newInstance() {
        Bundle args = new Bundle();
        ChatListFragment fragment = new ChatListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenterImp = new ChatListPresenterImp(getActivity());
        mPresenterImp.onInit(this);
        initAdapter();
    }

    private void initAdapter() {
        mChatAdapter = new ChatListAdapter(getActivity());
        mChatAdapter.subscribeToChatClick(chat -> {
            if (mPresenterImp != null) {
                mPresenterImp.onChatClick(chat);
            }
        });
        mChatAdapter.subscribeToUnreadMessage(isUnread -> {
            if (mPresenterImp != null) {
                mPresenterImp.onUnreadMessageReceived(isUnread);
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_inbox_chats, container, false);
        ButterKnife.bind(this, root);
        initRecycler();
        mPresenterImp.onGetChats();
        return root;
    }

    private void initRecycler() {
        mChatRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        mChatRecycler.setAdapter(mChatAdapter);
    }

    @Override
    public void showAllChats(List<Chat> chatList) {
        mChatAdapter.addItems(chatList);
    }

    @Override
    public void showChat(Bundle bundle) {
        Intent intent = new Intent(getActivity(), ChatActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @Override
    public void showProgress(boolean show) {

    }

    @Override
    public void showMessage(String message) {

    }

    @Override
    public void showMessage(int messageId) {

    }

    @Override
    public void onDestroy() {
        mPresenterImp.onDestroy();
        super.onDestroy();
    }
}

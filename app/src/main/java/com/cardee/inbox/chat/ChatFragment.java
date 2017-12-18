package com.cardee.inbox.chat;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cardee.R;
import com.cardee.domain.inbox.usecase.entity.InboxChat;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.functions.Consumer;

public class ChatFragment extends Fragment implements ChatContract.View {

    @BindView(R.id.chat_recycler)
    RecyclerView mChatRecycler;

    private ChatPresenterImp mPresenterImp;
    private ChatAdapter mChatAdapter;

    public static ChatFragment newInstance() {
        Bundle args = new Bundle();
        ChatFragment fragment = new ChatFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenterImp = new ChatPresenterImp(getActivity());
        mPresenterImp.onInit(this);
        mChatAdapter = new ChatAdapter(getActivity());
        mChatAdapter.subscribe(mPresenterImp);
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
    public void showAllChats(List<InboxChat> chatList) {
        mChatAdapter.addItems(chatList);
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

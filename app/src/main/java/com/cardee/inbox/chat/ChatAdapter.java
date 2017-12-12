package com.cardee.inbox.chat;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.cardee.R;
import com.cardee.domain.inbox.usecase.entity.InboxChat;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.functions.Consumer;
import io.reactivex.subjects.PublishSubject;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ChatViewHolder> {

    private List<InboxChat> mInboxChats;
    private final RequestManager mRequestManager;
    private final PublishSubject<InboxChat> mOnClickSubject;

    ChatAdapter(Context context) {
        mInboxChats = new ArrayList<>();
        mRequestManager = Glide.with(context);
        mOnClickSubject = PublishSubject.create();
    }

    @Override
    public ChatViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View root = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_inbox_chat, parent, false);
        return new ChatViewHolder(root);
    }

    @Override
    public void onBindViewHolder(ChatViewHolder holder, int position) {
        InboxChat chat = mInboxChats.get(position);

        mRequestManager.load(chat.getRecipientPhotoUrl()).into(holder.mAvatar);
        mRequestManager.load(chat.getCarPhotoUrl()).into(holder.mCarPreview);
        holder.mCompanionName.setText(chat.getRecipientName());
        holder.mLastMessage.setText(chat.getLastMessageText());
        holder.mLastMessageTime.setText(chat.getLastMessageTime());

        holder.mContainer.setOnClickListener(view -> mOnClickSubject.onNext(chat));
    }

    void addItems(List<InboxChat> list) {
        mInboxChats.addAll(list);
        notifyDataSetChanged();
    }

    void updateItem(InboxChat newChat) {
        int position = mInboxChats.indexOf(newChat);
        if (isChatExist(position)) {
            mInboxChats.add(position, newChat);
            notifyItemChanged(position);
        } else {
            mInboxChats.add(0, newChat);
            notifyDataSetChanged();
        }
    }

    public void subscribe(Consumer<InboxChat> consumer) {
        mOnClickSubject.subscribe(consumer);
    }

    private boolean isChatExist(int position) {
        return position != -1;
    }

    @Override
    public int getItemCount() {
        return mInboxChats.size();
    }

    class ChatViewHolder extends RecyclerView.ViewHolder {

        View mContainer;
        ImageView mAvatar;
        ImageView mCarPreview;

        TextView mCompanionName;
        TextView mLastMessage;
        TextView mLastMessageTime;

        ChatViewHolder(View itemView) {
            super(itemView);
            mContainer = itemView;
            mAvatar = itemView.findViewById(R.id.chat_avatar);
            mCarPreview = itemView.findViewById(R.id.chat_car_preview);
            mCompanionName = itemView.findViewById(R.id.chat_companion_name);
            mLastMessage = itemView.findViewById(R.id.chat_last_message);
            mLastMessageTime = itemView.findViewById(R.id.chat_last_message_time);
        }
    }
}

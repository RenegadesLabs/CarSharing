package com.cardee.inbox.chat.adapter;

import android.content.Context;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.cardee.CardeeApp;
import com.cardee.R;
import com.cardee.domain.inbox.usecase.entity.InboxChat;
import com.cardee.util.glide.CircleTransform;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.functions.Consumer;
import io.reactivex.subjects.PublishSubject;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ChatViewHolder> {

    private List<InboxChat> mInboxChats;
    private final RequestManager mRequestManager;
    private final PublishSubject<InboxChat> mOnClickSubject;
    private final PublishSubject<Integer> mUnreadSubject;
    private final UtcDateFormatter mDateFormatter;

    public ChatAdapter(Context context) {
        mInboxChats = new ArrayList<>();
        mDateFormatter = new ChatDateFormatter(context);
        mRequestManager = Glide.with(context);
        mOnClickSubject = PublishSubject.create();
        mUnreadSubject = PublishSubject.create();
    }

    @Override
    public ChatViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View root = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_inbox_chat, parent, false);
        return new ChatViewHolder(root);
    }

    @Override
    public void onBindViewHolder(ChatViewHolder holder, int position) {
        InboxChat chat = mInboxChats.get(position);
        mRequestManager
                .load(chat.getRecipientPhotoUrl())
                .centerCrop()
                .placeholder(R.drawable.ic_photo_placeholder)
                .transform(new CircleTransform(CardeeApp.context))
                .into(holder.mAvatar);
        mRequestManager
                .load(chat.getCarPhotoUrl())
                .centerCrop()
                .into(holder.mCarPreview);

        holder.mCompanionName.setText(chat.getRecipientName());
        holder.mLastMessage.setText(chat.getLastMessageText());
        holder.mLastMessageTime.setText(mDateFormatter.formatDate(chat.getLastMessageTime()));
        setUnreadMessageCount(chat, holder);
        holder.mContainer.setOnClickListener(view -> mOnClickSubject.onNext(chat));
    }

    private void setUnreadMessageCount(InboxChat chat, ChatViewHolder holder) {
        int unreadMessages = chat.getUnreadMessageCount();
        if (unreadMessages == 0) {
            holder.mUnreadCount.setText(null);
            holder.mUnreadCount.setVisibility(View.GONE);
            holder.mUnreadView.setVisibility(View.GONE);
        } else {
            holder.mUnreadCount.setText(String.valueOf(unreadMessages));
            holder.mUnreadCount.setVisibility(View.VISIBLE);
            holder.mUnreadView.setVisibility(View.VISIBLE);
        }
        mUnreadSubject.onNext(unreadMessages);
    }

    public void addItems(List<InboxChat> list) {
        mInboxChats.clear();
        mInboxChats.addAll(list);
        notifyDataSetChanged();
    }

    private void updateList(List<InboxChat> newChatList) {
        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(new ChatDiffCallback(mInboxChats, newChatList));
        diffResult.dispatchUpdatesTo(this);
    }

    public void subscribe(Consumer<InboxChat> consumer) {
        mOnClickSubject
                .debounce(300, TimeUnit.MILLISECONDS)
                .subscribe(consumer);
    }

    @Override
    public int getItemCount() {
        return mInboxChats.size();
    }

    class ChatViewHolder extends RecyclerView.ViewHolder {

        View mContainer;
        ImageView mAvatar;
        ImageView mCarPreview;
        ImageView mUnreadView;
        TextView mUnreadCount;

        TextView mCompanionName;
        TextView mLastMessage;
        TextView mLastMessageTime;

        ChatViewHolder(View itemView) {
            super(itemView);
            mContainer = itemView;
            mAvatar = itemView.findViewById(R.id.chat_avatar);
            mCarPreview = itemView.findViewById(R.id.chat_car_preview);
            mUnreadView = itemView.findViewById(R.id.chat_unread_message);
            mUnreadCount = itemView.findViewById(R.id.chat_unread_message_count);

            mCompanionName = itemView.findViewById(R.id.chat_companion_name);
            mLastMessage = itemView.findViewById(R.id.chat_last_message);
            mLastMessageTime = itemView.findViewById(R.id.chat_last_message_time);
        }
    }
}

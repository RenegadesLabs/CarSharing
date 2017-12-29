package com.cardee.inbox.chat.single.adapter;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cardee.R;
import com.cardee.data_source.inbox.local.chat.entity.ChatMessage;
import com.cardee.inbox.chat.list.adapter.UtcDateFormatter;

import java.util.ArrayList;
import java.util.List;

public class SingleChatAdapter extends RecyclerView.Adapter {

    private static final int USER_MESSAGE_TYPE = 0;
    private static final int RECIPIENT_MESSAGE_TYPE = 1;

    private List<ChatMessage> mMessageList;
    private UtcDateFormatter.ChatMessageFormatter mMessageFormatter;

    public SingleChatAdapter() {
        mMessageList = new ArrayList<>();
        mMessageFormatter = new MessageDateFormatter();
    }

    @Override
    public int getItemViewType(int position) {
        if (mMessageList.get(position).getInbox()) return RECIPIENT_MESSAGE_TYPE;
        else return USER_MESSAGE_TYPE;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rootView;
        switch (viewType) {
            case USER_MESSAGE_TYPE:
                rootView = getLayoutInflater(parent.getContext()).inflate(R.layout.view_chatmessage_user, parent, false);
                return new UserMessageHolder(rootView);
            default:
                rootView = getLayoutInflater(parent.getContext()).inflate(R.layout.view_chatmessage_recipient, parent, false);
                return new RecipientMessageHolder(rootView);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ChatMessage chatMessage = mMessageList.get(position);
        boolean isNewDay = false;

        if (position == 0 && !mMessageFormatter.sameWithCurrentDate(chatMessage.getDateCreated())) {
            isNewDay = true;
        } else if (position < mMessageList.size()) {
            ChatMessage prevMessage = mMessageList.get(position - 1);
            if (!mMessageFormatter.hasSameDate(chatMessage.getDateCreated(), prevMessage.getDateCreated())) {
                isNewDay = true;
            }
        }

        switch (holder.getItemViewType()) {
            case USER_MESSAGE_TYPE:
                ((UserMessageHolder) holder).bind(chatMessage, mMessageFormatter, isNewDay);
                break;
            case RECIPIENT_MESSAGE_TYPE:
                ((RecipientMessageHolder) holder).bind(chatMessage, mMessageFormatter, isNewDay);
                break;
        }
    }

    @Override
    public int getItemCount() {
        return mMessageList.size();
    }

    public void setMessageList(List<ChatMessage> messageList) {
        mMessageList.clear();
        mMessageList.addAll(messageList);
        notifyDataSetChanged();
    }

    public void updateMessageList(List<ChatMessage> newList) {
        DiffUtil.DiffResult result = DiffUtil.calculateDiff(new MessageDiffCallback(mMessageList, newList));
        mMessageList = newList;
        result.dispatchUpdatesTo(this);
    }

    private LayoutInflater getLayoutInflater(Context context) {
        return LayoutInflater.from(context);
    }

    public class UserMessageHolder extends RecyclerView.ViewHolder {

        private ConstraintLayout messageTitle;
        private AppCompatTextView dividerDate;
        private AppCompatTextView messageText;
        private AppCompatTextView messageTime;
        private AppCompatImageView messageStatusSent;
        private AppCompatImageView messageStatusRead;

        UserMessageHolder(View itemView) {
            super(itemView);
            messageTitle = itemView.findViewById(R.id.message_title);
            dividerDate = itemView.findViewById(R.id.divider_date);
            messageText = itemView.findViewById(R.id.message_text);
            messageTime = itemView.findViewById(R.id.message_time);
            messageStatusSent = itemView.findViewById(R.id.message_status_sent);
            messageStatusRead = itemView.findViewById(R.id.message_status_read);
        }

        public void bind(ChatMessage chatMessage, UtcDateFormatter.ChatMessageFormatter dateFormatter, boolean isNewDay) {
            messageTitle.setVisibility(isNewDay ? View.VISIBLE : View.GONE);
            dividerDate.setText(dateFormatter.formatDividerDate(chatMessage.getDateCreated()));

            messageText.setText(chatMessage.getMessage());
            messageTime.setText(dateFormatter.formatDate(chatMessage.getDateCreated()));
            messageStatusSent.setVisibility(chatMessage.getIsRead() ? View.GONE : View.VISIBLE);
            messageStatusRead.setVisibility(chatMessage.getIsRead() ? View.VISIBLE : View.GONE);
        }
    }

    public class RecipientMessageHolder extends RecyclerView.ViewHolder {

        private ConstraintLayout messageTitle;
        private AppCompatTextView dividerDate;
        private AppCompatTextView messageText;
        private AppCompatTextView messageTime;
        private AppCompatImageView messageStatusSent;
        private AppCompatImageView messageStatusRead;

        RecipientMessageHolder(View itemView) {
            super(itemView);
            messageTitle = itemView.findViewById(R.id.message_title);
            dividerDate = itemView.findViewById(R.id.divider_date);
            messageText = itemView.findViewById(R.id.message_text);
            messageTime = itemView.findViewById(R.id.message_time);
            messageStatusSent = itemView.findViewById(R.id.message_status_sent);
            messageStatusRead = itemView.findViewById(R.id.message_status_read);
        }

        public void bind(ChatMessage chatMessage, UtcDateFormatter.ChatMessageFormatter dateFormatter, boolean isNewDay) {
            messageTitle.setVisibility(isNewDay ? View.VISIBLE : View.GONE);
            dividerDate.setText(dateFormatter.formatDividerDate(chatMessage.getDateCreated()));

            messageText.setText(chatMessage.getMessage());
            messageTime.setText(dateFormatter.formatDate(chatMessage.getDateCreated()));
            messageStatusSent.setVisibility(chatMessage.getIsRead() ? View.GONE : View.VISIBLE);
            messageStatusRead.setVisibility(chatMessage.getIsRead() ? View.VISIBLE : View.GONE);
        }
    }
}

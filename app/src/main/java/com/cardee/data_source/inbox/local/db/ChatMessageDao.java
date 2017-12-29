package com.cardee.data_source.inbox.local.db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.cardee.data_source.inbox.local.chat.entity.ChatMessage;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;

@Dao
public interface ChatMessageDao {

    @Query("SELECT * FROM chat_message WHERE chat_owner_id IS :chatId ORDER BY date ASC")
    Flowable<List<ChatMessage>> getMessages(String chatId);

    @Insert
    void addNewMessage(ChatMessage chatMessage);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void persistMessages(List<ChatMessage> messageList);
}

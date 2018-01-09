package com.cardee.data_source.inbox.local.db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.cardee.data_source.inbox.local.chat.entity.Chat;

import org.reactivestreams.Publisher;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Single;

@Dao
public interface ChatDao {

    @Query("SELECT * FROM chats WHERE attachment IS :attachment ORDER BY last_message_time DESC")
    Flowable<List<Chat>> getChats(String attachment);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addChat(Chat chat);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addChats(List<Chat> chats);

    @Query("UPDATE chats " +
            "SET last_message = :messageText, last_message_time = :messageTime, unread_count = :unreadMessageCount " +
            "WHERE chat_id IS :chatId AND attachment IS :attachment")
    long updateChatPresentation(String messageText, String messageTime, int unreadMessageCount, int chatId, String attachment);

    @Query("UPDATE chats SET unread_count = 0 WHERE chat_id IS :chatId")
    void updateChatUnreadCount(int chatId);

    @Query("UPDATE chats SET last_message = :messageText, last_message_time = :messageTime WHERE chat_id IS :chatId AND attachment IS :attachment")
    void updateChatLastMessage(String messageText, String messageTime, int chatId, String attachment);

    @Query("SELECT * FROM chats WHERE chat_id IS :chatId AND attachment IS :attachment")
    Single<Chat> getChat(int chatId, String attachment);


    @Query("SELECT * FROM chats WHERE chat_id IS :chatId")
    Single<Chat> getChatInfo(int chatId);

}

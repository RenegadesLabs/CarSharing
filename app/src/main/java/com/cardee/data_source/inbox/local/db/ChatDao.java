package com.cardee.data_source.inbox.local.db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.cardee.data_source.inbox.local.chat.entity.Chat;

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

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateChats(List<Chat> chats);

    @Query("SELECT * FROM chats WHERE chat_id IS :chatId AND attachment IS :attachment")
    Single<Chat> getChat(Integer chatId, String attachment);


    @Query("SELECT * FROM chats WHERE chat_id IS :serverId AND _id IS :databaseId ")
    Single<Chat> getChatInfo(Integer databaseId, Integer serverId);

}

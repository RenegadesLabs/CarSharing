package com.cardee.data_source.inbox.local.db;


import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.cardee.data_source.inbox.local.entity.ChatMessage;

import java.util.List;

@Dao
public interface MessageDao {

    @Insert()
    void persistMessage(ChatMessage chatMessage);

    @Query("SELECT * FROM chat_message WHERE chat_owner_id IS :chatId")
    List<ChatMessage> getAllMessages(String chatId);

}

package com.cardee.data_source.inbox.local.db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.cardee.data_source.inbox.local.entity.Chat;
import com.cardee.domain.inbox.usecase.entity.InboxChat;

import java.util.List;

import io.reactivex.Maybe;
import io.reactivex.Observable;

@Dao
public interface ChatDao {

    @Query("SELECT * FROM chats WHERE attachment IS :attachment")
    Maybe<List<Chat>> getChats(String attachment);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addChat(Chat chat);

    @Update
    void updateChat(Chat chat);
}

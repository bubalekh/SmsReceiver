package com.example.smsparcerwip.data;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface MessageDao {

    @Query("SELECT * FROM message")
    List<Message> getAllMessages();

    @Query("SELECT * FROM message WHERE local_id = :messageId")
    Message getMessageById(long messageId);

    @Insert
    void addReceivedMessage(Message...messages);

    @Query("DELETE FROM message WHERE local_id = :messageId")
    void deleteMessageById(long messageId);

    @Delete
    void deleteMessage(Message message);

    @Query("UPDATE message SET remote_id = :remoteId WHERE local_id = :localId")
    void updateMessageRemoteId(long localId, long remoteId);

    @Query("UPDATE message SET receipt_uri = :uri WHERE local_id = :localId")
    void updateMessageReceiptUri(long localId, String uri);

    @Update
    void updateMessage(Message message);
}

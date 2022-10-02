package com.example.smsparcerwip.data;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Message.class}, version = 1, exportSchema = false)
public abstract class MessageDatabase extends RoomDatabase {
    public abstract MessageDao messageDao();
}

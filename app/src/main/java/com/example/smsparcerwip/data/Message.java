package com.example.smsparcerwip.data;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity
public class Message {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "local_id")
    public long localId;

    @ColumnInfo(name = "remote_id", defaultValue = "0")
    public long remoteId;

    @ColumnInfo(name = "sender")
    public String sender;

    @ColumnInfo(name = "body")
    public String body;

    @ColumnInfo(name = "receipt_uri", defaultValue = "NULL")
    public String receiptUri;

    public Message(String sender, String body) {
        this.sender = sender;
        this.body = body;
    }

    @Ignore
    public long getLocalId() {
        return localId;
    }
    @Ignore
    public void setLocalId(long localId) {
        this.localId = localId;
    }
    @Ignore
    public long getRemoteId() {
        return remoteId;
    }
    @Ignore
    public void setRemoteId(long remoteId) {
        this.remoteId = remoteId;
    }
    @Ignore
    public String getSender() {
        return sender;
    }
    @Ignore
    public void setSender(String sender) {
        this.sender = sender;
    }
    @Ignore
    public String getBody() {
        return body;
    }
    @Ignore
    public void setBody(String body) {
        this.body = body;
    }
    @Ignore
    public String getReceiptUri() {
        return receiptUri;
    }
    @Ignore
    public void setReceiptUri(String receiptUri) {
        this.receiptUri = receiptUri;
    }
}

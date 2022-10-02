package com.example.smsparcerwip;

import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import androidx.room.Room;

import com.example.smsparcerwip.data.Message;
import com.example.smsparcerwip.data.MessageDao;
import com.example.smsparcerwip.data.MessageDatabase;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MySmsService extends Service {

    public static final MediaType JSON = MediaType.get("application/json; charset=utf-8");
    private final String TAG = "ServiceTag";
    //TODO: Add a proper comparing mechanism with Room!
    private final String allowedSenders = "Unibank";
    private final String url = "http://localhost:8080";
    private final OkHttpClient httpClient = new OkHttpClient()
            .newBuilder()
            .writeTimeout(1000, TimeUnit.MILLISECONDS)
            .build();
    private int serviceId = 0;

    private MessageDatabase database;
    private MessageDao messageDao;

    public MySmsService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        database = Room.databaseBuilder(getApplicationContext(), MessageDatabase.class, "message-database").build();
        messageDao = database.messageDao();
        //Тут сделать все необходимое для работы с API
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        serviceId = startId;
        String msgSender = intent.getExtras().getString("sender");
        if (allowedSenders.contains(msgSender)) {
            String msgBody = intent.getExtras().getString("body");
            Log.d(TAG, msgBody);
            new ApiQueryTask().execute(new ApiQuery(url, msgSender, msgBody));
        } else stopSelf();
        return START_NOT_STICKY;
    }

    private String post(String url, String json) throws IOException {
        RequestBody body = RequestBody.create(json, JSON);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        try (Response response = httpClient.newCall(request).execute()) {
            return Objects.requireNonNull(response.body()).string();
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    class ApiQueryTask extends AsyncTask<ApiQuery, Void, String> {
        String response = "";
        @Override
        protected String doInBackground(ApiQuery... apiQueries) {
            Log.d(TAG, "Async task started");
            //TODO: Сделать один большой json объект вместо кучи маленьких
            for (ApiQuery apiQuery: apiQueries) {
                String requestBody = "{\n" +
                        "   \"sender\": " + apiQuery.getSender() + ",\n" +
                        "   \"body\": " + apiQuery.getBody() + "\n" +
                        "}";
                try {
                    messageDao.addReceivedMessage(new Message(apiQuery.getSender(), apiQuery.getBody()));
                    response = post(apiQuery.getUrl(), requestBody);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return response;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.d(TAG, response);
            stopSelf();
        }
    }

    class ApiQuery {
        private final String url;
        private final String sender;
        private final String body;

        public ApiQuery(String url, String sender, String body) {
            this.url = url;
            this.sender = sender;
            this.body = body;
        }

        public String getUrl() {
            return url;
        }

        public String getSender() {
            return sender;
        }

        public String getBody() {
            return body;
        }
    }
}
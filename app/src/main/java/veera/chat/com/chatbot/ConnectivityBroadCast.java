package veera.chat.com.chatbot;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.media.RingtoneManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.support.v7.app.NotificationCompat;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ConnectivityBroadCast extends BroadcastReceiver {
    private Context context;

    public ConnectivityBroadCast() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        this.context = context;
        try {

            // If it is visible then trigger the task else do nothing
            ConnectivityManager connectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connectivityManager
                    .getActiveNetworkInfo();

            // Check internet connection and accrding to state change the
            // text of activity by calling method
            if (networkInfo != null && networkInfo.isConnected()) {
                Cursor cursor = context.getContentResolver().query(ChatContract.ChatMessageEntry.CHAT_MESSAGE_URI
                        , null, "message_status=?", new String[]{"0"}, null);
                ApiService apiService = ServiceHelper.createService(ApiService.class, ServiceHelper.getRetrofitInstance());
                if (cursor != null) {
                    while (cursor.moveToNext()) {
                        Map<String, String> params = new HashMap<>();
                        params.put("apiKey", "6nt5d1nJHkqbkphe");
                        params.put("chatBotID", "63906");
                        params.put("externalID", "chirag1");
                        params.put("message", cursor.getString(1));
                        Call<ChatResponse> sendMessageCall = apiService.sendChatMessage(params);
                        sendMessageCall.enqueue(new ResponseListener(cursor.getInt(0)));
                    }
                    cursor.close();
                }

            } else {
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void insertRespone(String message, int id) {
        if (context != null) {
            Cursor cursor = context.getContentResolver().query(ChatContract.ChatMessageEntry.CHAT_MESSAGE_URI
                    , null, "_id=?", new String[]{String.valueOf(id)}, null);
            if (cursor != null) {
                cursor.moveToFirst();
                ContentValues values = new ContentValues();
                values.put(ChatContract.ChatMessageEntry.COLUMN_MESSAGE_STATUS, 1);
                context.getContentResolver().update(ChatContract.ChatMessageEntry.CHAT_MESSAGE_URI,
                        values, "_id=?", new String[]{String.valueOf(id)});
                cursor.close();
            }
            ContentValues values = new ContentValues();
            values.put(ChatContract.ChatMessageEntry.COLUMN_MESSAGE, message);
            values.put(ChatContract.ChatMessageEntry.COLUMN_MESSAGE_TYPE, 1);
            context.getContentResolver().insert(ChatContract.ChatMessageEntry.CHAT_MESSAGE_URI, values);

            Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context);
            notificationBuilder.setSmallIcon(R.mipmap.ic_launcher_round)
                    .setContentTitle("New Message")
                    .setContentText(message)
                    .setAutoCancel(true)
                    .setSound(defaultSoundUri)
                    .setDefaults(Notification.DEFAULT_ALL);
            Intent intent = new Intent();
            Intent backIntent = new Intent(context, MainActivity.class);
            backIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            PendingIntent pendingIntent = PendingIntent.getActivities(context, 0, new Intent[]{backIntent, intent},
                    PendingIntent.FLAG_ONE_SHOT);
            notificationBuilder.setContentIntent(pendingIntent);
            NotificationManager notificationManager =
                    (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

            notificationManager.notify((int) System.currentTimeMillis(), notificationBuilder.build());
        }
    }

    private class ResponseListener implements Callback<ChatResponse> {
        private int id;

        public ResponseListener(int id) {
            this.id = id;
        }

        @Override
        public void onResponse(Call<ChatResponse> call, Response<ChatResponse> response) {
            if (response.isSuccessful() && response.body() != null) {
                insertRespone(response.body().getMessage().getMessage(), id);
            }
        }

        @Override
        public void onFailure(Call<ChatResponse> call, Throwable t) {

        }
    }
}
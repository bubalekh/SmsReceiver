package com.example.smsparcerwip;

import static androidx.fragment.app.FragmentManager.TAG;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

public class MySmsReceiver extends BroadcastReceiver {

    private static final String TAG = "MySmsReceiver";
    public static final String pdu_type = "pdus";

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        // Get the SMS message.
        Bundle bundle = intent.getExtras();
        SmsMessage[] msgs;
        String format = bundle.getString("format");
        // Retrieve the SMS message received.
        Object[] pdus = (Object[]) bundle.get(pdu_type);
        if (pdus != null) {
            // Check the Android version.
            boolean isVersionM = Build.VERSION.SDK_INT >= Build.VERSION_CODES.M;
            // Fill the msgs array.
            msgs = new SmsMessage[pdus.length];
            for (int i = 0; i < msgs.length; i++) {
                // Check Android version and use appropriate createFromPdu.
                if (isVersionM) {
                    // If Android version M or newer:
                    msgs[i] = SmsMessage.createFromPdu((byte[]) pdus[i], format);
                } else {
                    // If Android version L or older:
                    msgs[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
                }
                // Build the message to show.
                // Log and display the SMS message.
                Log.d(TAG, "onReceive");

                Intent smsIntent = new Intent(context, MySmsService.class);
                smsIntent.putExtra("sender", msgs[i].getOriginatingAddress());
                smsIntent.putExtra("body", msgs[i].getMessageBody());
                smsIntent.setType("text/plain");

                context.startService(smsIntent);
            }
        }
    }
}
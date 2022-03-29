package com.example.schoolscheduler;


import android.app.Notification;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Vibrator;


public class notification_broadcast extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Vibrator vibrator = (Vibrator)context.getSystemService(Context.VIBRATOR_SERVICE);
        long ms = 2000;
        vibrator.vibrate(2000);

        //Notification noti = new Notification.Builder(context);
    }
}
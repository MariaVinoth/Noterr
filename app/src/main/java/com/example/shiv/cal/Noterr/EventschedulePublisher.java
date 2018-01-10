package com.example.shiv.cal.Noterr;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by PRADEESH S on 2017-11-29.
 */

//Class to publish the notification
public class EventschedulePublisher extends BroadcastReceiver {
    public static String ID = "ID";
    public static String NOTIFICATION = "Event Notification";
    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);    //Notification builder
        Notification notification = intent.getParcelableExtra(NOTIFICATION);
        int id = intent.getIntExtra(ID,0);
        notificationManager.notify(id,notification);


    }
}

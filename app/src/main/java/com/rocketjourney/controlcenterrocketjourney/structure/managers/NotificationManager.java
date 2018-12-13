package com.rocketjourney.controlcenterrocketjourney.structure.managers;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.os.Build;
import android.support.v4.app.NotificationCompat;

import com.rocketjourney.controlcenterrocketjourney.R;
import com.rocketjourney.controlcenterrocketjourney.structure.RJControlCenter;
import com.rocketjourney.controlcenterrocketjourney.structure.network.utils.Utils;

/**
 * Created by joseivanpenuelas on 6/27/18.
 */

public class NotificationManager {

    private static final String channel_id = "Control_Center_RocketJourney_Channel_1";

    public static final String rjControlCenter = RJControlCenter.context.getString(R.string.app_name);

    private static final int icon = R.drawable.ic_notif_white;
    private static final Bitmap largeIcon = BitmapFactory.decodeResource(RJControlCenter.context.getResources(), R.drawable.ic_notif);

    /**
     * Type of notifications
     */

    public static String NEW_CHECKIN_ON_SPOT_FOR_CC = "NEW_CHECKIN_ON_SPOT_FOR_CC";

    public static void sendNotification(String title, String body, Intent intent) {

        int mNotificationId = Utils.Companion.getIntegerFromPrefs(RJControlCenter.context, Utils.PUSH_NOTIFICATIONS_COUNT, 0);

        PendingIntent resultPendingIntent = PendingIntent.getActivity(RJControlCenter.context, mNotificationId, intent, PendingIntent.FLAG_CANCEL_CURRENT);

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(RJControlCenter.context);

        Notification notification = mBuilder
                .setSmallIcon(icon)
                .setWhen(0)
                .setAutoCancel(true)
                .setContentTitle(title)
                .setContentIntent(resultPendingIntent)
                .setLargeIcon(largeIcon)
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                .setContentText(body)
                .setPriority(Notification.PRIORITY_MAX)
                .setStyle(new NotificationCompat.BigTextStyle())
                .setChannelId(channel_id)
                .build();

        android.app.NotificationManager notificationManager = (android.app.NotificationManager) RJControlCenter.context.getSystemService(Context.NOTIFICATION_SERVICE);

        //compatibility with Android Oreo if not, the notify will not been shown
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            NotificationChannel mChannel = new NotificationChannel(channel_id, rjControlCenter, android.app.NotificationManager.IMPORTANCE_HIGH);
            mChannel.enableVibration(true);
            notificationManager.createNotificationChannel(mChannel);
        }

        Utils.Companion.saveIntegerToPrefs(RJControlCenter.context, Utils.PUSH_NOTIFICATIONS_COUNT, ++mNotificationId);

        notificationManager.notify(mNotificationId, notification);
    }
}

package com.collegeproject.personx;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.widget.RemoteViews;

import androidx.core.app.NotificationCompat;

public class AlarmNotification extends BroadcastReceiver {
  @Override
  public void onReceive(Context context, Intent intent) {
    Bundle bundle = intent.getExtras();
    String text = bundle.getString("event");
    String date = bundle.getString("category");
    
    //Click on Notification
    Intent intent1 = new Intent(context, MainActivity.class);
    intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
    
    //Notification Builder
    PendingIntent pendingIntent = PendingIntent.getActivity(context, 1, intent1, PendingIntent.FLAG_ONE_SHOT | PendingIntent.FLAG_IMMUTABLE);
    NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
    NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context, "notify_001");
    
    RemoteViews contentView = new RemoteViews(context.getPackageName(), R.layout.notification_layout);
    PendingIntent pendingSwitchIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_ONE_SHOT | PendingIntent.FLAG_IMMUTABLE);
    contentView.setOnClickPendingIntent(R.id.flashButton, pendingSwitchIntent);
    contentView.setTextViewText(R.id.message, text);
    contentView.setTextViewText(R.id.date, date);
    mBuilder.setSmallIcon(R.drawable.personx);
    mBuilder.setColorized(true);
    mBuilder.setColor(Color.WHITE);
    mBuilder.setAutoCancel(true);
    mBuilder.setOngoing(true);
    mBuilder.setPriority(Notification.PRIORITY_HIGH);
    mBuilder.setOnlyAlertOnce(true);
    mBuilder.build().flags = Notification.FLAG_NO_CLEAR | Notification.PRIORITY_HIGH;
    mBuilder.setContent(contentView);
    mBuilder.setContentIntent(pendingIntent);
    
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
      String channelId = "channel_id";
      NotificationChannel channel = new NotificationChannel(channelId, "channel name", NotificationManager.IMPORTANCE_HIGH);
      channel.enableVibration(true);
      notificationManager.createNotificationChannel(channel);
      mBuilder.setChannelId(channelId);
    }
    Notification notification = mBuilder.build();
    notificationManager.notify(1, notification);
  }
}

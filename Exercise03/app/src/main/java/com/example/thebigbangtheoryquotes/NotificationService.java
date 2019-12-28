package com.example.thebigbangtheoryquotes;

import android.app.AlarmManager;
import android.app.IntentService;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

public class NotificationService extends IntentService {

    private static final String ACTION_NOTIFICATION = "com.example.thebigbangtheoryquotes.action.NOTIFICATION";
    private static final long INTERVAL_FIVE_MINUTES = (AlarmManager.INTERVAL_FIFTEEN_MINUTES / 3);

    public NotificationService() {
        super("NotificationService");
    }

    public static void startActionNotification(Context context) {
        Intent intent = new Intent(context, NotificationService.class);
        intent.setAction(ACTION_NOTIFICATION);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_NOTIFICATION.equals(action)) {
                handleActionNotification();
            } else {
                throw new RuntimeException("Unknown action provided");
            }
        }
    }

    private void handleActionNotification() {
        Intent intent = new Intent(getApplicationContext(), NotificationReceiver.class);
        intent.addFlags(Intent.FLAG_RECEIVER_FOREGROUND);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), NotificationReceiver.REQUEST_CODE,
                intent, PendingIntent.FLAG_UPDATE_CURRENT);
        long firstMillis = System.currentTimeMillis();
        AlarmManager alarmManager = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, firstMillis, INTERVAL_FIVE_MINUTES, pendingIntent);
    }
}

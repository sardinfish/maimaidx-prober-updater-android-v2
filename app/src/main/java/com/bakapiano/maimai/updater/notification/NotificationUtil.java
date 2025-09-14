package com.bakapiano.maimai.updater.notification;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.IBinder;
import androidx.annotation.Nullable;
import android.util.Log;

import com.bakapiano.maimai.updater.R;
import com.bakapiano.maimai.updater.ui.MainActivity;

public class NotificationUtil extends Service {

    private volatile static NotificationUtil INSTANCE;
    private Context mContext;
    private static final String TAG = "notification";
    public static NotificationUtil getINSTANCE() {
        if (INSTANCE == null) {
            synchronized (NotificationUtil.class) {
                if (INSTANCE == null) {
                    INSTANCE = new NotificationUtil();
                }
            }
        }
        return INSTANCE;
    }

    public NotificationUtil() {
    }

    public NotificationUtil setContext(Context mContext) {
        Log.d(TAG, "setContext: "+mContext);
        this.mContext = mContext;
        return getINSTANCE();
    }

    public void startNotification() {
        Log.d(TAG, "startNotification: "+ mContext);
        Intent notificationIntent = new Intent(mContext, this.getClass());
        mContext.startService(notificationIntent);
    }
    public void stopNotification() {
        Intent notificationIntent = new Intent(mContext, this.getClass());
        mContext.stopService(notificationIntent);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        createNotificationChannel();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stopForeground(true);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_STICKY;
    }

    private void createNotificationChannel() {
        Notification.Builder builder = new Notification.Builder(getApplicationContext());
        Intent nfIntent = new Intent(this, MainActivity.class);
        nfIntent.setAction(Intent.ACTION_MAIN);
        nfIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        nfIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
        builder.setContentIntent(PendingIntent.getActivity(this, 0, nfIntent, PendingIntent.FLAG_IMMUTABLE))
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher))
                .setContentTitle("更新器后台运行通知")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentText("正在努力的帮你上传分数")
                .setWhen(System.currentTimeMillis());


    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


}

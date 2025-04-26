package com.mirea.mitrofanovms.serviceapp;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class PlayerService extends Service {
    private MediaPlayer mediaPlayer;
    public static final String CHANNEL_ID = "ForegroundServiceChannel";

    @Override
    public void onCreate() {
        super.onCreate();
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("Музыкальный проигрыватель")
                .setContentText("Играет трек: Pharaoh - Бензобак")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setStyle(new NotificationCompat.BigTextStyle().bigText("Новый трек Pharaoh"))
                .setAutoCancel(true);

        NotificationChannel channel = new NotificationChannel(
                CHANNEL_ID, "MIREA Music Notification", NotificationManager.IMPORTANCE_HIGH);
        channel.setDescription("Канал уведомлений музыкального сервиса");

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        notificationManager.createNotificationChannel(channel);

        startForeground(1, builder.build());

        mediaPlayer = MediaPlayer.create(this, R.raw.benzobak);
        mediaPlayer.setLooping(false);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        mediaPlayer.start();
        mediaPlayer.setOnCompletionListener(mp -> stopForeground(true));
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        stopForeground(true);
        mediaPlayer.stop();
        mediaPlayer.release();
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
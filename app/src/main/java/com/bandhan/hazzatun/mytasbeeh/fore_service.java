//package com.bandhan.hazzatun.mytasbeeh;
//
//
//import android.app.Notification;
//import android.app.NotificationChannel;
//import android.app.NotificationManager;
//import android.app.PendingIntent;
//import android.app.Service;
//import android.content.Context;
//import android.content.Intent;
//import android.graphics.Color;
//import android.os.Build;
//import android.os.IBinder;
//import android.util.Log;
//
//import androidx.annotation.Nullable;
//import androidx.annotation.RequiresApi;
//import androidx.core.app.NotificationCompat;
//
//public class fore_service extends Service {
//        @Override
//        public void onCreate() {
//            super.onCreate();
//        }
//
//        @Override
//        public int onStartCommand(Intent intent, int flags, int startId) {
//            String input = intent.getStringExtra("inputExtra");
//            Intent notificationIntent = new Intent(this, MainActivity.class);
//            PendingIntent pendingIntent = PendingIntent.getActivity(this,
//                    0, notificationIntent, 0);
//          //  Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
//                    .setContentTitle("Example Service")
//                    .setContentText(input)
//                    .setSmallIcon(R.drawable.ic_notification)
//                    .setContentIntent(pendingIntent)
//                    .build();
//            startForeground(1, notification);
//            //do heavy work on a background thread
//            //stopSelf();
//            return START_NOT_STICKY;
//        }
//
//        @Override
//        public void onDestroy() {
//            super.onDestroy();
//        }
//
//        @Nullable
//        @Override
//        public IBinder onBind(Intent intent) {
//            return null;
//        }
//    }
//}
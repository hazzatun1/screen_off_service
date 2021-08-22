package com.bandhan.hazzatun.mytasbeeh;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;

import com.google.firebase.database.FirebaseDatabase;

public class App extends Application {
    public static final String CHANNEL_ID = "exampleServiceChannel";
    @Override
    public void onCreate() {
        super.onCreate();
      //  createNotificationChannel();
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
    }

}

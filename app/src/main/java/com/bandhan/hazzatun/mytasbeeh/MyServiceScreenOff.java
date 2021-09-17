package com.bandhan.hazzatun.mytasbeeh;


import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class MyServiceScreenOff extends Service {
    public MyServiceScreenOff() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent != null && intent.getExtras() != null) {
//            Intent dialogIntent = new Intent(this, MainActivity.class);
//            dialogIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            startActivity(dialogIntent);


        }


        return START_STICKY;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();

    }





}
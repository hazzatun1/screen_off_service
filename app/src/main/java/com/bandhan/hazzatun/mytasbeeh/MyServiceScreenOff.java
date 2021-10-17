package com.bandhan.hazzatun.mytasbeeh;


import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;
import static android.view.KeyEvent.KEYCODE_HEADSETHOOK;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.media.ToneGenerator;
import android.os.IBinder;
import android.view.KeyEvent;
import android.widget.Toast;

import androidx.core.content.ContextCompat;

public class MyServiceScreenOff extends Service {
    public MyServiceScreenOff() {
    }
    @Override
    public void onCreate() {
        super.onCreate();


    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        MediaButtonIntentReceiver me= new MediaButtonIntentReceiver();
        MainActivity m= new MainActivity();
        //m.onKeyDown(KEYCODE_HEADSETHOOK, me.event);
        me.onReceive(this, intent);



        return START_STICKY;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }


}

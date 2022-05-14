package com.bandhan.hazzatun.mytasbeeh;


import android.view.KeyEvent;

import static android.view.KeyEvent.ACTION_DOWN;
import static android.view.KeyEvent.ACTION_UP;
import static android.view.KeyEvent.KEYCODE_HEADSETHOOK;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;


import androidx.annotation.Nullable;


public class MyServiceScreenOff extends Service {
//public KeyEvent event;
    int keyCode;
    public int k=0;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //event= (KeyEvent)intent.getParcelableExtra(Intent.EXTRA_KEY_EVENT);
       // String value = intent.getStringExtra("mcounter");
        //int action = event.getAction();
            if (keyCode== KEYCODE_HEADSETHOOK) {

                MainActivity.mcounter++;
            }
        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {

        return null;
    }

    @Override
    public void onDestroy() {


        super.onDestroy();
    }


}

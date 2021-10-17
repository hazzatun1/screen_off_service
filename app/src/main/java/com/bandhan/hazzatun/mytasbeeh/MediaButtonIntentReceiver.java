package com.bandhan.hazzatun.mytasbeeh;

import static android.view.KeyEvent.KEYCODE_HEADSETHOOK;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.view.KeyEvent;
import android.widget.Toast;

public class MediaButtonIntentReceiver extends BroadcastReceiver {
    public KeyEvent event;

    public MediaButtonIntentReceiver() {
        super();
    }

    @SuppressLint("UnsafeProtectedBroadcastReceiver")
    @Override
    public void onReceive(Context context, Intent intent) {

        int action = event.getAction();
        if (action == KeyEvent.ACTION_DOWN) {

            MainActivity m= new MainActivity();

            m.onKeyDown(KEYCODE_HEADSETHOOK, event);
            Toast.makeText(context, "BUTTON PRESSED!", Toast.LENGTH_SHORT).show();
        }
        abortBroadcast();
    }
}

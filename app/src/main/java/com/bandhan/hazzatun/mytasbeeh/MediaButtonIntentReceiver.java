//package com.bandhan.hazzatun.mytasbeeh;
//
//import static android.view.KeyEvent.ACTION_DOWN;
//import static android.view.KeyEvent.KEYCODE_HEADSETHOOK;
//
//import android.annotation.SuppressLint;
//import android.content.BroadcastReceiver;
//import android.content.Context;
//import android.content.Intent;
//import android.view.KeyEvent;
//import android.widget.Toast;
//
//public class MediaButtonIntentReceiver extends BroadcastReceiver {
//    public KeyEvent event;
//    int k=0;
//    public MediaButtonIntentReceiver() {
//        super();
//
//    }
//
//
////    public MediaButtonIntentReceiver(int k) {
////        super();
////        this.k = k;
////    }
//
//    public int getK() {
//        return k;
//    }
//
//    public void setK(int k) {
//        this.k = k;
//    }
//
//    @Override
//    public void onReceive(Context context, Intent intent) {
//
//        int action = event.getAction();
//        k=Integer.parseInt(intent.getStringExtra("mcounter"));
//        if (action == ACTION_DOWN) {
//
//        k++;
//
//            MainActivity.mcounter=k;
//            MainActivity.txv.setText(k);
//            this.setK(k);
//
//        }
//        abortBroadcast();
//    }
//
//
//
//
//
//}

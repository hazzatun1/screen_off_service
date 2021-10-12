package com.bandhan.hazzatun.mytasbeeh;

import android.app.Application;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class offline_firebase extends Application {


        @Override
        public void onCreate() {
            super.onCreate();
            FirebaseDatabase.getInstance().setPersistenceEnabled(true);
            DatabaseReference workoutsRef = FirebaseDatabase.getInstance().getReference("MyDigitalCounter");
            workoutsRef.keepSynced(true);
        }
    }


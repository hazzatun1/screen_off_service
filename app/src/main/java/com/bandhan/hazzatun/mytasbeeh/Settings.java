package com.bandhan.hazzatun.mytasbeeh;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.os.SystemClock;
import android.provider.AlarmClock;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Locale;

public class Settings extends AppCompatActivity {
    String alarm = "";
    Button set_lang;

    Button upload, alarms;
    Button download;
    String CID="", cname="", upDate="";
    Integer mcounter=0, mytargets=0;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_settings);


        FirebaseDatabase rootNode = FirebaseDatabase.getInstance();
        reference = rootNode.getReference("MyDigitalCounter");
        set_lang=findViewById(R.id.btn5);
        //  ActionBar actionBar= getSupportActionBar();
        //  actionBar.setTitle(getResources().getString(R.string.app_name));
        Button alarms=findViewById(R.id.btn1);

        download= findViewById(R.id.btn7);

        if (getIntent().hasExtra("cID") && getIntent().hasExtra("cName")
                && getIntent().hasExtra("counts") && getIntent().hasExtra("tcounts")
                && getIntent().hasExtra("up_date")) {

            CID = getIntent().getStringExtra("cID");
            cname = getIntent().getStringExtra("cName");

            mcounter = Integer.parseInt(getIntent().getStringExtra("counts"));

            mytargets = Integer.parseInt(getIntent().getStringExtra("tcounts"));

            upDate= getIntent().getStringExtra("up_date");

        }





    }
    public void setAlarm(View view) {
        //   Intent i = new Intent(getApplicationContext(), userlist.class);
        ///  i.putExtra("setAlarm", alarm);

        // startActivity(i);
        SetAlarm();
    }

    public void SetAlarm() {
         Intent i = new Intent(AlarmClock.ACTION_SET_ALARM);
        //  i.putExtra(AlarmClock.EXTRA_DAYS, 7);
        // i.putExtra(AlarmClock.EXTRA_HOUR, 00);
         i.putExtra(AlarmClock.EXTRA_MESSAGE, "This alarm for "+ cname);
          i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
          startActivity(i);
    }

    public void setLang(View view) {
        final String[] listItems = {"English", "বাংলা"};
        AlertDialog.Builder mbuilder = new AlertDialog.Builder(Settings.this);
        mbuilder.setTitle("Choose Language:");
        mbuilder.setSingleChoiceItems(listItems, -1, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (i == 0) {
                  //  setLocale("en");
                    Locale locale = new Locale("en", "US");
                    Resources res = getResources();
                    DisplayMetrics dm = res.getDisplayMetrics();
                    Configuration conf = res.getConfiguration();
                    conf.locale = locale;
                    res.updateConfiguration(conf, dm);
                    Intent in = new Intent(getApplicationContext(), MainActivity.class);
                    Intent in2 = new Intent(getApplicationContext(), userlist.class);
                    in.putExtra("lang_code","en");
                    in2.putExtra("lang_code","en");
                    startActivity(in);
                    recreate();
                }
                else if (i == 1) {
                    Locale locale = new Locale("bn", "bd");
                    Resources res = getResources();
                    DisplayMetrics dm = res.getDisplayMetrics();
                    Configuration conf = res.getConfiguration();
                    conf.locale = locale;
                    res.updateConfiguration(conf, dm);
                    Intent in = new Intent(getApplicationContext(), MainActivity.class);
                    Intent in2 = new Intent(getApplicationContext(), userlist.class);
                    in.putExtra("lang_code","bn");
                    in2.putExtra("lang_code","bn");
                    startActivity(in);
                    recreate();
                }
                dialogInterface.dismiss();

            }

        });
        AlertDialog mDialog = mbuilder.create();
        mDialog.show();

    }


    public void set_sound(View view, Context context) {

    }


    public void uploadOnNet(View view) {


    }
    public static void setLocale(Activity activity, String languageCode) {
        Locale locale = new Locale(languageCode);
        Locale.setDefault(locale);
        Resources resources = activity.getResources();
        Configuration config = resources.getConfiguration();
        config.setLocale(locale);
        resources.updateConfiguration(config, resources.getDisplayMetrics());

    }


}
package com.bandhan.hazzatun.mytasbeeh;

//settings_bk.setBackgroundResource(R.drawable.bk);
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.media.AudioManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.AlarmClock;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.ToggleButton;

import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.util.ArrayList;
import java.util.Locale;


public class Settings extends AppCompatActivity {
    Switch sound;
    Button set_lang, set_back;
    String CID = "", cname = "", upDate = "", spusername="";
    Integer mcounter = 0, mytargets = 0;
    DatabaseReference reference;
    View settings_bk; //bk=background
    Spinner spinner_1;
    SharedPreferences set_locale;
    Context context;
    Resources resources;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
//        SharedPreferences sharedPrefs = getSharedPreferences("sound_on", MODE_PRIVATE);
//        if (sharedPrefs.contains("sound_on")){
//            sound.setChecked(sharedPrefs.getBoolean("sound", sound.isChecked()));}

        set_locale = getSharedPreferences("set_lang", Context.MODE_PRIVATE);
        String hello = set_locale.getString("lang", "");
        if (hello != null && !hello.equals("")){
          if(hello.equals("bn")){
              LocaleHelper.setLocale( Settings.this, set_locale.getString("lang", "bn"));
        }
        else{
              LocaleHelper.setLocale( Settings.this, set_locale.getString("lang", "en"));
            }}



            FirebaseDatabase rootNode = FirebaseDatabase.getInstance();
        reference = rootNode.getReference("MyDigitalCounter");
        settings_bk = findViewById(R.id.set);
        set_lang = findViewById(R.id.btn5);
        sound = findViewById(R.id.btn2);
        spinner_1 = findViewById(R.id.btn3);


        sound.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // The toggle is enabled
                    sound.setSoundEffectsEnabled(false);
                    sound.setChecked(true);
                    SharedPreferences.Editor editor = getSharedPreferences("sound_on", MODE_PRIVATE).edit();
                    editor.putBoolean("sound", true);
                    editor.commit();


                } else {
//                    AudioManager amanager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
//                    amanager.setStreamMute(AudioManager.STREAM_NOTIFICATION, false);
//                    amanager.setStreamMute(AudioManager.STREAM_ALARM, false);
//                    amanager.setStreamMute(AudioManager.STREAM_MUSIC, false);
//                   amanager.setStreamMute(AudioManager.STREAM_SYSTEM, false);
                    sound.setSoundEffectsEnabled(false);
                    sound.setChecked(true);
                    SharedPreferences.Editor editor = getSharedPreferences("sound_on", MODE_PRIVATE).edit();
                    sound.setChecked(false);
                    editor.putBoolean("sound", false);
                    editor.commit();

                }
            }
        });


        if (getIntent().hasExtra("cID") && getIntent().hasExtra("cName")
                && getIntent().hasExtra("counts") && getIntent().hasExtra("tcounts")
                && getIntent().hasExtra("up_date")) {

            CID = getIntent().getStringExtra("cID");
            cname = getIntent().getStringExtra("cName");
            mcounter = Integer.parseInt(getIntent().getStringExtra("counts"));
            mytargets = Integer.parseInt(getIntent().getStringExtra("tcounts"));
            upDate = getIntent().getStringExtra("up_date");
        }
    }

    public void setAlarm(View view) {
        SetAlarm();
    }

    public void SetAlarm() {
        Intent i = new Intent(AlarmClock.ACTION_SET_ALARM);
        i.putExtra(AlarmClock.EXTRA_MESSAGE, "This alarm for " + cname);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(i);
    }

    public void setLang(View view) {
        final String[] listItems = {"English", "বাংলা"};
        AlertDialog.Builder mbuilder = new AlertDialog.Builder(Settings.this);
        mbuilder.setTitle("Choose Language:");
        mbuilder.setSingleChoiceItems(listItems, -1, new DialogInterface.OnClickListener() {
            final SharedPreferences.Editor edit=getSharedPreferences("set_lang", MODE_PRIVATE).edit();
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (i == 0) {
                    //  setLocale("en");
                    context = LocaleHelper.setLocale(Settings.this, "en");
                  //  resources = context.getResources();
                    edit.putString("lang","en").apply();
                    recreate();
                } else if (i == 1) {
                   // setLocale("bn");
                    context = LocaleHelper.setLocale(Settings.this, "bn");
                   // resources = context.getResources();
                    edit.putString("lang","bn").apply();
                    recreate();
                }
                dialogInterface.dismiss();
            }
        });
        AlertDialog mDialog = mbuilder.create();
        mDialog.show();
    }

//    public void setLocale(String lang) {
//        Locale myLocale = new Locale(lang);
//        Resources res = getResources();
//        DisplayMetrics dm = res.getDisplayMetrics();
//        Configuration conf = res.getConfiguration();
//        conf.locale = myLocale;
//        res.updateConfiguration(conf, dm);
//    }

    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferences sharedPrefs = getSharedPreferences("sound_on", MODE_PRIVATE);
        sound.setChecked(sharedPrefs.getBoolean("sound", sound.isChecked()));

    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    public void set_back (View view){

            SharedPreferences pref = PreferenceManager
                    .getDefaultSharedPreferences(this);
            if(view.getId()==R.id.Attack)
            {
                ArrayList<String> arrayList1 = new ArrayList<String>();

                arrayList1.add("bk");
                arrayList1.add("pic_1");
                arrayList1.add("pic_2");
                ArrayAdapter<String> adp = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, arrayList1);
                spinner_1.setAdapter(adp);
                spinner_1.setVisibility(View.VISIBLE);
                //Set listener Called when the item is selected in spinner
                spinner_1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    public void onItemSelected(AdapterView<?> parent, View view,
                                               int position, long arg3) {
                        if (position == 0)
                           // settings_bk.setBackgroundResource(R.drawable.bk);
                        setTheme(R.style.AppTheme);
                        else if (position == 1)
                            setTheme(R.style.bk1);
                        else if (position == 2)
                            setTheme(R.style.bk2);

                        Settings.this.recreate();
                    }

                    public void onNothingSelected(AdapterView<?> arg0) {

                    }
                });
    }
    }


    public void go_main(View view) {
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
        this.finish();
    }

}




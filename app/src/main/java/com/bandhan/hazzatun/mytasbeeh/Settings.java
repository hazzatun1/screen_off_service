package com.bandhan.hazzatun.mytasbeeh;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.media.AudioManager;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import java.util.Locale;

public class Settings extends AppCompatActivity {
    String alarm = "";
    Button set_lang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_settings);
        loadLocale();
        set_lang=findViewById(R.id.btn5);
      //  ActionBar actionBar= getSupportActionBar();
      //  actionBar.setTitle(getResources().getString(R.string.app_name));


    }
    public void setAlarm(View view) {
        Intent i = new Intent(getApplicationContext(), open_page.class);
        i.putExtra("setAlarm", alarm);

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
                    setLocale("en");
                    recreate();
                }
               else if (i == 1) {
                    setLocale("bn");
                    recreate();
                }
dialogInterface.dismiss();

            }


        });
        AlertDialog mDialog = mbuilder.create();
        mDialog.show();

    }

    private void setLocale(String lang){
        Locale locale = new Locale(lang);
        Locale.setDefault(locale);
Configuration config= new Configuration();
config.locale= locale;
getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
        SharedPreferences.Editor editor = getSharedPreferences("Settings", MODE_PRIVATE).edit();
        //SharedPreferences.Editor editor1 = getSharedPreferences("MainActivity", MODE_PRIVATE).edit();
        editor.putString("My_Lang", lang);
        editor.apply();
       // editor1.putString("My_Lang", lang);
       // editor1.apply();
    }

    private void loadLocale() {

        SharedPreferences prefs = getSharedPreferences("Settings", Activity.MODE_PRIVATE);
        String language= prefs.getString("My_Lang","");
        setLocale(language);

        SharedPreferences prefs1 = getSharedPreferences("MainActivity", Activity.MODE_PRIVATE);
        String language1= prefs1.getString("My_Lang","");
        setLocale(language);

    }

    public void set_sound(View view, Context context) {

    }


}
package com.bandhan.hazzatun.mytasbeeh;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class Settings extends AppCompatActivity {
String alarm="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);


    }


    public void setAlarm(View view) {
        Intent i = new Intent(getApplicationContext(), open_page.class);
        i.putExtra("setAlarm", alarm);

        startActivity(i);


    }
}
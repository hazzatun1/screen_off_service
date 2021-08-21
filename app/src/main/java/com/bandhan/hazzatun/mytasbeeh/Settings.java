package com.bandhan.hazzatun.mytasbeeh;

//settings_bk.setBackgroundResource(R.drawable.bk);

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.AlarmClock;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Switch;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.util.ArrayList;



public class Settings extends AppCompatActivity {
    Switch sound_btn;
    Button set_lang, set_back;
    String CID = "", cname = "", upDate = "", spusername="";
    Integer mcounter = 0, mytargets = 0;
    DatabaseReference reference;
    View settings_bk; //bk=background
    Spinner spinner_1;
    SharedPreferences set_locale, set_sound, set_backs;
    Context context;
    AudioManager audioManager;
    LinearLayout layout, layout1, layout2, layout3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);


        sound_btn = findViewById(R.id.btn2);
        set_sound = getSharedPreferences("set_sounds", Context.MODE_PRIVATE);
        if (sound_btn.isChecked()){
            set_sound.getBoolean("sound", false);
          //  sound.setChecked(true);
        }
        else{
            set_sound.getBoolean("sound", true);
          //  sound.setChecked(false);
        }

        layout=(LinearLayout)findViewById(R.id.set);

        set_backs=getSharedPreferences("set_back", Context.MODE_PRIVATE);
        String back = set_backs.getString("pic_name", "");
        if (back != null && !back.equals("")) {

            switch (back) {
                case "bk":
                    layout.setBackgroundResource(R.drawable.bk);
                    break;
                case "pic_1":
                    layout.setBackgroundResource(R.drawable.pic_1);
                    break;
                case "pic_2":
                    layout.setBackgroundResource(R.drawable.pic_2);
                    break;
                default:
                    break;
            }
        }

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

        spinner_1 = findViewById(R.id.btn3);


        sound_btn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                final SharedPreferences.Editor editor = getSharedPreferences("set_sounds", MODE_PRIVATE).edit();
              if (isChecked) {
                    // The toggle is enabled and sound will disabled
                  sound_btn.setSoundEffectsEnabled(false);
                    editor.putBoolean("sound", false).apply();

                }
              else {
                  //sound enable
                  sound_btn.setSoundEffectsEnabled(true);
                    editor.putBoolean("sound", true).apply();
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
                    context = LocaleHelper.setLocale(Settings.this, "en");
                    edit.putString("lang","en").apply();
                    recreate();
                } else if (i == 1) {
                    context = LocaleHelper.setLocale(Settings.this, "bn");
                    edit.putString("lang","bn").apply();
                    recreate();
                }
                dialogInterface.dismiss();
            }
        });
        AlertDialog mDialog = mbuilder.create();
        mDialog.show();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    public void set_background(View view){
        String[] items = new String[] {"bk", "pic_1", "pic_2"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, items);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_1.setAdapter(adapter);

        spinner_1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {
                final SharedPreferences.Editor edits=getSharedPreferences("set_back", MODE_PRIVATE).edit();
                switch(position) {
                    case 0:
                        layout.setBackgroundResource(R.drawable.bk);
                        edits.putString("pic_name","bk").apply();
                        break;
                    case 1:
                        layout.setBackgroundResource(R.drawable.pic_1);
                        edits.putString("pic_name","pic_1").apply();
                        break;
                    case 2:
                        layout.setBackgroundResource(R.drawable.pic_2);
                        edits.putString("pic_name","pic_2").apply();
                        break;
                    default:
                        break;
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });
    }


    public void go_main(View view) {
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
        this.finish();
    }

}




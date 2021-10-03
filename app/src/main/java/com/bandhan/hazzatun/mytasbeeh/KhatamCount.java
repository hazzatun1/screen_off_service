package com.bandhan.hazzatun.mytasbeeh;

import static android.content.ContentValues.TAG;
import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;
import static android.view.KeyEvent.KEYCODE_HEADSETHOOK;
import static java.lang.Integer.parseInt;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.ToneGenerator;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class KhatamCount extends AppCompatActivity {

    AudioManager audioManager;
    private Integer mcounter = 0, ttl=0;
    Button cnt;
    TextView txv;
    EditText txv_et;
    TextView name_input;
    EditText name_input_et;
    String value ="";
    boolean haveIBeenClicked;
    String CID = "";
    String cname = "";
    String names = "";
    String formattedDate = "";
    private int mytargets = 0;
    Button targett;
    ImageButton lt;
    DatabaseReference reference;
    String counting = "", t_count;
    String target = "";
    Button save, edit_btn;
    Button open;
    String email1 = "", providerId = "", name = "", userId = "";
    FirebaseUser user;
    private KhatamCount.MusicIntentReceiver myReceiver;
    SharedPreferences prefs1, set_locale, set_back;
    LinearLayout layout;
    KhatamUser ku;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        set_locale = getSharedPreferences("set_lang", Context.MODE_PRIVATE);
        String hello = set_locale.getString("lang", "");
        if (hello != null && !hello.equals("")) {
            if (hello.equals("bn")) {
                LocaleHelper.setLocale(KhatamCount.this, set_locale.getString("lang", "bn"));
            } else {
                LocaleHelper.setLocale(KhatamCount.this, set_locale.getString("lang", "en"));

            }
        }
        setContentView(R.layout.activity_khatam_count);
             ku=new KhatamUser();
        user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            for (UserInfo profile : user.getProviderData()) {
                providerId = profile.getProviderId();
                name = profile.getDisplayName();
                email1 = profile.getEmail();
                userId = user.getUid();
            }
        }

        layout = findViewById(R.id.main_back);
        set_back = getSharedPreferences("set_back", Context.MODE_PRIVATE);
        String back = set_back.getString("pic_name", "");
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


        FirebaseDatabase rootNode = FirebaseDatabase.getInstance();
        reference = rootNode.getReference("MyDigitalCounter");


        edit_btn = findViewById(R.id.edit_btn);
        txv_et= findViewById(R.id.uput);
        cnt = findViewById(R.id.count);
        txv = findViewById(R.id.txt);
        save = findViewById(R.id.save);
        targett = findViewById(R.id.target);
        name_input = findViewById(R.id.count_name);
        name_input_et = findViewById(R.id.count_name_et);
        lt = findViewById(R.id.light);



        open = findViewById(R.id.open);

        open.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(KhatamCount.this, khatam_list.class);
                i.putExtra("k_count", String.valueOf(mcounter));
                i.putExtra("tgt", String.valueOf(mytargets));
                i.putExtra("cname", name_input.getText().toString());
                startActivity(i);
            }
        });

        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        formattedDate = df.format(c);
        targett.setBackgroundColor(Color.GREEN);

        if (getIntent().hasExtra("k_name") && getIntent().hasExtra("tgt")
                && getIntent().hasExtra("myCount")) {
            cname = getIntent().getStringExtra("k_name");
            target=getIntent().getStringExtra("tgt");
            mytargets=Integer.parseInt(target);
            mcounter=Integer.parseInt(getIntent().getStringExtra("myCount"));
            name_input.setText(cname);
            name_input_et.setText(cname);
            targett.setText(getString(R.string.target_string)+target);
            txv.setText(String.valueOf(mcounter));
            txv_et.setText(String.valueOf(mcounter));
        }

    }





    public void play(View view) {


        if (mytargets == 0) {
            mcounter++;
            txv.setText(String.valueOf(mcounter));
        } else {
            mcounter++;
            txv.setText(String.valueOf(mcounter));

            if (mcounter >= mytargets) {
                cnt.setEnabled(false);
                targett.setBackgroundColor(ContextCompat.getColor(this, R.color.y));
                Toast.makeText(this, "target filled up", Toast.LENGTH_SHORT).show();
                ToneGenerator toneGen1 = new ToneGenerator(AudioManager.STREAM_MUSIC, 100);
                boolean b = toneGen1.startTone(ToneGenerator.TONE_CDMA_PIP, 150);
                ArrayList<User> list;
                String count = "0";
                txv.setText("0");
                txv_et.setText("0");
                mcounter=0;
                KhatamUser helperClass = new KhatamUser(email1, String.valueOf(mytargets), cname, formattedDate, count, "0");
                reference.child(userId).child(cname).setValue(helperClass);

                Intent i = new Intent(getApplicationContext(), userlist.class);
                // i.putExtra("name", "cname");
                // i.setFlags(FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);
            }
        }
    }


    public void edits(View view) {

        txv.setVisibility(txv.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);
        txv_et.setVisibility(txv_et.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);

        int mr = parseInt(txv_et.getText().toString());

        txv_et.setText((value = String.valueOf(mcounter)));
        txv.setText(String.valueOf(mcounter = mr));


        name_input.setVisibility(name_input.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);
        name_input_et.setVisibility(name_input_et.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);

        if (name_input_et.getVisibility() == View.VISIBLE) {
            names = name_input.getText().toString();
            name_input_et.setText(names);
        } else {
            names = name_input_et.getText().toString();
            name_input.setText(names);
        }

    }




    public void setMyCount() {
        cname = name_input.getText().toString();
        counting = String.valueOf(mcounter);

        DatabaseReference updateData = FirebaseDatabase.getInstance()
                .getReference("MyDigitalCounter")
                .child("Group").child(cname);
       // updateData.getRef().child("myCount").setValue(counting);

        updateData.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){

                    for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                        KhatamUser usa=snapshot.getValue(KhatamUser.class);
                        String email=usa.getK_acc_email();
                        if(email.equals(user.getEmail())) {
                            snapshot.getRef().child("myCount").setValue(counting);
                        }

                    }

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }


    public void saves(View view) {
        setMyCount();
        //addgroupCounts();
        Intent i = new Intent(this, khatam_list.class);
        i.putExtra("k_count", String.valueOf(mcounter));
        i.putExtra("tgt", String.valueOf(mytargets));
        i.putExtra("cname", name_input.getText().toString());
        startActivity(i);
        this.finish();
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(getApplicationContext(), khatam_list.class);
        // i.setFlags(FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        i.putExtra("k_count", String.valueOf(mcounter));
        i.putExtra("tgt", String.valueOf(mytargets));
        i.putExtra("cname", name_input.getText().toString());
        startActivity(i);
        this.finish();
    }

    public void go_main(View view) {
        Intent i = new Intent(this, khatam_list.class);
        i.putExtra("k_count", String.valueOf(mcounter));
        i.putExtra("tgt", String.valueOf(mytargets));
        i.putExtra("cname", name_input.getText().toString());
        startActivity(i);
        this.finish();
    }

    public void lt(View view) { //night-mode
        haveIBeenClicked = !haveIBeenClicked;
        if (haveIBeenClicked) {
            txv_et.setTextColor(getResources().getColor(R.color.y));
            txv.setTextColor(getResources().getColor(R.color.y));
            cnt.setTextColor(getResources().getColor(R.color.y));
            layout.setBackgroundResource(R.drawable.bl);
        } else {
            txv_et.setTextColor(getResources().getColor(R.color.b));
            txv.setTextColor(getResources().getColor(R.color.b));
            cnt.setTextColor(getResources().getColor(R.color.b));
            layout.setBackgroundResource(R.drawable.bk);

        }
    }

    @Override
    public void onResume() {
        super.onResume();
        IntentFilter filter = new IntentFilter(Intent.ACTION_HEADSET_PLUG);
        registerReceiver(myReceiver, filter);
    }

    public void calc_open(View view) {

        LinearLayout layout = new LinearLayout(KhatamCount.this);
        layout.setOrientation(LinearLayout.VERTICAL);

        final EditText value1 = new EditText(KhatamCount.this);
        value1.setHint("Value1");
        layout.addView(value1);
        String uvalue1=String.valueOf(mcounter);
        value1.setText(uvalue1);

        AlertDialog.Builder alert2 = new AlertDialog.Builder(KhatamCount.this);
        alert2.setTitle("Add Outside counts");
        final EditText value2 = new EditText(KhatamCount.this);
        value2.setHint("Value2");
        layout.addView(value2);
        alert2.setView(layout);
        alert2.setPositiveButton(R.string.add, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface param2DialogInterface, int param2Int) {
                int val2 = 0;
                if(!value2.getText().toString().isEmpty()) {
                    val2 = Integer.parseInt(value2.getText().toString());
                }
                mcounter=(mcounter+val2);
                txv_et.setText(String.valueOf(mcounter));
                txv.setText(String.valueOf(mcounter));

            }
        })
        .setNeutralButton(R.string.cancel, new DialogInterface.OnClickListener() {
         public void onClick(DialogInterface param2DialogInterface, int param2Int) {
         Toast.makeText(KhatamCount.this, "Cancel", Toast.LENGTH_LONG).show();
         param2DialogInterface.cancel();
                    }
                });

        alert2.create().show();
    }




    private class MusicIntentReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(Intent.ACTION_HEADSET_PLUG)) {
                int state = intent.getIntExtra("state", -1);
                switch (state) {
                    case 0:
                        cnt.setClickable(true);
                        targett.setClickable(true);
                        lt.setClickable(true);
                        open.setClickable(false);
                        edit_btn.setClickable(false);
                        //stopService(new Intent(MainActivity.this, MediaButtonIntentReceiver.class));
                        Log.d(TAG, "Headset is unplugged");
                        break;
                    case 1:
                        Log.d(TAG, "Headset is plugged");
                        break;
                    default:
                        Log.d(TAG, "Undetermined state");
                }
            }
        }

    }


    @Override  //headphone count
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KEYCODE_HEADSETHOOK) {
            cnt.setClickable(false);
            targett.setClickable(false);
            lt.setClickable(false);
            open.setClickable(false);
            edit_btn.setClickable(false);

            myReceiver = new KhatamCount.MusicIntentReceiver();
            audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
            audioManager.setMode(AudioManager.MODE_IN_COMMUNICATION);
            audioManager.setSpeakerphoneOn(true);
            //handle click
            if (mytargets == 0) {
                mcounter++;
                txv.setText(String.valueOf(mcounter));
            } else {
                mcounter++;
                txv.setText(String.valueOf(mcounter));
                if (mcounter >= mytargets) {
                    cnt.setEnabled(false);
                    targett.setBackgroundColor(ContextCompat.getColor(this, R.color.y));
                    Toast.makeText(this, "target filled up", Toast.LENGTH_SHORT).show();
                    ToneGenerator toneGen1 = new ToneGenerator(AudioManager.STREAM_MUSIC, 500);
                    boolean b = toneGen1.startTone(ToneGenerator.TONE_CDMA_PIP, 150);
                    String count = "0";
                    User helperClass = new User(CID, cname, count, formattedDate, String.valueOf(mytargets), email1);
                    reference.child(userId).child(cname).setValue(helperClass);
                    txv.setText("0");
                    Intent i = new Intent(getApplicationContext(), userlist.class);
                    i.setFlags(FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(i);

                }
            }

        }
        return super.onKeyDown(keyCode, event);
    }


    public void target_method (View view){

        AlertDialog.Builder alert2 = new AlertDialog.Builder(KhatamCount.this);
        final EditText editText3 = new EditText(KhatamCount.this);
        alert2.setTitle("Set Target Limit");
        alert2.setView(editText3);

        alert2.setPositiveButton(R.string.set, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface param2DialogInterface, int param2Int) {

                mytargets = parseInt(editText3.getText().toString());
                targett.setText(getString(R.string.target_string) + String.valueOf(mytargets));
                mcounter = 0;
                txv.setText(String.valueOf(mcounter));//will work by save button
            }
        })
        .setNegativeButton(R.string.remove, new DialogInterface.OnClickListener() {
        public void onClick(DialogInterface param2DialogInterface, int param2Int) {

        reference.child("Group").child(cname)
        .equalTo(name_input.getText().toString())
        .addValueEventListener(new ValueEventListener() {
         @Override
         public void onDataChange(DataSnapshot dataSnapshot) {

         if (dataSnapshot.hasChild(cname)) {
                                            // maxId=dataSnapshot.getChildrenCount();
        Toast.makeText(KhatamCount.this, "name already exists", Toast.LENGTH_SHORT).show();

        }
                                        //  reference.child(String.valueOf(maxId+1)).setvalue();
         else {
         if (mytargets != 0) {
             String mytarget = "0";
             User helperClass = new User(CID, cname, String.valueOf(mcounter), formattedDate, mytarget, email1);

             reference.child(userId).child(cname).setValue(helperClass);
             mytargets = 0;
             targett.setText(getString(R.string.target_string)+mytarget);
             Toast.makeText(KhatamCount.this, "success to update", Toast.LENGTH_SHORT).show();

             }
         }
         }

          @Override
          public void onCancelled(DatabaseError databaseError) {
          Toast.makeText(KhatamCount.this, "cancel", Toast.LENGTH_SHORT).show();
           }
            });

            }

            })
                .setNeutralButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface param2DialogInterface, int param2Int) {

                        Toast.makeText(KhatamCount.this, "Cancel", Toast.LENGTH_LONG).show();
                        param2DialogInterface.cancel();

                    }

                });

        alert2.create().show();

    }

    public void resets () {
        mcounter = 0;
        mytargets = 0;
        txv.setText(String.valueOf(mcounter));
        // String st_count_name=getResources().getString(R.string.reset);
        name_input.setText(R.string.default_title);
        if (!CID.equals("")) {
            CID = "";
        }
        targett.setText(getString(R.string.target_string) + mytargets);

    }


    }

package com.bandhan.hazzatun.mytasbeeh;

import static android.content.ContentValues.TAG;

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

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Query;
import com.google.firebase.database.Transaction;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;



public class MainActivity extends AppCompatActivity {
    //  private static final String FILE_NAME = "exampleTasbeeh.txt";
    Spinner mLanguage;
    ArrayAdapter<String> mAdapter;

    AudioManager audioManager;
    private int mcounter = 0;
    private SharedPreferences prefs;
    Button cnt;
    TextView txv;
    EditText et;
    TextView name_input;
    EditText name_input_et;
    String value;
    boolean haveIBeenClicked;
    String CID = "";
    String cname = "";
    String names = "";
    String formattedDate = "";
    int mytargets = 0;
    Button targett;
Button lt;
    DatabaseReference reference;
    String counting ="";
    String target="";
    Button save;
    Button open;
long maxId;
    private MusicIntentReceiver myReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseApp.initializeApp(this);
      // FirebaseDatabase.getInstance().setPersistenceEnabled(true);

        myReceiver = new MusicIntentReceiver();
         audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        audioManager.setMode(AudioManager.MODE_IN_COMMUNICATION);
        audioManager.setSpeakerphoneOn(true);


        if (getIntent().hasExtra("lang_code")){
          String lang_code = getIntent().getStringExtra("lang_code");
            Settings.setLocale(this, lang_code);
        }



        prefs = getSharedPreferences("auto.tasbeeh.data", MODE_PRIVATE);
        String strPref = prefs.getString("count", null);
        String strPref2 = prefs.getString("cname", null);
        String strPref3 = prefs.getString("tget", null);

        FirebaseDatabase rootNode = FirebaseDatabase.getInstance();
        reference = rootNode.getReference("MyDigitalCounter");


        et = findViewById(R.id.uput);
        cnt = findViewById(R.id.count);
        txv = findViewById(R.id.txt);
        save=findViewById(R.id.save);
        targett = findViewById(R.id.target);
        name_input = findViewById(R.id.count_name);
        name_input_et = findViewById(R.id.count_name_et);
        lt = findViewById(R.id.light);

        if (strPref != null && strPref2 != null && strPref3!=null) {
            txv.setText(prefs.getString("count", "0"));
            name_input.setText(prefs.getString("cname", "Default"));
            targett.setText(prefs.getString("tget", "0"));
            value = txv.getText().toString();
            int mr = Integer.parseInt(value);
            txv.setText(String.valueOf(mcounter = mr));
        }

        if (getIntent().hasExtra("cID") && getIntent().hasExtra("cName") && getIntent().hasExtra("counts") ) {

            CID = getIntent().getStringExtra("cID");
            cname = getIntent().getStringExtra("cName");
            name_input.setText(cname);
            name_input_et.setText(cname);
            mcounter = Integer.parseInt(getIntent().getStringExtra("counts"));
            txv.setText(String.valueOf(mcounter));
            et.setText(String.valueOf(mcounter));
            mytargets = Integer.parseInt(getIntent().getStringExtra("tcounts"));
            targett.setText("Target: "+ mytargets);
        }

        open=findViewById(R.id.open);
        // viewConst user = new viewConst(CID, cname, counting,formattedDate, target);

        open.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(MainActivity.this, userlist.class);
                startActivity(i);
                //finish();


            }
        });

        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        formattedDate = df.format(c);
        targett.setBackgroundColor(Color.GREEN);


    }


    public void showPopup(View v) {
        PopupMenu popup = new PopupMenu(this, v);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.counter_menu, popup.getMenu());
        popup.show();
    }

    // @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.item1:
                Intent intent = new Intent(getBaseContext(), Login.class);
                startActivity(intent);
                finish();
            case R.id.item2:
                resets();
                return true;
            case R.id.item3:

                Intent i = new Intent(getApplicationContext(), Settings.class);

                startActivity(i);

                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }


    public void play(View view) {


        if (mytargets == 0) {
            mcounter++;
            txv.setText(String.valueOf(mcounter));
        }


        else {
            mcounter++;
            txv.setText(String.valueOf(mcounter));

            if (mcounter >= mytargets){
                cnt.setEnabled(false);
                targett.setBackgroundColor(ContextCompat.getColor(this, R.color.y));
                Toast.makeText(this, "target filled up", Toast.LENGTH_SHORT).show();
                ToneGenerator toneGen1 = new ToneGenerator(AudioManager.STREAM_MUSIC, 100);
                boolean b = toneGen1.startTone(ToneGenerator.TONE_CDMA_PIP, 150);
                ArrayList<User> list;
                String count="0";

                User helperClass = new User(CID, cname, count, formattedDate, String.valueOf(mytargets));
                reference.child(cname).setValue(helperClass);

                    Intent i = new Intent(getApplicationContext(), userlist.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(i);

            }



        }
    }




    public void edits(View view) {

        // Button ed = (Button) findViewById(R.id.edit);

        txv.setVisibility(txv.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);
        et.setVisibility(et.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);

        int mr = Integer.parseInt(et.getText().toString());

        et.setText((value = String.valueOf(mcounter)));
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

    public void saves(View view) {
        counting = String.valueOf(mcounter).trim();

        target = String.valueOf(mytargets);


        reference.child(cname)
                .equalTo(name_input.getText().toString())
                .addValueEventListener(new ValueEventListener(){
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        if (dataSnapshot.hasChild(cname)){
                            // maxId=dataSnapshot.getChildrenCount();
                            Toast.makeText(MainActivity.this, "name already exists", Toast.LENGTH_SHORT).show();

                        }
                      //  reference.child(String.valueOf(maxId+1)).setvalue();
                        else {

                                writeNewUser();

                                Toast.makeText(MainActivity.this, "success to insert", Toast.LENGTH_SHORT).show();

                        }
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Toast.makeText(MainActivity.this, "cancel", Toast.LENGTH_SHORT).show();
                    }
                });


    }



    public void writeNewUser() {
        cname=name_input.getText().toString();
        counting=txv.getText().toString();
        target=String.valueOf(mytargets);


        User helperClass = new User(CID, cname, counting, formattedDate, target);

            reference.child(cname).setValue(helperClass);

    }

    public void lt(View view) { //light
        haveIBeenClicked=!haveIBeenClicked;
        if (haveIBeenClicked) {
            et.setTextColor(getResources().getColor(R.color.y));
            txv.setTextColor(getResources().getColor(R.color.y));
            cnt.setTextColor(getResources().getColor(R.color.y));
            LinearLayout layout = findViewById(R.id.lb);
            layout.setBackgroundResource(R.drawable.bl);
        } else {
            et.setTextColor(getResources().getColor(R.color.b));
            txv.setTextColor(getResources().getColor(R.color.b));
            cnt.setTextColor(getResources().getColor(R.color.b));
            LinearLayout layout = findViewById(R.id.lb);
            layout.setBackgroundResource(R.drawable.bk);

        }
    }



    @Override
    protected void onPause() {
        super.onPause();
        value = txv.getText().toString();
        cname=name_input.getText().toString();
        String tgt=targett.getText().toString();
        prefs.edit().putString("count", value).apply();
        prefs.edit().putString("cname", cname).apply();
        prefs.edit().putString("tget", tgt).apply();

    }



    @Override public void onResume() {
        IntentFilter filter = new IntentFilter(Intent.ACTION_HEADSET_PLUG);
        registerReceiver(myReceiver, filter);
        super.onResume();
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
                        Log.d(TAG, "Headset is unplugged");
                        break;
                    case 1:
                        Log.d(TAG, "Headset is plugged");
                        break;
                    default:
                        Log.d(TAG, "I have no idea what the headset state is");
                }
            }
        }
    }


    @Override  //headphone count
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_HEADSETHOOK) {
            cnt.setClickable(false);
            targett.setClickable(false);
            lt.setClickable(false);
            //audioManager.setSpeakerphoneOn(false);

          // audioManager.setMode(AudioManager.MODE_IN_CALL);
            audioManager.setSpeakerphoneOn(true);

            AudioManager mAudioMgr = (AudioManager)getSystemService(Context.AUDIO_SERVICE);
            mAudioMgr.setSpeakerphoneOn(true);
            mAudioMgr.setMode(AudioManager.MODE_IN_COMMUNICATION);

            //handle click
            if (mytargets == 0) {
                mcounter++;
                txv.setText(String.valueOf(mcounter));
            }

            else {
                mcounter++;
                txv.setText(String.valueOf(mcounter));

                if (mcounter >= mytargets) {


                    cnt.setEnabled(false);
                    targett.setBackgroundColor(ContextCompat.getColor(this, R.color.y));
                    Toast.makeText(this, "target filled up", Toast.LENGTH_SHORT).show();

                    ToneGenerator toneGen1 = new ToneGenerator(AudioManager.STREAM_MUSIC, 500);
                    boolean b= toneGen1.startTone(ToneGenerator.TONE_CDMA_PIP, 150);

                    String count="0";

                    User helperClass = new User(CID, cname, count, formattedDate, String.valueOf(mytargets));
                    reference.child(cname).setValue(helperClass);

                    Intent i = new Intent(getApplicationContext(), userlist.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(i);

                }
            }

            return true;
        }
        return super.onKeyDown(keyCode, event);
    }



    public void target_method(View view){

        AlertDialog.Builder alert2 = new AlertDialog.Builder(MainActivity.this);
        final EditText editText3 = new EditText(MainActivity.this);
        alert2.setTitle("Set Target Limit");
        alert2.setView(editText3);

        alert2.setPositiveButton(R.string.set, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface param2DialogInterface, int param2Int) {

                mytargets= Integer.parseInt(editText3.getText().toString());
                targett.setText("Target: "+editText3.getText().toString()); //will work by save button


            }
        })
                .setNegativeButton(R.string.remove, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface param2DialogInterface, int param2Int) {

                        reference.child(cname)
                                .equalTo(name_input.getText().toString())
                                .addValueEventListener(new ValueEventListener(){
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {

                                        if (dataSnapshot.hasChild(cname)){
                                            // maxId=dataSnapshot.getChildrenCount();
                                            Toast.makeText(MainActivity.this, "name already exists", Toast.LENGTH_SHORT).show();

                                        }
                                        //  reference.child(String.valueOf(maxId+1)).setvalue();
                                        else {


if(mytargets!=0){
   String count="0";
 String mytarget="0";
     User helperClass = new User(CID, cname, count, formattedDate, mytarget);

  reference.child(cname).setValue(helperClass);
   mytargets=0;
 mcounter=0;
     targett.setText("Target: "+String.valueOf(mytargets));
     txv.setText(String.valueOf(mcounter));


                                            Toast.makeText(MainActivity.this, "success to update", Toast.LENGTH_SHORT).show();

                                        }
                                        }


                                    }
                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {
                                        Toast.makeText(MainActivity.this, "cancel", Toast.LENGTH_SHORT).show();
                                    }

                                });

                    }

                })
                .setNeutralButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface param2DialogInterface, int param2Int) {

                        Toast.makeText(MainActivity.this, "Cancel", Toast.LENGTH_LONG).show();
                        param2DialogInterface.cancel();

                    }

                });

        alert2.create().show();

    }


    public void resets() {
        mcounter = 0;
        mytargets=0;
        txv.setText(String.valueOf(mcounter));
        // String st_count_name=getResources().getString(R.string.reset);
        name_input.setText(R.string.default_title);
        if(!CID.equals("")){
            CID="";
        }

        targett.setText("Target: "+ String.valueOf(0));

    }



}

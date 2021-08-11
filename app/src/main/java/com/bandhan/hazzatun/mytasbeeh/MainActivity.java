package com.bandhan.hazzatun.mytasbeeh;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.ToneGenerator;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.view.KeyEvent;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;



public class MainActivity extends AppCompatActivity {
    //  private static final String FILE_NAME = "exampleTasbeeh.txt";
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

    DatabaseReference reference;
     String counting ="";
String target="";
Button save;
    Button open;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        FirebaseApp.initializeApp(this);
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);


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
            targett.setText(R.string.target+ mytargets);
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

                    if (dataSnapshot.exists()){
                       // maxId=dataSnapshot.getChildrenCount();
                        Toast.makeText(MainActivity.this, "name already exists", Toast.LENGTH_SHORT).show();

                    }

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



       // Intent intent = new Intent(getBaseContext(), userlist.class);
      //  intent.putExtra("EXTRA_SESSION_ID", formattedDate);
//startActivity(intent);
    }



    public void writeNewUser() {
        cname=name_input.getText().toString();
        counting=txv.getText().toString();
        target=String.valueOf(mytargets);

                User helperClass = new User(CID, cname, counting, formattedDate, target);

              // reference.child("Id").setValue(helperClass);
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


    @Override  //headphone count
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_HEADSETHOOK) {

            //handle click
cnt.setClickable(false);
targett.setClickable(false);

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
                    ToneGenerator toneGen1 = new ToneGenerator(AudioManager.STREAM_MUSIC, 100);
                    boolean b = toneGen1.startTone(ToneGenerator.TONE_CDMA_PIP, 150);

                    String count = "0";
                    // String myterget="0";

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
            targett.setText(editText3.getText().toString()); //will work by save button


        }
    })
    .setNegativeButton(R.string.remove, new DialogInterface.OnClickListener() {
        public void onClick(DialogInterface param2DialogInterface, int param2Int) {

            String count="0";
            String mytarget="0";


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
        // Button ret = findViewById(R.id.reset);
        txv.setText(String.valueOf(mcounter = 0));
       // String st_count_name=getResources().getString(R.string.reset);
        name_input.setText(R.string.default_title);
        if(!CID.equals("")){
            CID="";
        }

        targett.setText(R.string.reset+ ": "+0);
        targett.setClickable(false);

    }





}





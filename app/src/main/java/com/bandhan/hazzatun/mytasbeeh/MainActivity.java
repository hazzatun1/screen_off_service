package com.bandhan.hazzatun.mytasbeeh;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.ToneGenerator;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
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

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;



public class MainActivity extends AppCompatActivity {
    //  private static final String FILE_NAME = "exampleTasbeeh.txt";
    Database db;
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = new Database(this);
        prefs = getSharedPreferences("auto.tasbeeh.data", MODE_PRIVATE);
        String strPref = prefs.getString("count", null);
        String strPref2 = prefs.getString("cname", null);
        String strPref3 = prefs.getString("tget", null);


        et = findViewById(R.id.uput);
        cnt = findViewById(R.id.count);
        txv = findViewById(R.id.txt);

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

        if (getIntent().hasExtra("cID") && getIntent().hasExtra("cName") && getIntent().hasExtra("counts") && getIntent().hasExtra("tcounts")) {

            CID = getIntent().getStringExtra("cID");
            cname = getIntent().getStringExtra("cName");
            name_input.setText(cname);
            name_input_et.setText(cname);
            mcounter = Integer.parseInt(getIntent().getStringExtra("counts"));
            txv.setText(String.valueOf(mcounter));
            et.setText(String.valueOf(mcounter));
            mytargets = Integer.parseInt(getIntent().getStringExtra("tcounts"));
            targett.setText("Target: " + mytargets);
        }



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
                Intent i = new Intent(MainActivity.this, Settings.class);
                MainActivity.this.startActivity(i);
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

                String count="0";
               // String myterget="0";
                boolean istInsert = db.updTarget(CID, cname, count, formattedDate, String.valueOf(mytargets));
if(istInsert){
    Intent i = new Intent(getApplicationContext(), open_page.class);
    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
    startActivity(i);
}

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
        String countName1 = name_input_et.getText().toString().trim();
        name_input_et = findViewById(R.id.count_name_et);
        String count = String.valueOf(mcounter).trim();
        boolean isInsert;
        boolean updt;


        if (CID.equals("")) {

           String taget= String.valueOf(mytargets);
           if(mytargets!=0) {
               isInsert = db.addName(countName1, count, formattedDate, taget);

               if (isInsert)
                   Toast.makeText(MainActivity.this, "new Data inserted", Toast.LENGTH_LONG).show();
               else
                   Toast.makeText(MainActivity.this, "Name already exists", Toast.LENGTH_LONG).show();
           }

else{

    String cont=txv.getText().toString();
               isInsert = db.addName(countName1, cont, formattedDate, taget);

               if (isInsert)
                   Toast.makeText(MainActivity.this, "new Data inserted", Toast.LENGTH_LONG).show();
               else
                   Toast.makeText(MainActivity.this, "Name already exists", Toast.LENGTH_LONG).show();
           }
  }

        else {
            String taget= String.valueOf(mytargets);
            updt = db.updTarget(CID, cname, count, formattedDate, taget);
            if (updt)
                Toast.makeText(MainActivity.this, "Existing data updated", Toast.LENGTH_LONG).show();
            else
                Toast.makeText(MainActivity.this, "Existing data not updated", Toast.LENGTH_LONG).show();
        }


        Intent intent = new Intent(getBaseContext(), open_page.class);
        intent.putExtra("EXTRA_SESSION_ID", formattedDate);


    }

    public void viewAll(View view) {

        Intent intent3 = new Intent(this,open_page.class);
        intent3.putExtra("countName", names);
        intent3.putExtra("counts", mcounter);
        startActivity(intent3);

                    }


        public void lt(View view) { //light
            haveIBeenClicked=!haveIBeenClicked;
          //  Button lt = findViewById(R.id.light);
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
                    boolean istInsert = db.updTarget(CID, cname, count, formattedDate, String.valueOf(mytargets));
                    if (istInsert) {
                        Intent i = new Intent(getApplicationContext(), open_page.class);
                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(i);
                    }
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

    alert2.setPositiveButton("Set", new DialogInterface.OnClickListener() {
        public void onClick(DialogInterface param2DialogInterface, int param2Int) {

            mytargets= Integer.parseInt(editText3.getText().toString());
            targett.setText("TARGET: " + editText3.getText().toString()); //will work by save button


        }
    })
    .setNegativeButton("remove", new DialogInterface.OnClickListener() {
        public void onClick(DialogInterface param2DialogInterface, int param2Int) {


            String count="0";
            String myterget="0";
            boolean istInsert = db.updTarget(CID, cname, count, formattedDate, myterget);

            if (istInsert) {
                Toast.makeText(MainActivity.this, "target removed", Toast.LENGTH_LONG).show();

                Intent i = new Intent(getApplicationContext(), open_page.class);
                startActivity(i);
                finish();


            }



        }

    })
            .setNeutralButton("cancel", new DialogInterface.OnClickListener() {
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
        name_input.setText(R.string.default_title);
        if(!CID.equals("")){
            CID="";
        }
        targett.setText("Target: "+0);
        targett.setClickable(false);

    }




}





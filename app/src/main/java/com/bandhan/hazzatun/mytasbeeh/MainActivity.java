package com.bandhan.hazzatun.mytasbeeh;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;



public class MainActivity extends AppCompatActivity{
  //  private static final String FILE_NAME = "exampleTasbeeh.txt";
  Database db;
    private int mcounter = 0;
    private SharedPreferences prefs;
    Button cnt;
    TextView txv;
    EditText et;
    String value;
    boolean haveIBeenClicked;

    private ListView lv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


         db = new Database(getApplicationContext());
        prefs = getSharedPreferences("auto.tasbeeh.data", MODE_PRIVATE);
        String strPref = prefs.getString("count", null);
        et = (EditText) findViewById(R.id.uput);
        cnt = (Button) findViewById(R.id.count);
        txv = (TextView) findViewById(R.id.txt);

        if (strPref != null) {
            txv.setText(prefs.getString("count", "0"));
            value = txv.getText().toString();
            int mr = Integer.parseInt(value);
            txv.setText(String.valueOf(mcounter = mr));

        }


           // mcounter = Integer.parseInt(getIntent().getStringExtra("counts"));
           // txv.setText(String.valueOf(mcounter));
        if(getIntent().hasExtra("counts")){
          mcounter=  Integer.parseInt(getIntent().getStringExtra("counts"));
            txv.setText(String.valueOf(mcounter));
        }

    }

    public void play(View view) {
        mcounter++;
        txv.setText(String.valueOf(mcounter));
    }

    public void resets(View view) {
        Button ret = (Button) findViewById(R.id.reset);
        txv.setText(String.valueOf(mcounter = 0));
    }

    public void edits(View view) {

       // Button ed = (Button) findViewById(R.id.edit);

        txv.setVisibility(txv.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);
        et.setVisibility(et.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);

        value = et.getText().toString();
        int mr = Integer.valueOf(value);
        if (value != String.valueOf(0)) {
            et.setText((value = String.valueOf(mcounter)));
        }

        txv.setText(String.valueOf(mcounter = mr));
    }




    public void saves(View view) {

        //    Button sv = (Button) findViewById(R.id.save);

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);

        final EditText input = new EditText(MainActivity.this);

        alertDialog.setTitle("Save as")

                .setMessage("Enter Name")
                .setView(input)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        String countName = input.getText().toString().trim();
                        String count = String.valueOf(mcounter);

                       // boolean isInsert=db.addName(countName, count );
                        boolean isInsert = false;
                        if(null != db) {
                            isInsert = db.addName(countName, count );
                        }

            if(isInsert==true)
                Toast.makeText(MainActivity.this,"Data inserted", Toast.LENGTH_LONG).show();
            else
                Toast.makeText(MainActivity.this,"Data not inserted", Toast.LENGTH_LONG).show();
                    }
                })

                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String countName = input.getText().toString().trim();
                        String count = String.valueOf(mcounter);
                        Toast.makeText(getApplicationContext(), "No Happened", Toast.LENGTH_LONG).show();

                    }
                })
                .setNeutralButton("Exists", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface param2DialogInterface, int param2Int) {

                        Toast.makeText((Context)MainActivity.this, "Cancel", Toast.LENGTH_LONG).show();

                        param2DialogInterface.cancel();
                    }
                })
                .show();

    }




    public void viewAll(View view) {

        Intent intent3=new Intent(this,open_page.class);
        intent3.putExtra("counts",mcounter);
        startActivity(intent3);



                    }






        public void lt(View view) { //light
            haveIBeenClicked=!haveIBeenClicked;
          //  Button lt = findViewById(R.id.light);
            if(haveIBeenClicked){
                et.setTextColor(getResources().getColor(R.color.y));
                txv.setTextColor(getResources().getColor(R.color.y));
                cnt.setTextColor(getResources().getColor(R.color.y));
                LinearLayout layout =(LinearLayout)findViewById(R.id.lb);
                layout.setBackgroundResource(R.drawable.bl);
            }
            else{
                et.setTextColor(getResources().getColor(R.color.b));
                txv.setTextColor(getResources().getColor(R.color.b));
                cnt.setTextColor(getResources().getColor(R.color.b));
                LinearLayout layout =(LinearLayout)findViewById(R.id.lb);
                layout.setBackgroundResource(R.drawable.bk);

            }
        }
        @Override
        protected void onPause() {
            super.onPause();
            value=txv.getText().toString();
            prefs.edit().putString("count",value).apply();
        }

    public void showMessage(String title, String message){

        AlertDialog.Builder builder= new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }


}



package com.bandhan.hazzatun.mytasbeeh;

import android.content.DialogInterface;

import android.content.Intent;
import android.content.SharedPreferences;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

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
    TextView text;
    EditText et;
    TextView name_input;
    EditText name_input_et;
    String value;
    boolean haveIBeenClicked;
String CID = "";
String cname="";
    String names="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);





         db = new Database(this);
        prefs = getSharedPreferences("auto.tasbeeh.data", MODE_PRIVATE);
        String strPref = prefs.getString("count", null);
        et = (EditText) findViewById(R.id.uput);
        cnt = (Button) findViewById(R.id.count);
        txv = (TextView) findViewById(R.id.txt);
       // text = (TextView) findViewById(R.id.name);

         name_input = (TextView) findViewById(R.id.count_name);
         name_input_et = (EditText) findViewById(R.id.count_name_et);


        if (strPref != null) {
            txv.setText(prefs.getString("count", "0"));
            value = txv.getText().toString();
            int mr = Integer.parseInt(value);
            txv.setText(String.valueOf(mcounter = mr));

        }

        if(getIntent().hasExtra("counts") && getIntent().hasExtra("cID")&& getIntent().hasExtra("cName") ){
          mcounter=  Integer.parseInt(getIntent().getStringExtra("counts"));
            txv.setText(String.valueOf(mcounter));
            CID=getIntent().getStringExtra("cID");
            cname=getIntent().getStringExtra("cName");
            name_input.setText(cname);
            name_input_et.setText(cname);
        }

    }



    public void play(View view) {
        mcounter++;
        txv.setText(String.valueOf(mcounter));
    }

    public void resets(View view) {
        Button ret = (Button) findViewById(R.id.reset);
        txv.setText(String.valueOf(mcounter = 0));
        name_input.setText("default");

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


        name_input.setVisibility(name_input.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);
        name_input_et.setVisibility(name_input_et.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);

if(name_input_et.getVisibility() == View.VISIBLE) {
    names = name_input.getText().toString();
    name_input_et.setText(names);
}
  else {
    names = name_input_et.getText().toString();
    name_input.setText(names);
}

    }




    public void saves(View view) {


        String countName = name_input.getText().toString().trim();
        String count = String.valueOf(mcounter).trim();
        String isData = db.ifExists(countName);
        boolean isInsert = db.addName(countName, count);
        boolean cName = db.updateNewData(CID, countName, count);


            // boolean cupdate = db.updateCount(CID, count);
            if (cName == true) {

                Toast.makeText(MainActivity.this, "Existing data updated", Toast.LENGTH_LONG).show();
            }
            else if (cName == false) {
                Toast.makeText(MainActivity.this, "Existing data not updated", Toast.LENGTH_LONG).show();
            }



            else if (isInsert == true)
                Toast.makeText(MainActivity.this, "New Data inserted", Toast.LENGTH_LONG).show();
            else if (isInsert == false)
                Toast.makeText(MainActivity.this, "Something Error", Toast.LENGTH_LONG).show();
                 }





    public void viewAll(View view) {

        Intent intent3=new Intent(this,open_page.class);
        intent3.putExtra("countName", names);
        intent3.putExtra("counts", mcounter);
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



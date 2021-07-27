package com.bandhan.hazzatun.mytasbeeh;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
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
    TextView name_input;
    EditText name_input_et;
    String value;
    boolean haveIBeenClicked;
    String CID = "";
    String cname= "";
    String names= "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = new Database(this);
        prefs = getSharedPreferences("auto.tasbeeh.data", MODE_PRIVATE);
        String strPref = prefs.getString("count", null);
        et = findViewById(R.id.uput);
        cnt = findViewById(R.id.count);
        txv = findViewById(R.id.txt);
        // text = (TextView) findViewById(R.id.name);

        name_input = findViewById(R.id.count_name);
        name_input_et = findViewById(R.id.count_name_et);


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
        Button ret = findViewById(R.id.reset);
        txv.setText(String.valueOf(mcounter = 0));
        name_input.setText("default");
        if(!CID.equals("")){
            CID="";
        }


    }

    public void edits(View view) {

       // Button ed = (Button) findViewById(R.id.edit);

        txv.setVisibility(txv.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);
        et.setVisibility(et.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);

        value = et.getText().toString();
        int mr = Integer.parseInt(value);
        if (!value.equals(String.valueOf(0))) {
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
        boolean isInsert;
        boolean updt;
//condition ? exprIfTrue : exprIfFalse


        if(CID.equals("")) {

            boolean isDataCount = db.updateCount(CID, count);
            boolean isDataName = db.updateData(CID, countName);


          if(isDataName || isDataCount) {

                isInsert = db.addName(countName, count);

                if (isInsert) Toast.makeText(MainActivity.this, "new Data inserted", Toast.LENGTH_LONG).show();
                else Toast.makeText(MainActivity.this, "data not inserted", Toast.LENGTH_LONG).show();
            }

            else {
                updt = db.updateNewData(CID, countName, count);

                if (updt) Toast.makeText(MainActivity.this, "Data updated", Toast.LENGTH_LONG).show();
                else Toast.makeText(MainActivity.this, "Data not updated", Toast.LENGTH_LONG).show();
           }

        }

        else {
            updt = db.updateNewData(CID, countName, count);
            if (updt) Toast.makeText(MainActivity.this, "Existing data updated", Toast.LENGTH_LONG).show();
            else Toast.makeText(MainActivity.this, "Existing data not updated", Toast.LENGTH_LONG).show();
    }

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
        prefs.edit().putString("count", value).apply();
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_HEADSETHOOK) {

            //handle click
            int k = mcounter;
            k++;
            mcounter = k;
            txv.setText(String.valueOf(mcounter));

            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}



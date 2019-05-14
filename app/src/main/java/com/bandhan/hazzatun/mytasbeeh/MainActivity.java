package com.bandhan.hazzatun.mytasbeeh;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    private static final String FILE_NAME = "exampleTasbeeh.txt";
    private int mcounter=0;
    private SharedPreferences prefs;
    Button cnt;
    TextView txv;
    EditText et;
    String value;
    boolean haveIBeenClicked;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



            prefs = getSharedPreferences("auto.tasbeeh.data", MODE_PRIVATE);
            String strPref = prefs.getString("count", null);
            et = (EditText) findViewById(R.id.uput);
            cnt = (Button) findViewById(R.id.count);
            txv = (TextView) findViewById(R.id.txt);

            if (strPref != null) {
                txv.setText(prefs.getString("count", ""));
                value = txv.getText().toString();
                int mr = Integer.parseInt(value);
                txv.setText(String.valueOf(mcounter = mr));
            }
        }

        public void play(View view) {
            mcounter ++;
            txv.setText(String.valueOf(mcounter));
        }

        public void resets(View view) {
            Button ret = (Button) findViewById(R.id.reset);
            txv.setText(String.valueOf(mcounter = 0));
        }

        public void edits(View view) {

            Button ed = (Button) findViewById(R.id.edit);

            txv.setVisibility(txv.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);
            et.setVisibility(et.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);

            value=et.getText().toString();
            int mr = Integer.valueOf(value);
            if(value != String.valueOf(0)){
                et.setText((value = String.valueOf(mcounter)));
            }

            txv.setText(String.valueOf(mcounter = mr));
        }

        public void saves(View view) {
        //    Button sv = (Button) findViewById(R.id.save);


            String text = et.getText().toString();
            FileOutputStream fos = null;

            try {
                fos = openFileOutput(FILE_NAME, MODE_PRIVATE);
                fos.write(text.getBytes());

                et.getText().clear();
                Toast.makeText(this, "Saved to " + getFilesDir() + "/" + FILE_NAME,
                        Toast.LENGTH_LONG).show();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (fos != null) {
                    try {
                        fos.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }


        public void lt(View view) {
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

        public void view(View view) {

        }
    }


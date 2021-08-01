package com.bandhan.hazzatun.mytasbeeh;


import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;

import android.graphics.Color;
import android.os.Bundle;
import android.provider.AlarmClock;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.telecom.Call;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


public class open_page extends AppCompatActivity {

    Database db;
    CustomAdapter data;
    ListView lv;
    viewConst dataModel;
    String countId;
    TextView date;
    String formattedDate = "";
    String cname = "";
      String target = "";
      String count = "";


    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_page);


        date = findViewById(R.id.date);


        db = new Database(this);
        lv = findViewById(R.id.list1);

        final ArrayList<viewConst> contacts = new ArrayList<>(db.getUser());
        data = new CustomAdapter(getApplicationContext(), contacts);

        fillListView();

    }





    private void fillListView() {


        lv.setItemsCanFocus(true);
        lv.setAdapter(data);
       // lv.getAdapter().getItemAt(position);

        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            //@SuppressLint("ResourceAsColor")
            @Override
            public boolean onItemLongClick(final AdapterView<?> arg0, View view,
                                           final int pos, long id) {

                dataModel = data.getItem(pos);

                assert dataModel != null;

                AlertDialog.Builder builder = new AlertDialog.Builder(open_page.this);

                builder.setTitle("Delete the zikr");

                builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface param2DialogInterface, int param2Int) {


                       String data_del= dataModel.get_id();

                        boolean del = db.deleteName(data_del);
                        if (del) {
                            Toast.makeText(open_page.this, "deleted", Toast.LENGTH_LONG).show();

                            recreate();

                        } else
                            Toast.makeText(open_page.this, "failed to delete", Toast.LENGTH_LONG).show();

                    }

                })

                .setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface param2DialogInterface, int param2Int) {
                        Toast.makeText(open_page.this, "Cancel", Toast.LENGTH_LONG).show();
                        param2DialogInterface.cancel();
                    }
                });
                builder.create().show();

                return true;

            }

        });

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                dataModel = data.getItem(position);
                assert dataModel != null; // assert ki
                count = dataModel.get_counts();
                countId = dataModel.get_id();
                cname = dataModel.get_name();
                target = dataModel.get_target();

                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                i.putExtra("cID", countId);
                i.putExtra("cName", cname);
                i.putExtra("counts", count);
                i.putExtra("tcounts", target);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);

            }
        });


    }




}
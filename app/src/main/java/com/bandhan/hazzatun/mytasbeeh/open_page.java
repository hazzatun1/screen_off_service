package com.bandhan.hazzatun.mytasbeeh;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class open_page extends AppCompatActivity {

    Database db;
    private CustomAdapter data;
    private ListView lv;
    private viewConst dataModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_page);

        //Get the bundle
        //   Bundle bundle = getIntent().getExtras();

//Extract the data…
        //   String counts = bundle.getString("user");

        db = new Database(this);
        lv = (ListView) findViewById(R.id.list1);
        final ArrayList<viewConst> contacts = new ArrayList<>(db.getUser());
        data=new CustomAdapter(getApplicationContext(), contacts);

        fillListView();
    }


    private void fillListView() {


        lv.setItemsCanFocus(true);
        lv.setAdapter(data);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

               // final String selected =(String) (lv.getItemAtPosition(position));
                AlertDialog.Builder builder = new AlertDialog.Builder((Context)open_page.this);
                EditText editText = new EditText((Context)open_page.this);
                builder.setTitle("Donate free or enter amount");
                builder.setMessage("Enter demanded amount")
                        .setCancelable(false).setView((View)editText)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface param2DialogInterface, int param2Int) {

                        Toast.makeText((Context)open_page.this, "hello", Toast.LENGTH_LONG).show();
                        param2DialogInterface.cancel();
                    }
                }).setNegativeButton("Free Donate", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface param2DialogInterface, int param2Int) {
                        Toast.makeText((Context)open_page.this, "nagative button", Toast.LENGTH_LONG).show();
                        param2DialogInterface.cancel();
                    }
                }).setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface param2DialogInterface, int param2Int) {
                        Toast.makeText((Context)open_page.this, "Cancel", Toast.LENGTH_LONG).show();
                        param2DialogInterface.cancel();
                    }
                });
                builder.create().show();
            }
        });

    }
    public void updonate(){

    }
    public void resume(View view){

        Toast.makeText(this,"hai",Toast.LENGTH_SHORT).show();



    }


}